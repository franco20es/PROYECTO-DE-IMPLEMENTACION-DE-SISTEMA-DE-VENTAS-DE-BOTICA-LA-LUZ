document.addEventListener('DOMContentLoaded', function() {
    const btnFiltrar = document.getElementById('btnFiltrar');
    const btnLimpiarFiltros = document.getElementById('btnLimpiarFiltros');
    const filtroNombre = document.getElementById('filtroNombre');

    cargarUsuariosFiltrados();

    btnFiltrar.addEventListener('click', cargarUsuariosFiltrados);

    filtroNombre.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            cargarUsuariosFiltrados();
        }
    });

    btnLimpiarFiltros.addEventListener('click', function() {
        filtroNombre.value = '';
        cargarUsuariosFiltrados();
    });

    async function cargarUsuariosFiltrados() {
        try {
            const params = new URLSearchParams();
            const nombre = filtroNombre.value.trim();
            if (nombre) {
                params.append('nombre', nombre);
            }
            const url = `http://localhost:8082/usuarios${params.toString() ? '?' + params.toString() : ''}`;
            const response = await fetch(url);
            if (!response.ok) throw new Error('Error al obtener usuarios');
            const usuarios = await response.json();
            renderizarTablaUsuarios(usuarios);
        } catch (error) {
            document.getElementById('tablaUsuarios').innerHTML =
                '<tr><td colspan="5" style="text-align: center; color: #dc3545; padding: 2rem;"> Error al cargar usuarios</td></tr>';
        }
    }

    // Exponer la función globalmente para que otros scripts puedan recargar la tabla
    window.cargarUsuariosFiltrados = cargarUsuariosFiltrados;

    function renderizarTablaUsuarios(usuarios) {
        const tbody = document.getElementById('tablaUsuarios');
        if (usuarios.length === 0) {
            tbody.innerHTML = '<tr><td colspan="5" style="text-align: center; padding: 2rem; color: #666;"> No se encontraron usuarios</td></tr>';
            return;
        }
        tbody.innerHTML = usuarios.map(u => `
            <tr>
                <td>${u.id}</td>
                <td>${u.nombre}</td>
                <td>${u.email}</td>
                <td>${u.rol}</td>
                <td>${u.fecha_registro}</td>
                <td>
                    <button onclick="editarUsuario(${u.id})" style="background: #28a745; color: white; border: none; padding: 0.4rem 0.8rem; border-radius: 4px; cursor: pointer; margin-right: 0.5rem; font-size: 0.9rem;">
                        Editar
                    </button>
                    <button onclick="eliminarUsuario(${u.id})" style="background: #dc3545; color: white; border: none; padding: 0.4rem 0.8rem; border-radius: 4px; cursor: pointer; font-size: 0.9rem;">
                        Eliminar
                    </button>
                </td>
            </tr>
        `).join("");
    }
    window.renderizarTablaUsuarios = renderizarTablaUsuarios;
});