const TASA_IGV = 0.18; // 18%

function calcularTotalFinanciamiento() {
    let subTotalMateriales = 0;
    document.querySelectorAll('#materialesContainer [name="material_total"]').forEach(input => {
        subTotalMateriales += parseFloat(input.value) || 0;
    });

    let subTotalEmpleados = 0;
    document.querySelectorAll('#empleadosContainer [name="empleado_total"]').forEach(input => {
        subTotalEmpleados += parseFloat(input.value) || 0;
    });

    const subTotalGeneral = subTotalMateriales + subTotalEmpleados;
    const igv = subTotalGeneral * TASA_IGV;
    const total = subTotalGeneral + igv;

    document.getElementById("subTotalFinanciamiento").value = subTotalGeneral.toFixed(2);
    document.getElementById("igvFinanciamiento").value = igv.toFixed(2);
    document.getElementById("totalFinanciamiento").value = total.toFixed(2);
}

async function verFinanciamiento(row) {
    const idObra = row.getAttribute('data-id');
    const nombreObra = row.getAttribute('data-nombre');

    try {
        const resBasico = await fetch(`/api/v1/financiamientos/por-obra/${idObra}`);
        if (!resBasico.ok) throw new Error("Financiamiento no encontrado");
        const fin = await resBasico.json();

        const resCompleto = await fetch(`/api/v1/financiamientos/completo/${fin.id_financiamiento}`);
        if (!resCompleto.ok) throw new Error("Detalle completo no disponible");
        const data = await resCompleto.json();

        renderFormularioFinanciamiento(idObra, nombreObra, data);
    } catch (err) {
        console.error("Error en verFinanciamiento:", err);
        renderFormularioFinanciamiento(idObra, nombreObra, null);
    }
}

function generarCodigoFinanciamiento() {
    const min = 0;
    const max = 8;
    const randomNumber = Math.floor(Math.random() * (max - min + 1)) + min;
    const timestamp = new Date().getTime(); // Usar timestamp para mayor unicidad
    return `O-${randomNumber}-${timestamp.toString().slice(-6)}`; // O-8-123456
}

function inicializarCalculosFinanciamiento() {
    const materialesContainer = document.getElementById("materialesContainer");
    const empleadosContainer = document.getElementById("empleadosContainer");

    // Observar cambios en el DOM para materiales y empleados
    // Esto es crucial porque los elementos se agregan/eliminan dinámicamente
    const observer = new MutationObserver(mutations => {
        mutations.forEach(mutation => {
            if (mutation.type === 'childList') {
                mutation.addedNodes.forEach(node => {
                    if (node.nodeType === 1 && (node.id === 'materialesContainer' || node.id === 'empleadosContainer' || node.closest('#materialesContainer') || node.closest('#empleadosContainer'))) {
                        adjuntarListenersDeCalculo();
                    }
                });
                // También recalcular si se eliminan filas
                calcularTotalFinanciamiento();
            }
        });
    });

    observer.observe(materialesContainer, { childList: true, subtree: true });
    observer.observe(empleadosContainer, { childList: true, subtree: true });

    // Adjuntar listeners iniciales
    adjuntarListenersDeCalculo();
}

function adjuntarListenersDeCalculo() {
    const materialesContainer = document.getElementById("materialesContainer");
    const empleadosContainer = document.getElementById("empleadosContainer");

    // Limpiar listeners existentes para evitar duplicados
    materialesContainer.querySelectorAll('[name="material_cantidad"], [name="material_total"]').forEach(input => {
        input.removeEventListener('input', calcularTotalFinanciamiento);
    });
    empleadosContainer.querySelectorAll('[name="empleado_dias"], [name="empleado_total"]').forEach(input => {
        input.removeEventListener('input', calcularTotalFinanciamiento);
    });

    // Adjuntar listeners a las cantidades y totales de materiales
    materialesContainer.querySelectorAll('[name="material_cantidad"], [name="material_total"]').forEach(input => {
        input.addEventListener('input', calcularTotalFinanciamiento);
    });

    // Adjuntar listeners a los días y totales de empleados
    empleadosContainer.querySelectorAll('[name="empleado_dias"], [name="empleado_total"]').forEach(input => {
        input.addEventListener('input', calcularTotalFinanciamiento);
    });

    // Calcular el total al inicializar o después de cambios mayores
    calcularTotalFinanciamiento();
}

async function renderFormularioFinanciamiento(idObra, nombreObra, data) {
    const f = data?.financiamiento || {};
    const materiales = data?.materiales || [];
    const empleados = data?.empleados || [];

    const listaMateriales = await fetch('/api/v1/materiales').then(r => r.json());

    // Generar código de financiamiento si es un nuevo registro
    const codigoFinanciamiento = f.codigo_financiamiento || generarCodigoFinanciamiento();

    document.getElementById("detalleFinanciamiento").innerHTML = `
        <form onsubmit="guardarFinanciamiento(event, ${f.id_financiamiento || 'null'})">
            <input type="hidden" name="id_obra" value="${idObra}">
            <h6 class="fw-bold mb-3 text-primary">Financiamiento para: ${nombreObra}</h6>

            <div class="row g-3">
                <div class="col-md-6">
                    <label class="form-label">Código</label>
                    <input type="text" class="form-control" name="codigo" value="${codigoFinanciamiento}" required readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Forma de Pago</label>
                    <select class="form-select" name="forma_pago" required>
                        <option value="">Seleccione una forma de pago</option>
                        <option value="Efectivo" ${f.forma_pago === 'Efectivo' ? 'selected' : ''}>Efectivo</option>
                        <option value="Credito a Cuotas" ${f.forma_pago === 'Credito a Cuotas' ? 'selected' : ''}>Crédito a Cuotas</option>
                        <option value="Transferencia Bancaria" ${f.forma_pago === 'Transferencia Bancaria' ? 'selected' : ''}>Transferencia Bancaria</option>
                        <option value="Cheque" ${f.forma_pago === 'Cheque' ? 'selected' : ''}>Cheque</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Subtotal</label>
                    <input type="number" step="0.01" class="form-control" name="sub_total" id="subTotalFinanciamiento" value="${f.sub_total || 0}" required readonly>
                </div>
                <div class="col-md-4">
                    <label class="form-label">IGV</label>
                    <input type="number" step="0.01" class="form-control" name="igv" id="igvFinanciamiento" value="${f.igv || 0}" required readonly>
                </div>
                <div class="col-md-4">
                    <label class="form-label">Total</label>
                    <input type="number" step="0.01" class="form-control" name="total" id="totalFinanciamiento" value="${f.total || 0}" required readonly>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Fecha</label>
                <input type="datetime-local" class="form-control" name="fecha"
                    value="${f.fecha_financiamiento ? f.fecha_financiamiento.replace(' ', 'T').split('.')[0] : ''}" required>
                </div>
                <div class="col-md-6">
                    <label class="form-label">Estado</label>
                    <select class="form-select" name="estado">
                        <option value="true" ${f.estado ? 'selected' : ''}>Activo</option>
                        <option value="false" ${!f.estado ? 'selected' : ''}>Inactivo</option>
                    </select>
                </div>
            </div>

            <hr>
            <h6 class="fw-bold text-success mb-3">Materiales</h6>
            <div class="table-responsive">
                <table class="table table-bordered align-middle">
                    <thead class="table-light text-center">
                        <tr>
                            <th>Material</th>
                            <th>Nombre</th>
                            <th>Cantidad</th>
                            <th>Precio Total</th>
                            <th>Precio Unitario</th>
                        </tr>
                    </thead>
                    <tbody id="materialesContainer"></tbody>
                </table>
            </div>
            <button type="button" class="btn btn-outline-success btn-sm mb-4" onclick="agregarMaterialDesdeLista()">+ Material</button>

            <h6 class="fw-bold text-danger mb-3">Empleados</h6>
            <div class="table-responsive">
                <table class="table table-bordered align-middle">
                    <thead class="table-light text-center">
                        <tr>
                            <th>Empleado</th>
                            <th>DNI</th>
                            <th>Días</th>
                            <th>Costo Total</th>
                        </tr>
                    </thead>
                    <tbody id="empleadosContainer"></tbody>
                </table>
            </div>
            <button type="button" class="btn btn-outline-danger btn-sm mb-3" onclick="agregarEmpleadoDesdeLista()">+ Empleado</button>

            <div class="text-end mt-4">
                <button class="btn btn-primary" type="submit">
                    <i class="fa fa-save me-1"></i> Guardar
                </button>
            </div>
        </form>
    `;

    await renderMateriales(materiales, listaMateriales);
    await renderEmpleados(empleados);

    // Inicializar cálculos automáticos
    inicializarCalculosFinanciamiento();
}

async function renderMateriales(materiales, listaMateriales) {
    const contenedor = document.getElementById("materialesContainer");
    contenedor.innerHTML = "";

    for (const m of materiales) {
        const materialInfo = listaMateriales.find(mat => mat.id_material === m.id_material);
        const nombre = materialInfo?.nombre || "Desconocido";
        const precioUnitario = materialInfo?.precio_unitario || 0;

        const tr = document.createElement("tr");
        tr.className = "text-center";
        tr.innerHTML = `
            <td>
                <select class="form-select form-select-sm" name="material_id" onchange="actualizarDatosMaterial(this)">
                    <option value="">Seleccione</option>
                    ${listaMateriales.map(mat => `
                        <option value="${mat.id_material}" ${mat.id_material === m.id_material ? 'selected' : ''}>${mat.nombre}</option>
                    `).join("")}
                </select>
            </td>
            <td><input type="text" class="form-control bg-light" value="${nombre}" readonly></td>
            <td><input type="number" class="form-control" name="material_cantidad" value="${m.cantidad}" required></td>
            <td><input type="number" step="0.01" class="form-control" name="material_total" value="${m.precio_total}" required></td>
            <td><input type="text" class="form-control bg-light" value="S/ ${precioUnitario}" readonly></td>
            <td>
                <button type="button" class="btn btn-sm btn-danger" onclick="eliminarFilaItem(this)">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;
        contenedor.appendChild(tr);
    }

    // Asegurarse de que el encabezado también tenga la columna de acciones
    const materialHeader = document.querySelector('#materialesContainer').closest('table').querySelector('thead tr');
    if (!materialHeader.querySelector('th.acciones-material')) { // Evitar duplicar
        const th = document.createElement('th');
        th.className = 'acciones-material';
        th.textContent = 'Acciones';
        materialHeader.appendChild(th);
    }
}

async function renderEmpleados(empleados) {
    const contenedor = document.getElementById("empleadosContainer");
    const listaEmpleados = await fetch('/api/v1/empleados').then(r => r.json());
    contenedor.innerHTML = "";

    for (const e of empleados) {
        const info = listaEmpleados.find(emp => emp.id_empleado === e.id_empleado) || {};
        const tr = document.createElement("tr");
        tr.className = "text-center";
        tr.innerHTML = `
            <td>
                <select class="form-select form-select-sm" name="empleado_id" onchange="actualizarDatosEmpleado(this)">
                    <option value="">Seleccione</option>
                    ${listaEmpleados.map(emp => `
                        <option value="${emp.id_empleado}" ${emp.id_empleado === e.id_empleado ? 'selected' : ''}>
                            ${emp.dni} - ${emp.nombre} ${emp.apellidos}
                        </option>
                    `).join("")}
                </select>
            </td>
            <td><input type="text" class="form-control bg-light" value="${info.dni || ''}" readonly></td>
            <td><input type="number" class="form-control" name="empleado_dias" value="${e.dias_participacion}" required></td>
            <td><input type="number" step="0.01" class="form-control" name="empleado_total" value="${e.costo_total}" required></td>
            <td>
                <button type="button" class="btn btn-sm btn-danger" onclick="eliminarFilaItem(this)">
                    <i class="fa fa-trash"></i>
                </button>
            </td>
        `;
        contenedor.appendChild(tr);
    }

    // Asegurarse de que el encabezado también tenga la columna de acciones
    const empleadoHeader = document.querySelector('#empleadosContainer').closest('table').querySelector('thead tr');
    if (!empleadoHeader.querySelector('th.acciones-empleado')) { // Evitar duplicar
        const th = document.createElement('th');
        th.className = 'acciones-empleado';
        th.textContent = 'Acciones';
        empleadoHeader.appendChild(th);
    }
}

async function agregarMaterialDesdeLista() {
    const contenedor = document.getElementById("materialesContainer");
    const listaMateriales = await fetch('/api/v1/materiales').then(r => r.json());

    const tr = document.createElement("tr");
    tr.className = "text-center";
    tr.innerHTML = `
        <td>
            <select class="form-select form-select-sm" name="material_id" onchange="actualizarDatosMaterial(this)">
                <option value="">Seleccione</option>
                ${listaMateriales.map(mat => `
                    <option value="${mat.id_material}">${mat.nombre}</option>
                `).join("")}
            </select>
        </td>
        <td><input type="text" class="form-control bg-light" value="" readonly></td>
        <td><input type="number" class="form-control" name="material_cantidad" value="1" required></td>
        <td><input type="number" step="0.01" class="form-control" name="material_total" value="0.00" required></td>
        <td><input type="text" class="form-control bg-light" value="" readonly></td>
        <td>
            <button type="button" class="btn btn-sm btn-danger" onclick="eliminarFilaItem(this)">
                <i class="fa fa-trash"></i>
            </button>
        </td>
    `;
    contenedor.appendChild(tr);
}

async function actualizarDatosMaterial(select) {
    const id = parseInt(select.value);
    const row = select.closest("tr");
    if (!id) {
        // Si no se selecciona nada, limpiar y recalcular
        row.cells[1].querySelector("input").value = "";
        row.cells[2].querySelector("input").value = "";
        row.cells[3].querySelector("input").value = "";
        row.cells[4].querySelector("input").value = "";
        calcularTotalFinanciamiento();
        return;
    }

    try {
        const res = await fetch(`/api/v1/materiales/${id}`);
        if (!res.ok) throw new Error();
        const material = await res.json();

        const filas = document.querySelectorAll("#materialesContainer tr");
        for (const fila of filas) {
            const otroSelect = fila.querySelector("[name='material_id']");
            if (fila !== row && parseInt(otroSelect.value) === id) {
                const cantidadActualInput = fila.querySelector("[name='material_cantidad']");
                const totalActualInput = fila.querySelector("[name='material_total']");

                const cantidadNuevaInput = row.querySelector("[name='material_cantidad']");
                const totalNuevaInput = row.querySelector("[name='material_total']");

                const cantidadActual = parseFloat(cantidadActualInput.value) || 0;
                const cantidadNueva = parseFloat(cantidadNuevaInput.value) || 0;

                const nuevaCantidad = cantidadActual + cantidadNueva;
                cantidadActualInput.value = nuevaCantidad.toFixed(2);
                totalActualInput.value = (nuevaCantidad * material.precio_unitario).toFixed(2);

                row.remove();
                calcularTotalFinanciamiento(); // Recalcular después de combinar
                return;
            }
        }

        row.cells[1].querySelector("input").value = material.nombre || "";
        row.cells[4].querySelector("input").value = `S/ ${material.precio_unitario?.toFixed(2) || 0}`;

        const cantidadInput = row.cells[2].querySelector("input");
        const totalInput = row.cells[3].querySelector("input");

        // Si no hay cantidad, establecer 1 por defecto al seleccionar un material nuevo
        if (parseFloat(cantidadInput.value) === 0 || isNaN(parseFloat(cantidadInput.value))) {
            cantidadInput.value = 1;
        }

        const cantidad = parseFloat(cantidadInput.value);
        if (!isNaN(cantidad)) {
            totalInput.value = (cantidad * material.precio_unitario).toFixed(2);
        }

        // Listener para recalcular al cambiar cantidad - ya se agrega en adjuntarListenersDeCalculo
        // Asegurarse de que el input tenga el listener principal de cálculo
        if (!cantidadInput.hasAttribute('data-listener-attached')) {
             cantidadInput.addEventListener('input', () => {
                const newCantidad = parseFloat(cantidadInput.value);
                if (!isNaN(newCantidad)) {
                    totalInput.value = (newCantidad * material.precio_unitario).toFixed(2);
                }
                calcularTotalFinanciamiento(); // Recalcular después de actualizar esta fila
            });
            cantidadInput.setAttribute('data-listener-attached', 'true');
        }

        calcularTotalFinanciamiento(); // Recalcular después de que los datos de la fila se actualicen
    } catch (err) {
        console.error("Error al actualizar datos de material:", err);
        row.cells[1].querySelector("input").value = "Error";
        row.cells[4].querySelector("input").value = "N/D";
        calcularTotalFinanciamiento();
    }
}

async function actualizarDatosEmpleado(select) {
    const id = parseInt(select.value);
    const row = select.closest("tr");
    if (!id) {
        // Si no se selecciona nada, limpiar y recalcular
        row.cells[1].querySelector("input").value = "";
        row.cells[2].querySelector("input").value = "";
        row.cells[3].querySelector("input").value = "";
        calcularTotalFinanciamiento();
        return;
    }

    try {
        const res = await fetch(`/api/v1/empleados/${id}`);
        if (!res.ok) throw new Error();
        const empleado = await res.json();

        const filas = document.querySelectorAll("#empleadosContainer tr");
        for (const fila of filas) {
            const otroSelect = fila.querySelector("[name='empleado_id']");
            if (fila !== row && parseInt(otroSelect.value) === id) {
                const diasActualInput = fila.querySelector("[name='empleado_dias']");
                const totalActualInput = fila.querySelector("[name='empleado_total']");

                const diasNuevoInput = row.querySelector("[name='empleado_dias']");

                const diasActual = parseFloat(diasActualInput.value) || 0;
                const diasNuevo = parseFloat(diasNuevoInput.value) || 0;
                const diasTotales = diasActual + diasNuevo;

                diasActualInput.value = diasTotales;
                totalActualInput.value = (diasTotales * (empleado.costo_diario || 100)).toFixed(2); // Usar costo_diario del empleado si existe

                row.remove();
                calcularTotalFinanciamiento(); // Recalcular después de combinar
                return;
            }
        }

        row.cells[1].querySelector("input").value = empleado.dni || "";

        const inputDias = row.cells[2].querySelector("input");
        const inputTotal = row.cells[3].querySelector("input");

        const costoDiario = empleado.costo_diario || 100; // Preferir costo_diario del empleado, sino 100 por defecto

        // Si no hay días, establecer 1 por defecto al seleccionar un empleado nuevo
        if (parseFloat(inputDias.value) === 0 || isNaN(parseFloat(inputDias.value))) {
            inputDias.value = 1;
        }

        const dias = parseFloat(inputDias.value);
        inputTotal.value = (dias * costoDiario).toFixed(2);

        // Listener para recalcular al cambiar días - ya se agrega en adjuntarListenersDeCalculo
        // Asegurarse de que el input tenga el listener principal de cálculo
        if (!inputDias.hasAttribute('data-listener-attached')) {
            inputDias.addEventListener("input", () => {
                const nuevosDias = parseFloat(inputDias.value);
                if (!isNaN(nuevosDias)) {
                    inputTotal.value = (nuevosDias * costoDiario).toFixed(2);
                }
                calcularTotalFinanciamiento(); // Recalcular después de actualizar esta fila
            });
            inputDias.setAttribute('data-listener-attached', 'true');
        }

        calcularTotalFinanciamiento(); // Recalcular después de que los datos de la fila se actualicen
    } catch (e) {
        console.error("Error al actualizar datos de empleado:", e);
        row.cells[1].querySelector("input").value = "Error";
        row.cells[3].querySelector("input").value = "N/D";
        calcularTotalFinanciamiento();
    }
}

function agregarMaterial() {
    const tr = document.createElement("tr");
    tr.className = "text-center";
    tr.innerHTML = `
        <td><input type="number" class="form-control" name="material_id" placeholder="ID Material" required></td>
        <td><input type="text" class="form-control bg-light" value="(nombre)" disabled></td>
        <td><input type="number" class="form-control" name="material_cantidad" placeholder="Cantidad" required></td>
        <td><input type="number" step="0.01" class="form-control" name="material_total" placeholder="Precio Total" required></td>
        <td><input type="text" class="form-control bg-light" value="(precio)" disabled></td>
    `;
    document.getElementById("materialesContainer").appendChild(tr);
}

async function agregarEmpleadoDesdeLista() {
    const contenedor = document.getElementById("empleadosContainer");
    const listaEmpleados = await fetch('/api/v1/empleados').then(r => r.json());

    const tr = document.createElement("tr");
    tr.className = "text-center";
    tr.innerHTML = `
        <td>
            <select class="form-select form-select-sm" name="empleado_id" onchange="actualizarDatosEmpleado(this)">
                <option value="">Seleccione</option>
                ${listaEmpleados.map(e => `
                    <option value="${e.id_empleado}">${e.dni} - ${e.nombre} ${e.apellidos}</option>
                `).join("")}
            </select>
        </td>
        <td><input type="text" class="form-control bg-light" readonly></td>
        <td><input type="number" class="form-control" name="empleado_dias" value="1" required></td>
        <td><input type="number" step="0.01" class="form-control" name="empleado_total" value="0.00" required></td>
        <td>
            <button type="button" class="btn btn-sm btn-danger" onclick="eliminarFilaItem(this)">
                <i class="fa fa-trash"></i>
            </button>
        </td>
    `;
    contenedor.appendChild(tr);
}

async function agregarEmpleado() {
    const contenedor = document.getElementById("empleadosContainer");
    const listaEmpleados = await fetch('/api/v1/empleados').then(r => r.json());

    const div = document.createElement("div");
    div.className = "row g-2 mb-2 align-items-center";

    div.innerHTML = `
        <div class="col-md-4">
            <select class="form-select form-select-sm empleado-select" name="empleado_id" required>
                <option value="">Buscar empleado...</option>
                ${listaEmpleados.map(emp => `
                    <option value="${emp.id_empleado}">${emp.dni} - ${emp.nombre} ${emp.apellidos}</option>
                `).join("")}
            </select>
        </div>
        <div class="col-md-2">
            <input type="number" class="form-control form-control-sm" name="empleado_dias" placeholder="Días" min="1" required>
        </div>
        <div class="col-md-2">
            <input type="number" step="0.01" class="form-control form-control-sm" name="empleado_total" placeholder="Costo total" required>
        </div>
        <div class="col-md-2">
            <input type="text" class="form-control form-control-sm bg-light" name="empleado_dni" placeholder="DNI" readonly>
        </div>
        <div class="col-md-2">
            <input type="text" class="form-control form-control-sm bg-light" name="empleado_nombre" placeholder="Nombre" readonly>
        </div>
    `;

    contenedor.appendChild(div);

    // Activar Select2
    $(div).find(".empleado-select").select2({
        placeholder: "Buscar empleado...",
        width: '100%'
    });

    // Evento al seleccionar un empleado
    const select = div.querySelector(".empleado-select");
    select.addEventListener("change", async () => {
        const id = parseInt(select.value);
        if (!id) return;

        const empleado = listaEmpleados.find(e => e.id_empleado === id);
        if (!empleado) return;

        // Verificar duplicado
        const selects = document.querySelectorAll('#empleadosContainer select[name="empleado_id"]');
        for (const s of selects) {
            if (s !== select && parseInt(s.value) === id) {
                const rowExistente = s.closest(".row");
                const diasInput = rowExistente.querySelector('[name="empleado_dias"]');
                const totalInput = rowExistente.querySelector('[name="empleado_total"]');

                const diasActual = parseFloat(diasInput.value || 0);
                const diasNuevo = parseFloat(div.querySelector('[name="empleado_dias"]').value || 0);
                const totalNuevo = parseFloat(div.querySelector('[name="empleado_total"]').value || 0);

                diasInput.value = diasActual + diasNuevo;
                totalInput.value = (parseFloat(totalInput.value || 0) + totalNuevo).toFixed(2);

                div.remove(); // Eliminar fila nueva (repetida)
                return;
            }
        }

        // Mostrar datos
        div.querySelector('[name="empleado_dni"]').value = empleado.dni;
        div.querySelector('[name="empleado_nombre"]').value = `${empleado.nombre} ${empleado.apellidos}`;
    });

    // Calcular total al escribir días
    const inputDias = div.querySelector('[name="empleado_dias"]');
    const inputTotal = div.querySelector('[name="empleado_total"]');
    inputDias.addEventListener("input", () => {
        const dias = parseFloat(inputDias.value);
        const costoDiario = 100; // Aquí puedes usar una lógica si traes el costo desde el backend
        if (!isNaN(dias)) {
            inputTotal.value = (dias * costoDiario).toFixed(2);
        }
    });
}

function obtenerMaterialPorId(id) {
    return fetch(`/api/v1/materiales/${id}`)
        .then(res => res.ok ? res.json() : null)
        .catch(() => null);
}

function guardarFinanciamiento(e, idFinanciamiento) {
    e.preventDefault();
    const form = e.target;
    const fd = new FormData(form);

    const dto = {
        financiamiento: {
            id_cliente: 1,
            id_obra: parseInt(fd.get("id_obra")),
            id_tipo_financiamiento: 1,
            codigo_financiamiento: fd.get("codigo"),
            forma_pago: fd.get("forma_pago"),
            sub_total: parseFloat(fd.get("sub_total")),
            igv: parseFloat(fd.get("igv")),
            total: parseFloat(fd.get("total")),
            fecha_financiamiento: fd.get("fecha"),
            estado: fd.get("estado") === "true"
        },
        materiales: [],
        empleados: []
    };

    const mats = form.querySelectorAll('[name="material_id"]');
    const cants = form.querySelectorAll('[name="material_cantidad"]');
    const totales = form.querySelectorAll('[name="material_total"]');

    for (let i = 0; i < mats.length; i++) {
        dto.materiales.push({
            id_material: parseInt(mats[i].value),
            cantidad: parseFloat(cants[i].value),
            precio_total: parseFloat(totales[i].value)
        });
    }

    const emps = form.querySelectorAll('[name="empleado_id"]');
    const dias = form.querySelectorAll('[name="empleado_dias"]');
    const totEmp = form.querySelectorAll('[name="empleado_total"]');

    for (let i = 0; i < emps.length; i++) {
        dto.empleados.push({
            id_empleado: parseInt(emps[i].value),
            dias_participacion: parseInt(dias[i].value),
            costo_total: parseFloat(totEmp[i].value)
        });
    }

    const url = idFinanciamiento
        ? `/api/v1/financiamientos/completo/${idFinanciamiento}`
        : `/api/v1/financiamientos/completo`;
    const method = idFinanciamiento ? "PUT" : "POST";

    fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(dto)
    })
    .then(res => {
        if (!res.ok) throw new Error();
        alertaExito(idFinanciamiento ? "Financiamiento actualizado" : "Financiamiento registrado");
        setTimeout(() => location.reload(), 1500);
    })
    .catch(() => alertaError("No se pudo guardar el financiamiento"));
}

function alertaExito(msg) {
    Swal.fire({
        icon: "success",
        title: "Éxito",
        text: msg,
        showConfirmButton: false,
        timer: 2000
    });
}

function alertaError(msg) {
    Swal.fire({
        icon: "error",
        title: "Error",
        text: msg
    });
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
    }).then(r => {
        if (r.isConfirmed) cb();
    });
}

// Asegúrate de que SweetAlert2 esté configurado y las librerías jsPDF y jspdf-autotable estén incluidas en tu HTML
// <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
// <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.23/jspdf.plugin.autotable.min.js"></script>
// Asegúrate de que SweetAlert2 esté configurado y las librerías jsPDF y jspdf-autotable estén incluidas en tu HTML
// <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
// <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.23/jspdf.plugin.autotable.min.js"></script>
// Asegúrate de que SweetAlert2 esté configurado y las librerías jsPDF y jspdf-autotable estén incluidas en tu HTML
// <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
// <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.23/jspdf.plugin.autotable.min.js"></script>

async function descargarReporteFinanciamientoPorObra(button) {
    const idObra = button.getAttribute('data-obra-id');
    if (!idObra) {
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: 'No se pudo obtener el ID de la obra para generar el reporte.',
            confirmButtonColor: '#3498db'
        });
        return;
    }

    try {
        // Paso 1: Obtener el ID del financiamiento para esta obra
        const resFinPorObra = await fetch(`/api/v1/financiamientos/por-obra/${idObra}`);
        if (!resFinPorObra.ok) {
            if (resFinPorObra.status === 404) {
                Swal.fire({
                    icon: 'info',
                    title: 'Información',
                    text: 'No se encontró un financiamiento asociado a esta obra para generar el reporte.',
                    confirmButtonColor: '#3498db'
                });
            } else {
                throw new Error(`Error ${resFinPorObra.status} al obtener el financiamiento de la obra.`);
            }
            return;
        }
        const financiamientoPorObra = await resFinPorObra.json();
        const idFinanciamiento = financiamientoPorObra.id_financiamiento;

        if (!idFinanciamiento) {
            Swal.fire({
                icon: 'warning',
                title: 'Advertencia',
                text: 'El financiamiento encontrado para esta obra no tiene un ID válido.',
                confirmButtonColor: '#3498db'
            });
            return;
        }

        // Paso 2: Obtener los DATOS COMPLETOS del financiamiento (con materiales y empleados)
        const resCompleto = await fetch(`/api/v1/financiamientos/completo/${idFinanciamiento}`);
        if (!resCompleto.ok) {
            throw new Error(`Error ${resCompleto.status} al obtener los datos completos del financiamiento.`);
        }
        const datosFinanciamiento = await resCompleto.json();

        // --- FUNCIONES AUXILIARES PARA OBTENER NOMBRES COMPLETOS (RECOMENDACIÓN: Mover esto al backend si es posible) ---
        // Estas funciones hacen llamadas adicionales a la API para obtener detalles completos de entidades
        const getClientDetails = async (id) => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/clientes/${id}`); // Ajusta la URL si es necesario
                if (!response.ok) return { nombre: `ID:${id}`, apellidos: '', dni: '', telefono: '', email: '' }; // Añadir campos por defecto
                return await response.json();
            } catch (error) {
                console.error('Error fetching client details:', error);
                return { nombre: `ID:${id}`, apellidos: '', dni: '', telefono: '', email: '' };
            }
        };

        const getObraDetails = async (id) => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/obras/${id}`); // Ajusta la URL si es necesario
                if (!response.ok) return { nombre: `ID:${id}`, responsable: {}, modeloObra: {}, terreno: {}, tipoConstruccion: {}, fecha_inicio: '', fecha_finalizacion: '', estado: '' }; // Añadir campos por defecto
                return await response.json();
            } catch (error) {
                console.error('Error fetching obra details:', error);
                return { nombre: `ID:${id}`, responsable: {}, modeloObra: {}, terreno: {}, tipoConstruccion: {}, fecha_inicio: '', fecha_finalizacion: '', estado: '' };
            }
        };

        const getTipoFinanciamientoDetails = async (id) => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/tipo-financiamientos/${id}`); // Ajusta la URL si es necesario
                if (!response.ok) return { nombre: `ID:${id}` };
                return await response.json();
            } catch (error) {
                console.error('Error fetching tipo financiamiento details:', error);
                return { nombre: `ID:${id}` };
            }
        };

        const getMaterialDetails = async (id) => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/materiales/${id}`); // Ajusta la URL si es necesario
                if (!response.ok) return { nombre: `ID:${id}`, precio_unitario: 0 };
                return await response.json();
            } catch (error) {
                console.error('Error fetching material details:', error);
                return { nombre: `ID:${id}`, precio_unitario: 0 };
            }
        };

        const getEmpleadoDetails = async (id) => {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/empleados/${id}`); // Ajusta la URL si es necesario
                if (!response.ok) return { nombre: `ID:${id}`, apellidos: '', dni: '' };
                return await response.json();
            } catch (error) {
                console.error('Error fetching empleado details:', error);
                return { nombre: `ID:${id}`, apellidos: '', dni: '' };
            }
        };
        // --- FIN FUNCIONES AUXILIARES ---

        // Obtener detalles completos de las entidades para el PDF
        const financiamientoInfo = datosFinanciamiento.financiamiento;
        const clienteDetalle = await getClientDetails(financiamientoInfo.id_cliente);
        const obraDetalle = await getObraDetails(financiamientoInfo.id_obra);
        const tipoFinanciamientoDetalle = await getTipoFinanciamientoDetails(financiamientoInfo.id_tipo_financiamiento);

        const materialesDetallados = await Promise.all(datosFinanciamiento.materiales.map(async m => {
            const matDetails = await getMaterialDetails(m.id_material);
            return {
                ...m,
                nombreMaterial: matDetails.nombre,
                precioUnitarioMaterial: matDetails.precio_unitario
            };
        }));

        const empleadosDetallados = await Promise.all(datosFinanciamiento.empleados.map(async e => {
            const empDetails = await getEmpleadoDetails(e.id_empleado);
            return {
                ...e,
                nombreEmpleado: `${empDetails.nombre} ${empDetails.apellidos}`,
                dniEmpleado: empDetails.dni
            };
        }));

        // Paso 3: GENERAR EL PDF EN EL FRONTEND
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();

        // Colores CSI Contratistas (RGB)
        const primaryBlue = [52, 152, 219]; // Azul primario
        const darkBlue = [41, 128, 185];    // Azul oscuro
        const lightBlue = [209, 230, 247];  // Azul claro (para fondos de tablas)
        const textColor = [50, 50, 50]; // Gris oscuro para texto general

        const margin = 15; // Margen uniforme
        let y = margin; // Posición Y inicial

        // --- Encabezado y Pie de Página para CADA página ---
        // Se ejecuta automáticamente en cada página.
        // No necesitamos un `doc.autoTable()` separado con `html: '#'` para esto.
        // El `didDrawPage` hook de la primera tabla o de cualquier `autoTable` existente
        // o un hook global si se configura para todas las tablas, es suficiente.
        // Para asegurar que se dibuje siempre, podemos usar un hook genérico:
        doc.setFontSize(10);
        doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
        doc.text("CSICONTRASTISTAS - Reporte de Financiamiento", margin, 10);
        doc.setFontSize(9);
        doc.setTextColor(textColor[0], textColor[1], textColor[2]);
        doc.text(`Fecha de Emisión: ${new Date().toLocaleDateString('es-ES')}`, doc.internal.pageSize.getWidth() - margin, 10, { align: "right" });

        // Para el pie de página y número de página, lo más robusto es usar el 'addPageContent'
        // o si tienes jspdf-autotable para todas las páginas, puedes ponerlo en didDrawPage de la primera tabla.
        // Vamos a incluirlo en el hook de la primera tabla autoTable real que se dibuja,
        // o como una función separada que se llama antes de cada `doc.addPage()` si fuera necesario.
        // Por simplicidad y para que funcione con `autoTable`, la voy a integrar en la primera tabla real.

        // --- Título Principal ---
        y += 10; // Espacio inicial después del encabezado superior
        doc.setFontSize(26);
        doc.setTextColor(primaryBlue[0], primaryBlue[1], primaryBlue[2]);
        doc.setFont(undefined, 'bold');
        doc.text("INFORME DETALLADO DE FINANCIAMIENTO DE PROYECTO", doc.internal.pageSize.getWidth() / 2, y, { align: "center" });
        y += 20;

        // --- Sección de Identificación del Financiamiento ---
        doc.setFontSize(16);
        doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
        doc.setFont(undefined, 'bold');
        doc.text("1. IDENTIFICACIÓN DEL FINANCIAMIENTO", margin, y);
        y += 10;
        doc.setFont(undefined, 'normal');
        doc.setTextColor(textColor[0], textColor[1], textColor[2]);

        const financiamientoData = [
            ["Código de Financiamiento:", financiamientoInfo.codigo_financiamiento],
            ["Fecha de Registro:", new Date(financiamientoInfo.fecha_financiamiento).toLocaleDateString('es-ES')],
            ["Tipo de Financiamiento:", tipoFinanciamientoDetalle.nombre],
            ["Forma de Pago:", financiamientoInfo.forma_pago],
            ["Estado Actual:", financiamientoInfo.estado ? 'Activo' : 'Inactivo']
        ];
        doc.autoTable({
            startY: y + 5,
            body: financiamientoData,
            theme: 'plain', // Sin bordes
            styles: { fontSize: 11, cellPadding: 2, textColor: textColor },
            columnStyles: { 0: { fontStyle: 'bold', cellWidth: 50, textColor: darkBlue } }, // Primera columna en negrita y azul
            margin: { left: margin },
            tableWidth: 'wrap', // Ajusta el ancho de la tabla al contenido
            // Aquí integramos el pie de página para cada página generada por autoTable
            didDrawPage: function (data) {
                // Pie de página
                doc.setFontSize(8);
                doc.setTextColor(textColor[0], textColor[1], textColor[2]);
                doc.text(`Página ${data.pageNumber} de ${doc.internal.getNumberOfPages()}`, doc.internal.pageSize.getWidth() / 2, doc.internal.pageSize.getHeight() - 10, { align: "center" });
            }
        });
        y = doc.autoTable.previous.finalY + 15;


        // --- Sección de Datos del Cliente ---
        doc.setFontSize(16);
        doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
        doc.setFont(undefined, 'bold');
        doc.text("2. CLIENTE ASOCIADO", margin, y);
        y += 10;
        doc.setFont(undefined, 'normal');
        doc.setTextColor(textColor[0], textColor[1], textColor[2]);

        const clientData = [
            ["Nombre Completo:", `${clienteDetalle.nombre} ${clienteDetalle.apellidos}`],
            ["DNI:", clienteDetalle.dni || 'No especificado'],
            ["Teléfono:", clienteDetalle.telefono || 'No especificado'],
            ["Email:", clienteDetalle.email || 'No especificado']
        ];
        doc.autoTable({
            startY: y + 5,
            body: clientData,
            theme: 'plain',
            styles: { fontSize: 11, cellPadding: 2, textColor: textColor },
            columnStyles: { 0: { fontStyle: 'bold', cellWidth: 50, textColor: darkBlue } },
            margin: { left: margin },
            tableWidth: 'wrap'
        });
        y = doc.autoTable.previous.finalY + 15;


        // --- Sección de Datos de la Obra ---
        doc.setFontSize(16);
        doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
        doc.setFont(undefined, 'bold');
        doc.text("3. PROYECTO / OBRA RELACIONADA", margin, y);
        y += 10;
        doc.setFont(undefined, 'normal');
        doc.setTextColor(textColor[0], textColor[1], textColor[2]);

        const obraData = [
            ["Nombre de la Obra:", obraDetalle.nombre],
            ["Responsable:", `${obraDetalle.responsable?.nombre || ''} ${obraDetalle.responsable?.apellidos || ''}` || 'No asignado'],
            ["Modelo de Obra:", obraDetalle.modeloObra?.nombre || 'No especificado'],
            ["Tipo de Construcción:", obraDetalle.tipoConstruccion?.nombre || 'No especificado'],
            ["Terreno:", obraDetalle.terreno?.nombre || 'No especificado'],
            ["Fecha de Inicio:", obraDetalle.fecha_inicio ? new Date(obraDetalle.fecha_inicio).toLocaleDateString('es-ES') : 'N/A'],
            ["Fecha de Finalización:", obraDetalle.fecha_finalizacion ? new Date(obraDetalle.fecha_finalizacion).toLocaleDateString('es-ES') : 'N/A'],
            ["Estado de Obra:", obraDetalle.estado ? 'Activa' : 'Inactiva']
        ];
        doc.autoTable({
            startY: y + 5,
            body: obraData,
            theme: 'plain',
            styles: { fontSize: 11, cellPadding: 2, textColor: textColor },
            columnStyles: { 0: { fontStyle: 'bold', cellWidth: 50, textColor: darkBlue } },
            margin: { left: margin },
            tableWidth: 'wrap'
        });
        y = doc.autoTable.previous.finalY + 15;


        // --- Resumen de Costos Detallado ---
        doc.setFontSize(16);
        doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
        doc.setFont(undefined, 'bold');
        doc.text("4. RESUMEN FINANCIERO", margin, y);
        y += 10;
        doc.setFont(undefined, 'normal');
        doc.setTextColor(textColor[0], textColor[1], textColor[2]);

        const summaryData = [
            ["Subtotal:", `S/ ${financiamientoInfo.sub_total ? financiamientoInfo.sub_total.toFixed(2) : '0.00'}`],
            ["Impuesto (IGV):", `S/ ${financiamientoInfo.igv ? financiamientoInfo.igv.toFixed(2) : '0.00'}`],
            ["Total General:", `S/ ${financiamientoInfo.total ? financiamientoInfo.total.toFixed(2) : '0.00'}`]
        ];
        doc.autoTable({
            startY: y + 5,
            body: summaryData,
            theme: 'grid', // Con bordes para destacar el resumen
            styles: { fontSize: 12, cellPadding: 3, halign: 'right' },
            headStyles: { fillColor: lightBlue, textColor: darkBlue, fontStyle: 'bold' },
            columnStyles: { 0: { fontStyle: 'bold', halign: 'left', textColor: darkBlue } }, // Etiqueta a la izquierda, negrita
            alternateRowStyles: { fillColor: [240, 248, 255] },
            margin: { left: margin, right: margin }
        });
        y = doc.autoTable.previous.finalY + 15;


        // --- Materiales Utilizados ---
        if (materialesDetallados && materialesDetallados.length > 0) {
            doc.setFontSize(16);
            doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
            doc.setFont(undefined, 'bold');
            doc.text("5. DETALLE DE MATERIALES UTILIZADOS", margin, y);
            y += 10;
            doc.setFont(undefined, 'normal');
            doc.setTextColor(textColor[0], textColor[1], textColor[2]);

            const materialColumns = [
                { header: 'Material', dataKey: 'nombreMaterial' },
                { header: 'Cantidad', dataKey: 'cantidad' },
                { header: 'P. Unitario (S/)', dataKey: 'precioUnitarioMaterial' },
                { header: 'P. Total (S/)', dataKey: 'precio_total' },
            ];

            const materialRowsData = materialesDetallados.map(m => ({
                nombreMaterial: m.nombreMaterial,
                cantidad: m.cantidad,
                precioUnitarioMaterial: m.precioUnitarioMaterial ? m.precioUnitarioMaterial.toFixed(2) : '0.00',
                precio_total: m.precio_total ? m.precio_total.toFixed(2) : '0.00'
            }));

            doc.autoTable({
                startY: y + 5,
                head: [materialColumns.map(col => col.header)],
                body: materialRowsData.map(row => materialColumns.map(col => row[col.dataKey])),
                theme: 'striped', // Estilo de rayas
                styles: { fontSize: 10, cellPadding: 3, textColor: textColor },
                headStyles: { fillColor: lightBlue, textColor: darkBlue, fontStyle: 'bold', halign: 'center' },
                columnStyles: {
                    'Cantidad': { halign: 'center' },
                    'P. Unitario (S/)': { halign: 'right' },
                    'P. Total (S/)': { halign: 'right' }
                },
                alternateRowStyles: { fillColor: [240, 248, 255] },
                margin: { left: margin, right: margin }
            });
            y = doc.autoTable.previous.finalY + 15;
        }

        // --- Personal Involucrado ---
        if (empleadosDetallados && empleadosDetallados.length > 0) {
            doc.setFontSize(16);
            doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
            doc.setFont(undefined, 'bold');
            doc.text("6. PERSONAL INVOLUCRADO EN EL FINANCIAMIENTO", margin, y);
            y += 10;
            doc.setFont(undefined, 'normal');
            doc.setTextColor(textColor[0], textColor[1], textColor[2]);

            const employeeColumns = [
                { header: 'Empleado', dataKey: 'nombreCompleto' },
                { header: 'DNI', dataKey: 'dniEmpleado' },
                { header: 'Días de Participación', dataKey: 'dias_participacion' },
                { header: 'Costo Total (S/)', dataKey: 'costo_total' },
            ];

            const employeeRowsData = empleadosDetallados.map(e => ({
                nombreCompleto: e.nombreEmpleado,
                dniEmpleado: e.dniEmpleado,
                dias_participacion: e.dias_participacion,
                costo_total: e.costo_total ? e.costo_total.toFixed(2) : '0.00'
            }));

            doc.autoTable({
                startY: y + 5,
                head: [employeeColumns.map(col => col.header)],
                body: employeeRowsData.map(row => employeeColumns.map(col => row[col.dataKey])),
                theme: 'striped',
                styles: { fontSize: 10, cellPadding: 3, textColor: textColor },
                headStyles: { fillColor: lightBlue, textColor: darkBlue, fontStyle: 'bold', halign: 'center' },
                columnStyles: {
                    'DNI': { halign: 'center' },
                    'Días de Participación': { halign: 'center' },
                    'Costo Total (S/)': { halign: 'right' }
                },
                alternateRowStyles: { fillColor: [240, 248, 255] },
                margin: { left: margin, right: margin }
            });
            y = doc.autoTable.previous.finalY + 15;
        }

        // --- Sección de Cláusulas / Observaciones (Simulado como contrato) ---
        doc.setFontSize(16);
        doc.setTextColor(darkBlue[0], darkBlue[1], darkBlue[2]);
        doc.setFont(undefined, 'bold');
        doc.text("7. OBSERVACIONES Y TÉRMINOS ADICIONALES", margin, y);
        y += 10;
        doc.setFont(undefined, 'normal');
        doc.setTextColor(textColor[0], textColor[1], textColor[2]);

        const clausulaText = `
El presente informe detalla el desglose financiero del proyecto "${obraDetalle.nombre || 'Nombre de Obra Desconocida'}" (Código de Financiamiento: ${financiamientoInfo.codigo_financiamiento || 'N/A'}), gestionado por CSICONTRASTISTAS. Este documento refleja el compromiso y la transparencia en la gestión de recursos asignados.

CLÁUSULA 1: Confirmación de Datos. Toda la información contenida en este reporte ha sido verificada y corresponde a los registros contables y operacionales de la empresa a la fecha de emisión. Cualquier inconsistencia debe ser reportada de inmediato.

CLÁUSULA 2: Propiedad Intelectual. Este reporte es propiedad exclusiva de CSICONTRASTISTAS y su contenido es para uso interno y del cliente asociado, ${clienteDetalle.nombre || ''} ${clienteDetalle.apellidos || 'No Especificado'}. Se prohíbe su reproducción total o parcial sin autorización expresa.

CLÁUSULA 3: Modificaciones. Cualquier modificación o ajuste a este financiamiento deberá ser documentado mediante adendas o nuevos reportes, debidamente aprobados por las partes involucradas.

Se deja constancia del presente informe en la ciudad de Chiclayo, Lambayeque, Perú, el día de la fecha de emisión del presente.
        `;
        const splitClausula = doc.splitTextToSize(clausulaText, doc.internal.pageSize.getWidth() - (2 * margin));
        doc.text(splitClausula, margin, y);
        y += splitClausula.length * doc.getLineHeight() / doc.internal.scaleFactor + 20;


        // --- Espacio para Firmas ---
        doc.setFontSize(12);
        doc.setTextColor(textColor[0], textColor[1], textColor[2]);
        doc.text("_____________________________", doc.internal.pageSize.getWidth() / 4, y);
        doc.text("_____________________________", doc.internal.pageSize.getWidth() * 3 / 4, y);
        y += 7;
        doc.setFontSize(10);
        doc.text("Representante de CSICONTRASTISTAS", doc.internal.pageSize.getWidth() / 4, y, { align: 'center' });
        doc.text(`Cliente: ${clienteDetalle.nombre || ''} ${clienteDetalle.apellidos || 'N/A'}`, doc.internal.pageSize.getWidth() * 3 / 4, y, { align: 'center' });
        y += 5;
        doc.text("Gerencia Financiera", doc.internal.pageSize.getWidth() / 4, y, { align: 'center' });
        doc.text(`DNI: ${clienteDetalle.dni || 'N/A'}`, doc.internal.pageSize.getWidth() * 3 / 4, y, { align: 'center' });


        // Guardar el PDF
        doc.save(`informe_financiamiento_${financiamientoInfo.codigo_financiamiento}.pdf`);


        Swal.fire({
            icon: 'success',
            title: '¡Éxito!',
            text: 'Informe PDF detallado generado y descargado con éxito.',
            confirmButtonColor: '#3498db'
        });

    } catch (err) {
        console.error("Error al generar el informe PDF en el frontend:", err);
        Swal.fire({
            icon: 'error',
            title: 'Error',
            text: err.message || "Ocurrió un error al generar el informe PDF.",
            confirmButtonColor: '#3498db'
        });
    }
}