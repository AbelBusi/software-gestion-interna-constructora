/* ---------- Cargar Obra en Formulario ---------- */
function cargarObra(row) {
    document.getElementById("obraId").value = row.dataset.id || "";
    document.getElementById("obraNombre").value = row.dataset.nombre || "";
    document.getElementById("obraResponsable").value = row.dataset.responsable || "";
    document.getElementById("obraModelo").value = row.dataset.modelo || "";
    document.getElementById("obraTerreno").value = row.dataset.terreno || "";
    document.getElementById("obraTipoConstruccion").value = row.dataset.tipo || "";
    document.getElementById("obraInicio").value = row.dataset.inicio || "";
    document.getElementById("obraFin").value = row.dataset.fin || "";
    document.getElementById("obraEstado").checked = row.dataset.estado === "true";
}

/* ---------- Nueva Obra ---------- */
function nuevaObra() {
    // Limpiar formulario
    document.getElementById("obraId").value = "";
    document.getElementById("obraNombre").value = "";
    document.getElementById("obraInicio").value = new Date().toISOString().split('T')[0];
    document.getElementById("obraFin").value = "";

    // Cargar selects sin nada seleccionado
    cargarSelectResponsables("obraResponsable", null);
    cargarSelectModelos("obraModelo", null);
    cargarSelectTerrenos("obraTerreno", null);
    cargarSelectTiposConstruccion("obraTipo", null);

    // Cambiar título del modal
    document.querySelector("#formObra .modal-title").innerHTML = '<i class="fa fa-plus me-2"></i> Registrar Obra';

    // Activar el modal (por si no se abre con data-bs-toggle)
    const modal = new bootstrap.Modal(document.getElementById("formObra"));
    modal.show();
}

/* ---------- Guardar Obra (crear o editar) ---------- */
function guardarObra(event) {
    event.preventDefault();

    const fechaInicio = document.getElementById("obraInicio").value;
    const fechaFin = document.getElementById("obraFin").value;

    if (fechaFin < fechaInicio) {
        return alertaError("La fecha de finalización no puede ser anterior a la fecha de inicio.");
    }

    const id = document.getElementById("obraId").value;

    const dto = {
        nombre: document.getElementById("obraNombre").value,
        id_responsable: parseInt(document.getElementById("obraResponsable").value),
        id_modelo_obra: parseInt(document.getElementById("obraModelo").value),
        id_terreno: parseInt(document.getElementById("obraTerreno").value),
        id_tipo_construccion: parseInt(document.getElementById("obraTipo").value),
        fecha_inicio: fechaInicio,
        fecha_finalizacion: fechaFin,
        estado:true
    };

    fetch(`/api/v1/obras/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    })
    .then(res => {
        if (!res.ok) throw new Error("Error al modificar");
        bootstrap.Offcanvas.getInstance(document.getElementById("formObra")).hide();
        alert("Obra actualizada correctamente");
        location.reload();
    })
    .catch(err => {
        console.error(err);
        alert("Error al actualizar la obra");
    });
}
8
/* ---------- Eliminar Obra ---------- */
function eliminarObra(row) {
    const id = row.dataset.id;
    if (!id) return alertaError("Seleccione una obra para eliminar");

    alertaConfirmacion("¿Deseas eliminar esta obra?", () => {
        fetch(`/api/v1/obras/${id}`, { method: "DELETE" })
        .then(r => {
            if (!r.ok) throw new Error();
            alertaExito("Obra eliminada");
            setTimeout(() => location.reload(), 1500);
        })
        .catch(() => alertaError("No se pudo eliminar la obra"));
    });
}

function verDetalle(event, tipo, id) {
    if (event) event.stopPropagation();

    const offcanvasEl = document.querySelector('.offcanvas.show');

    if (offcanvasEl) {
        const instancia = bootstrap.Offcanvas.getInstance(offcanvasEl);
        instancia.hide();

        // Espera a que el offcanvas se oculte antes de continuar
        setTimeout(() => abrirModalDetalle(tipo, id), 300);
    } else {
        abrirModalDetalle(tipo, id);
    }
}

function abrirModalDetalle(tipo, id) {
    const modalEl = document.getElementById('detalleModal');
    const contenido = document.getElementById('detalleContenido');
    const modal = bootstrap.Modal.getOrCreateInstance(modalEl);

    contenido.innerHTML = `
        <div class="text-center text-muted">
            <i class="fa fa-spinner fa-spin fa-2x"></i>
            <p class="mt-3">Cargando datos...</p>
        </div>`;
    modal.show();

    let endpoint = "";
    switch (tipo) {
        case 'responsable': endpoint = `/api/v1/empleados/${id}`; break;
        case 'modelo': endpoint = `/api/v1/modelos_obras/${id}`; break;
        case 'terreno': endpoint = `/api/v1/terrenos/${id}`; break;
        case 'tipo': endpoint = `/api/v1/tipos_construcciones/${id}`; break;
    }

    fetch(endpoint)
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(data => {
            let html = '<dl class="row">';
            for (const key in data) {
                const label = key.replace(/_/g, " ").replace(/\b\w/g, l => l.toUpperCase());
                html += `<dt class="col-sm-4">${label}</dt><dd class="col-sm-8">${data[key]}</dd>`;
            }
            html += '</dl>';
            contenido.innerHTML = html;
        })
        .catch(() => {
            contenido.innerHTML = `
                <div class="alert alert-danger text-center">
                    <i class="fa fa-triangle-exclamation me-1"></i> Error al cargar información.
                </div>`;
        });
}

function cargarSelectConNombre(idSelect, urlApi, idSeleccionado) {
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="">Cargando...</option>';

    fetch(urlApi)
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(data => {
            select.innerHTML = ''; // limpiar

            data.forEach(item => {
                const option = document.createElement('option');
                option.value = item.id || item.id_empleado || item.id_modelo_obra || item.id_terreno || item.id_tipo_construccion;
                option.textContent = item.nombre || item.descripcion || `ID ${option.value}`;
                select.appendChild(option);
            });

            // Ahora sí: asignar el seleccionado
            select.value = idSeleccionado;
        })
        .catch(err => {
            console.error("Error al cargar " + idSelect, err);
            select.innerHTML = '<option value="">Error</option>';
        });
}

let listaResponsables = []; // variable global

function cargarSelectResponsables(idSelect, idSeleccionado) {
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="">Cargando...</option>';

    fetch("/api/v1/empleados")
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(empleados => {
            listaResponsables = empleados; // guarda globalmente
            renderizarResponsables(empleados, idSeleccionado);
        })
        .catch(err => {
            console.error("Error cargando responsables", err);
            select.innerHTML = '<option value="">Error al cargar</option>';
        });
}

function renderizarResponsables(responsables, idSeleccionado) {
    const select = document.getElementById("obraResponsable");
    select.innerHTML = "";

    responsables
        .filter(emp => emp.estado === "Activo") // <-- Solo activos
        .forEach(emp => {
            const option = document.createElement("option");
            option.value = emp.id_empleado;
            option.textContent = `ID: ${emp.id_empleado} - ${emp.nombre} ${emp.apellidos} (DNI: ${emp.dni})`;
            select.appendChild(option);
        });

    if (idSeleccionado) {
        select.value = idSeleccionado;
    }
}

// Activar Select2 cuando se abre el modal de edición
document.getElementById("formObra").addEventListener("shown.bs.modal", () => {
    const selectResponsable = document.getElementById("obraResponsable");
    const selectModelo = document.getElementById("obraModelo");
    const selectTerreno = document.getElementById("obraTerreno");
    const selectTipo = document.getElementById("obraTipo");
    // Limpiar y volver a activar select2 en responsable
    if ($(selectResponsable).hasClass("select2-hidden-accessible")) {
        $(selectResponsable).select2('destroy');
    }
    $(selectResponsable).select2({
        placeholder: "Buscar responsable...",
        width: '100%',
        allowClear: true,
        dropdownParent: $('#formObra')
    });

    // Limpiar y volver a activar select2 en modelo
    if ($(selectModelo).hasClass("select2-hidden-accessible")) {
        $(selectModelo).select2('destroy');
    }
    $(selectModelo).select2({
        placeholder: "Buscar modelo...",
        width: '100%',
        allowClear: true,
        dropdownParent: $('#formObra')
    });

    if ($(selectTerreno).hasClass("select2-hidden-accessible")) {
        $(selectTerreno).select2('destroy');
    }
    $(selectTerreno).select2({
        placeholder: "Buscar terreno...",
        width: '100%',
        allowClear: true,
        dropdownParent: $('#formObra')
    });

    if ($(selectTipo).hasClass("select2-hidden-accessible")) {
        $(selectTipo).select2('destroy');
    }
    $(selectTipo).select2({
        placeholder: "Buscar tipo de construcción...",
        width: '100%',
        allowClear: true,
        dropdownParent: $('#formObra')
    });

});

function extraerFechaDeLocalDateTime(fechaHora) {
    if (!fechaHora) return "";
    // Ejemplo de fechaHora: "2025-08-14T00:00:00"
    return fechaHora.split("T")[0]; // Devuelve: "2025-08-14"
}

function filtrarResponsables(termino) {
    const filtrados = listaResponsables.filter(emp =>
        emp.dni.toLowerCase().includes(termino.toLowerCase()) ||
        emp.nombre.toLowerCase().includes(termino.toLowerCase()) ||
        emp.apellidos.toLowerCase().includes(termino.toLowerCase())
    );

    renderizarResponsables(filtrados);
}

function editarObra(tr) {
    const id = tr.dataset.id;
    document.getElementById("obraId").value = id;

    fetch(`/api/v1/obras/${id}`)
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(data => {
            document.getElementById("obraNombre").value = data.nombre;
            document.getElementById("obraInicio").value = extraerFechaDeLocalDateTime(data.fecha_inicio);
            document.getElementById("obraFin").value = extraerFechaDeLocalDateTime(data.fecha_finalizacion);
            data.estado=true;

            cargarSelectResponsables("obraResponsable", data.id_responsable);
            cargarSelectModelos("obraModelo", data.id_modelo_obra);
            cargarSelectTerrenos("obraTerreno", data.id_terreno);
            cargarSelectTiposConstruccion("obraTipo", data.id_tipo_construccion);
        });
}

function verSeleccionado(tipo, idSelect) {
    const select = document.getElementById(idSelect);
    const id = select.value;

    if (!id) {
        alert("Selecciona una opción antes de ver detalles.");
        return;
    }

    verDetalle(null, tipo, id);
}

document.getElementById("obraForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const id = document.getElementById("obraId").value;

    const dto = {
        nombre: document.getElementById("obraNombre").value,
        id_responsable: parseInt(document.getElementById("obraResponsable").value),
        id_modelo_obra: parseInt(document.getElementById("obraModelo").value),
        id_terreno: parseInt(document.getElementById("obraTerreno").value),
        id_tipo_construccion: parseInt(document.getElementById("obraTipo").value),
        fecha_inicio: document.getElementById("obraInicio").value + "T00:00:00",
        fecha_finalizacion: document.getElementById("obraFin").value + "T00:00:00",
        estado: true
    };

    const url = id ? `/api/v1/obras/${id}` : `/api/v1/obras`;
    const method = id ? "PUT" : "POST";

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    })
    .then(res => {
        if (!res.ok) throw new Error("Error al guardar obra");
        bootstrap.Modal.getInstance(document.getElementById("formObra")).hide();
        alertaExito(id ? "Obra actualizada correctamente" : "Obra registrada correctamente");
        setTimeout(() => location.reload(), 1500);
    })
    .catch(err => {
        console.error(err);
        alertaError("No se pudo guardar la obra");
    });
});

let listaModelos = [];

function cargarSelectModelos(idSelect, idSeleccionado) {
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="">Cargando...</option>';

    fetch("/api/v1/modelos_obras")
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(modelos => {
            listaModelos = modelos;
            renderizarModelos(modelos, idSeleccionado);
        })
        .catch(err => {
            console.error("Error cargando modelos", err);
            select.innerHTML = '<option value="">Error al cargar</option>';
        });
}

function renderizarModelos(modelos, idSeleccionado) {
    const select = document.getElementById("obraModelo");
    select.innerHTML = "";

    modelos
        .filter(m => m.estado === true) // <-- Solo activos
        .forEach(modelo => {
            const option = document.createElement("option");
            option.value = modelo.id_modelo_obra;
            option.textContent = `ID: ${modelo.id_modelo_obra} - ${modelo.nombre}`;
            select.appendChild(option);
        });

    if (idSeleccionado) {
        select.value = idSeleccionado;
    }
}


let listaTerrenos = [];

function cargarSelectTerrenos(idSelect, idSeleccionado) {
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="">Cargando...</option>';

    fetch("/api/v1/terrenos")
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(terrenos => {
            listaTerrenos = terrenos;
            renderizarTerrenos(terrenos, idSeleccionado);
        })
        .catch(err => {
            console.error("Error cargando terrenos", err);
            select.innerHTML = '<option value="">Error al cargar</option>';
        });
}

function renderizarTerrenos(terrenos, idSeleccionado) {
    const select = document.getElementById("obraTerreno");
    select.innerHTML = "";

    terrenos
        .filter(t => t.estado === true) // <-- Solo activos
        .forEach(t => {
            const option = document.createElement("option");
            option.value = t.id_terreno;
            option.textContent = `ID: ${t.id_terreno} - ${t.area_total} m² - Acceso: ${t.acceso_vial}`;
            select.appendChild(option);
        });

    if (idSeleccionado) {
        select.value = idSeleccionado;
    }
}


let listaTiposConstruccion = [];

function cargarSelectTiposConstruccion(idSelect, idSeleccionado) {
    const select = document.getElementById(idSelect);
    select.innerHTML = '<option value="">Cargando...</option>';

    fetch("/api/v1/tipos_construcciones")
        .then(r => r.ok ? r.json() : Promise.reject())
        .then(tipos => {
            listaTiposConstruccion = tipos;
            renderizarTiposConstruccion(tipos, idSeleccionado);
        })
        .catch(err => {
            console.error("Error cargando tipos de construcción", err);
            select.innerHTML = '<option value="">Error al cargar</option>';
        });
}

function renderizarTiposConstruccion(tipos, idSeleccionado) {
    const select = document.getElementById("obraTipo");
    select.innerHTML = "";

    tipos
        .filter(t => t.estado === true) // <-- Solo activos
        .forEach(t => {
            const option = document.createElement("option");
            option.value = t.id_tipo_construccion;
            option.textContent = `ID: ${t.id_tipo_construccion} - ${t.nombre}`;
            select.appendChild(option);
        });

    if (idSeleccionado) {
        select.value = idSeleccionado;
    }
}