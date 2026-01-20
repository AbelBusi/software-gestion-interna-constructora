/* ---------- Seleccionar Tipo de Suelo ---------- */
function seleccionarTipoSuelo(element) {
    document.getElementById("tipoSueloId").value          = element.getAttribute("data-id");
    document.getElementById("tipoSueloNombre").value      = element.getAttribute("data-nombre");
    document.getElementById("tipoSueloDescripcion").value = element.getAttribute("data-descripcion");
    document.getElementById("tipoSueloEstado").value      = element.getAttribute("data-estado");
}

/* ---------- Guardar Tipo de Suelo (crear o editar) ---------- */
function guardarTipoSuelo(event) {
    event.preventDefault();

    const tipo = {
        nombre: document.getElementById("tipoSueloNombre").value,
        descripcion: document.getElementById("tipoSueloDescripcion").value,
        estado: document.getElementById("tipoSueloEstado").value === "true"
    };

    const id = document.getElementById("tipoSueloId").value;

    const url = id ? `/api/v1/tipos_suelos/${id}` : `/api/v1/tipos_suelos`;
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
                alertaExito(id ? 'Tipo de suelo actualizado' : 'Tipo de suelo registrado');
                setTimeout(() => location.reload(), 1500);
            } else {
                alertaError('No se pudo guardar el tipo de suelo');
            }
        })
        .catch(error => {
            console.error(error);
            alertaError('Error inesperado');
        });
}

/* ---------- Limpiar Formulario ---------- */
function limpiarFormularioTipoSuelo() {
    document.getElementById("tipoSueloId").value = '';
    document.getElementById("tipoSueloNombre").value = '';
    document.getElementById("tipoSueloDescripcion").value = '';
    document.getElementById("tipoSueloEstado").value = 'true';
}

/* ---------- Eliminar Tipo de Suelo ---------- */
function eliminarTipoSuelo() {
    const id = document.getElementById("tipoSueloId").value;
    if (!id) return alertaError('Selecciona un tipo de suelo para eliminar');

    alertaConfirmacion('¿Deseas eliminar este tipo de suelo?', () => {
        fetch(`/api/v1/tipos_suelos/${id}`, {
            method: 'DELETE'
        })
            .then(response => {
                if (response.ok) {
                    alertaExito('Tipo de suelo eliminado');
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

/* ---------- Alertas con SweetAlert2 ---------- */
function alertaExito(mensaje = 'Guardado correctamente') {
    Swal.fire({
        icon: 'success',
        title: 'Éxito',
        text: mensaje,
        showConfirmButton: false,
        timer: 2000
    });
}

function alertaError(mensaje = 'Ocurrió un error') {
    Swal.fire({
        icon: 'error',
        title: 'Error',
        text: mensaje
    });
}

function alertaConfirmacion(texto, confirmarCallback) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: texto,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#198754',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, confirmar',
        cancelButtonText: 'Cancelar'
    }).then((result) => {
        if (result.isConfirmed) {
            confirmarCallback();
        }
    });
}
