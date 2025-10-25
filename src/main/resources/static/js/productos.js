document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tablaProductos");
    const form = document.getElementById("formProducto");
    const mensaje = document.getElementById("mensaje");
    const btnMostrarForm = document.getElementById("btnMostrarForm");
    const btnCancelar = document.getElementById("btnCancelar");

    // Mostrar y ocultar formulario
    btnMostrarForm.onclick = () => {
        form.style.display = "block";
    };
    btnCancelar.onclick = () => {
        form.style.display = "none";
        mensaje.textContent = "";
    };

    // Cargar productos desde el backend
    async function cargarProductos() {
        tabla.innerHTML = "";
        try {
            const response = await fetch("http://localhost:8082/productos");
            const productos = await response.json();
            productos.forEach(p => {
                const fila = `<tr>
                    <td>${p.id}</td>
                    <td>${p.nombre}</td>
                    <td>${p.descripcion}</td>
                    <td>S/ ${p.precio.toFixed(2)}</td>
                    <td>${p.stock}</td>
                </tr>`;
                tabla.innerHTML += fila;
            });
        } catch {
            tabla.innerHTML = "<tr><td colspan='5'>No se pudieron cargar los productos</td></tr>";
        }
    }

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const nombre = document.getElementById("nombre").value.trim();
        const descripcion = document.getElementById("descripcion").value.trim();
        const precio = parseFloat(document.getElementById("precio").value);
        const stock = parseInt(document.getElementById("stock").value);

        try {
            const response = await fetch("http://localhost:8082/productos", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nombre, descripcion, precio, stock })
            });
            if (response.ok) {
                mensaje.textContent = "Producto registrado correctamente.";
                mensaje.style.color = "green";
                form.reset();
                form.style.display = "none";
                cargarProductos();
            } else {
                mensaje.textContent = "Error al registrar producto.";
                mensaje.style.color = "red";
            }
        } catch {
            mensaje.textContent = "Error al conectar con el servidor.";
            mensaje.style.color = "red";
        }
    });

    cargarProductos();
});