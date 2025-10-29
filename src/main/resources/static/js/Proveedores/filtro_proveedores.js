// Filtro de proveedores por nombre
document.addEventListener('DOMContentLoaded', function() {
    const btnFiltrar = document.getElementById('btnFiltrar');
    const btnLimpiarFiltros = document.getElementById('btnLimpiarFiltros');
    const filtroNombre = document.getElementById('filtroNombre');
    
    // Cargar proveedores al inicio
    cargarProveedoresFiltrados();
    
    // Evento para filtrar
    btnFiltrar.addEventListener('click', cargarProveedoresFiltrados);
    
    // Filtrar al presionar Enter en el input
    filtroNombre.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            cargarProveedoresFiltrados();
        }
    });
    
    // Evento para limpiar filtros
    btnLimpiarFiltros.addEventListener('click', function() {
        filtroNombre.value = '';
        cargarProveedoresFiltrados();
    });
    
    // Función para cargar proveedores con filtros
    async function cargarProveedoresFiltrados() {
        try {
            // Construir los parámetros de filtro
            const params = new URLSearchParams();
            
            const nombre = filtroNombre.value.trim();
            
            if (nombre) {
                params.append('nombre', nombre);
            }
            
            // Hacer la petición con los parámetros
            const url = `http://localhost:8082/proveedores${params.toString() ? '?' + params.toString() : ''}`;
            const response = await fetch(url);
            
            if (!response.ok) {
                throw new Error('Error al obtener proveedores');
            }
            
            const proveedores = await response.json();
            
            // Renderizar la tabla
            renderizarTablaProveedores(proveedores);
            
        } catch (error) {
            console.error('Error al cargar proveedores:', error);
            document.getElementById('tablaProveedores').innerHTML = 
                '<tr><td colspan="6" style="text-align: center; color: #dc3545; padding: 2rem;"> Error al cargar proveedores</td></tr>';
        }
    }
    
    // Función para renderizar la tabla
    function renderizarTablaProveedores(proveedores) {
        const tbody = document.getElementById('tablaProveedores');
        
        if (proveedores.length === 0) {
            tbody.innerHTML = '<tr><td colspan="6" style="text-align: center; padding: 2rem; color: #666;">📭 No se encontraron proveedores con los filtros aplicados</td></tr>';
            return;
        }
        
        tbody.innerHTML = proveedores.map(p => `
            <tr>
                <td>${p.id}</td>
                <td>${p.nombre}</td>
                <td>${p.telefono}</td>
                <td>${p.email}</td>
                <td>${p.direccion}</td>
                <td>
                    <button onclick="editarProveedor(${p.id})" style="background: #28a745; color: white; border: none; padding: 0.4rem 0.8rem; border-radius: 4px; cursor: pointer; margin-right: 0.5rem; font-size: 0.9rem;">
                         Editar
                    </button>
                    <button onclick="eliminarProveedor(${p.id})" style="background: #dc3545; color: white; border: none; padding: 0.4rem 0.8rem; border-radius: 4px; cursor: pointer; font-size: 0.9rem;">
                         Eliminar
                    </button>
                </td>
            </tr>
        `).join('');
    }
    
    // Hacer la función disponible globalmente para que proveedores.js pueda usarla
    window.cargarProveedoresFiltrados = cargarProveedoresFiltrados;
});
