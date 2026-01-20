// Abrir modal de edición y llenar datos
$(document).on('click', '.btn-editar-tipo-material', function () {
    $('#editarIdTipoMaterial').val($(this).data('idtipomaterial'));
    $('#editarNombreTipo').val($(this).data('nombre'));
    $('#editarDescripcionTipo').val($(this).data('descripcion'));
    $('#editarClasificacionTipo').val($(this).data('clasificacion'));
    $('#editarEstadoTipo').val($(this).data('estado') === true || $(this).data('estado') === "true" ? "true" : "false");
    $('#modalEditarTipoMaterial').modal('show');
});


$('#modalNuevoTipoMaterial').on('hidden.bs.modal', function () {
    $(this).find('form')[0].reset();
});

$('#modalEditarTipoMaterial').on('hidden.bs.modal', function () {
    $(this).find('form')[0].reset();
});

$(document).on('click', '.btn-eliminar-tipo-material', function () {
    const id = $(this).data('idtipomaterial');
    Swal.fire({
        title: '¿Estás seguro?',
        text: "¡Esta acción no se puede deshacer!",
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
                action: '/administrador/tipos-material/eliminar/'+id
            }).append($('<input>', {
                type: 'hidden',
                name: 'idtipomaterial',
                value: id
            }));
            $('body').append(form);
            form.submit();
        }
    });
});