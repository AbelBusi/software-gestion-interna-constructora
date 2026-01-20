$(document).ready(function () {

    $('.btn-editar-rol').on('click', function () {
        const id = $(this).data('id_rol');
        const nombre = $(this).data('nombre');
        const descripcion = $(this).data('descripcion');
        $('#editarIdRol').val(id);
        $('#editarNombre').val(nombre);
        $('#editarDescripcion').val(descripcion);
        $('#modalEditarRol').modal('show');
    });

    $('.btn-eliminar-rol').on('click', function () {
        const id = $(this).data('id_rol');
        const nombre = $(this).data('nombre');
        const row = $(this).closest('tr');
        
        Swal.fire({
            title: '¿Estás seguro?',
            text: `¿Deseas eliminar el rol "${nombre}"? Esta acción no se puede deshacer.`,
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#d33',
            cancelButtonColor: '#3085d6',
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar',
            reverseButtons: true
        }).then((result) => {
            if (result.isConfirmed) {
                mostrarLoading('Eliminando rol...', 'Por favor espera');
                
                $.ajax({
                    url: '/administrador/roles/eliminar-ajax',
                    method: 'POST',
                    data: { id_rol: id },
                    success: function(response) {
                        cerrarSweetAlert();
                        Swal.fire({
                            icon: 'success',
                            title: '¡Rol eliminado!',
                            text: 'El rol ha sido eliminado exitosamente',
                            timer: 2000,
                            timerProgressBar: true
                        }).then(() => {
                            row.fadeOut(300, function() {
                                $(this).remove();
                                actualizarContadorRoles();
                            });
                        });
                    },
                    error: function(xhr) {
                        cerrarSweetAlert();
                        let mensaje = 'Error al eliminar el rol';
                        if (xhr.responseJSON && xhr.responseJSON.message) {
                            mensaje = xhr.responseJSON.message;
                        }
                        mostrarError(mensaje);
                    }
                });
            }
        });
    });

    $('.btn-permisos-rol').on('click', function () {
        const idRol = $(this).data('id_rol');
        const nombreRol = $(this).data('nombre');
        $('#idRolPermiso').val(idRol);
        $('#nombreRolPermiso').text(nombreRol);

        $('#listaPermisos input[type="checkbox"]').prop('checked', false);
        const asignados = permisosPorRol[idRol] || [];
        $('#listaPermisos input[type="checkbox"]').each(function () {
            const val = parseInt($(this).val());
            if (asignados.includes(val)) {
                $(this).prop('checked', true);
            }
        });
    });

    $('#formNuevoRol').on('submit', function (e) {
        e.preventDefault();
        
        if (this.checkValidity() === false) {
            e.stopPropagation();
            $(this).addClass('was-validated');
            return;
        }

        mostrarLoading('Creando rol...', 'Por favor espera');
        
        const formData = new FormData(this);
        
        $.ajax({
            url: '/administrador/roles/guardar-ajax',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                cerrarSweetAlert();
                Swal.fire({
                    icon: 'success',
                    title: '¡Rol creado!',
                    text: 'El rol ha sido creado exitosamente',
                    timer: 2000,
                    timerProgressBar: true
                }).then(() => {
                    $('#modalNuevoRol').modal('hide');
                    recargarTablaRoles();
                });
            },
            error: function(xhr) {
                cerrarSweetAlert();
                let mensaje = 'Error al crear el rol';
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    mensaje = xhr.responseJSON.message;
                }
                mostrarError(mensaje);
            }
        });
    });

    $('#formEditarRol').on('submit', function (e) {
        e.preventDefault();
        
        if (this.checkValidity() === false) {
            e.stopPropagation();
            $(this).addClass('was-validated');
            return;
        }

        mostrarLoading('Actualizando rol...', 'Por favor espera');
        
        const formData = new FormData(this);
        
        $.ajax({
            url: '/administrador/roles/editar-ajax',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                cerrarSweetAlert();
                Swal.fire({
                    icon: 'success',
                    title: '¡Rol actualizado!',
                    text: 'El rol ha sido actualizado exitosamente',
                    timer: 2000,
                    timerProgressBar: true
                }).then(() => {
                    $('#modalEditarRol').modal('hide');
                    recargarTablaRoles();
                });
            },
            error: function(xhr) {
                cerrarSweetAlert();
                let mensaje = 'Error al actualizar el rol';
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    mensaje = xhr.responseJSON.message;
                }
                mostrarError(mensaje);
            }
        });
    });

    $('#formAsignarPermisos').on('submit', function (e) {
        e.preventDefault();
        
        mostrarLoading('Actualizando permisos...', 'Por favor espera');
        
        const formData = new FormData(this);
        
        $.ajax({
            url: '/administrador/roles/asignar-permisos-ajax',
            method: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                cerrarSweetAlert();
                Swal.fire({
                    icon: 'success',
                    title: '¡Permisos actualizados!',
                    text: 'Los permisos han sido actualizados exitosamente',
                    timer: 2000,
                    timerProgressBar: true
                }).then(() => {
                    // Cerrar modal
                    $('#modalPermisosRol').modal('hide');
                });
            },
            error: function(xhr) {
                cerrarSweetAlert();
                let mensaje = 'Error al actualizar los permisos';
                if (xhr.responseJSON && xhr.responseJSON.message) {
                    mensaje = xhr.responseJSON.message;
                }
                mostrarError(mensaje);
            }
        });
    });

    function recargarTablaRoles() {
        $.ajax({
            url: '/administrador/roles/tabla',
            method: 'GET',
            success: function(response) {
                $('.tabla-roles tbody').html(response);
                aplicarEventosBotonesRoles();
            },
            error: function() {
                window.location.reload();
            }
        });
    }


    function actualizarContadorRoles() {
        const filas = $('.tabla-roles tbody tr').length;
        const contador = $('.contador-roles');
        if (contador.length > 0) {
            contador.text(filas);
        }
    }

    function aplicarEventosBotonesRoles() {
        $('.btn-editar-rol').off('click').on('click', function () {
            const id = $(this).data('id_rol');
            const nombre = $(this).data('nombre');
            const descripcion = $(this).data('descripcion');
            $('#editarIdRol').val(id);
            $('#editarNombre').val(nombre);
            $('#editarDescripcion').val(descripcion);
            $('#modalEditarRol').modal('show');
        });

        $('.btn-eliminar-rol').off('click').on('click', function () {
            const id = $(this).data('id_rol');
            const nombre = $(this).data('nombre');
            const row = $(this).closest('tr');
            
            Swal.fire({
                title: '¿Estás seguro?',
                text: `¿Deseas eliminar el rol "${nombre}"? Esta acción no se puede deshacer.`,
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: '#3085d6',
                confirmButtonText: 'Sí, eliminar',
                cancelButtonText: 'Cancelar',
                reverseButtons: true
            }).then((result) => {
                if (result.isConfirmed) {
                    mostrarLoading('Eliminando rol...', 'Por favor espera');
                    
                    $.ajax({
                        url: '/administrador/roles/eliminar-ajax',
                        method: 'POST',
                        data: { id_rol: id },
                        success: function(response) {
                            cerrarSweetAlert();
                            Swal.fire({
                                icon: 'success',
                                title: '¡Rol eliminado!',
                                text: 'El rol ha sido eliminado exitosamente',
                                timer: 2000,
                                timerProgressBar: true
                            }).then(() => {
                                row.fadeOut(300, function() {
                                    $(this).remove();
                                    actualizarContadorRoles();
                                });
                            });
                        },
                        error: function(xhr) {
                            cerrarSweetAlert();
                            let mensaje = 'Error al eliminar el rol';
                            if (xhr.responseJSON && xhr.responseJSON.message) {
                                mensaje = xhr.responseJSON.message;
                            }
                            mostrarError(mensaje);
                        }
                    });
                }
            });
        });

        $('.btn-permisos-rol').off('click').on('click', function () {
            const idRol = $(this).data('id_rol');
            const nombreRol = $(this).data('nombre');
            $('#idRolPermiso').val(idRol);
            $('#nombreRolPermiso').text(nombreRol);

            $('#listaPermisos input[type="checkbox"]').prop('checked', false);
            const asignados = permisosPorRol[idRol] || [];
            $('#listaPermisos input[type="checkbox"]').each(function () {
                const val = parseInt($(this).val());
                if (asignados.includes(val)) {
                    $(this).prop('checked', true);
                }
            });
        });
    }


    function mostrarLoading(titulo = 'Procesando...', mensaje = 'Por favor espera') {
        Swal.fire({
            title: titulo,
            text: mensaje,
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });
    }

    function cerrarSweetAlert() {
        Swal.close();
    }

    function mostrarError(mensaje) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: mensaje,
            confirmButtonText: 'Aceptar'
        });
    }

    window.mostrarLoading = mostrarLoading;
    window.cerrarSweetAlert = cerrarSweetAlert;
    window.mostrarError = mostrarError;
    window.recargarTablaRoles = recargarTablaRoles;

    if (window.location.search.includes('success') || window.location.search.includes('error')) {
        window.history.replaceState({}, document.title, window.location.pathname);
    }

    $('#btnExportPdf').on('click', function () {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        const fechaActual = new Date().toLocaleString();
        const empresa = "Sistema de Gestión CSI";

        toDataURL('/img/logo-report.png', function(base64Img){
            doc.addImage(base64Img, 'PNG', 150, 10, 40, 15);
            doc.setFontSize(16);
            doc.setFont("helvetica", "bold");
            doc.text(empresa, 14, 15);
            doc.setFontSize(14);
            doc.setFont("helvetica", "normal");
            doc.text("Reporte de Roles", 14, 25);
            doc.setFontSize(10);
            doc.text(`Fecha de generación: ${fechaActual}`, 14, 32);

            const table = document.querySelector(".tabla-roles table");
            if (!table) {
                alert("No se encontró la tabla  'tabla-roles'.");
                return;
            }

            const headers = Array.from(table.querySelectorAll("thead th"))
                .map(th => th.innerText)
                .slice(0, -1);

            const rows = Array.from(table.querySelectorAll("tbody tr")).map(tr =>
                Array.from(tr.querySelectorAll("td"))
                    .slice(0, -1)
                    .map(td => td.innerText.trim())
            );

            doc.autoTable({
                startY: 38,
                head: [headers],
                body: rows,
                theme: 'striped',
                styles: { fontSize: 10, cellPadding: 3 },
                headStyles: { fillColor: [13, 110, 253], textColor: 255, fontStyle: 'bold' },
                margin: { top: 38 },
                didDrawPage: function (data) {
                    const pageCount = doc.internal.getNumberOfPages();
                    doc.setFontSize(8);
                    doc.text(`Página ${data.pageNumber} de ${pageCount}`, data.settings.margin.left, doc.internal.pageSize.height - 10);
                }
            });

            doc.save("reporte_roles.pdf");
        });
    });

    function toDataURL(url, callback) {
        var xhr = new XMLHttpRequest();
        xhr.onload = function() {
            var reader = new FileReader();
            reader.onloadend = function() {
                callback(reader.result);
            }
            reader.readAsDataURL(xhr.response);
        };
        xhr.open('GET', url);
        xhr.responseType = 'blob';
        xhr.send();
    }
});