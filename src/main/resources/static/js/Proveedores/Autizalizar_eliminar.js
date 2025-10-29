// Función para editar proveedor
function editarProveedor(id) {
    fetch(`http://localhost:8082/proveedores`)
        .then(response => response.json())
        .then(proveedores => {
            const proveedor = proveedores.find(p => p.id === id);
            if (proveedor) {
                // Abrir el modal con los datos del proveedor
                modal.style.display = "block";
                tituloModal.textContent = " Editar Proveedor";
                
                // Rellenar el formulario
                document.getElementById("nombre").value = proveedor.nombre;
                document.getElementById("telefono").value = proveedor.telefono;
                document.getElementById("correo").value = proveedor.email;
                document.getElementById("direccion").value = proveedor.direccion;
                
                // Cambiar el comportamiento del formulario para actualizar
                form.onsubmit = async (e) => {
                    e.preventDefault();
                    
                    const proveedorActualizado = {
                        nombre: document.getElementById("nombre").value.trim(),
                        telefono: document.getElementById("telefono").value.trim(),
                        email: document.getElementById("correo").value.trim(),
                        direccion: document.getElementById("direccion").value.trim()
                    };
                    
                    try {
                        const response = await fetch(`http://localhost:8082/proveedores/${id}`, {
                            method: "PUT",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify(proveedorActualizado)
                        });
                        
                        if (response.ok) {
                            mensaje.textContent = " Proveedor actualizado correctamente";
                            mensaje.style.color = "green";
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
                            mensaje.textContent = " Error al actualizar proveedor";
                            mensaje.style.color = "red";
                        }
                    } catch (error) {
                        mensaje.textContent = " Error al conectar con el servidor";
                        mensaje.style.color = "red";
                    }
                };
            }
        });
}

// Función para eliminar proveedor
function eliminarProveedor(id) {
    if (confirm("¿Estás seguro de eliminar este proveedor?")) {
        fetch(`http://localhost:8082/proveedores/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                alert(" Proveedor eliminado correctamente");
                // Recargar usando el filtro si existe
                if (typeof cargarProveedoresFiltrados === 'function') {
                    cargarProveedoresFiltrados();
                } else {
                    cargarProveedores();
                }
            } else {
                alert(" Error al eliminar proveedor");
            }
        })
        .catch(error => {
            alert(" Error al conectar con el servidor");
        });
    }
}