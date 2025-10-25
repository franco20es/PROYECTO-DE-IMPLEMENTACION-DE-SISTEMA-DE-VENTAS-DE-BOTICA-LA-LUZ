document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tablaUsuarios");
    const form = document.getElementById("formUsuario");
    const mensaje = document.getElementById("mensaje");
    const btnMostrarForm = document.getElementById("btnMostrarForm");
    const btnCancelar = document.getElementById("btnCancelar");

    btnMostrarForm.onclick = () => {
        form.style.display = "block";
    };
    btnCancelar.onclick = () => {
        form.style.display = "none";
        mensaje.textContent = "";
    };

    // Cargar usuarios desde el backend

    async function cargarUsuarios() {
        tabla.innerHTML = "";
        try {
            const response = await fetch("http://localhost:8082/usuarios");
            const usuarios = await response.json();
            usuarios.forEach(u => {
                const fila = `<tr>
                    <td>${u.id}</td>
                    <td>${u.nombre}</td>
                    <td>${u.email}</td>
                    <td>${u.rol}</td>
                    <td>${u.fecha_registro}</td>
                </tr>`;
                tabla.innerHTML += fila;
            });
        } catch {
            tabla.innerHTML = "<tr><td colspan='5'>No se pudieron cargar los usuarios</td></tr>";
        }
    }
    // Evento para el envío del formulario
    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const nombre = document.getElementById("nombre").value.trim();
        const correo = document.getElementById("correo").value.trim();
        const password = document.getElementById("password").value.trim();
        const rol = document.getElementById("rol").value;
        const fecha = document.getElementById("fecha").value;
        if (!nombre || !correo || !password) {
            mensaje.textContent = "Por favor, complete todos los campos obligatorios.";
            mensaje.style.color = "red";
            return;
        }
        try {
            const response = await fetch("http://localhost:8082/usuarios", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nombre, email: correo, password, rol, fecha_registro: fecha })
            });
            if (response.ok) {
                mensaje.textContent = "Usuario registrado correctamente.";
                mensaje.style.color = "green";
                form.reset();
                form.style.display = "none";
                cargarUsuarios();
            } else {
                mensaje.textContent = "Error al registrar usuario.";
                mensaje.style.color = "red";
            }
        } catch {
            mensaje.textContent = "Error al conectar con el servidor.";
            mensaje.style.color = "red";
        }
    });

    // Cargar usuarios al iniciar la página
    cargarUsuarios();
});