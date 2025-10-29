const tabla = document.getElementById("tablaClientes");
const form = document.getElementById("formCategoria");
const mensaje = document.getElementById("mensaje");
const btnMostrarForm = document.getElementById("btnMostrarForm");
const btnCancelar = document.getElementById("btnCancelar");
const modal = document.getElementById("modalCliente");
const btnCerrarModal = document.getElementById("btnCerrarModal");
const tituloModal = document.getElementById("tituloModal");

// Función para abrir el modal
function abrirModal() {
    modal.style.display = "block";
    tituloModal.textContent = " Agregar Cliente";
    mensaje.textContent = "";
    // asegurarse de que el formulario esté en modo crear
    const clienteIdInput = document.getElementById('clienteId');
    if (clienteIdInput) clienteIdInput.value = '';
}

// Función para cerrar el modal
function cerrarModal() {
    modal.style.display = "none";
    form.reset();
    mensaje.textContent = "";
}

// Event listeners para abrir/cerrar modal
btnMostrarForm.onclick = abrirModal;
btnCancelar.onclick = cerrarModal;
btnCerrarModal.onclick = cerrarModal;

// Cerrar modal al hacer click fuera del contenido
window.onclick = (event) => {
    if (event.target === modal) {
        cerrarModal();
    }
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
                <td>
                    <button onclick="editarCliente(${c.id})" 
                        style="background:#4CAF50; color:white; border:none; padding:0.5rem 1rem; border-radius:4px; cursor:pointer; margin-right:0.5rem; font-weight:600;">
                         Editar
                    </button>
                    <button onclick="eliminarCliente(${c.id})" 
                        style="background:#f44336; color:white; border:none; padding:0.5rem 1rem; border-radius:4px; cursor:pointer; font-weight:600;">
                         Eliminar
                    </button>
                </td>
            </tr>`;
            tabla.innerHTML += fila;
        });
    } catch {
        tabla.innerHTML = "<tr><td colspan='5'>No se pudieron cargar los clientes</td></tr>";
    }
}
form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const clienteId = document.getElementById('clienteId').value;
    const nombre = document.getElementById("nombre").value.trim();
    const apellido = document.getElementById("apellido").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    try {
        if (clienteId) {
            // actualizar
            const response = await fetch(`http://localhost:8082/clientes/${clienteId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nombre, apellido, telefono })
            });
            if (response.ok) {
                mensaje.textContent = "Cliente actualizado correctamente";
                mensaje.style.color = "green";
                setTimeout(() => {
                    cerrarModal();
                    if (typeof cargarClientesFiltrados === 'function') {
                        cargarClientesFiltrados();
                    } else {
                        cargarClientes();
                    }
                }, 800);
            } else {
                mensaje.textContent = "Error al actualizar cliente";
                mensaje.style.color = "red";
            }
        } else {
            // crear
            const response = await fetch("http://localhost:8082/clientes", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nombre, apellido, telefono })
            });
            if (response.ok) {
                mensaje.textContent = "Cliente registrado correctamente";
                mensaje.style.color = "green";
                form.reset();
                setTimeout(() => {
                    cerrarModal();
                    if (typeof cargarClientesFiltrados === 'function') {
                        cargarClientesFiltrados();
                    } else {
                        cargarClientes();
                    }
                }, 800);
            } else {
                mensaje.textContent = " Error al registrar cliente";
                mensaje.style.color = "red";
            }
        }
    } catch (err) {
        mensaje.textContent = " Error al conectar con el servidor";
        mensaje.style.color = "red";
    }
});

