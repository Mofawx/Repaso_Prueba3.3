-- Cursos de Capacitación | dos tablas relacionadas, con registros de prueba.
CREATE DATABASE IF NOT EXISTS cursos_capacitacion CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE cursos_capacitacion;
CREATE TABLE IF NOT EXISTS tipos_curso (id_tipo INT PRIMARY KEY, descripcion VARCHAR(60) NOT NULL UNIQUE) ENGINE=InnoDB;
CREATE TABLE IF NOT EXISTS cursos (codigo VARCHAR(12) PRIMARY KEY,nombre VARCHAR(100) NOT NULL,precio_base DECIMAL(12,2) NOT NULL,detalle VARCHAR(100) NOT NULL,id_tipo INT NOT NULL,CONSTRAINT fk_cursos_capacit FOREIGN KEY(id_tipo) REFERENCES tipos_curso(id_tipo)) ENGINE=InnoDB;
INSERT INTO tipos_curso(id_tipo,descripcion) VALUES (1,'CursoPresencial'),(2,'CursoOnline') ON DUPLICATE KEY UPDATE descripcion=VALUES(descripcion);
INSERT INTO cursos(codigo,nombre,precio_base,detalle,id_tipo) VALUES('CUR01','Java Básico',85000,'Lab A',1) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),precio_base=VALUES(precio_base),detalle=VALUES(detalle),id_tipo=VALUES(id_tipo);
INSERT INTO cursos(codigo,nombre,precio_base,detalle,id_tipo) VALUES('CUR02','SQL Inicial',65000,'Lab B',1) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),precio_base=VALUES(precio_base),detalle=VALUES(detalle),id_tipo=VALUES(id_tipo);
INSERT INTO cursos(codigo,nombre,precio_base,detalle,id_tipo) VALUES('CUR03','Git Online',45000,'Meet',2) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),precio_base=VALUES(precio_base),detalle=VALUES(detalle),id_tipo=VALUES(id_tipo);
INSERT INTO cursos(codigo,nombre,precio_base,detalle,id_tipo) VALUES('CUR04','Diseño Web',55000,'Teams',2) ON DUPLICATE KEY UPDATE nombre=VALUES(nombre),precio_base=VALUES(precio_base),detalle=VALUES(detalle),id_tipo=VALUES(id_tipo);
SELECT * FROM tipos_curso;
SELECT * FROM cursos;
