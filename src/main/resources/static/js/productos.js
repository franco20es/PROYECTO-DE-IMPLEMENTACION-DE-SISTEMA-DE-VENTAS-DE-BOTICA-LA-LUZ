document.addEventListener("DOMContentLoaded", () => {
    // Cargar categorías en el select
    async function cargarCategoriasSelect() {
        const select = document.getElementById("categoria");
        if (!select) return;
        select.innerHTML = '<option value="">Seleccione una categoría</option>';
        try {
            const response = await fetch("http://localhost:8082/categorias");
            if (response.ok) {
                const categorias = await response.json();
                categorias.forEach(cat => {
                    const option = document.createElement("option");
                    option.value = cat.id;
                    option.textContent = cat.nombre;
                    select.appendChild(option);
                });
            }
        } catch {
            // Si falla, deja solo la opción por defecto
        }
    }

    // Llamar al cargar la página y al abrir el modal
    cargarCategoriasSelect();
    const form = document.getElementById("formProducto");
    const mensaje = document.getElementById("mensaje");
    const btnMostrarForm = document.getElementById("btnMostrarForm");
    const btnCancelar = document.getElementById("btnCancelar");
    const modal = document.getElementById("modalProducto");
    const btnCerrarModal = document.getElementById("btnCerrarModal");
    const tituloModal = document.getElementById("tituloModal");

    // Función para abrir el modal
    function abrirModal() {
        modal.style.display = "block";
        tituloModal.textContent = " Agregar Producto";
        form.dataset.modo = "crear";
        form.onsubmit = crearProducto;
        mensaje.textContent = "";
        cargarCategoriasSelect();
    }

    // Función para cerrar el modal
    function cerrarModal() {
        modal.style.display = "none";
        form.reset();
        mensaje.textContent = "";
    }

    // Event listeners para abrir/cerrar modal
    btnMostrarForm.onclick = abrirModal;
    btnCancelar.onclick = cerrarModal;
    btnCerrarModal.onclick = cerrarModal;

    // Cerrar modal al hacer click fuera del contenido
    window.onclick = (event) => {
        if (event.target === modal) {
            cerrarModal();
        }
    };

    // Función para crear producto
    async function crearProducto(e) {
        e.preventDefault();
        const nombre = document.getElementById("nombre").value.trim();
        const descripcion = document.getElementById("descripcion").value.trim();
        const precio = parseFloat(document.getElementById("precio").value);
        const stock = parseInt(document.getElementById("stock").value);
        const categoriaId = document.getElementById("categoria").value;

        if (!categoriaId) {
            mensaje.textContent = "Seleccione una categoría";
            mensaje.style.color = "red";
            return;
        }
        try {
            const response = await fetch("http://localhost:8082/productos", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ nombre, descripcion, precio, stock, categoria: { id: parseInt(categoriaId) } })
            });
            if (response.ok) {
                mensaje.textContent = "Producto registrado correctamente";
                mensaje.style.color = "green";
                form.reset();
                setTimeout(() => {
                    cerrarModal();
                    location.reload(); // Recargar para actualizar la tabla
                }, 1500);
            } else {
                mensaje.textContent = " Error al registrar producto";
                mensaje.style.color = "red";
            }
        } catch {
            mensaje.textContent = " Error al conectar con el servidor";
            mensaje.style.color = "red";
        }
    }

    // Hacer accesible la función para editar desde otros archivos
    window.abrirModalEditar = function(producto) {
        modal.style.display = "block";
        tituloModal.textContent = "Editar Producto";
        
        // Llenar el formulario con los datos del producto
        document.getElementById("nombre").value = producto.nombre;
        document.getElementById("descripcion").value = producto.descripcion;
        document.getElementById("precio").value = producto.precio;
        document.getElementById("stock").value = producto.stock;
        
        form.dataset.modo = "editar";
        form.dataset.productoId = producto.id;
    };
});