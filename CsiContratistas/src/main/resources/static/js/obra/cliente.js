/* ---------- Cargar Cliente en Formulario ---------- */
function cargarCliente(row) {
    document.getElementById("clienteId").value = row.dataset.id || "";
    document.getElementById("clienteTipo").value = row.dataset.tipo || "";
    document.getElementById("clienteTipo").disabled = true;

    // Campos comunes
    document.getElementById("clienteCorreo").value = row.dataset.correo || "";
    document.getElementById("clienteTelefono").value = row.dataset.telefono || "";

    if (row.dataset.tipo === "PERSONA") {
        document.getElementById("clienteDni").value = row.dataset.dni || "";
        document.getElementById("clienteNombre").value = row.dataset.nombre || "";
    } else if (row.dataset.tipo === "EMPRESA") {
        document.getElementById("clienteRuc").value = row.dataset.ruc || "";
        document.getElementById("clienteRazon").value = row.dataset.razon || "";
        document.getElementById("clienteComercial").value = row.dataset.comercial || "";
    }

    ajustarFormulario();
}

/* ---------- Ajustar Visibilidad de Formulario ---------- */
function ajustarFormulario() {
    const tipo = document.getElementById("clienteTipo").value;
    const grupoPersona = document.getElementById("grupoPersona");
    const grupoEmpresa = document.getElementById("grupoEmpresa");

    grupoPersona.style.display = (tipo === "PERSONA") ? "flex" : "none";
    grupoEmpresa.style.display = (tipo === "EMPRESA") ? "flex" : "none";
}

/* ---------- Limpiar Formulario para NUEVO ---------- */
function nuevoCliente() {
    document.getElementById("clienteForm").reset();
    document.getElementById("clienteId").value = "";
    document.getElementById("clienteTipo").disabled = false;
    document.getElementById("clienteTipo").value = "PERSONA";

    ajustarFormulario();
}

/* ---------- Guardar Cliente (crear o editar) ---------- */
function guardarCliente(event) {
    event.preventDefault();

    const id   = document.getElementById("clienteId").value;
    const tipo = document.getElementById("clienteTipo").value;

    if (!tipo) return alertaError("Seleccione el tipo de cliente");

    const dto = {
        tipo_cliente: tipo,
        correo: document.getElementById("clienteCorreo").value,
        telefono: document.getElementById("clienteTelefono").value,
        estado: true // Estado fijo como true
    };

    if (tipo === "PERSONA") {
        dto.dni    = document.getElementById("clienteDni").value;
        dto.nombre = document.getElementById("clienteNombre").value;

        if (!dto.dni || !dto.nombre) return alertaError("Complete DNI y nombre");
    } else if (tipo === "EMPRESA") {
        dto.ruc              = document.getElementById("clienteRuc").value;
        dto.razon_social     = document.getElementById("clienteRazon").value;
        dto.nombre_comercial = document.getElementById("clienteComercial").value;

        if (!dto.ruc || !dto.razon_social || !dto.nombre_comercial)
            return alertaError("Complete todos los campos de empresa");
    }

    const url    = id ? `/api/v1/clientes/${id}` : `/api/v1/clientes`;
    const method = id ? "PUT" : "POST";

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    })
    .then(res => {
        if (!res.ok) throw new Error();
        alertaExito(id ? "Cliente actualizado" : "Cliente registrado");
        setTimeout(() => location.reload(), 1500);
    })
    .catch(() => alertaError("No se pudo guardar el cliente"));
}

/* ---------- Eliminar Cliente ---------- */
function eliminarCliente() {
    const id = document.getElementById("clienteId").value;
    if (!id) return alertaError("Seleccione un cliente para eliminar");

    alertaConfirmacion("¿Deseas eliminar este cliente?", () => {
        fetch(`/api/v1/clientes/${id}`, { method: "DELETE" })
        .then(r => {
            if (!r.ok) throw new Error();
            alertaExito("Cliente eliminado");
            setTimeout(() => location.reload(), 1500);
        })
        .catch(() => alertaError("No se pudo eliminar"));
    });
}

/* ---------- SweetAlert2 (ya lo tienes, pero lo incluimos aquí para el paquete completo) ---------- */
function alertaExito(msg) {
    Swal.fire({ icon: "success", title: "Éxito", text: msg, showConfirmButton: false, timer: 2000 });
}
function alertaError(msg) {
    Swal.fire({ icon: "error", title: "Error", text: msg });
}
function alertaConfirmacion(texto, cb) {
    Swal.fire({
        title: "¿Estás seguro?",
        text: texto,
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#198754",
        cancelButtonColor: "#d33",
        confirmButtonText: "Sí",
        cancelButtonText: "Cancelar"
    }).then(r => { if (r.isConfirmed) cb(); });
}
document.addEventListener("DOMContentLoaded", () => {
    const inputBuscar = document.getElementById("buscarCliente");
    const selectTipo = document.getElementById("filtroTipo");
    const selectEstado = document.getElementById("filtroEstado");

    inputBuscar.addEventListener("input", filtrarClientes);
    selectTipo.addEventListener("change", filtrarClientes);
    selectEstado.addEventListener("change", filtrarClientes);
});

function filtrarClientes() {
    const texto = document.getElementById("buscarCliente").value.toLowerCase();
    const tipo = document.getElementById("filtroTipo").value;
    const estado = document.getElementById("filtroEstado").value;

    const filas = document.querySelectorAll("#tablaClientes tr");

    filas.forEach(fila => {
        const tipoCliente = fila.dataset.tipo || "";
        const estadoCliente = fila.dataset.estado || "";
        const dni = fila.dataset.dni || "";
        const ruc = fila.dataset.ruc || "";
        const nombre = fila.dataset.nombre || "";
        const razon = fila.dataset.razon || "";
        const correo = fila.dataset.correo || "";

        const coincideTexto =
            dni.toLowerCase().includes(texto) ||
            ruc.toLowerCase().includes(texto) ||
            nombre.toLowerCase().includes(texto) ||
            razon.toLowerCase().includes(texto) ||
            correo.toLowerCase().includes(texto);

        const coincideTipo = tipo === "" || tipoCliente === tipo;
        const coincideEstado = estado === "" || estadoCliente === estado;

        if (coincideTexto && coincideTipo && coincideEstado) {
            fila.style.display = "";
        } else {
            fila.style.display = "none";
        }
    });
}
document.getElementById("btnLimpiarFiltros").addEventListener("click", () => {
    document.getElementById("buscarCliente").value = "";
    document.getElementById("filtroTipo").value = "";
    document.getElementById("filtroEstado").value = "";
    filtrarClientes();
});




