document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tablaUsuarios");
    const form = document.getElementById("formUsuario");
    const mensaje = document.getElementById("mensaje");
    const btnMostrarForm = document.getElementById("btnMostrarForm");
    const btnCancelar = document.getElementById("btnCancelar");
    const modal = document.getElementById("modalUsuario");
    const btnCerrarModal = document.getElementById("btnCerrarModal");

    btnMostrarForm.onclick = () => {
        modal.style.display = "block";
    };
    btnCancelar.onclick = cerrarModal;
    if (btnCerrarModal) btnCerrarModal.onclick = cerrarModal;

    function cerrarModal() {
        modal.style.display = "none";
        form.reset();
        mensaje.textContent = "";
    }

    window.addEventListener('click', function(event) {
        if (event.target === modal) {
            cerrarModal();
        }
    });

    // (Eliminada función cargarUsuarios, ahora la tabla se gestiona desde filtro.js)
    // Evento para el envío del formulario (crear o actualizar según usuarioId)
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const usuarioId = document.getElementById("usuarioId").value;
        const nombre = document.getElementById("nombre").value.trim();
        const correo = document.getElementById("correo").value.trim();
        const password = document.getElementById("password").value.trim();
        const rol = document.getElementById("rol").value;
        const fecha = document.getElementById("fecha").value;

        if (!nombre || !correo) {
            mensaje.textContent = "Por favor, complete todos los campos obligatorios.";
            mensaje.style.color = "red";
            return;
        }

        try {
            if (usuarioId) {
                // Actualizar usuario
                const body = { nombre, email: correo, rol, fecha_registro: fecha };
                // incluir password solo si se proporciona
                if (password) body.password = password;
                const response = await fetch(`http://localhost:8082/usuarios/${usuarioId}`, {
                    method: "PUT",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(body)
                });
                if (response.ok) {
                    mensaje.textContent = "Usuario actualizado correctamente.";
                    mensaje.style.color = "green";
                    setTimeout(() => {
                        cerrarModal();
                        if (typeof cargarUsuariosFiltrados === 'function') cargarUsuariosFiltrados();
                    }, 800);
                } else {
                    const text = await response.text();
                    mensaje.textContent = text || "Error al actualizar usuario.";
                    mensaje.style.color = "red";
                }
            } else {
                // Crear nuevo usuario
                if (!password) {
                    mensaje.textContent = "La contraseña es obligatoria para crear un usuario.";
                    mensaje.style.color = "red";
                    return;
                }
                const response = await fetch("http://localhost:8082/usuarios", {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify({ nombre, email: correo, password, rol, fecha_registro: fecha })
                });
                if (response.ok) {
                    mensaje.textContent = "Usuario registrado correctamente.";
                    mensaje.style.color = "green";
                    form.reset();
                    setTimeout(() => {
                        cerrarModal();
                        if (typeof cargarUsuariosFiltrados === 'function') cargarUsuariosFiltrados();
                    }, 800);
                } else {
                    const text = await response.text();
                    mensaje.textContent = text || "Error al registrar usuario.";
                    mensaje.style.color = "red";
                }
            }
        } catch (err) {
            mensaje.textContent = "Error al conectar con el servidor.";
            mensaje.style.color = "red";
        }
    });

    // Cargar usuarios al iniciar la página
    cargarUsuarios();
});