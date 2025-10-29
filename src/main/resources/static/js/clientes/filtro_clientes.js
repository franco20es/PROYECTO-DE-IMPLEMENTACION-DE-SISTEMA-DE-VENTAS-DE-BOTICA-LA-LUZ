// Filtro de clientes por nombre y apellido
document.addEventListener('DOMContentLoaded', function() {
    const btnFiltrar = document.getElementById('btnFiltrar');
    const btnLimpiarFiltros = document.getElementById('btnLimpiarFiltros');
    const filtroNombre = document.getElementById('filtroNombre');
    const filtroApellido = document.getElementById('filtroApellido');
    
    // Cargar clientes al inicio
    cargarClientesFiltrados();
    
    // Evento para filtrar
    btnFiltrar.addEventListener('click', cargarClientesFiltrados);
    
    // Filtrar al presionar Enter en los inputs
    filtroNombre.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            cargarClientesFiltrados();
        }
    });
    
    filtroApellido.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            cargarClientesFiltrados();
        }
    });
    
    // Evento para limpiar filtros
    btnLimpiarFiltros.addEventListener('click', function() {
        filtroNombre.value = '';
        filtroApellido.value = '';
        cargarClientesFiltrados();
    });
    
    // Función para cargar clientes con filtros
    async function cargarClientesFiltrados() {
        try {
            // Construir los parámetros de filtro
            const params = new URLSearchParams();
            
            const nombre = filtroNombre.value.trim();
            const apellido = filtroApellido.value.trim();
            
            if (nombre) {
                params.append('nombre', nombre);
            }
            
            if (apellido) {
                params.append('apellido', apellido);
            }
            
            // Hacer la petición con los parámetros
            const url = `http://localhost:8082/clientes${params.toString() ? '?' + params.toString() : ''}`;
            const response = await fetch(url);
            
            if (!response.ok) {
                throw new Error('Error al obtener clientes');
            }
            
            const clientes = await response.json();
            
            // Renderizar la tabla
            renderizarTablaClientes(clientes);
            
        } catch (error) {
            console.error('Error al cargar clientes:', error);
            document.getElementById('tablaClientes').innerHTML = 
                '<tr><td colspan="5" style="text-align: center; color: #dc3545; padding: 2rem;"> Error al cargar clientes</td></tr>';
        }
    }
    
    // Función para renderizar la tabla
    function renderizarTablaClientes(clientes) {
        const tbody = document.getElementById('tablaClientes');
        
        if (clientes.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem; color: #666;"> No se encontraron clientes con los filtros aplicados</td></tr>';
            return;
        }
        
        tbody.innerHTML = clientes.map(c => `
            <tr>
                <td>${c.id}</td>
                <td>${c.nombre}</td>
                <td>${c.apellido}</td>
                <td>${c.telefono}</td>
                <td>
                    <button onclick="editarCliente(${c.id})" style="background: #28a745; color: white; border: none; padding: 0.4rem 0.8rem; border-radius: 4px; cursor: pointer; margin-right: 0.5rem; font-size: 0.9rem;">
                         Editar
                    </button>
                    <button onclick="eliminarCliente(${c.id})" style="background: #dc3545; color: white; border: none; padding: 0.4rem 0.8rem; border-radius: 4px; cursor: pointer; font-size: 0.9rem;">
                        Eliminar
                    </button>
                </td>
            </tr>
        `).join('');
    }
});
