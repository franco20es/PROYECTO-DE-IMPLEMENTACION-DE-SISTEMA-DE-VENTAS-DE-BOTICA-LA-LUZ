document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tablaHistorialStock");
    const form = document.getElementById("formHistorialStock");
    const mensaje = document.getElementById("mensaje");
    const btnMostrarForm = document.getElementById("btnMostrarForm");
    const btnCancelar = document.getElementById("btnCancelar");
    const selectProducto = document.getElementById("producto");

    // Mostrar y ocultar formulario
    btnMostrarForm.onclick = () => {
        form.style.display = "block";
    };
    btnCancelar.onclick = () => {
        form.style.display = "none";
        mensaje.textContent = "";
    };

    // Cargar productos en el select
    async function cargarProductos() {
        try {
            const response = await fetch("http://localhost:8082/productos");
            const productos = await response.json();
            productos.forEach(p => {
                const option = document.createElement("option");
                option.value = p.id;
                option.textContent = p.nombre;
                selectProducto.appendChild(option);
            });
        } catch (error) {
            console.error("Error al cargar productos:", error);
        }
    }

    // Cargar historial desde el backend
    async function cargarHistorial() {
        tabla.innerHTML = "";
        try {
            const response = await fetch("http://localhost:8082/historial-stock");
            const historial = await response.json();

            historial.forEach(h => {
                const fila = `<tr>
                    <td>${h.id}</td>
                    <td>${h.producto.nombre}</td>
                    <td>${h.cantidad}</td>
                    <td>${h.tipo_movimiento}</td>
                    <td>${h.fecha}</td>
                </tr>`;
                tabla.innerHTML += fila;
            });
        } catch (error) {
            tabla.innerHTML = "<tr><td colspan='5'>Error al cargar el historial de stock</td></tr>";
        }
    }

    // Evento para el envío del formulario
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const idProducto = document.getElementById("producto").value;
        const cantidad = document.getElementById("cantidad").value;
        const tipo_movimiento = document.getElementById("tipo_movimiento").value;
        const fecha = document.getElementById("fecha").value;

        try {
            const response = await fetch("http://localhost:8082/historial-stock", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ 
                    producto: { id: idProducto },
                    cantidad: parseInt(cantidad),
                    tipo_movimiento,
                    fecha
                })
            });
            if (response.ok) {
                const result = await response.json();
                mensaje.textContent = `Movimiento registrado correctamente. Nuevo stock: ${result.cantidad}`;
                mensaje.style.color = "green";
                form.reset();
                form.style.display = "none";
                cargarHistorial();
                cargarProductos(); // Recargar la tabla de productos
            } else {
                mensaje.textContent = "Error al registrar movimiento.";
                mensaje.style.color = "red";
            }
        } catch (error) {
            mensaje.textContent = "Error al conectar con el servidor.";
            mensaje.style.color = "red";
        }
    });

    // Cargar datos al iniciar
    cargarProductos();
    cargarHistorial();
});