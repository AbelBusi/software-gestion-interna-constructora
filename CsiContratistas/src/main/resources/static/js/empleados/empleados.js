document.addEventListener('DOMContentLoaded', function () {

    function calcularEdad(fechaNacimiento) {
        const hoy = new Date();
        const nacimiento = new Date(fechaNacimiento);
        let edad = hoy.getFullYear() - nacimiento.getFullYear();
        const m = hoy.getMonth() - nacimiento.getMonth();
        if (m < 0 || (m === 0 && hoy.getDate() < nacimiento.getDate())) {
            edad--;
        }
        return edad;
    }

    function mostrarError(input, span, mensaje) {
        input.classList.add('is-invalid');
        span.textContent = mensaje;
    }

    function limpiarError(input, span) {
        input.classList.remove('is-invalid');
        span.textContent = '';
    }

    const formNuevoEmpleado = document.querySelector('#modalNuevoEmpleado form');
    const btnGuardar = document.querySelector('#modalNuevoEmpleado .btn-primary[type="submit"]');
    const fechaNacimientoInput = document.getElementById('nuevoFechaNacimiento');
    const fechaNacimientoError = document.getElementById('fechaNacimientoError');

    function validarEdad() {
        const fechaNacimiento = fechaNacimientoInput.value;
        let fechaInvalida = false;
        if (fechaNacimiento) {
            const edad = calcularEdad(fechaNacimiento);
            if (edad < 18) {
                mostrarError(fechaNacimientoInput, fechaNacimientoError, 'El empleado debe tener al menos 18 años.');
                fechaInvalida = true;
            } else {
                limpiarError(fechaNacimientoInput, fechaNacimientoError);
            }
        } else {
            limpiarError(fechaNacimientoInput, fechaNacimientoError);
        }
        if (btnGuardar) btnGuardar.disabled = fechaInvalida;
        return !fechaInvalida;
    }

    if (fechaNacimientoInput) {
        fechaNacimientoInput.addEventListener('input', validarEdad);
    }

    if (formNuevoEmpleado) {
        formNuevoEmpleado.addEventListener('submit', function (e) {
            if (!validarEdad()) {
                e.preventDefault();
                fechaNacimientoInput.focus();
            }
        });
    }

    const buscarDniBtn = document.getElementById('buscarDniBtn');
    if (buscarDniBtn) {
        buscarDniBtn.addEventListener('click', function () {
            const dniInput = document.getElementById('nuevoDni');
            const errorSpan = document.getElementById('dniError');
            errorSpan.textContent = '';
            const dni = dniInput.value;
            if (dni.length !== 8 || isNaN(dni)) {
                errorSpan.textContent = 'DNI inválido.';
                return;
            }
            fetch('/api/reniec/dni/' + dni)
                .then(response => response.json())
                .then(data => {
                    if (data.error) {
                        errorSpan.textContent = data.error;
                    } else if (data.nombres && data.apellidoPaterno && data.apellidoMaterno) {
                        document.getElementById('nuevoNombre').value = data.nombres;
                        document.getElementById('nuevoApellidos').value = data.apellidoPaterno + ' ' + data.apellidoMaterno;
                    } else {
                        errorSpan.textContent = 'No se encontraron datos completos para este DNI.';
                    }
                })
                .catch(() => {
                    errorSpan.textContent = 'Error consultando el DNI.';
                });
        });
    }

    // --- Validación básica de formularios (Bootstrap) ---
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
            form.querySelectorAll('input, select').forEach(input => {
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
                }
            });
        }
    }

    setupFormValidation('#modalNuevoEmpleado form');
    setupFormValidation('#modalEditarEmpleado form');

    // --- Funcionalidad para rellenar el modal de edición ---
    document.querySelectorAll('.btn-editar-empleado').forEach(function (btn) {
        btn.addEventListener('click', function () {
            document.getElementById('editarIdEmpleado').value = btn.dataset.id_empleado;
            document.getElementById('editarDni').value = btn.dataset.dni;
            document.getElementById('editarNombre').value = btn.dataset.nombre;
            document.getElementById('editarApellidos').value = btn.dataset.apellidos;
            document.getElementById('editarFechaNacimiento').value = btn.dataset.fecha_nacimiento;
            document.getElementById('editarGenero').value = btn.dataset.genero;
            document.getElementById('editarTelefono').value = btn.dataset.telefono;
            document.getElementById('editarEstadoCivil').value = btn.dataset.estado_civil;
            document.getElementById('editarDireccion').value = btn.dataset.direccion;
            document.getElementById('editarEstado').value = btn.dataset.estado;
            document.getElementById('editarProfesion').value = btn.dataset.id_profesion;
            document.getElementById('editarRama').value = btn.dataset.id_rama;

            let idCargoRaw = btn.dataset.id_cargo;
            let idCargoValue = (idCargoRaw === null || idCargoRaw === undefined || idCargoRaw.trim() === '' || idCargoRaw.toLowerCase() === 'null' || idCargoRaw.toLowerCase() === 'undefined') ? '' : idCargoRaw;
            document.getElementById('editarIdCargo').value = idCargoValue;
        });
    });

    // --- Funcionalidad para eliminar empleado con SweetAlert2 ---
    document.querySelectorAll('.btn-eliminar-empleado').forEach(function (btn) {
        btn.addEventListener('click', function () {
            const empleadoId = btn.dataset.id_empleado;
            Swal.fire({
                title: '¿Estás seguro?',
                text: "¡No podrás revertir esto!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                confirmButtonText: 'Sí, eliminarlo!',
                cancelButtonText: 'Cancelar'
            }).then((result) => {
                if (result.isConfirmed) {
                    const form = document.createElement('form');
                    form.method = 'POST';
                    form.action = `/administrador/empleados/eliminar/${empleadoId}`;
                    document.body.appendChild(form);
                    form.submit();
                }
            });
        });
    });

    // --- Filtros de la Tabla ---
    const searchInput = document.getElementById('searchEmpleado');
    const filterEstadoSelect = document.getElementById('filterEstado');
    const filterProfesionSelect = document.getElementById('filterProfesion');
    const clearFiltersBtn = document.getElementById('clearFilters');
    const tableRows = document.querySelectorAll('#empleadosTable tbody tr');

    function applyFilters() {
        const searchTerm = searchInput.value.toLowerCase().trim();
        const estadoFilter = filterEstadoSelect.value.toLowerCase().trim();
        const profesionFilter = filterProfesionSelect.value;

        tableRows.forEach(row => {
            const dni = row.cells[1].textContent.toLowerCase();
            const nombreCompleto = row.cells[2].textContent.toLowerCase();
            const estadoEnTabla = row.cells[10].textContent.toLowerCase().trim();
            const profesionEnTabla = row.cells[8].textContent;

            const matchesSearch = dni.includes(searchTerm) || nombreCompleto.includes(searchTerm);
            const matchesEstado = estadoFilter === '' || estadoEnTabla === estadoFilter;
            const matchesProfesion = profesionFilter === '' || profesionEnTabla === profesionFilter;

            row.style.display = (matchesSearch && matchesEstado && matchesProfesion) ? '' : 'none';
        });
    }

    if (searchInput) searchInput.addEventListener('input', applyFilters);
    if (filterEstadoSelect) filterEstadoSelect.addEventListener('change', applyFilters);
    if (filterProfesionSelect) filterProfesionSelect.addEventListener('change', applyFilters);
    if (clearFiltersBtn) {
        clearFiltersBtn.addEventListener('click', function () {
            searchInput.value = '';
            filterEstadoSelect.value = '';
            filterProfesionSelect.value = '';
            applyFilters();
        });
    }

    // --- Descarga de Reportes PDF ---
    const downloadPdfBtn = document.getElementById('downloadPdfBtn');
    if (downloadPdfBtn) {
        downloadPdfBtn.addEventListener('click', () => {
            Swal.fire({
                title: 'Generando PDF...',
                text: 'Por favor, espere mientras se genera el reporte.',
                allowOutsideClick: false,
                didOpen: () => {
                    Swal.showLoading();
                }
            });

            fetch('/administrador/empleados/reporte/pdf')
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Error al generar el PDF');
                    }
                    return response.blob();
                })
                .then(blob => {
                    const url = window.URL.createObjectURL(blob);
                    const a = document.createElement('a');
                    a.href = url;
                    a.download = 'empleados.pdf';
                    document.body.appendChild(a);
                    a.click();
                    a.remove();
                    window.URL.revokeObjectURL(url);
                    Swal.fire({
                        icon: 'success',
                        title: 'PDF Generado',
                        text: 'El reporte PDF se ha descargado exitosamente.',
                        confirmButtonText: 'Aceptar'
                    });
                })
                .catch(error => {
                    Swal.fire({
                        icon: 'error',
                        title: 'Error al generar PDF',
                        text: 'Hubo un problema al generar el archivo PDF. Por favor, inténtelo de nuevo.',
                        confirmButtonText: 'Aceptar'
                    });
                });
        });
    }

    // --- Descarga de Reportes XLS ---
    const downloadXlsBtn = document.getElementById('downloadXlsBtn');
    if (downloadXlsBtn) {
        downloadXlsBtn.addEventListener('click', () => {
            const table = document.getElementById('empleadosTable');
            const ws_data = [];
            const headerRow = table.querySelector('thead tr');
            const headers = Array.from(headerRow.children)
                .slice(0, 11)
                .map(th => th.textContent.trim());
            ws_data.push(headers);

            const visibleRows = Array.from(table.querySelectorAll('tbody tr'))
            if (table.querySelectorAll('tbody tr').length === 0) { // Comprobar si la tabla tiene datos en la página actual
                Swal.fire({
                    icon: 'info',
                    title: 'Sin datos para exportar',
                    text: 'No hay empleados en la tabla actual para generar el XLS.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            table.querySelectorAll('tbody tr').forEach(row => { // Iterar sobre todas las filas de la página actual
                const rowData = [];
                for (let i = 0; i < 11; i++) {
                    const cell = row.cells[i];
                    if (i === 10) { // Columna de estado con badge
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
                    text: 'No hay empleados en la tabla actual para generar el XLS.',
                    confirmButtonText: 'Entendido'
                });
                return;
            }

            const wb = XLSX.utils.book_new();
            const ws = XLSX.utils.aoa_to_sheet(ws_data);
            XLSX.utils.book_append_sheet(wb, ws, "Empleados");
            const today = new Date();
            const year = today.getFullYear();
            const month = String(today.getMonth() + 1).padStart(2, '0');
            const day = String(today.getDate()).padStart(2, '0');
            const fileName = `reporte_empleados_${year}${month}${day}.xlsx`;

            XLSX.writeFile(wb, fileName);

            Swal.fire({
                icon: 'success',
                title: 'XLS Generado',
                text: 'El reporte XLS se ha descargado exitosamente.',
                confirmButtonText: 'Aceptar'
            });
        });
    }
});