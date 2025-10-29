//filtro para buscar productos
document.addEventListener("DOMContentLoaded", () => {
    const tabla = document.getElementById("tablaProductos");
    const formFiltro = document.getElementById("formFiltroProductos");
    const inputNombre = document.getElementById("filtroNombre");
    const inputPrecioMin = document.getElementById("filtroPrecioMin");
    const inputPrecioMax = document.getElementById("filtroPrecioMax");
    const btnLimpiar = document.getElementById("btnLimpiarFiltros");
    
    // Cargar productos desde el backend con filtros
    async function cargarProductos(filtros = {}) {
        tabla.innerHTML = "";
        try {
            let url = "http://localhost:8082/productos?";
            if (filtros.nombre) url += `nombre=${encodeURIComponent(filtros.nombre)}&`;
            if (filtros.precioMin) url += `precioMin=${filtros.precioMin}&`;
            if (filtros.precioMax) url += `precioMax=${filtros.precioMax}&`;
            
            const response = await fetch(url);
            const productos = await response.json();
            
            if (productos.length === 0) {
                tabla.innerHTML = "<tr><td colspan='5' style='text-align:center; padding:2rem; color:#999;'>No se encontraron productos con esos filtros</td></tr>";
                return;
            }
            
            productos.forEach(p => {
                const fila = `<tr>
                    <td>${p.id}</td>
                    <td>${p.nombre}</td>
                    <td>${p.descripcion}</td>
                    <td>S/ ${p.precio.toFixed(2)}</td>
                    <td>${p.stock}</td>
                    <td>
                        <button onclick="editarProducto(${p.id})" 
                            style="background:#4CAF50; color:white; border:none; padding:0.5rem 1rem; border-radius:4px; cursor:pointer; margin-right:0.5rem; font-weight:600;">
                             Actualizar
                        </button>
                        <button onclick="eliminarProducto(${p.id})" 
                            style="background:#f44336; color:white; border:none; padding:0.5rem 1rem; border-radius:4px; cursor:pointer; font-weight:600;">
                             Eliminar
                        </button>
                    </td>
                </tr>`;
                tabla.innerHTML += fila;
            });
        } catch (error) {
            console.error("Error al cargar productos:", error);
            tabla.innerHTML = "<tr><td colspan='5' style='text-align:center; padding:2rem; color:red;'>Error al cargar los productos</td></tr>";
        }
    }

    // Event listener para filtrar
    formFiltro.addEventListener("submit", (e) => {
        e.preventDefault();
        const filtros = {
            nombre: inputNombre.value.trim(),
            precioMin: inputPrecioMin.value,
            precioMax: inputPrecioMax.value
        };
        cargarProductos(filtros);
    });

    // Event listener para limpiar filtros
    btnLimpiar.addEventListener("click", () => {
        formFiltro.reset();
        cargarProductos(); // Cargar todos los productos
    });

    // Cargar todos los productos al inicio
    cargarProductos();
});