/* ========== CREAR / ACTUALIZAR ========== */
function guardarTerreno(event) {
    event.preventDefault();

    const id = document.getElementById('terId').value.trim();
    const fechaAsignacion = id
        ? document.getElementById('terFechaAsignacion').value
        : getFechaActualFormateada();

    const terreno = {
        id_tipo_suelo   : +document.getElementById('terTipoSuelo').value,
        id_forma_terreno: +document.getElementById('terFormaTerreno').value,
        area_total      : +document.getElementById('terAreaTotal').value,
        area_util       : +document.getElementById('terAreaUtil').value,
        frente_metros   : +document.getElementById('terFrente').value,
        fondo_metros    : +document.getElementById('terFondo').value,
        zonificacion    : document.getElementById('terZonificacion').value,
        servicios_basicos: document.getElementById('terServicios').value === 'true',
        acceso_vial     : document.getElementById('terAcceso').value,
        observaciones   : document.getElementById('terObservaciones').value,
        coordenadas     : document.getElementById('terCoordenadas').value,
        fecha_asignacion: fechaAsignacion,
        estado          : true
    };

    const url = id ? `/api/v1/terrenos/${id}` : `/api/v1/terrenos`;
    const method = id ? 'PUT' : 'POST';

    fetch(url, {
        method,
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(terreno)
    })
    .then(resp => {
        if (!resp.ok) throw new Error('Error al guardar');
        return resp.text(); // o .json() si sabes que responde con JSON
    })
    .then(() => {
        alertaExito(id ? 'Terreno actualizado' : 'Terreno registrado');
        setTimeout(() => location.reload(), 1500);
    })
    .catch(err => {
        console.error(err);
        alertaError('No se pudo guardar el terreno');
    });
}

/* ========== OBTENER FECHA ACTUAL EN FORMATO ISO ========== */
function getFechaActualFormateada() {
    const now = new Date();
    const año = now.getFullYear();
    const mes = String(now.getMonth() + 1).padStart(2, '0');
    const dia = String(now.getDate()).padStart(2, '0');
    const hora = String(now.getHours()).padStart(2, '0');
    const min = String(now.getMinutes()).padStart(2, '0');
    const seg = String(now.getSeconds()).padStart(2, '0');
    return `${año}-${mes}-${dia}T${hora}:${min}:${seg}`;
}

/* ========== CARGAR DATOS EN EL FORMULARIO ========== */
function seleccionarTerreno(tr) {
    const d = tr.dataset;

    document.getElementById('terId').value              = d.id;
    document.getElementById('terTipoSuelo').value       = d.id_tipo_suelo;
    document.getElementById('terFormaTerreno').value    = d.id_forma_terreno;
    document.getElementById('terAreaTotal').value       = d.area_total;
    document.getElementById('terAreaUtil').value        = d.area_util;
    document.getElementById('terFrente').value          = d.frente_metros;
    document.getElementById('terFondo').value           = d.fondo_metros;
    document.getElementById('terZonificacion').value    = d.zonificacion;
    document.getElementById('terServicios').value       = d.servicios_basicos;
    document.getElementById('terAcceso').value          = d.acceso_vial;
    document.getElementById('terObservaciones').value   = d.observaciones;
    document.getElementById('terCoordenadas').value     = d.coordenadas;
    document.getElementById('terFechaAsignacion').value = d.fecha_asignacion;
}

/* ========== ELIMINAR ========== */
function eliminarTerreno() {
    const id = document.getElementById('terId').value.trim();
    if (!id) return alertaError('Selecciona un terreno para eliminar');

    alertaConfirmacion('¿Deseas eliminar este terreno?', () => {
        fetch(`/api/v1/terrenos/${id}`, { method: 'DELETE' })
            .then(resp => {
                if (!resp.ok) throw new Error('No se pudo eliminar');
                return resp.text();
            })
            .then(() => {
                alertaExito('Terreno eliminado');
                setTimeout(() => location.reload(), 1500);
            })
            .catch(() => alertaError('No se pudo eliminar'));
    });
}

/* ========== LIMPIAR FORMULARIO ========== */
function limpiarFormularioTerreno() {
    document.getElementById('form-terrenos').reset();
    document.getElementById('terId').value = '';
    document.getElementById('terFechaAsignacion').value = '';
}

/* ========== SWEETALERT UTILIDADES ========== */
function alertaExito(msg = 'Guardado correctamente') {
    Swal.fire({ icon: 'success', title: 'Éxito', text: msg, timer: 2000, showConfirmButton: false });
}
function alertaError(msg = 'Ocurrió un error') {
    Swal.fire({ icon: 'error', title: 'Error', text: msg });
}
function alertaConfirmacion(texto, callback) {
    Swal.fire({
        title: '¿Estás seguro?',
        text: texto,
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#198754',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Sí, confirmar',
        cancelButtonText: 'Cancelar'
    }).then(r => { if (r.isConfirmed) callback(); });
}


