const tabla = document.getElementById("tablaClientes");
const form = document.getElementById("formCategoria");
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

// cargar clientes desde el backend
async function cargarClientes() {
    tabla.innerHTML = "";
    try {
        const response = await fetch("http://localhost:8082/clientes");
        const clientes = await response.json();
        clientes.forEach(c => {
            const fila = `<tr>
                <td>${c.id}</td>
                <td>${c.nombre}</td>
                <td>${c.apellido}</td>
                <td>${c.telefono}</td>
            </tr>`;
            tabla.innerHTML += fila;
        });
    } catch {
        tabla.innerHTML = "<tr><td colspan='4'>No se pudieron cargar los clientes</td></tr>";
    }
}
form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const nombre = document.getElementById("nombre").value.trim();
    const apellido = document.getElementById("apellido").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    try {
        const response = await fetch("http://localhost:8082/clientes", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nombre, apellido, telefono })
        });
        if (response.ok) {
            mensaje.textContent = "Cliente registrado correctamente.";
            mensaje.style.color = "green";
            form.reset();
            form.style.display = "none";
            cargarClientes();
        } else {
            mensaje.textContent = "Error al registrar cliente.";
            mensaje.style.color = "red";
        }
    } catch {
        mensaje.textContent = "Error al conectar con el servidor.";
        mensaje.style.color = "red";
    }
});

cargarClientes();
