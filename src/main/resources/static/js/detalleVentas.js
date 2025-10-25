document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tablaDetalleVentas");
    const form = document.getElementById("formDetalleVenta");
    const mensaje = document.getElementById("mensaje");
    const btnMostrarForm = document.getElementById("btnMostrarForm");
    const btnCancelar = document.getElementById("btnCancelar");
    const selectVenta = document.getElementById("venta");
    const selectProducto = document.getElementById("producto");
    // Filtros y paginación
    const formFiltro = document.getElementById("formFiltroDetalleVentas");
    const inputCliente = document.getElementById("filtroCliente");
    // Solo filtro por cliente
    const btnAnterior = document.getElementById("btnAnterior");
    const btnSiguiente = document.getElementById("btnSiguiente");
    const paginacionInfo = document.getElementById("paginacionInfo");
    let paginaActual = 0;
    let totalPaginas = 1;

    // Mostrar y ocultar formulario
    btnMostrarForm.onclick = () => {
        form.style.display = "block";
    };
    btnCancelar.onclick = () => {
        form.style.display = "none";
        mensaje.textContent = "";
    };

    // Cargar ventas en el select
    async function cargarVentas() {
        try {
            const response = await fetch("http://localhost:8082/ventas");
            const ventas = await response.json();
            ventas.forEach(v => {
                const option = document.createElement("option");
                option.value = v.id;
                option.textContent = `Venta #${v.id} - ${v.fecha}`;
                selectVenta.appendChild(option);
            });
        } catch (error) {
            console.error("Error al cargar ventas:", error);
        }
    }

    // Cargar productos en el select
    async function cargarProductos() {
        try {
            const response = await fetch("http://localhost:8082/productos");
            const productos = await response.json();
            productos.forEach(p => {
                const option = document.createElement("option");
                option.value = p.id;
                option.textContent = `${p.nombre} - S/. ${p.precio}`;
                option.dataset.precio = p.precio;
                selectProducto.appendChild(option);
            });
        } catch (error) {
            console.error("Error al cargar productos:", error);
        }
    }

    // Auto-completar precio unitario cuando se selecciona un producto
    selectProducto.addEventListener("change", () => {
        const selectedOption = selectProducto.options[selectProducto.selectedIndex];
        if (selectedOption.dataset.precio) {
            document.getElementById("precio_unitario").value = selectedOption.dataset.precio;
        }
    });

    // Cargar detalles de ventas paginados y filtrados
    async function cargarDetalles(page = 0) {
        tabla.innerHTML = "";
        let cliente = inputCliente.value || "";
        try {
            const response = await fetch(`http://localhost:8082/detalle-ventas?cliente=${encodeURIComponent(cliente)}&page=${page}&size=30`);
            const data = await response.json();
            const detalles = data.content || [];
            totalPaginas = data.totalPages || 1;
            paginaActual = data.number || 0;
            detalles.forEach(d => {
                const subtotal = (d.precio_unitario * d.cantidad * (1 - d.descuento / 100)).toFixed(2);
                const fila = `<tr>
                    <td>${d.id}</td>
                    <td>${d.venta.id}</td>
                    <td>${d.producto.nombre}</td>
                    <td>${d.cantidad}</td>
                    <td>S/. ${d.precio_unitario}</td>
                    <td>S/. ${subtotal}</td>
                    <td>${d.descuento}%</td>
                </tr>`;
                tabla.innerHTML += fila;
            });
            paginacionInfo.textContent = `Página ${paginaActual + 1} de ${totalPaginas}`;
            btnAnterior.disabled = paginaActual === 0;
            btnSiguiente.disabled = paginaActual >= totalPaginas - 1;
        } catch (error) {
            tabla.innerHTML = "<tr><td colspan='7'>Error al cargar los detalles de ventas</td></tr>";
        }
    }

    // Filtros
    formFiltro.addEventListener("submit", function(e) {
        e.preventDefault();
        cargarDetalles(0);
    });
    btnAnterior.addEventListener("click", function() {
        if (paginaActual > 0) cargarDetalles(paginaActual - 1);
    });
    btnSiguiente.addEventListener("click", function() {
        if (paginaActual < totalPaginas - 1) cargarDetalles(paginaActual + 1);
    });
    // ...

    // Evento para el envío del formulario
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const idVenta = document.getElementById("venta").value;
        const idProducto = document.getElementById("producto").value;
        const cantidad = document.getElementById("cantidad").value;
        const precio_unitario = document.getElementById("precio_unitario").value;
        const descuento = document.getElementById("descuento").value || 0;

        // Calcular el subtotal
        const subtotal = parseFloat(precio_unitario) * parseInt(cantidad) * (1 - parseFloat(descuento) / 100);

        try {
            const response = await fetch("http://localhost:8082/detalle-ventas", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    venta: { id: idVenta },
                    producto: { id: idProducto },
                    cantidad: parseInt(cantidad),
                    precio_unitario: parseFloat(precio_unitario),
                    descuento: parseFloat(descuento),
                    subtotal: parseFloat(subtotal.toFixed(2))
                })
            });
            if (response.ok) {
                mensaje.textContent = "Detalle de venta registrado correctamente.";
                mensaje.style.color = "green";
                form.reset();
                form.style.display = "none";
                cargarDetalles(0);
            } else {
                mensaje.textContent = "Error al registrar detalle de venta.";
                mensaje.style.color = "red";
            }
        } catch (error) {
            mensaje.textContent = "Error al conectar con el servidor.";
            mensaje.style.color = "red";
        }
    });

    // Cargar datos al iniciar
    cargarVentas();
    cargarProductos();
    cargarDetalles(0);
});