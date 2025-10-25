document.addEventListener("DOMContentLoaded", function() {
    const btnRecibo = document.getElementById("btnImprimirRecibo");
    const btnFactura = document.getElementById("btnImprimirFactura");

    // Esta variable se llenará desde el flujo de venta
    let ventaSeleccionada = null;

    // Permite que otro JS asigne los datos de la venta finalizada
    window.setVentaParaImprimir = function(datosVenta) {
        ventaSeleccionada = datosVenta;
    };

    function generarHTML(tipo, datosVenta) {
        let productosHTML = datosVenta.productos.map(p =>
            `<tr><td>${p.nombre}</td><td>${p.cantidad}</td><td>S/. ${p.precio.toFixed(2)}</td><td>S/. ${(p.cantidad * p.precio).toFixed(2)}</td></tr>`
        ).join("");
        return `
        <html>
        <head>
            <title>Imprimir ${tipo}</title>
            <style>
                body { font-family: Arial, sans-serif; margin: 30px; }
                h2 { text-align: center; }
                table { width: 100%; border-collapse: collapse; margin-top: 20px; }
                th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
                th { background: #f0f0f0; }
                .total { text-align: right; font-size: 1.2em; font-weight: bold; }
            </style>
        </head>
        <body>
            <h2>BOTICA LA LUZ</h2>
            <h3>${tipo}</h3>
            <p><strong>Cliente:</strong> ${datosVenta.cliente}</p>
            <p><strong>Fecha:</strong> ${datosVenta.fecha}</p>
            <table>
                <thead>
                    <tr><th>Producto</th><th>Cantidad</th><th>Precio</th><th>Subtotal</th></tr>
                </thead>
                <tbody>
                    ${productosHTML}
                </tbody>
            </table>
            <p class="total">TOTAL: S/. ${datosVenta.total.toFixed(2)}</p>
        </body>
        </html>
        `;
    }

    function imprimir(tipo) {
        if (!ventaSeleccionada) {
            alert("Primero finaliza la venta para imprimir.");
            return;
        }
        const win = window.open("", "_blank", "width=600,height=700");
        win.document.write(generarHTML(tipo, ventaSeleccionada));
        win.document.close();
        win.focus();
        setTimeout(() => win.print(), 500);
    }

    if (btnRecibo) {
        btnRecibo.addEventListener("click", function() {
            imprimir("RECIBO");
        });
    }
    if (btnFactura) {
        btnFactura.addEventListener("click", function() {
            imprimir("FACTURA");
        });
    }
});