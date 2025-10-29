// Función para editar usuario
function editarUsuario(id) {
    fetch(`http://localhost:8082/usuarios`)
        .then(response => response.json())
        .then(usuarios => {
            const usuario = usuarios.find(u => u.id === id);
            if (usuario) {
                // Abrir el modal con los datos del usuario
                modal.style.display = "block";
                tituloModal.textContent = "Editar Usuario";
                mensaje.textContent = "";

                // Rellenar el formulario
                document.getElementById("nombre").value = usuario.nombre;
                document.getElementById("correo").value = usuario.email;
                document.getElementById("rol").value = usuario.rol;
                document.getElementById("fecha").value = usuario.fecha_registro ? usuario.fecha_registro.split('T')[0] : "";
                document.getElementById("password").value = ""; // No mostrar contraseña

                // Preparar formulario para edición: setear usuarioId y rellenar campos
                const usuarioIdField = document.getElementById('usuarioId');
                if (usuarioIdField) usuarioIdField.value = id;
                // No sobrescribimos el submit handler aquí; el listener de form decidirá POST o PUT.
            }
        });
}

// Función para eliminar usuario
function eliminarUsuario(id) {
    if (confirm("¿Estás seguro de eliminar este usuario?")) {
        fetch(`http://localhost:8082/usuarios/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                alert("Usuario eliminado correctamente");
                // Recargar usando el filtro si existe
                if (typeof cargarUsuariosFiltrados === 'function') {
                    cargarUsuariosFiltrados();
                } else {
                    cargarUsuarios();
                }
            } else {
                alert("Error al eliminar usuario");
            }
        })
        .catch(error => {
            alert("Error al conectar con el servidor");
        });
    }
}