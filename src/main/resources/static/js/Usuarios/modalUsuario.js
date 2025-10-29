const tabla = document.getElementById("tablaUsuarios");
const form = document.getElementById("formUsuario");
const mensaje = document.getElementById("mensaje");
const btnMostrarForm = document.getElementById("btnMostrarForm");
const btnCancelar = document.getElementById("btnCancelar");
const modal = document.getElementById("modalUsuario");
const btnCerrarModal = document.getElementById("btnCerrarModal");
const tituloModal = document.getElementById("tituloModal");

// Función para abrir el modal
function abrirModal() {
    modal.style.display = "block";
    tituloModal.textContent = "Agregar Usuario";
    mensaje.textContent = "";
    const usuarioIdField = document.getElementById('usuarioId');
    if (usuarioIdField) usuarioIdField.value = '';
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
window.addEventListener('click', function(event) {
    if (event.target === modal) {
        cerrarModal();
    }
});
