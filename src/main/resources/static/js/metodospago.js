document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tablaMetodosPago");
    const form = document.getElementById("formMetodoPago");
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

    // Cargar métodos de pago desde el backend
    async function cargarMetodosPago() {
        tabla.innerHTML = "";
        try {
            const response = await fetch("http://localhost:8082/metodos-pago");
            const metodos = await response.json();
            metodos.forEach(m => {
                const fila = `<tr>
                    <td>${m.id}</td>
                    <td>${m.nombre}</td>
                </tr>`;
                tabla.innerHTML += fila;
            });
        } catch (error) {
            tabla.innerHTML = "<tr><td colspan='2'>Error al cargar los métodos de pago</td></tr>";
        }
    }

    // Evento para el envío del formulario
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const nombre = document.getElementById("nombre").value.trim();

        if (!nombre) {
            mensaje.textContent = "El nombre es obligatorio.";
            mensaje.style.color = "red";
            return;
        }

        try {
            const response = await fetch("http://localhost:8082/metodos-pago", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nombre })
            });
            if (response.ok) {
                mensaje.textContent = "Método de pago registrado correctamente.";
                mensaje.style.color = "green";
                form.reset();
                form.style.display = "none";
                cargarMetodosPago();
            } else {
                mensaje.textContent = "Error al registrar método de pago.";
                mensaje.style.color = "red";
            }
        } catch (error) {
            mensaje.textContent = "Error al conectar con el servidor.";
            mensaje.style.color = "red";
        }
    });

    // Cargar métodos de pago al iniciar
    cargarMetodosPago();
});