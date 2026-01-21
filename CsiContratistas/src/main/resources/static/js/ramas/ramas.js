document.addEventListener('DOMContentLoaded', function () {

    // --- Funciones de validación general ---
    function validateField(input) {
        if (input.checkValidity()) {
            input.classList.remove('is-invalid');
            input.classList.add('is-valid');
            const feedback = input.nextElementSibling;
            if (feedback && feedback.classList.contains('invalid-feedback')) {
                feedback.textContent = '';
            }
            return true;
        } else {
            input.classList.add('is-invalid');
            input.classList.remove('is-valid');
            const feedback = input.nextElementSibling;
            if (feedback && feedback.classList.contains('invalid-feedback')) {
                feedback.textContent = input.validationMessage;
            }
            return false;
        }
    }

    function setupFormValidation(formSelector) {
        const form = document.querySelector(formSelector);
        if (form) {
            form.querySelectorAll('input, select, textarea').forEach(input => { // Incluye textarea
                input.addEventListener('input', () => validateField(input));
                input.addEventListener('blur', () => validateField(input));
            });

            form.addEventListener('submit', (event) => {
                let allValid = true;
                form.querySelectorAll('[required]').forEach(input => {
                    if (!validateField(input)) {
                        allValid = false;
                    }
                });
                if (!allValid) {
                    event.preventDefault();
                    form.querySelector('.is-invalid')?.focus();
                }
            });
        }
    }

    // --- Inicializar validación para los formularios de Ramas ---
    setupFormValidation('#modalNuevaRama form');
    setupFormValidation('#modalEditarRama form');

    // --- Funcionalidad para rellenar el modal de edición de Rama ---
    const editRamaModal = document.getElementById('modalEditarRama');
    if (editRamaModal) {
        editRamaModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const idRama = button.dataset.id_rama;
            const nombre = button.dataset.nombre;
            const descripcion = button.dataset.descripcion;
            const idProfesion = button.dataset.id_profesion;
            const estado = button.dataset.estado === 'true';

            document.getElementById('editarIdRama').value = idRama;
            document.getElementById('editarRamaNombre').value = nombre;
            document.getElementById('editarRamaDescripcion').value = descripcion;
            const editEstadoRamaSelect = document.getElementById('editarRamaEstado');
            if (editEstadoRamaSelect) {
                editEstadoRamaSelect.value = String(estado);
            }

            const selectProfesion = document.getElementById('editarRamaProfesion');
            if (selectProfesion) {
                selectProfesion.value = idProfesion || '';
            }
        });
    }

    // --- Funcionalidad para activar/desactivar Rama con SweetAlert2 ---
    document.querySelectorAll('.btn-desactivar-rama, .btn-activar-rama').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const ramaId = this.dataset.id_rama;
            const isActivar = this.classList.contains('btn-activar-rama');

            let title = '';
            let text = '';
            let confirmButtonText = '';
            let formAction = '';

            if (isActivar) {
                title = '¿Activar esta rama?';
                text = "La rama volverá a estar activa.";
                confirmButtonText = 'Sí, activar';
                formAction = `/administrador/ramas/activar`;
            } else { // Es desactivar
                title = '¿Desactivar esta rama?';
                text = "La rama ya no estará activa.";
                confirmButtonText = 'Sí, desactivar';
                formAction = `/administrador/ramas/desactivar`;
            }

            Swal.fire({
                title: title,
                text: text,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: confirmButtonText,
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = document.createElement('form');
                    form.method = 'POST';
                    form.action = formAction;

                    const idInput = document.createElement('input');
                    idInput.type = 'hidden';
                    idInput.name = 'id_rama';
                    idInput.value = ramaId;
                    form.appendChild(idInput);

                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });
    });

    // --- Funcionalidad para eliminar Rama con SweetAlert2 (si se añade eliminar) ---
    document.querySelectorAll('.btn-eliminar-rama').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const ramaId = this.dataset.id_rama;
            Swal.fire({
                title: '¿Estás seguro?',
                text: "¡Esta acción desactivará la rama! Podrás reactivarla después.",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Sí, desactivar!',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = document.createElement('form');
                    form.method = 'POST';
                    form.action = `/administrador/ramas/eliminar`;
                    const idInput = document.createElement('input');
                    idInput.type = 'hidden';
                    idInput.name = 'id_rama';
                    idInput.value = ramaId;
                    form.appendChild(idInput);
                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });
    });

    // --- Lógica de Filtros y Búsqueda (Se envía al backend con la paginación) ---
    const filterRamasForm = document.getElementById('filterRamasForm');
    const clearRamasFiltersBtn = document.getElementById('clearRamasFilters');

    if (clearRamasFiltersBtn) {
        clearRamasFiltersBtn.addEventListener('click', function () {
            document.getElementById('searchRama').value = '';
            document.getElementById('filterEstadoRama').value = '';
            document.getElementById('filterProfesionRama').value = '';

            filterRamasForm.submit();
        });
    }


    // --- Descarga de Reportes PDF para Ramas ---
    const downloadRamasPdfBtn = document.getElementById('downloadRamasPdfBtn');
    if (downloadRamasPdfBtn) {
        downloadRamasPdfBtn.addEventListener('click', () => {
            Swal.fire({
                title: 'Generando PDF...',
                text: 'Por favor, espere mientras se genera el reporte de ramas.',
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });

            fetch('/administrador/ramas/reporte/pdf')
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => { throw new Error(text || 'Error desconocido al generar el PDF de ramas'); });
                    }
                    return response.blob();
                })
                .then(blob => {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    const today = new Date();
                    const year = today.getFullYear();
                    const month = String(today.getMonth() + 1).padStart(2, '0');
                    const day = String(today.getDate()).padStart(2, '0');
                    a.download = `reporte_ramas_${year}${month}${day}.pdf`;
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(url);
                    Swal.fire({
                        icon: 'success',
                        title: 'PDF Generado',
                        text: 'El reporte PDF de ramas ha sido descargado exitosamente.',
                        confirmButtonText: 'Aceptar'
                    });
                })
                .catch(error => {
                    console.error('Error al generar PDF:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error al generar PDF',
                        text: 'Hubo un problema al generar el archivo PDF de ramas. Por favor, inténtelo de nuevo. Detalles: ' + error.message,
                        confirmButtonText: 'Aceptar'
                    });
                });
        });
    }

    // --- Descarga de Reportes XLS para Ramas ---
    const downloadRamasXlsBtn = document.getElementById('downloadRamasXlsBtn');
    if (downloadRamasXlsBtn) {
        downloadRamasXlsBtn.addEventListener('click', () => {
            const table = document.getElementById('ramasTable');
            if (!table) {
                Swal.fire({
                    icon: 'info',
                    title: 'Tabla no encontrada',
                    text: 'No se encontró la tabla de ramas para exportar.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            const ws_data = [];
            const headerRow = table.querySelector('thead tr');
            const headers = Array.from(headerRow.children).map(th => th.textContent.trim());
            ws_data.push(headers)

            const dataRows = Array.from(table.querySelectorAll('tbody tr')).filter(row =>
                row.style.display !== 'none' && !row.querySelector('td.text-muted') && row.querySelectorAll('td').length > 1
            );

            if (dataRows.length === 0) {
                Swal.fire({
                    icon: 'info',
                    title: 'Sin datos para exportar',
                    text: 'No hay ramas visibles en la tabla para generar el XLS.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            dataRows.forEach(row => {
                const rowData = [];
                rowData.push(row.cells[0].textContent.trim());
                rowData.push(row.cells[1].textContent.trim());
                rowData.push(row.cells[2].textContent.trim());
                rowData.push(row.cells[3].textContent.trim());
                const estadoCell = row.cells[4].querySelector('.badge');
                rowData.push(estadoCell ? estadoCell.textContent.trim() : row.cells[4].textContent.trim()); // Estado
                ws_data.push(rowData);
            });

            if (ws_data.length === 1) {
                Swal.fire({
                    icon: 'info',
                    title: 'Sin datos para exportar',
                    text: 'No hay ramas en la tabla para generar el XLS.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            const wb = XLSX.utils.book_new();
            const ws = XLSX.utils.aoa_to_sheet(ws_data);
            XLSX.utils.book_append_sheet(wb, ws, "Ramas");
            const today = new Date();
            const year = today.getFullYear();
            const month = String(today.getMonth() + 1).padStart(2, '0');
            const day = String(today.getDate()).padStart(2, '0');
            const fileName = `reporte_ramas_${year}${month}${day}.xlsx`;

            XLSX.writeFile(wb, fileName);
            Swal.fire({
                icon: 'success',
                title: 'XLS Generado',
                text: 'El reporte XLS de ramas ha sido descargado exitosamente.',
                confirmButtonText: 'Aceptar'
            });
        });
    }
});