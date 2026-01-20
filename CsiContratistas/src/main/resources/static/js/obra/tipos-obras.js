/* ---------- Seleccionar Tipo de Obra ---------- */
function seleccionarTipoObra(element) {
    document.getElementById("tipoObraId").value          = element.getAttribute("data-id");
    document.getElementById("tipoObraNombre").value      = element.getAttribute("data-nombre");
    document.getElementById("tipoObraDescripcion").value = element.getAttribute("data-descripcion");
    document.getElementById("tipoObraEstado").value      = element.getAttribute("data-estado");
}

/* ---------- Guardar Tipo de Obra (crear o editar) ---------- */
function guardarTipoObra(event) {
    event.preventDefault();

    const tipo = {
        nombre: document.getElementById("tipoObraNombre").value,
        descripcion: document.getElementById("tipoObraDescripcion").value,
        estado: document.getElementById("tipoObraEstado").value === "true"
    };

    const id = document.getElementById("tipoObraId").value;

    const url = id ? `/api/v1/tipos_obras/${id}` : `/api/v1/tipos_obras`;
    const method = id ? 'PUT' : 'POST';

    fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(tipo)
    })
    .then(response => {
        if (response.ok) {
            alertaExito(id ? 'Tipo de obra actualizado' : 'Tipo de obra registrado');
            setTimeout(() => location.reload(), 1500);
        } else {
            alertaError('No se pudo guardar el tipo de obra');
        }
    })
    .catch(error => {
        console.error(error);
        alertaError('Error inesperado');
    });
}

/* ---------- Limpiar Formulario Tipo de Obra ---------- */
function limpiarFormularioTipoObra() {
    document.getElementById("tipoObraId").value = '';
    document.getElementById("tipoObraNombre").value = '';
    document.getElementById("tipoObraDescripcion").value = '';
    document.getElementById("tipoObraEstado").value = 'true';
}

/* ---------- Eliminar Tipo de Obra ---------- */
function eliminarTipoObra() {
    const id = document.getElementById("tipoObraId").value;
    if (!id) return alertaError('Selecciona un tipo de obra para eliminar');

    alertaConfirmacion('¿Deseas eliminar este tipo de obra?', () => {
        fetch(`/api/v1/tipos_obras/${id}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (response.ok) {
                alertaExito('Tipo de obra eliminado');
                setTimeout(() => location.reload(), 1500);
            } else {
                alertaError('No se pudo eliminar');
            }
        })
        .catch(error => {
            console.error(error);
            alertaError('Error inesperado al eliminar');
        });
    });
}
