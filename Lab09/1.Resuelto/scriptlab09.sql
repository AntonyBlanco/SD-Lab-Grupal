-- Crear la base de datos
CREATE DATABASE mydb3;

-- Usar la base de datos
USE mydb3;

-- Estructura de tabla para la tabla 'mitabla'
CREATE TABLE mitabla (
    DNI VARCHAR(12) DEFAULT NULL,
    correo VARCHAR(32) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Estructura de tabla para la tabla 'miotratabla'
CREATE TABLE miotratabla (
    nombre VARCHAR(20) DEFAULT NULL,
    apellido VARCHAR(20) DEFAULT NULL,
    edad INT(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

