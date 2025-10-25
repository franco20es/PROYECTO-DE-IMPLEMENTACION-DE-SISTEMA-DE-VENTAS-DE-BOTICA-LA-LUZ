package com.example.FARMACIA.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Clientes;
import com.example.FARMACIA.Model.DetalleVentaDTO;
import com.example.FARMACIA.Model.Detalle_ventas;
import com.example.FARMACIA.Model.Metodos_pago;
import com.example.FARMACIA.Model.Usuarios;
import com.example.FARMACIA.Model.VentaConDetallesDTO;
import com.example.FARMACIA.Model.Ventas;
import com.example.FARMACIA.Repository.ClientesRepository;
import com.example.FARMACIA.Repository.Detalle_ventasRepository;
import com.example.FARMACIA.Repository.MetodosPagoRepository;
import com.example.FARMACIA.Repository.ProductosRepository;
import com.example.FARMACIA.Repository.UsuarioRepository;
import com.example.FARMACIA.Repository.VentasRepository;

import jakarta.transaction.Transactional;


@RestController
@RequestMapping("/ventas")
public class VentasController {

    @Autowired
    private VentasRepository ventasRepository;
    @Autowired
    private ClientesRepository clientesRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private MetodosPagoRepository metodosPagoRepository;

    @Autowired
    private Detalle_ventasRepository detalleVentasRepository;

    @Autowired
    private ProductosRepository productosRepository;

    @GetMapping("/detalle")
    public Ventas listarVentas() {
        // Método para listar una venta específica o lógica personalizada
        Clientes cliente = new Clientes();
        cliente.setId(1L);
        cliente.setNombre("franno");
        cliente.setApellido("gomez");
        cliente.setTelefono("987654321");

        Usuarios usuario = new Usuarios();
        usuario.setId(1L);
        usuario.setNombre("Usuario Ejemplo");
        usuario.setEmail("usuario@example.com");
        usuario.setPassword("password");
        usuario.setRol("CLIENTE");
        usuario.setFecha_registro(LocalDate.EPOCH);

        Metodos_pago metodoPago = new Metodos_pago();
        metodoPago.setId(1L);
        metodoPago.setNombre("Efectivo");

        return new Ventas(cliente, usuario, metodoPago, LocalDateTime.now(), new BigDecimal("100.50"));
    }

    @PostMapping
    public ResponseEntity<?> crearVenta(@RequestBody Ventas venta) {
        if (venta.getCliente() == null || venta.getCliente().getId() == null) {
            return ResponseEntity.badRequest().body("El cliente es obligatorio.");
        }
        if (venta.getUsuario() == null || venta.getUsuario().getId() == null) {
            return ResponseEntity.badRequest().body("El usuario es obligatorio.");
        }
        if (venta.getMetodoPago() == null || venta.getMetodoPago().getId() == null) {
            return ResponseEntity.badRequest().body("El método de pago es obligatorio.");
        }

        // Cargar los objetos relacionados desde la base de datos
        Clientes cliente = clientesRepository.findById(venta.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
        Usuarios usuario = usuarioRepository.findById(venta.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
        Metodos_pago metodoPago = metodosPagoRepository.findById(venta.getMetodoPago().getId())
                .orElseThrow(() -> new RuntimeException("Método de pago no encontrado."));

        // Asignar los objetos cargados a la venta
        venta.setCliente(cliente);
        venta.setUsuario(usuario);
        venta.setMetodoPago(metodoPago);

        // Guardar la venta en la base de datos
        Ventas nuevaVenta = ventasRepository.save(venta);
        return ResponseEntity.ok(nuevaVenta);
    }
    @PutMapping("/{id}")    
    public ResponseEntity<?> actualizarVenta(@PathVariable Long id, @RequestBody Ventas venta) {
        Ventas ventaExistente = ventasRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada."));
        if (venta.getCliente() != null && venta.getCliente().getId() != null) {
            Clientes cliente = clientesRepository.findById(venta.getCliente().getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado."));
            ventaExistente.setCliente(cliente);
        }
        if (venta.getUsuario() != null && venta.getUsuario().getId() != null) {
            Usuarios usuario = usuarioRepository.findById(venta.getUsuario().getId())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado."));
            ventaExistente.setUsuario(usuario);
        }
        if (venta.getMetodoPago() != null && venta.getMetodoPago().getId() != null) {
            Metodos_pago metodoPago = metodosPagoRepository.findById(venta.getMetodoPago().getId())
                    .orElseThrow(() -> new RuntimeException("Método de pago no encontrado."));
            ventaExistente.setMetodoPago(metodoPago);
        }
        ventaExistente.setFecha(venta.getFecha());
        ventaExistente.setTotal(venta.getTotal());
        Ventas ventaActualizada = ventasRepository.save(ventaExistente);
        return ResponseEntity.ok(ventaActualizada);
    }
    @GetMapping
    public java.util.List<Ventas> listarTodasLasVentas() {
        return ventasRepository.findAll();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelarVentas(@PathVariable Long id) {
        ventasRepository.deleteById(id);
        return ResponseEntity.ok("Venta cancelada exitosamente.");
    }

    /**
     * Endpoint para crear una venta completa con todos sus detalles en una sola petición
     * Este método recibe el carrito de compras y crea automáticamente:
     * 1. El registro de la venta principal
     * 2. Todos los detalles de venta (productos del carrito)
     * 3. El trigger de BD actualiza automáticamente el stock de productos
     * 
     * @param ventaDTO Objeto que contiene la venta y la lista de detalles (carrito)
     * @return La venta creada con todos sus detalles
     */
    @PostMapping("/crear-con-detalles")
    @Transactional // Asegura que todo se guarda o nada (transacción atómica)
    public ResponseEntity<?> crearVentaConDetalles(@RequestBody VentaConDetallesDTO ventaDTO) {
        try {
            System.out.println("=== INICIO CREAR VENTA CON DETALLES ===");
            System.out.println("Cliente ID recibido: " + (ventaDTO.getCliente() != null ? ventaDTO.getCliente().getId() : "NULL"));
            System.out.println("Usuario ID recibido: " + (ventaDTO.getUsuario() != null ? ventaDTO.getUsuario().getId() : "NULL"));
            System.out.println("Metodo Pago ID recibido: " + (ventaDTO.getMetodos_pago() != null ? ventaDTO.getMetodos_pago().getId() : "NULL"));
            
            // Validaciones básicas
            if (ventaDTO.getCliente() == null || ventaDTO.getCliente().getId() == null) {
                return ResponseEntity.badRequest().body("El cliente es obligatorio.");
            }
            if (ventaDTO.getUsuario() == null || ventaDTO.getUsuario().getId() == null) {
                return ResponseEntity.badRequest().body("El usuario es obligatorio.");
            }
            if (ventaDTO.getMetodos_pago() == null || ventaDTO.getMetodos_pago().getId() == null) {
                return ResponseEntity.badRequest().body("El método de pago es obligatorio.");
            }
            if (ventaDTO.getDetalles() == null || ventaDTO.getDetalles().isEmpty()) {
                return ResponseEntity.badRequest().body("Debe agregar al menos un producto al carrito.");
            }

            // Cargar las entidades relacionadas desde la base de datos usando findById
            // Esto asegura que realmente se valide la existencia del registro
            System.out.println("Buscando cliente con ID: " + ventaDTO.getCliente().getId());
            Clientes cliente = clientesRepository.findById(ventaDTO.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + ventaDTO.getCliente().getId() + " no encontrado en la base de datos."));
            System.out.println("Cliente encontrado: " + cliente.getNombre());
            
            System.out.println("Buscando usuario con ID: " + ventaDTO.getUsuario().getId());
            Usuarios usuario = usuarioRepository.findById(ventaDTO.getUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + ventaDTO.getUsuario().getId() + " no encontrado en la base de datos."));
            System.out.println("Usuario encontrado: " + usuario.getNombre());
            
            System.out.println("Buscando método de pago con ID: " + ventaDTO.getMetodos_pago().getId());
            Metodos_pago metodoPago = metodosPagoRepository.findById(ventaDTO.getMetodos_pago().getId())
                .orElseThrow(() -> new RuntimeException("Método de pago con ID " + ventaDTO.getMetodos_pago().getId() + " no encontrado en la base de datos."));
            System.out.println("Método de pago encontrado: " + metodoPago.getNombre());

            // PASO 1: Crear y guardar la venta principal
            Ventas venta = new Ventas();
            venta.setCliente(cliente);
            venta.setUsuario(usuario);
            venta.setMetodoPago(metodoPago);
            venta.setFecha(ventaDTO.getFecha());
            venta.setTotal(ventaDTO.getTotal());
            
            System.out.println("Guardando venta con Cliente ID: " + cliente.getId() + ", Usuario ID: " + usuario.getId());
            
            // Guardar la venta y obtener el ID generado
            Ventas ventaGuardada = ventasRepository.save(venta);
            ventasRepository.flush(); // Forzar la sincronización inmediata con la BD
            
            System.out.println("Venta guardada exitosamente con ID: " + ventaGuardada.getId());

            // PASO 2: Crear todos los detalles de venta (productos del carrito)
            for (DetalleVentaDTO detalleDTO : ventaDTO.getDetalles()) {
                // Validar que el producto exista
                if (detalleDTO.getProducto() == null || detalleDTO.getProducto().getId() == null) {
                    throw new RuntimeException("Producto inválido en el carrito.");
                }

                // Cargar el producto desde la base de datos validando su existencia
                var producto = productosRepository.findById(detalleDTO.getProducto().getId())
                    .orElseThrow(() -> new RuntimeException("Producto con ID " + detalleDTO.getProducto().getId() + " no encontrado."));

                // Crear el detalle de venta
                Detalle_ventas detalle = new Detalle_ventas();
                detalle.setVenta(ventaGuardada); // Asociar con la venta recién creada
                detalle.setProducto(producto); // Usar el producto cargado desde BD
                detalle.setCantidad(detalleDTO.getCantidad());
                detalle.setPrecio_unitario(detalleDTO.getPrecio_unitario());
                detalle.setDescuento(detalleDTO.getDescuento());
                detalle.setSubtotal(detalleDTO.getSubtotal());

                // Guardar el detalle
                // IMPORTANTE: Al hacer este INSERT, el trigger actualizar_stock() se ejecuta automáticamente
                // y actualiza el stock del producto: UPDATE productos SET stock = stock - cantidad
                detalleVentasRepository.save(detalle);
            }

            // Retornar la venta completa
            return ResponseEntity.ok(ventaGuardada);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error al crear la venta: " + e.getMessage());
        }
    }
}
