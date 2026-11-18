-- Ejecutar solo desde una sesión administrativa, después de crear la base de datos.
CREATE USER IF NOT EXISTS 'alumno_java'@'localhost' IDENTIFIED BY 'Alumno2026!';
GRANT SELECT,INSERT,UPDATE,DELETE ON cursos_capacitacion.* TO 'alumno_java'@'localhost';
-- Cambie la contraseña de ejemplo antes de usar en otro entorno.
