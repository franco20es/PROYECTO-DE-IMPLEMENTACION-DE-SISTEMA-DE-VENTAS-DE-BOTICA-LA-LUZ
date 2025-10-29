
// Función para editar producto
function editarProducto(id) {
    // Buscar el producto por ID
    fetch(`http://localhost:8082/productos`)
        .then(response => response.json())
        .then(productos => {
            const producto = productos.find(p => p.id === id);
            if (producto) {
                // Abrir el modal con los datos del producto
                const modal = document.getElementById("modalProducto");
                const tituloModal = document.getElementById("tituloModal");
                const form = document.getElementById("formProducto");
                const mensaje = document.getElementById("mensaje");
                
                modal.style.display = "block";
                tituloModal.textContent = "Actualizar Producto";
                
                // Rellenar el formulario con los datos del producto
                document.getElementById("nombre").value = producto.nombre;
                document.getElementById("descripcion").value = producto.descripcion;
                document.getElementById("precio").value = producto.precio;
                document.getElementById("stock").value = producto.stock;
                
                // Cambiar el comportamiento del formulario para actualizar
                form.onsubmit = async (e) => {
                    e.preventDefault();
                    
                    const productoActualizado = {
                        nombre: document.getElementById("nombre").value.trim(),
                        descripcion: document.getElementById("descripcion").value.trim(),
                        precio: parseFloat(document.getElementById("precio").value),
                        stock: parseInt(document.getElementById("stock").value)
                    };
                    
                    try {
                        const response = await fetch(`http://localhost:8082/productos/${id}`, {
                            method: "PUT",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify(productoActualizado)
                        });
                        
                        if (response.ok) {
                            mensaje.textContent = " Producto actualizado correctamente";
                            mensaje.style.color = "green";
                            setTimeout(() => {
                                modal.style.display = "none";
                                form.reset();
                                location.reload();
                            }, 1500);
                        } else {
                            mensaje.textContent = " Error al actualizar producto";
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

// Función para eliminar producto
function eliminarProducto(id) {
    if (confirm("¿Estás seguro de eliminar este producto?")) {
        fetch(`http://localhost:8082/productos/${id}`, {
            method: "DELETE"
        })
        .then(response => {
            if (response.ok) {
                alert("Producto eliminado correctamente");
                location.reload(); // Recargar la página
            } else {
                alert("Error al eliminar producto");
            }
        })
        .catch(error => {
            alert("Error al conectar con el servidor");
        });
    }
}