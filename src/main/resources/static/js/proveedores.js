const tabla = document.getElementById("tablaProveedores");
const form = document.getElementById("formProveedor");
const mensaje = document.getElementById("mensaje");
const btnMostrarForm = document.getElementById("btnMostrarForm");
const btnCancelar = document.getElementById("btnCancelar");
const modal = document.getElementById("modalProveedor");
const btnCerrarModal = document.getElementById("btnCerrarModal");
const tituloModal = document.getElementById("tituloModal");

// Función para abrir el modal
function abrirModal() {
    modal.style.display = "block";
    tituloModal.textContent = " Agregar Proveedor";
    mensaje.textContent = "";
}

// Función para cerrar el modal
function cerrarModal() {
    modal.style.display = "none";
    form.reset();
    mensaje.textContent = "";
    // Restaurar el comportamiento original del formulario
    restaurarFormularioAgregar();
}

// Guardar el manejador original del formulario
const manejadorOriginalSubmit = async (e) => {
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
            mensaje.textContent = "✅ Proveedor registrado correctamente";
            mensaje.style.color = "green";
            form.reset();
            setTimeout(() => {
                cerrarModal();
                // Recargar usando el filtro si existe
                if (typeof cargarProveedoresFiltrados === 'function') {
                    cargarProveedoresFiltrados();
                } else {
                    cargarProveedores();
                }
            }, 1500);
        } else {
            mensaje.textContent = "❌ Error al registrar proveedor";
            mensaje.style.color = "red";
        }
    } catch {
        mensaje.textContent = "❌ Error al conectar con el servidor";
        mensaje.style.color = "red";
    }
};

// Función para restaurar el formulario al modo "agregar"
function restaurarFormularioAgregar() {
    form.onsubmit = manejadorOriginalSubmit;
    tituloModal.textContent = "📦 Agregar Proveedor";
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

// cargar proveedores desde el backend
// NOTA: Esta función ya no se usa para renderizar la tabla
// El archivo filtro_proveedores.js maneja la renderización
async function cargarProveedores() {    
    // Función mantenida por compatibilidad, pero no renderiza
    // El filtro se encarga de cargar y renderizar
    console.log("cargarProveedores() llamada - redirigiendo al filtro");
    if (typeof cargarProveedoresFiltrados === 'function') {
        cargarProveedoresFiltrados();
    }
}

// Establecer el manejador inicial del formulario
form.onsubmit = manejadorOriginalSubmit;

// No cargar automáticamente, el filtro lo hará
// cargarProveedores();

