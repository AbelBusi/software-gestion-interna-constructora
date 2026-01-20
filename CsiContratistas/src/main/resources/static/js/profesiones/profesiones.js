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

    setupFormValidation('#modalNuevaProfesion form');
    setupFormValidation('#modalEditarProfesion form');

    // --- Funcionalidad para rellenar el modal de edición de Profesión ---
    const editProfesionModal = document.getElementById('modalEditarProfesion');
    if (editProfesionModal) {
        editProfesionModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const idProfesion = button.dataset.id_profesion;
            const nombre = button.dataset.nombre;
            const descripcion = button.dataset.descripcion;
            const estado = button.dataset.estado === 'true';

            document.getElementById('editarIdProfesion').value = idProfesion;
            document.getElementById('editarNombreProfesion').value = nombre;
            document.getElementById('editarDescripcionProfesion').value = descripcion;
            const editarEstadoProfesionSelect = document.getElementById('editarEstadoProfesion');
            if (editarEstadoProfesionSelect) {
                editarEstadoProfesionSelect.value = String(estado);
            }
        });
    }

    // --- Funcionalidad para activar/desactivar profesión con SweetAlert2 ---
    document.querySelectorAll('.btn-desactivar-profesion, .btn-activar-profesion').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const profesionId = this.dataset.id_profesion;
            const isActivar = this.classList.contains('btn-activar-profesion');

            let title = '';
            let text = '';
            let confirmButtonText = '';
            let formAction = '';

            if (isActivar) {
                title = '¿Activar esta profesión?';
                text = "La profesión volverá a estar activa.";
                confirmButtonText = 'Sí, activar';
                formAction = `/administrador/profesiones/activar`;
            } else { // Es desactivar
                title = '¿Desactivar esta profesión?';
                text = "La profesión ya no estará activa.";
                confirmButtonText = 'Sí, desactivar';
                formAction = `/administrador/profesiones/desactivar`;
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
                    idInput.name = 'id_profesion';
                    idInput.value = profesionId;
                    form.appendChild(idInput);

                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });
    });

    // --- Buscador y Filtros de la Tabla (NUEVO) ---
    const searchProfesionInput = document.getElementById('searchProfesion');
    const filterEstadoProfesionSelect = document.getElementById('filterEstadoProfesion');
    const clearProfesionFiltersBtn = document.getElementById('clearProfesionFilters');
    const profesionTableRows = document.querySelectorAll('#profesionesTable tbody tr');

    function applyProfesionFilters() {
        const searchTerm = searchProfesionInput ? searchProfesionInput.value.toLowerCase().trim() : '';
        const estadoFilter = filterEstadoProfesionSelect ? filterEstadoProfesionSelect.value.toLowerCase().trim() : '';

        profesionTableRows.forEach(row => {
            if (row.querySelectorAll('td').length <= 1) {
                return;
            }

            const nombre = row.cells[1].textContent.toLowerCase();
            const descripcion = row.cells[2].textContent.toLowerCase();
            const estadoEnTabla = row.cells[3].textContent.toLowerCase().trim();

            const matchesSearch = nombre.includes(searchTerm) || descripcion.includes(searchTerm);
            const matchesEstado = estadoFilter === '' || estadoEnTabla === estadoFilter;

            row.style.display = (matchesSearch && matchesEstado) ? '' : 'none';
        });
    }

    if (searchProfesionInput) searchProfesionInput.addEventListener('input', applyProfesionFilters);
    if (filterEstadoProfesionSelect) filterEstadoProfesionSelect.addEventListener('change', applyProfesionFilters);
    if (clearProfesionFiltersBtn) {
        clearProfesionFiltersBtn.addEventListener('click', function () {
            if (searchProfesionInput) searchProfesionInput.value = '';
            if (filterEstadoProfesionSelect) filterEstadoProfesionSelect.value = '';
            applyProfesionFilters();
        });
    }
    applyProfesionFilters();

    // --- Descarga de Reportes PDF (si aplica para profesiones) ---
    const downloadPdfBtn = document.getElementById('downloadPdfBtn');
    if (downloadPdfBtn) {
        downloadPdfBtn.addEventListener('click', () => {
            Swal.fire({
                title: 'Generando PDF...',
                text: 'Por favor, espere mientras se genera el reporte de profesiones.',
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });

            fetch('/administrador/profesiones/reporte/pdf')
                .then(response => {
                    if (!response.ok) {
                        return response.text().then(text => { throw new Error(text || 'Error desconocido al generar el PDF de profesiones'); });
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
                    a.download = `reporte_profesiones_${year}${month}${day}.pdf`;
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(url);
                    Swal.fire({
                        icon: 'success',
                        title: 'PDF Generado',
                        text: 'El reporte PDF de profesiones ha sido descargado exitosamente.',
                        confirmButtonText: 'Aceptar'
                    });
                })
                .catch(error => {
                    console.error('Error al generar PDF:', error);
                    Swal.fire({
                        icon: 'error',
                        title: 'Error al generar PDF',
                        text: 'Hubo un problema al generar el archivo PDF de profesiones. Por favor, inténtelo de nuevo. Detalles: ' + error.message,
                        confirmButtonText: 'Aceptar'
                    });
                });
        });
    }

    // --- Descarga de Reportes XLS (si aplica para profesiones) ---
    const downloadXlsBtn = document.getElementById('downloadXlsBtn');
    if (downloadXlsBtn) {
        downloadXlsBtn.addEventListener('click', () => {
            const table = document.getElementById('profesionesTable');
            if (!table) {
                Swal.fire({
                    icon: 'info',
                    title: 'Tabla no encontrada',
                    text: 'No se encontró la tabla de profesiones para exportar.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            const ws_data = [];
            const headerRow = table.querySelector('thead tr');
            const headers = Array.from(headerRow.children).map(th => th.textContent.trim());
            ws_data.push(headers)

            const dataRows = Array.from(table.querySelectorAll('tbody tr')).filter(row =>
                !row.classList.contains('text-muted') && row.querySelectorAll('td').length > 1
            );

            if (dataRows.length === 0) {
                Swal.fire({
                    icon: 'info',
                    title: 'Sin datos para exportar',
                    text: 'No hay profesiones visibles en la tabla actual para generar el XLS.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            dataRows.forEach(row => {
                const rowData = [];
                for (let i = 0; i < 4; i++) {
                    const cell = row.cells[i];
                    if (i === 3) {
                        const badgeSpan = cell.querySelector('.badge');
                        rowData.push(badgeSpan ? badgeSpan.textContent.trim() : cell.textContent.trim());
                    } else {
                        rowData.push(cell.textContent.trim());
                    }
                }
                ws_data.push(rowData);
            });

            if (ws_data.length === 1) {
                Swal.fire({
                    icon: 'info',
                    title: 'Sin datos para exportar',
                    text: 'No hay profesiones en la tabla para generar el XLS.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            const wb = XLSX.utils.book_new();
            const ws = XLSX.utils.aoa_to_sheet(ws_data);
            XLSX.utils.book_append_sheet(wb, ws, "Profesiones");
            const today = new Date();
            const year = today.getFullYear();
            const month = String(today.getMonth() + 1).padStart(2, '0');
            const day = String(today.getDate()).padStart(2, '0');
            const fileName = `reporte_profesiones_${year}${month}${day}.xlsx`;

            XLSX.writeFile(wb, fileName);
            Swal.fire({
                icon: 'success',
                title: 'XLS Generado',
                text: 'El reporte XLS de profesiones ha sido descargado exitosamente.',
                confirmButtonText: 'Aceptar'
            });
        });
    }
});