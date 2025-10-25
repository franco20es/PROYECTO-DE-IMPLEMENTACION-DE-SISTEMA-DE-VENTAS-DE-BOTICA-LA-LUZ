addEventListener('DOMContentLoaded', function() {
    showSection('dashboard');
});
// Función para cambiar de sección
function showSection(section) {
    // Ocultar todas las secciones
    document.querySelectorAll('.content-section').forEach(sec => {
        sec.style.display = 'none';
    });

    // Mostrar la sección seleccionada
    const sectionElement = document.getElementById(section + '-section');
    if (sectionElement) {
        sectionElement.style.display = 'block';
    }

    // Actualizar menú activo
    document.querySelectorAll('.menu-item').forEach(item => {
        item.classList.remove('active');
    });
    event.target.closest('.menu-item').classList.add('active');

    // Cargar datos según la sección
    switch(section) {
        case 'productos':
            cargarProductos();
            break;
        case 'proveedores':
            cargarProveedores();
            break;
        case 'clientes':
            cargarClientes();
            break;
        case 'ventas':
            cargarVentas();
            break;
        case 'usuarios':
            cargarUsuarios();
            break;
        case 'categorias':
            cargarCategorias();
            break;
        case 'metodos-pago':
            cargarMetodosPago();
            break;
        case 'detalle-ventas':
            cargarDetalleVentas();
            break;
        case 'stock':
            cargarHistorialStock();
            break;
        case 'dashboard':
            cargarDashboard();
            break;
    }
}