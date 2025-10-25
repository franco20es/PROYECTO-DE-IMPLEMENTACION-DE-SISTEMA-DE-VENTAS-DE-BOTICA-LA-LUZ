// Cargar dashboard al inicio
        document.addEventListener('DOMContentLoaded', function() {
            document.getElementById('dashboard-section').style.display = 'block';
            cargarDashboard();
        });
        // Funciones de eliminación para nuevas secciones
        async function eliminarCategoria(id) {
            if (confirm('¿Está seguro de eliminar esta categoría?')) {
                try {
                    await fetch(`/categorias/${id}`, { method: 'DELETE' });
                    cargarCategorias();
                } catch (error) {
                    console.error('Error al eliminar categoría:', error);
                }
            }
        }

        async function eliminarMetodoPago(id) {
            if (confirm('¿Está seguro de eliminar este método de pago?')) {
                try {
                    await fetch(`/metodos-pago/${id}`, { method: 'DELETE' });
                    cargarMetodosPago();
                } catch (error) {
                    console.error('Error al eliminar método de pago:', error);
                }
            }
        }
        // Funciones de eliminación
        async function eliminarProducto(id) {
            if (confirm('¿Está seguro de eliminar este producto?')) {
                try {
                    await fetch(`/productos/${id}`, { method: 'DELETE' });
                    cargarProductos();
                } catch (error) {
                    console.error('Error al eliminar producto:', error);
                }
            }
        }

        async function eliminarProveedor(id) {
            if (confirm('¿Está seguro de eliminar este proveedor?')) {
                try {
                    await fetch(`/proveedores/${id}`, { method: 'DELETE' });
                    cargarProveedores();
                } catch (error) {
                    console.error('Error al eliminar proveedor:', error);
                }
            }
        }

        async function eliminarCliente(id) {
            if (confirm('¿Está seguro de eliminar este cliente?')) {
                try {
                    await fetch(`/clientes/${id}`, { method: 'DELETE' });
                    cargarClientes();
                } catch (error) {
                    console.error('Error al eliminar cliente:', error);
                }
            }
        }

        async function eliminarVenta(id) {
            if (confirm('¿Está seguro de eliminar esta venta?')) {
                try {
                    await fetch(`/ventas/${id}`, { method: 'DELETE' });
                    cargarVentas();
                } catch (error) {
                    console.error('Error al eliminar venta:', error);
                }
            }
        }

        async function eliminarUsuario(id) {
            if (confirm('¿Está seguro de eliminar este usuario?')) {
                try {
                    await fetch(`/usuarios/${id}`, { method: 'DELETE' });
                    cargarUsuarios();
                } catch (error) {
                    console.error('Error al eliminar usuario:', error);
                }
            }
        }