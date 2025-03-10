-- Insertar clientes
INSERT INTO CLIENTES (nombre, apellido, documento) VALUES ('Juan', 'Pérez', '12345678901');
INSERT INTO CLIENTES (nombre, apellido, documento) VALUES ('María', 'Gómez', '23456789012');
INSERT INTO CLIENTES (nombre, apellido, documento) VALUES ('Carlos', 'López', '34567890123');

-- Insertar productos
INSERT INTO PRODUCTOS (nombre, precio) VALUES ('Laptop Dell', 1200.50);
INSERT INTO PRODUCTOS (nombre, precio) VALUES ('Mouse Inalámbrico', 25.99);
INSERT INTO PRODUCTOS (nombre, precio) VALUES ('Teclado Mecánico', 75.00);
INSERT INTO PRODUCTOS (nombre, precio) VALUES ('Monitor LED 24"', 180.00);

-- Insertar facturas
INSERT INTO FACTURA (cliente_id, fecha) VALUES (1, CURRENT_TIMESTAMP);
INSERT INTO FACTURA (cliente_id, fecha) VALUES (2, CURRENT_TIMESTAMP);

-- Insertar detalles de facturas
INSERT INTO DETALLE_FACTURA (factura_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (1, 1, 1, 1200.50, 1200.50); -- Laptop Dell
INSERT INTO DETALLE_FACTURA (factura_id, producto_id, cantidad, precio_unitario, subtotal) VALUES (1, 2, 2, 25.99, 51.98); -- Mouse Inalámbrico


