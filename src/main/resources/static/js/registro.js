document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formRegistro");
    const mensaje = document.getElementById("mensaje");

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const nombre = document.getElementById("nombre").value.trim();
        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();

        if (!nombre || !email || !password) {
            mensaje.textContent = "Por favor complete todos los campos.";
            mensaje.style.color = "red";
            return;
        }

        try {
            const response = await fetch("http://localhost:8082/usuarios", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nombre, email, password })
            });

            if (response.ok) {
                mensaje.textContent = "Usuario registrado correctamente. Ahora puedes iniciar sesión.";
                mensaje.style.color = "green";
                setTimeout(() => {
                    window.location.href = "login.html";
                }, 1500);
            } else {
                mensaje.textContent = "Error al registrar usuario.";
                mensaje.style.color = "red";
            }
        } catch (error) {
            mensaje.textContent = "Error al conectar con el servidor.";
            mensaje.style.color = "red";
        }
    });
});
