/* ---------- Seleccionar Tipo de Construcción ---------- */
function seleccionarTipoConstruccion(row) {
    document.getElementById("tipoConstruccionId").value = row.dataset.id;
    document.getElementById("tipoConstruccionNombre").value = row.dataset.nombre;
    document.getElementById("tipoConstruccionDescripcion").value = row.dataset.descripcion;
}

/* ---------- Limpiar Formulario ---------- */
function limpiarFormularioTipoConstruccion() {
    document.getElementById('form-tipo-construccion').reset();
    document.getElementById('tipoConstruccionId').value = '';
}

/* ---------- Guardar (crear o editar) ---------- */
async function guardarTipoConstruccion(ev) {
    ev.preventDefault();

    const dto = {
        nombre:      document.getElementById('tipoConstruccionNombre').value,
        descripcion: document.getElementById('tipoConstruccionDescripcion').value,
        estado:      true
    };

    const id = document.getElementById('tipoConstruccionId').value;
    const url = id ? `/api/v1/tipos_construcciones/${id}` : '/api/v1/tipos_construcciones';
    const method = id ? 'PUT' : 'POST';

    fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(dto)
    })
    .then(r => { if (!r.ok) throw new Error(); })
    .then(() => {
        alertaExito(id ? 'Tipo actualizado' : 'Tipo registrado');
        setTimeout(() => location.reload(), 1500);
    })
    .catch(() => alertaError('No se pudo guardar el tipo de construcción'));
}

/* ---------- Eliminar Tipo ---------- */
function eliminarTipoConstruccion() {
    const id = document.getElementById('tipoConstruccionId').value;
    if (!id) return alertaError('Selecciona un tipo de construcción para eliminar');

    alertaConfirmacion('¿Eliminar este tipo de construcción?', () => {
        fetch(`/api/v1/tipos_construcciones/${id}`, { method: 'DELETE' })
        .then(r => { if (!r.ok) throw new Error(); })
        .then(() => {
            alertaExito('Tipo eliminado');
            setTimeout(() => location.reload(), 1500);
        })
        .catch(() => alertaError('No se pudo eliminar el tipo'));
    });
}
