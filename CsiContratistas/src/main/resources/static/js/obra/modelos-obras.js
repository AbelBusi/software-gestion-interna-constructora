/* ---------- Seleccionar Modelo de Obra ---------- */
function seleccionarModelo(row) {
    document.getElementById("modeloId").value          = row.dataset.id;
    document.getElementById("modeloNombre").value      = row.dataset.nombre;
    document.getElementById("modeloDescripcion").value = row.dataset.descripcion;
    document.getElementById("modeloTipoObra").value    = row.dataset.id_tipo_obra;
    document.getElementById("modeloUsoDestinado").value       = row.dataset.uso_destinado || '';
    document.getElementById("modeloPisos").value              = row.dataset.pisos || 0;
    document.getElementById("modeloAmbientes").value          = row.dataset.ambientes || 0;
    document.getElementById("modeloAreaTotal").value          = row.dataset.area_total || 0;
    document.getElementById("modeloCapacidadPersonas").value  = row.dataset.capacidad_personas || 0;
    document.getElementById("modeloPrecioPreferencial").value = row.dataset.precio_preferencial || 0;

    const url = row.dataset.url_modelo || '';

    // Mostramos el modelo 3D si existe
    const viewer = document.getElementById("glbViewer");
    viewer.removeAttribute('src');

    if (url) {
        // Asegúrate que la URL es válida y accesible por el navegador
        viewer.setAttribute('src', url);
    } else {
        // Si no hay modelo 3D, mostramos vacío
        viewer.setAttribute('src', '');
    }

    console.log("Mostrando modelo 3D desde:", url);
}


/* ---------- Previsualizar GLB local ---------- */
function mostrarModeloGLB(e) {
    const file = e.target.files[0];
    if (file) document.getElementById('glbViewer').src = URL.createObjectURL(file);
}

/* ---------- Limpiar Formulario ---------- */
function limpiarFormularioModelo() {
    document.getElementById('form-modelo-obra').reset();
    document.getElementById('modeloId').value = '';
    document.getElementById('glbViewer').src  = '';
}

/* ---------- Guardar (crear o editar) ---------- */
async function guardarModeloObra(ev) {
    ev.preventDefault();

    /* 1. Subir GLB si se seleccionó archivo */
    const fileInput  = document.getElementById('modeloArchivo');
    let   urlModelo3d = '';

    if (fileInput.files.length) {
        const fd = new FormData();
        fd.append('file', fileInput.files[0]);

        try {
            const res = await fetch('/api/v1/modelos_obras/upload', { method:'POST', body:fd });
            if (!res.ok) throw new Error();
            urlModelo3d = await res.text();          // "/modelos3d/uuid.glb"
        } catch {
            return alertaError('Error al subir el archivo 3D');
        }
    } else {
        urlModelo3d = document.getElementById('glbViewer').src.replace(location.origin,'') || '';
    }

    const dto = {
    id_tipo_obra:        parseInt(document.getElementById('modeloTipoObra').value),
    url_modelo3d:        urlModelo3d,
    nombre:              document.getElementById('modeloNombre').value,
    descripcion:         document.getElementById('modeloDescripcion').value,
    uso_destinado:       document.getElementById('modeloUsoDestinado').value,
    pisos:               parseInt(document.getElementById('modeloPisos').value) || 0,
    ambientes:           parseInt(document.getElementById('modeloAmbientes').value) || 0,
    area_total:          parseFloat(document.getElementById('modeloAreaTotal').value) || 0,
    capacidad_personas:  parseInt(document.getElementById('modeloCapacidadPersonas').value) || 0,
    precio_preferencial: parseFloat(document.getElementById('modeloPrecioPreferencial').value) || 0,
    estado:              true
};


    const id     = document.getElementById('modeloId').value;
    const url    = id ? `/api/v1/modelos_obras/${id}` : '/api/v1/modelos_obras';
    const method = id ? 'PUT' : 'POST';

    fetch(url, {
        method,
        headers:{'Content-Type':'application/json'},
        body:JSON.stringify(dto)
    })
    .then(r=>{ if(!r.ok) throw new Error(); })
    .then(()=>{
        alertaExito(id ? 'Modelo actualizado' : 'Modelo registrado');
        setTimeout(()=> location.reload(), 1500);
    })
    .catch(()=> alertaError('No se pudo guardar el modelo'));
}

/* ---------- Eliminar ---------- */
function eliminarModelo() {
    const id = document.getElementById('modeloId').value;
    if (!id) return alertaError('Selecciona un modelo para eliminar');

    alertaConfirmacion('¿Eliminar este modelo de obra?', () => {
        fetch(`/api/v1/modelos_obras/${id}`, { method:'DELETE' })
        .then(r=>{ if(!r.ok) throw new Error(); })
        .then(()=>{
            alertaExito('Modelo eliminado');
            setTimeout(()=> location.reload(), 1500);
        })
        .catch(()=> alertaError('No se pudo eliminar'));
    });
}

/* ---------- SweetAlert2 wrappers ---------- */
function alertaExito(msg){ Swal.fire({icon:'success',title:'Éxito',text:msg,showConfirmButton:false,timer:2000}); }
function alertaError(msg){ Swal.fire({icon:'error',title:'Error',text:msg}); }
function alertaConfirmacion(texto,cb){
    Swal.fire({
        title:'¿Estás seguro?',
        text:texto,
        icon:'warning',
        showCancelButton:true,
        confirmButtonColor:'#198754',
        cancelButtonColor:'#d33',
        confirmButtonText:'Sí',
        cancelButtonText:'Cancelar'
    }).then(r=>{ if(r.isConfirmed) cb(); });
}
