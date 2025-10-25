CREATE OR REPLACE FUNCTION actualizar_stock() RETURNS TRIGGER AS $$
BEGIN
    UPDATE productos
    SET stock = stock - NEW.cantidad
    WHERE id_producto = NEW.id_producto;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_actualizar_stock
AFTER INSERT ON detalle_ventas
FOR EACH ROW
EXECUTE FUNCTION actualizar_stock();
