// SISTEMA DE VENTAS CON CARRITO DE COMPRAS
const API_URL = 'http://127.0.0.1:8082';
let carrito = [];
let clientesGlobal = [];
let productosGlobal = [];

document.addEventListener('DOMContentLoaded', () => {
    cargarClientes();
    cargarUsuarios();
    cargarMetodosPago();
    cargarProductos();
    cargarVentas();
    configurarFechaActual();
    configurarEventos();
    configurarBuscadores();
});

function configurarFechaActual() {
    const fechaInput = document.getElementById('fecha');
    const ahora = new Date();
    const fechaFormateada = ahora.toISOString().slice(0, 16);
    fechaInput.value = fechaFormateada;
}

function configurarEventos() {
    document.getElementById('cantidad').addEventListener('input', calcularSubtotalProducto);
    document.getElementById('descuento').addEventListener('input', calcularSubtotalProducto);
    document.getElementById('btnAgregarAlCarrito').addEventListener('click', agregarAlCarrito);
    document.getElementById('btnLimpiarCarrito').addEventListener('click', limpiarCarrito);
    document.getElementById('btnFinalizarVenta').addEventListener('click', finalizarVenta);
}

function configurarBuscadores() {
    const buscarCliente = document.getElementById('buscarCliente');
    const resultadosClientes = document.getElementById('resultadosClientes');
    const buscarProducto = document.getElementById('buscarProducto');
    const resultadosProductos = document.getElementById('resultadosProductos');
    
    buscarCliente.addEventListener('input', function() {
        const texto = this.value.toLowerCase();
        if (texto.length < 2) {
            resultadosClientes.style.display = 'none';
            return;
        }
        const filtrados = clientesGlobal.filter(c => 
            c.nombre.toLowerCase().includes(texto) || 
            c.apellido.toLowerCase().includes(texto)
        );
        mostrarResultadosClientes(filtrados);
    });
    
    buscarProducto.addEventListener('input', function() {
        const texto = this.value.toLowerCase();
        if (texto.length < 2) {
            resultadosProductos.style.display = 'none';
            return;
        }
        const filtrados = productosGlobal.filter(p => 
            p.nombre.toLowerCase().includes(texto)
        );
        mostrarResultadosProductos(filtrados);
    });
    
    document.addEventListener('click', function(e) {
        if (!buscarCliente.contains(e.target) && !resultadosClientes.contains(e.target)) {
            resultadosClientes.style.display = 'none';
        }
        if (!buscarProducto.contains(e.target) && !resultadosProductos.contains(e.target)) {
            resultadosProductos.style.display = 'none';
        }
    });
}

function mostrarResultadosClientes(clientes) {
    const resultados = document.getElementById('resultadosClientes');
    if (clientes.length === 0) {
        resultados.innerHTML = '<div style="padding:8px;">No se encontraron clientes</div>';
        resultados.style.display = 'block';
        return;
    }
    resultados.innerHTML = '';
    clientes.forEach(cliente => {
        const div = document.createElement('div');
        div.style.padding = '8px';
        div.style.cursor = 'pointer';
        div.style.borderBottom = '1px solid #eee';
        div.textContent = `${cliente.nombre} ${cliente.apellido}`;
        div.addEventListener('mouseenter', () => div.style.background = '#f0f0f0');
        div.addEventListener('mouseleave', () => div.style.background = '#fff');
        div.addEventListener('click', () => {
            document.getElementById('buscarCliente').value = `${cliente.nombre} ${cliente.apellido}`;
            document.getElementById('clienteId').value = cliente.id;
            resultados.style.display = 'none';
        });
        resultados.appendChild(div);
    });
    resultados.style.display = 'block';
}

function mostrarResultadosProductos(productos) {
    const resultados = document.getElementById('resultadosProductos');
    if (productos.length === 0) {
        resultados.innerHTML = '<div style="padding:8px;">No se encontraron productos</div>';
        resultados.style.display = 'block';
        return;
    }
    resultados.innerHTML = '';
    productos.forEach(producto => {
        const div = document.createElement('div');
        div.style.padding = '8px';
        div.style.cursor = 'pointer';
        div.style.borderBottom = '1px solid #eee';
        div.textContent = `${producto.nombre} - S/. ${producto.precio} (Stock: ${producto.stock})`;
        div.addEventListener('mouseenter', () => div.style.background = '#f0f0f0');
        div.addEventListener('mouseleave', () => div.style.background = '#fff');
        div.addEventListener('click', () => {
            document.getElementById('buscarProducto').value = producto.nombre;
            document.getElementById('productoIdSeleccionado').value = producto.id;
            document.getElementById('precioUnitario').value = producto.precio;
            document.getElementById('stockDisponible').value = producto.stock;
            calcularSubtotalProducto();
            resultados.style.display = 'none';
        });
        resultados.appendChild(div);
    });
    resultados.style.display = 'block';
}

async function cargarClientes() {
    try {
        const response = await fetch(`${API_URL}/clientes`);
        clientesGlobal = await response.json();
    } catch (error) {
        console.error('Error al cargar clientes:', error);
    }
}

async function cargarUsuarios() {
    try {
        const response = await fetch(`${API_URL}/usuarios`);
        const usuarios = await response.json();
        const select = document.getElementById('usuario');
        usuarios.forEach(usuario => {
            const option = document.createElement('option');
            option.value = usuario.id;
            option.textContent = `${usuario.nombre} (${usuario.rol})`;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error al cargar usuarios:', error);
    }
}

async function cargarMetodosPago() {
    try {
        const response = await fetch(`${API_URL}/metodos-pago`);
        const metodos = await response.json();
        const select = document.getElementById('metodoPago');
        metodos.forEach(metodo => {
            const option = document.createElement('option');
            option.value = metodo.id;
            option.textContent = metodo.nombre;
            select.appendChild(option);
        });
    } catch (error) {
        console.error('Error al cargar métodos de pago:', error);
    }
}

async function cargarProductos() {
    try {
        const response = await fetch(`${API_URL}/productos`);
        productosGlobal = await response.json();
    } catch (error) {
        console.error('Error al cargar productos:', error);
    }
}

// Eliminada función actualizarDatosProducto - ahora se usa el autocompletado


function calcularSubtotalProducto() {
    const precio = parseFloat(document.getElementById('precioUnitario').value) || 0;
    const cantidad = parseInt(document.getElementById('cantidad').value) || 0;
    const descuento = parseFloat(document.getElementById('descuento').value) || 0;
    const subtotal = (precio * cantidad) * (1 - descuento / 100);
    document.getElementById('subtotalProducto').value = subtotal.toFixed(2);
}

function agregarAlCarrito() {
    const productoId = document.getElementById('productoIdSeleccionado').value;
    const productoNombre = document.getElementById('buscarProducto').value;
    if (!productoId) {
        mostrarMensaje('Seleccione un producto', 'error');
        return;
    }
    const cantidad = parseInt(document.getElementById('cantidad').value);
    const stockDisponible = parseInt(document.getElementById('stockDisponible').value);
    if (cantidad <= 0) {
        mostrarMensaje('La cantidad debe ser mayor a 0', 'error');
        return;
    }
    if (cantidad > stockDisponible) {
        mostrarMensaje(`Stock insuficiente. Disponible: ${stockDisponible}`, 'error');
        return;
    }
    if (carrito.find(item => item.producto.id === parseInt(productoId))) {
        mostrarMensaje('Este producto ya está en el carrito', 'error');
        return;
    }
    const item = {
        producto: { id: parseInt(productoId), nombre: productoNombre },
        cantidad: cantidad,
        precio_unitario: parseFloat(document.getElementById('precioUnitario').value),
        descuento: parseFloat(document.getElementById('descuento').value),
        subtotal: parseFloat(document.getElementById('subtotalProducto').value)
    };
    carrito.push(item);
    renderizarCarrito();
    calcularTotal();
    document.getElementById('buscarProducto').value = '';
    document.getElementById('productoIdSeleccionado').value = '';
    document.getElementById('precioUnitario').value = '';
    document.getElementById('stockDisponible').value = '';
    document.getElementById('cantidad').value = 1;
    document.getElementById('descuento').value = 0;
    document.getElementById('subtotalProducto').value = '';
    mostrarMensaje('Producto agregado al carrito', 'success');
}

function renderizarCarrito() {
    const tbody = document.getElementById('carritoBody');
    tbody.innerHTML = '';
    if (carrito.length === 0) {
        tbody.innerHTML = '<tr><td colspan="6" style="text-align:center;">El carrito está vacío</td></tr>';
        return;
    }
    carrito.forEach((item, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `<td>${item.producto.nombre}</td><td>$${item.precio_unitario.toFixed(2)}</td><td>${item.cantidad}</td><td>${item.descuento.toFixed(2)}%</td><td>$${item.subtotal.toFixed(2)}</td><td><button onclick="eliminarDelCarrito(${index})" style="background:#f44336; color:#fff; border:none; border-radius:4px; padding:0.4rem 0.8rem; cursor:pointer;">🗑️ Eliminar</button></td>`;
        tbody.appendChild(tr);
    });
}

function eliminarDelCarrito(index) {
    carrito.splice(index, 1);
    renderizarCarrito();
    calcularTotal();
    mostrarMensaje('Producto eliminado', 'success');
}

function limpiarCarrito() {
    if (carrito.length === 0) return;
    if (confirm('¿Limpiar carrito?')) {
        carrito = [];
        renderizarCarrito();
        calcularTotal();
    }
}

function calcularTotal() {
    const total = carrito.reduce((sum, item) => sum + item.subtotal, 0);
    document.getElementById('totalVenta').textContent = total.toFixed(2);
}

async function finalizarVenta() {
    const clienteId = document.getElementById('clienteId').value;
    const usuarioId = document.getElementById('usuario').value;
    const metodoPagoId = document.getElementById('metodoPago').value;
    const fecha = document.getElementById('fecha').value;
    if (!clienteId || !usuarioId || !metodoPagoId || !fecha || carrito.length === 0) {
        mostrarMensaje('Complete todos los campos y agregue productos', 'error');
        return;
    }
    const total = carrito.reduce((sum, item) => sum + item.subtotal, 0);
    const ventaConDetalles = {
        cliente: { id: parseInt(clienteId) },
        usuario: { id: parseInt(usuarioId) },
        metodos_pago: { id: parseInt(metodoPagoId) },
        fecha: fecha,
        total: total,
        detalles: carrito.map(item => ({
            producto: { id: item.producto.id },
            cantidad: item.cantidad,
            precio_unitario: item.precio_unitario,
            descuento: item.descuento,
            subtotal: item.subtotal
        }))
    };
    console.log('Enviando venta al backend:', JSON.stringify(ventaConDetalles, null, 2));
    try {
        const response = await fetch(`${API_URL}/ventas/crear-con-detalles`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(ventaConDetalles)
        });
        if (response.ok) {
            const ventaCreada = await response.json();
            mostrarMensaje('✅ Venta realizada. ID: ' + ventaCreada.id, 'success');
            
            // Preparar datos para impresión
            const clienteNombre = document.getElementById('buscarCliente').value;
            
            if (typeof window.setVentaParaImprimir === 'function') {
                window.setVentaParaImprimir({
                    cliente: clienteNombre,
                    fecha: new Date(ventaCreada.fecha).toLocaleString('es-ES'),
                    productos: carrito.map(item => ({
                        nombre: item.producto.nombre,
                        cantidad: item.cantidad,
                        precio: item.precio_unitario
                    })),
                    total: ventaCreada.total
                });
            }
            
            carrito = [];
            renderizarCarrito();
            calcularTotal();
            document.getElementById('buscarCliente').value = '';
            document.getElementById('clienteId').value = '';
            document.getElementById('usuario').value = '';
            document.getElementById('metodoPago').value = '';
            configurarFechaActual();
            cargarVentas();
            cargarProductos();
        } else {
            const error = await response.text();
            mostrarMensaje('Error: ' + error, 'error');
        }
    } catch (error) {
        mostrarMensaje('Error de conexión', 'error');
    }
}

async function cargarVentas() {
    try {
        const response = await fetch(`${API_URL}/ventas`);
        const ventas = await response.json();
        const tbody = document.getElementById('tablaVentas');
        tbody.innerHTML = '';
        ventas.forEach(venta => {
            const tr = document.createElement('tr');
            tr.innerHTML = `<td>${venta.id}</td><td>${venta.cliente?.nombre || 'N/A'} ${venta.cliente?.apellido || ''}</td><td>${venta.usuario?.nombre || 'N/A'}</td><td>${venta.metodoPago?.nombre || 'N/A'}</td><td>${new Date(venta.fecha).toLocaleString('es-ES')}</td><td>$${venta.total}</td>`;
            tbody.appendChild(tr);
        });
    } catch (error) {
        console.error('Error al cargar ventas:', error);
    }
}

function mostrarMensaje(texto, tipo) {
    const mensaje = document.getElementById('mensaje');
    mensaje.textContent = texto;
    mensaje.style.color = tipo === 'success' ? 'green' : 'red';
    setTimeout(() => mensaje.textContent = '', 5000);
}
