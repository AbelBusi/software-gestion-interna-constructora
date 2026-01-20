$(document).on('click', '.btn-editar-material', function () {
    $('#editarIdMaterial').val($(this).data('id_material'));
    $('#editarNombre').val($(this).data('nombre'));
    $('#editarTipoMaterial').val($(this).data('tipo_material'));
    $('#editarPrecioUnitario').val($(this).data('precio_unitario'));
    $('#editarStockActual').val($(this).data('stock_actual'));
    $('#editarMasaEspecifica').val($(this).data('masa_especifica'));
    $('#editarLongitud').val($(this).data('longitud'));
    $('#editarTemperaturaTermodinamica').val($(this).data('temperatura_termodinamica'));
    $('#editarDescripcion').val($(this).data('descripcion'));
    $('#editarEstado').val($(this).data('estado') === true || $(this).data('estado') === "true" ? "true" : "false");
    $('#modalEditarMaterial').modal('show');
});


$('#modalNuevoMaterial').on('show.bs.modal', function () {
    $(this).find('form')[0].reset();
});


$('#modalEditarMaterial').on('hidden.bs.modal', function () {
    $(this).find('form')[0].reset();
});


$(document).on('click', '.btn-eliminar-material', function () {
    const id = $(this).data('id_material');
    const nombre = $(this).closest('tr').find('td:nth-child(2)').text().trim();
    Swal.fire({
        title: '¿Eliminar material?',
        html: `<b>${nombre}</b><br>Esta acción no se puede deshacer.`,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, eliminar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            const form = $('<form>', {
                method: 'POST',
                action: '/administrador/materiales/eliminar'
            }).append($('<input>', {
                type: 'hidden',
                name: 'id_material',
                value: id
            }));
            $('body').append(form);
            form.submit();
        }
    });
});

const downloadPdfBtn = document.getElementById('downloadPdfBtn');
if (downloadPdfBtn) {
    downloadPdfBtn.addEventListener('click', () => {
        
        Swal.fire({
            title: 'Generando reporte...',
            text: 'Por favor, espere un momento mientras se genera el PDF.',
            allowOutsideClick: false,
            showConfirmButton: false,
            didOpen: () => {
                Swal.showLoading();
            }
        });

        fetch('/administrador/materiales/reporte/pdf')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Error al generar el PDF');
                }
                return response.blob();
            })
            .then(blob => {
                Swal.close(); 
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'Reporte_materiales(' + new Date().toISOString().slice(0, 10) + ').pdf';
                document.body.appendChild(a);
                a.click();
                a.remove();
                window.URL.revokeObjectURL(url);
            })
            .catch(error => {
                Swal.close(); 
                Swal.fire({
                    icon: 'error',
                    title: 'Error al generar PDF',
                    text: 'Hubo un problema al generar el archivo PDF. Por favor, inténtelo de nuevo.',
                    confirmButtonText: 'Aceptar'
                });
            });
    });
}
const downloadXlsBtn = document.getElementById('downloadXlsBtn');
if (downloadXlsBtn) {
    downloadXlsBtn.addEventListener('click', () => {
        const progressSwal = Swal.fire({
            title: 'Generando Reporte Excel',
            html: `
                <div class="progress-container" style="margin: 1rem 0;">
                    <div class="progress-bar-excel" style="height: 6px; background: #e0e0e0; border-radius: 3px;">
                        <div class="progress-bar-fill" style="height: 100%; width: 0%; background: #28a745; border-radius: 3px; transition: width 0.3s;"></div>
                    </div>
                </div>
                <p class="progress-text" style="margin-top: 8px; font-size: 14px; color: #555;">Preparando datos...</p>
            `,
            showConfirmButton: false,
            allowOutsideClick: false,
            didOpen: () => {

                const progressFill = document.querySelector('.progress-bar-fill');
                const progressText = document.querySelector('.progress-text');

                let progress = 0;
                const progressInterval = setInterval(() => {
                    progress += 5;
                    if (progress > 80) {
                        progress = 80;
                        progressText.textContent = 'Generando archivo Excel...';
                    } else if (progress > 50) {
                        progressText.textContent = 'Procesando información...';
                    }
                    progressFill.style.width = `${progress}%`;
                }, 200);

                fetch('/administrador/materiales/reporte/excel')
                    .then(response => {
                        if (!response.ok) {
                            throw new Error('Error al generar el reporte');
                        }
                        return response.blob();
                    })
                    .then(blob => {

                        clearInterval(progressInterval);
                        progressFill.style.width = '100%';
                        progressText.textContent = '¡Reporte listo!';

                        setTimeout(() => {
                            progressSwal.close();

                            const url = window.URL.createObjectURL(blob);
                            const a = document.createElement('a');
                            a.href = url;
                            a.download = `Reporte_Materiales_${new Date().toISOString().slice(0, 10)}.xlsx`;
                            document.body.appendChild(a);
                            a.click();

                            setTimeout(() => {
                                document.body.removeChild(a);
                                window.URL.revokeObjectURL(url);
                            }, 100);

                        }, 500);
                    })
                    .catch(error => {
                        clearInterval(progressInterval);
                        progressSwal.close();

                        Swal.fire({
                            icon: 'error',
                            title: 'Error',
                            text: error.message || 'Ocurrió un error al generar el Excel',
                            confirmButtonText: 'Aceptar'
                        });
                    });
            }
        });
    });
}
document.addEventListener('DOMContentLoaded', function () {
    const inputNombre = document.getElementById('filtroNombre');
    const selectTipo = document.getElementById('filtroTipoMaterial');
    const tabla = document.getElementById('tablaMateriales');
    if (!tabla) return;

    function filtrarTabla() {
        const filtroNombre = inputNombre.value.toLowerCase();
        const filtroTipo = selectTipo.value;
        Array.from(tabla.tBodies[0].rows).forEach(row => {
            const nombre = row.cells[2].textContent.toLowerCase();
            const tipo = row.cells[1].textContent;
            const coincideNombre = nombre.includes(filtroNombre);
            const coincideTipo = !filtroTipo || tipo === filtroTipo;
            row.style.display = (coincideNombre && coincideTipo) ? '' : 'none';
        });
    }

    inputNombre.addEventListener('input', filtrarTabla);
    selectTipo.addEventListener('change', filtrarTabla);
});