CREATE DATABASE LABUENATIERRA;
USE LABUENATIERRA;

CREATE TABLE Productos (
                           id_producto INT PRIMARY KEY AUTO_INCREMENT,
                           nombre VARCHAR(100) NOT NULL,
                           descripcion TEXT,
                           precio DECIMAL(10, 2) NOT NULL,
                           stock INT NOT NULL,
                           categoria VARCHAR(50),
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
                          comentarios TEXT
);

CREATE TABLE Pedidos (
                         id_pedido INT PRIMARY KEY AUTO_INCREMENT,
                         id_cliente INT,
                         fecha_pedido DATE,
                         total DECIMAL(10, 2),
                         estado VARCHAR(50),
                         FOREIGN KEY (id_cliente) REFERENCES Clientes(id_cliente)
);

CREATE TABLE DetallesPedidos (
                                 id_detalle INT PRIMARY KEY AUTO_INCREMENT,
                                 id_pedido INT,
                                 id_producto INT,
                                 cantidad INT,
                                 precio_unitario DECIMAL(10, 2),
                                 FOREIGN KEY (id_pedido) REFERENCES Pedidos(id_pedido),
                                 FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

CREATE TABLE Inventario (
                            id_movimiento INT PRIMARY KEY AUTO_INCREMENT,
                            id_producto INT,
                            cantidad INT,
                            tipo_movimiento VARCHAR(50), -- "entrada" o "salida"
                            fecha_movimiento DATE,
                            FOREIGN KEY (id_producto) REFERENCES Productos(id_producto)
);

CREATE TABLE Finanzas (
                          id_finanza INT PRIMARY KEY AUTO_INCREMENT,
                          tipo VARCHAR(50), -- "venta" o "inversion"
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
