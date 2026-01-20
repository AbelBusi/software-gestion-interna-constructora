function seleccionarForma(element) {
    const id = element.getAttribute("data-id");
    const nombre = element.getAttribute("data-nombre");
    const descripcion = element.getAttribute("data-descripcion");
    const estado = element.getAttribute("data-estado");

    // Ya puedes usar los datos
    document.getElementById("formaId").value = id;
    document.getElementById("formaNombre").value = nombre;
    document.getElementById("formaDescripcion").value = descripcion;
    document.getElementById("formaEstado").value = estado;
}

function guardarFormaTerreno(event) {
    event.preventDefault();

    const forma = {
        nombre: document.getElementById('formaNombre').value,
        descripcion: document.getElementById('formaDescripcion').value,
        estado: document.getElementById('formaEstado').value === 'true'
    };

    const id = document.getElementById('formaId').value;

    const url = id ? `/api/v1/formas_terrenos/${id}` : '/api/v1/formas_terrenos';
    const method = id ? 'PUT' : 'POST';

    fetch(url, {
        method,
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(forma)
    }).then(response => {
        if (response.ok) {
            alertaExito(id ? 'Forma de terreno actualizada' : 'Forma de terreno registrada');
            setTimeout(() => location.reload(), 1500);
        } else {
            alertaError('No se pudo guardar la forma de terreno');
        }
    }).catch(error => {
        console.error(error);
        alertaError('Error inesperado');
    });
}

/* ---------- Llenar el formulario al seleccionar ---------- */
function seleccionarForma(tr) {
  document.getElementById('formaId').value          = tr.dataset.id;
  document.getElementById('formaNombre').value      = tr.dataset.nombre;
  document.getElementById('formaDescripcion').value = tr.dataset.descripcion;
  document.getElementById('formaEstado').value      = tr.dataset.estado;
}

/* ---------- Limpiar formulario ---------- */
function limpiarFormularioForma() {
  document.getElementById('form-forma-terreno').reset();
  document.getElementById('formaId').value = '';    // dejar vacío ⇒ modo crear
}

function limpiarFormularioForma() {
    document.getElementById('formaId').value = '';
    document.getElementById('formaNombre').value = '';
    document.getElementById('formaDescripcion').value = '';
    document.getElementById('formaEstado').value = 'true';
}

function eliminarFormaTerreno() {
    const id = document.getElementById('formaId').value;
    if (!id) return alertaError('Selecciona una forma para eliminar');

    alertaConfirmacion('¿Deseas eliminar esta forma de terreno?', () => {
        fetch(`/api/v1/formas_terrenos/${id}`, {
            method: 'DELETE'
        }).then(response => {
            if (response.ok) {
                alertaExito('Forma de terreno eliminada');
                setTimeout(() => location.reload(), 1500);
            } else {
                alertaError('No se pudo eliminar');
            }
        }).catch(error => {
            console.error(error);
            alertaError('Error inesperado al eliminar');
        });
    });
}

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
            confirmarCallback(); // Ejecuta el callback si confirma
        }
    });
}

