function verModeloObra(id) {
    const modal = new bootstrap.Modal(document.getElementById('modeloObraModal'));
    const contenedor = document.getElementById('modeloObraContenido');

    contenedor.innerHTML = `
        <div class="text-center text-muted">
            <i class="fa fa-spinner fa-spin fa-2x"></i>
            <p class="mt-3">Cargando datos del modelo...</p>
        </div>`;
    modal.show();

    fetch(`/api/v1/modelos_obras/${id}`)
        .then(res => res.ok ? res.json() : Promise.reject())
        .then(data => {
            const {
                id_modelo_obra, nombre, descripcion, uso_destinado,
                pisos, ambientes, area_total, capacidad_personas,
                precio_preferencial, estado, id_tipo_obra
            } = data;

            let html = `
                <dl class="row">
                    <dt class="col-sm-4">Nombre</dt><dd class="col-sm-8">${nombre}</dd>
                    <dt class="col-sm-4">Descripción</dt><dd class="col-sm-8">${descripcion}</dd>
                    <dt class="col-sm-4">Uso Destinado</dt><dd class="col-sm-8">${uso_destinado}</dd>
                    <dt class="col-sm-4">Pisos</dt><dd class="col-sm-8">${pisos}</dd>
                    <dt class="col-sm-4">Ambientes</dt><dd class="col-sm-8">${ambientes}</dd>
                    <dt class="col-sm-4">Área Total</dt><dd class="col-sm-8">${area_total} m²</dd>
                    <dt class="col-sm-4">Capacidad</dt><dd class="col-sm-8">${capacidad_personas} personas</dd>
                    <dt class="col-sm-4">Precio Preferencial</dt><dd class="col-sm-8">S/. ${precio_preferencial}</dd>
                    <dt class="col-sm-4">Estado</dt><dd class="col-sm-8">
                        <span class="badge ${estado ? 'bg-success' : 'bg-secondary'}">
                            ${estado ? 'Activo' : 'Inactivo'}
                        </span>
                    </dd>`;

            if (id_tipo_obra) {
                html += `
                    <dt class="col-sm-4">Tipo de Obra</dt>
                    <dd class="col-sm-8 d-flex align-items-center gap-2">
                        ${id_tipo_obra}
                        <button class="btn btn-outline-dark btn-sm p-1"
                                onclick="verTipoObraDesdeModelo(${id_tipo_obra})">
                            <i class="fa fa-eye"></i>
                        </button>
                    </dd>`;
            }

            html += `</dl>`;
            contenedor.innerHTML = html;
        })
        .catch(() => {
            contenedor.innerHTML = `
                <div class="alert alert-danger text-center">
                    <i class="fa fa-triangle-exclamation me-1"></i> No se pudo cargar el modelo.
                </div>`;
        });
}

function verTipoObraDesdeModelo(id) {
    const detalleModal = document.getElementById('detalleModal');
    historialDetalle.length = 0; // Limpia historial
    detalleModal.dataset.volviendo = ""; // No estamos volviendo
    bootstrap.Modal.getOrCreateInstance(document.getElementById('modeloObraModal')).hide();

    setTimeout(() => verDetalle(null, 'tipo_obra', id), 300);
}
