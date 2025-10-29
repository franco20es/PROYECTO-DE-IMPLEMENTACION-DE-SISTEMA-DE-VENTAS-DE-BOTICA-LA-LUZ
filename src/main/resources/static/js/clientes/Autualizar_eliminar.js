// Función para editar cliente
function editarCliente(id) {
    fetch(`http://localhost:8082/clientes`)
        .then(response => response.json())
        .then(clientes => {
            const cliente = clientes.find(c => c.id === id);
            if (cliente) {
                // Abrir el modal con los datos del cliente
                modal.style.display = "block";
                tituloModal.textContent = " Editar Cliente";
                
                // Rellenar el formulario
                document.getElementById("nombre").value = cliente.nombre;
                document.getElementById("apellido").value = cliente.apellido;
                document.getElementById("telefono").value = cliente.telefono;
                
                // Indicar al formulario que está en modo edición: setear clienteId oculto
                const clienteIdInput = document.getElementById('clienteId');
                if (clienteIdInput) {
                    clienteIdInput.value = id;
                }
                // El submit real lo maneja el listener central en clientes.js
            }
        });
}

// Función para eliminar cliente
function eliminarCliente(id) {
    if (confirm("¿Estás seguro de eliminar este cliente?")) {
        fetch(`http://localhost:8082/clientes/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                alert("Cliente eliminado correctamente");
                // Recargar usando el filtro si existe
                if (typeof cargarClientesFiltrados === 'function') {
                    cargarClientesFiltrados();
                } else {
                    cargarClientes();
                }
            } else {
                alert("Error al eliminar cliente");
            }
        })
        .catch(error => {
            alert("Error al conectar con el servidor");
        });
    }
}