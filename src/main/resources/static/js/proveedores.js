const tabla = document.getElementById("tablaProveedores");
const form = document.getElementById("formProveedor");
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
// cargar proveedores desde el backend
async function cargarProveedores() {    
    tabla.innerHTML = "";
    try {
        const response = await fetch("http://localhost:8082/proveedores");
        const proveedores = await response.json();
        proveedores.forEach(p => {
            const fila = `<tr>
                <td>${p.id}</td>
                <td>${p.nombre}</td>
        <td>${p.telefono}</td>
        <td>${p.correo}</td>
        <td>${p.direccion}</td>
    </tr>`;
    tabla.innerHTML += fila;
});
    } catch {
        tabla.innerHTML = "<tr><td colspan='5'>No se pudieron cargar los proveedores</td></tr>";
    }
}
form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const nombre = document.getElementById("nombre").value.trim();
    const telefono = document.getElementById("telefono").value.trim();
    const correo = document.getElementById("correo").value.trim();
    const direccion = document.getElementById("direccion").value.trim();
    try {
        const response = await fetch("http://localhost:8082/proveedores", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ nombre, telefono, email: correo, direccion })
        });
        if (response.ok) {
            mensaje.textContent = "Proveedor registrado correctamente.";
            mensaje.style.color = "green";
            form.reset();
            form.style.display = "none";
            cargarProveedores();
        } else {
            mensaje.textContent = "Error al registrar proveedor.";
            mensaje.style.color = "red";
        }
    } catch {
        mensaje.textContent = "Error al conectar con el servidor.";
        mensaje.style.color = "red";
    }
});

cargarProveedores();