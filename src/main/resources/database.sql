CREATE DATABASE LABUENATIERRA;
USE LABUENATIERRA;

CREATE TABLE Productos (
                           id_producto INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(100) NOT NULL,
                           descripcion TEXT,
                           precio DECIMAL(10, 2) NOT NULL,
                           stock INT NOT NULL,
                           imagen VARCHAR(255),
                           categoria ENUM('Bollería Artesanal', 'Productos de Navidad', 'Fritos', 'Tortas Artesanas') NOT NULL, -- Modificación: categoria ahora es ENUM
                           fecha_creacion DATE,
                           calidad VARCHAR(50)
);

CREATE TABLE Proveedores (
                             id_proveedor INT PRIMARY KEY AUTO_INCREMENT,
                             nombre VARCHAR(100) NOT NULL,
                             contacto VARCHAR(100),
                             direccion VARCHAR(255),
                             telefono VARCHAR(15),
                             email VARCHAR(100),
                             calidad_producto VARCHAR(50)
);

CREATE TABLE Clientes (
                          id_cliente INT PRIMARY KEY AUTO_INCREMENT,
                          nombre VARCHAR(100) NOT NULL,
                          email VARCHAR(100) NOT NULL,
                          telefono VARCHAR(15),
                          direccion VARCHAR(255),
                          programa_fidelizacion BOOLEAN DEFAULT FALSE,
                          comentarios TEXT,
                          contraseña VARCHAR(255)
);

CREATE TABLE Pedidos (
                         id_pedido INT PRIMARY KEY AUTO_INCREMENT,
                         id_cliente INT,
                         fecha_pedido DATE,
                         total DECIMAL(10, 2),
                         estado VARCHAR(50),
                         FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE Inventario (
                            id_movimiento INT PRIMARY KEY AUTO_INCREMENT,
                            id_producto INT,
                            cantidad INT,
                            tipo_movimiento VARCHAR(50),
                            fecha_movimiento DATE,
                            FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

CREATE TABLE Finanzas (
                          id_finanza INT PRIMARY KEY AUTO_INCREMENT,
                          tipo VARCHAR(50),
                          monto DECIMAL(15, 2),
                          fecha DATE,
                          descripcion TEXT
);

CREATE TABLE Marketing (
                           id_campaña INT PRIMARY KEY AUTO_INCREMENT,
                           nombre_campaña VARCHAR(100),
                           descripcion TEXT,
                           fecha_inicio DATE,
                           fecha_fin DATE,
                           colaborador VARCHAR(100)
);

CREATE TABLE Empleados (
                           id_empleado INT PRIMARY KEY AUTO_INCREMENT,
                           nombre_apellidos VARCHAR(255) NOT NULL,
                           email VARCHAR(100) NOT NULL,
                           telefono VARCHAR(15),
                           direccion VARCHAR(255),
                           salario DECIMAL(10, 2),
                           puesto_trabajo VARCHAR(100)
);

INSERT INTO Productos (nombre, descripcion, precio, stock, imagen, categoria, fecha_creacion, calidad) VALUES
        ('Palmeras de huevo', 'Deliciosas palmeras de huevo rellenas de crema', 20.00, 50, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Alta'),
        ('Donuts', 'Esponjosos donuts glaseados', 15.00, 30, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Media'),
        ('Tortas de aceite', 'Tortas crujientes con sabor a aceite de oliva', 5.00, 100, 'palmerasdehuevo.jpg', 'Tortas Artesanas', CURDATE(), 'Alta'),
        ('Galletas de chocolate', 'Galletas caseras con trozos de chocolate', 10.00, 60, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Media'),
        ('Churros', 'Deliciosos churros con chocolate caliente', 8.00, 80, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Media'),
        ('Tarta de manzana', 'Tarta de manzana fresca y crujiente', 25.00, 20, 'palmerasdehuevo.jpg', 'Tortas Artesanas', CURDATE(), 'Alta'),
        ('Tarta de chocolate', 'Tarta de chocolate negro con crema', 30.00, 15, 'palmerasdehuevo.jpg', 'Tortas Artesanas', CURDATE(), 'Alta'),
        ('Magdalenas', 'Magdalenas esponjosas con sabor a limón', 12.00, 40, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Media'),
        ('Bizcocho de vainilla', 'Bizcocho suave y esponjoso con sabor a vainilla', 15.00, 35, 'palmerasdehuevo.jpg', 'Tortas Artesanas', CURDATE(), 'Media'),
        ('Pasteles de crema', 'Pasteles rellenos de crema pastelera', 18.00, 25, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Alta'),
        ('Pan de chocolate', 'Pan suave con trozos de chocolate', 6.00, 75, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Media'),
        ('Rosquillas', 'Rosquillas caseras con glaseado', 9.00, 50, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Media'),
        ('Tartaletas de fruta', 'Tartaletas frescas con frutas variadas', 20.00, 30, 'palmerasdehuevo.jpg', 'Tortas Artesanas', CURDATE(), 'Alta'),
        ('Panettone', 'Pan dulce italiano con frutas confitadas', 40.00, 10, 'palmerasdehuevo.jpg', 'Productos de Navidad', CURDATE(), 'Alta'),
        ('Galletas de jengibre', 'Galletas especiadas de jengibre', 12.00, 20, 'palmerasdehuevo.jpg', 'Productos de Navidad', CURDATE(), 'Media'),
        ('Turrón de almendra', 'Turrón tradicional de almendra', 25.00, 5, 'palmerasdehuevo.jpg', 'Productos de Navidad', CURDATE(), 'Alta'),
        ('Mantecados', 'Mantecados caseros con canela', 15.00, 40, 'palmerasdehuevo.jpg', 'Productos de Navidad', CURDATE(), 'Media'),
        ('Roscón de Reyes', 'Roscón tradicional para la celebración de Reyes', 50.00, 8, 'palmerasdehuevo.jpg', 'Productos de Navidad', CURDATE(), 'Alta'),
        ('Coca de San Juan', 'Coca dulce con frutas confitadas', 30.00, 15, 'palmerasdehuevo.jpg', 'Productos de Navidad', CURDATE(), 'Alta'),
        ('Cinnamon Rolls', 'Rollos de canela con glaseado', 22.00, 18, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Alta'),
        ('Tarta de queso', 'Tarta de queso con mermelada de frutas', 28.00, 12, 'palmerasdehuevo.jpg', 'Tortas Artesanas', CURDATE(), 'Alta'),
        ('Bollos de leche', 'Bollos tiernos y esponjosos', 11.00, 70, 'palmerasdehuevo.jpg', 'Bollería Artesanal', CURDATE(), 'Media');

INSERT INTO Clientes (nombre, email, telefono, direccion, programa_fidelizacion, comentarios, contraseña) VALUES
        ('Juan Pérez', 'juan.perez@example.com', '612345678', 'Calle Falsa 123', FALSE, 'Cliente habitual', 'contraseña123'),
        ('María García', 'maria.garcia@example.com', '698765432', 'Avenida Siempre Viva 742', TRUE, 'Interesada en promociones', 'contraseña456');

INSERT INTO Proveedores (nombre, contacto, direccion, telefono, email, calidad_producto) VALUES
        ('Proveedores de Dulces S.A.', 'Ana López', 'Calle de la Azucarera 45', '612345678', 'contacto@dulcessa.com', 'Alta'),
        ('Distribuciones Bakery', 'Carlos Fernández', 'Avenida de la Panadería 12', '698765432', 'info@distribucionesbakery.com', 'Media'),
        ('Frutos Secos y Más', 'Laura Martínez', 'Calle de los Frutos 101', '623456789', 'ventas@frutossecos.com', 'Alta');

INSERT INTO Finanzas (tipo, monto, fecha, descripcion) VALUES
        ('venta', 1500.00, '2024-10-01', 'Venta de productos artesanales'),
        ('inversion', 800.00, '2024-10-05', 'Inversión en materiales de marketing'),
        ('venta', 300.00, '2024-10-07', 'Venta de merchandising');

INSERT INTO Marketing (nombre_campaña, descripcion, fecha_inicio, fecha_fin, colaborador) VALUES
        ('Campaña de Navidad', 'Promoción de productos navideños', '2024-11-01', '2024-12-31', 'Juan Pérez'),
        ('Verano 2024', 'Descuentos en productos de verano', '2024-06-01', '2024-08-31', 'Ana García'),
        ('Lanzamiento Nuevo Producto', 'Presentación de la nueva línea de productos', '2024-09-15', '2024-09-30', 'Pedro López');

INSERT INTO Empleados (nombre_apellidos, email, telefono, direccion, salario, puesto_trabajo) VALUES
        ('Carlos Mendoza', 'c.mendoza@email.com', '123456789', 'Calle Falsa 123, Ciudad', 2500.00, 'Laminador'),
        ('Laura Gómez', 'l.gomez@email.com', '987654321', 'Avenida Siempre Viva 456, Ciudad', 2000.00, 'Asistente de Marketing'),
        ('Javier Torres', 'j.torres@email.com', '555123456', 'Calle de la Libertad 789, Ciudad', 1800.00, 'Encargado');
