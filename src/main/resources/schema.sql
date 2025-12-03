-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS inscripciones_talleres;
USE inscripciones_talleres;

-- Tabla de talleres
CREATE TABLE taller (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(100) NOT NULL,
                        descripcion VARCHAR(255),
                        fecha DATETIME,
                        precio DECIMAL(10,2) NOT NULL
);

-- Insertar datos de ejemplo
INSERT INTO taller (nombre, descripcion, fecha, precio) VALUES
                                                            ('Taller de Fotografía', 'Aprende técnicas de fotografía profesional', '2025-06-15 10:00:00', 50.00),
                                                            ('Taller de Cocina Vegana', 'Recetas veganas fáciles y deliciosas', '2025-07-01 15:00:00', 30.00),
                                                            ('Taller de Programación en Java', 'Curso básico de Java para principiantes', '2025-08-10 09:30:00', 40.00);

-- Tabla de inscripciones
CREATE TABLE inscripcion (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             taller_id INT NOT NULL,
                             nombre_participante VARCHAR(100),
                             email_participante VARCHAR(100),
                             numero_asistentes INT,
                             precio_total DECIMAL(10,2),
                             FOREIGN KEY (taller_id) REFERENCES taller(id)
);

-- Datos de prueba para inscripciones
INSERT INTO inscripcion (taller_id, nombre_participante, email_participante, numero_asistentes, precio_total) VALUES
                                                                                                                  (1, 'María López', 'maria.lopez@example.com', 2, 100.00),
                                                                                                                  (2, 'Javier Ruiz', 'j.ruiz@gmail.com', 1, 30.00),
                                                                                                                  (3, 'Ana García', 'ana.garcia@yahoo.com', 3, 120.00);
