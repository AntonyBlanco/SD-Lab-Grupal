-- Crear la base de datos
CREATE DATABASE mydb4;

-- Usar la base de datos
USE mydb4;

CREATE TABLE productos (
    id INT PRIMARY KEY,
    nombre VARCHAR(100),
    cantidad INT,
    precio DECIMAL(10, 2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE ventas (
    id INT PRIMARY KEY AUTO_INCREMENT,
    producto_id INT,
    cantidad INT,
    total DECIMAL(10, 2),
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (producto_id) REFERENCES productos(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


