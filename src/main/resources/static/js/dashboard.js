// dashboard.js - Panel de Control

document.addEventListener('DOMContentLoaded', function() {
    cargarDashboard();
});

async function cargarDashboard() {
    try {
    const response = await fetch('http://localhost:8082/dashboard/totales');
        if (!response.ok) {
            throw new Error('Error HTTP: ' + response.status);
        }
        const data = await response.json();
        
        document.getElementById('totalProductos').textContent = data.totalProductos;
        document.getElementById('totalClientes').textContent = data.totalClientes;
        document.getElementById('totalProveedores').textContent = data.totalProveedores;
        document.getElementById('totalVentas').textContent = data.totalVentas;
        document.getElementById('ventasHoy').textContent = data.ventasHoy;
        document.getElementById('ventasSemana').textContent = data.ventasSemana;
        document.getElementById('ventasMes').textContent = data.ventasMes;
    } catch (error) {
        console.error('Error al cargar dashboard:', error);
    }
}
