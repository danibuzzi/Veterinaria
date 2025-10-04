-- #################################################################
-- # SCRIPT DE CREACIÓN: CLÍNICA VETERINARIA (Versión Final)
-- #################################################################

-- CREACIÓN DE LA BASE DE DATOS
CREATE DATABASE IF NOT EXISTS Veterinaria
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE Veterinaria;

-- #################################################################
-- # Creación de las tablas
-- #################################################################


-- Tabla: Propietarios
CREATE TABLE propietario (
    idPropietario INT PRIMARY KEY AUTO_INCREMENT,
    dni VARCHAR(15) UNIQUE NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    fechanacimiento DATE,
    pais VARCHAR(50),
    ciudad VARCHAR(50),
	direccion VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Tabla: Mascotas 
CREATE TABLE mascota (
    idMascota INT PRIMARY KEY AUTO_INCREMENT,
    idPropietario INT NOT NULL, -- FK
    nombre VARCHAR(50) NOT NULL,
    fechanacimiento DATE,
    especie VARCHAR(50),
    sexo VARCHAR(10),
    seniasparticulares VARCHAR(250),
    
    FOREIGN KEY (idPropietario) REFERENCES Propietarios(idPropietario) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Tabla: TipoConsulta
CREATE TABLE tipoconsulta (
    idTipoConsulta INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Tabla: TipoPractica
CREATE TABLE tipopractica (
    idTipoPractica INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- #################################################################
-- # TABLAS DE TRANSACCIÓN Y REGISTROS
-- #################################################################

-- Tabla: Turno
CREATE TABLE turno (
    idTurno INT PRIMARY KEY AUTO_INCREMENT,
    idTipoConsulta INT NOT NULL, 
    idPropietario INT NOT NULL,  
    idMascota INT NOT NULL,      
    fechaturno DATE NOT NULL,
    hora TIME NOT NULL,
    
    FOREIGN KEY (idTipoConsulta) REFERENCES TipoConsulta(idTipoConsulta) ON DELETE RESTRICT,
    FOREIGN KEY (idPropietario) REFERENCES Propietarios(idPropietario) ON DELETE CASCADE,
    FOREIGN KEY (idMascota) REFERENCES Mascotas(idMascota) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- Tabla: Consulta
CREATE TABLE consulta (
    idConsulta INT PRIMARY KEY AUTO_INCREMENT,
    idMascota INT NOT NULL,      
    idPropietario INT NOT NULL,  
    idTipoPractica INT NOT NULL, 
    fechaconsulta DATE NOT NULL,
    hora TIME NOT NULL,
    resultadoestudio VARCHAR(2000) NOT NULL DEFAULT 'No solicitado',
    Diagnostico TEXT NOT NULL,
    Pronostico TEXT,              -- Pronóstico se mantiene opcional (NULL permitido)
    Tratamiento TEXT NOT NULL,
    FOREIGN KEY (idMascota) REFERENCES Mascotas(idMascota) ON DELETE CASCADE,
    FOREIGN KEY (idPropietario) REFERENCES Propietarios(idPropietario) ON DELETE CASCADE,
    FOREIGN KEY (idTipoPractica) REFERENCES TipoPractica(idTipoPractica) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Inserciones y borrados datos

-- Inserción de propietarios
INSERT INTO propietario (dni, nombre, apellido, fechanacimiento,pais, ciudad, direccion ) 
VALUES 
('12345678A', 'Juan', 'Pérez García', '1985-05-10', 'España', 'Madrid', 'Calle Alcalá 15'),
('87654321B', 'Ana', 'López Martínez', '1992-11-20', 'Argentina', 'Córdoba', 'Av. Colón 450'),
('55544433C', 'Laura', 'Gómez Ríos', '1978-03-05', 'México', 'Ciudad de México', 'Paseo de la Reforma 100');

-- Inserción de tipos de consulta
INSERT INTO tipoconsulta (descripcion) VALUES 
('Consulta de Control Anual'),
('Vacunación y Desparasitación'),
('Consulta de Emergencia/Urgencia');


-- Inserción de tipos de práctica 
INSERT INTO tipopractica (descripcion) VALUES 
('Análisis Clínico Completo'),
('Radiografía Abdominal'),
('Cirugía Menor de Tejidos Blandos');

-- Inserción de mascotas
INSERT INTO mascota (idPropietario, nombre, fechanacimiento, especie, sexo, seniasparticulares) 
VALUES 
(1, 'Fido', '2020-01-15', 'Perro', 'Macho', 'Mancha negra en el ojo derecho'),
(2, 'Michi', '2022-07-01', 'Gato', 'Hembra', 'Cola corta, pelaje atigrado'),
(3, 'Coco', '2023-04-12', 'Ave', 'Hembra', 'Plumaje azul y pico amarillo');




-- Inserción de turnos 
INSERT INTO turno (idTipoConsulta, idPropietario, idMascota, fechaturno, hora) 
VALUES 
(1, 1, 1, '2025-10-10', '10:00:00'),
(2, 2, 2, '2025-10-15', '14:30:00'),
(3, 3, 3, '2025-10-16', '11:00:00');


-- Inserción de consultas 
INSERT INTO consulta (idMascota, idPropietario, idTipoPractica, fechaconsulta, hora, 
resultadoestudio, Diagnostico, Pronostico, Tratamiento) VALUES 
(1, 1, 1, '2024-03-25', '11:00:00', 'Glóbulos blancos altos.', 
'Infección bacteriana leve', 'Favorable con antibióticos', 'Antibiótico por 7 días, dieta blanda.'),
(2, 2, 2, '2024-05-01', '16:00:00', 'Fractura limpia en radio.', 
'Fractura en pata delantera', NULL, 'Inmovilización con férula por 4 semanas.'),
(3, 3, 3, '2024-09-01', '10:30:00', 'No solicitado', 'Extracción de quiste sebáceo', 
'Excelente y curado', 'Sutura, antibióticos y analgésicos post-operatorios.');


-- Borrado de propietario
DELETE FROM propietario WHERE idPropietario = 3;

-- Borrado en tipo de consulta 
DELETE FROM tipoconsulta WHERE idTipoConsulta = 3;

-- Borrados en tipo de práctica
DELETE FROM tipopractica WHERE idTipoPractica = 3;
DELETE FROM tipopractica WHERE idTipoPractica = 3;

-- Borrado en mascota 
DELETE FROM mascota WHERE idMascota = 2;

-- Borrado en turno 
DELETE FROM turno WHERE idTurno = 2;

-- Borrado en Consulta 
DELETE FROM consulta WHERE idConsulta = 2;

-- Consultas
-- todos datos propietario
select * from propietario;

-- Consulta por turnos asignados para determinada fecha y hora
-- Por dni
SELECT
    T.fechaturno AS Fecha,
    T.hora AS Hora,
    
    TC.descripcion AS Tipo_Consulta,
    
    P.nombre AS Nombre_Propietario,
    P.apellido AS Apellido_Propietario,
    
    M.nombre AS Nombre_Mascota
FROM
    turno T  
INNER JOIN
    tipoconsulta TC ON T.idTipoConsulta = TC.idTipoConsulta
INNER JOIN
    propietario P ON T.idPropietario = P.idPropietario
INNER JOIN
    mascota M ON T.idMascota = M.idMascota
WHERE
    T.fechaturno = '2025-10-10'  
    AND T.hora = '10:00:00';     
    
 -- Consulta turnos por propietario
 SELECT
    T.fechaturno AS Fecha_Turno,
    T.hora AS Hora_Turno,
    TC.descripcion AS Tipo_Consulta,
    M.apodo AS Nombre_Mascota
FROM
    turno T
INNER JOIN propietario P ON T.idPropietario = P.idPropietario
INNER JOIN tipoconsulta TC ON T.idTipoConsulta = TC.idTipoConsulta
INNER JOIN mascota M ON T.idMascota = M.idMascota
WHERE
    P.nombre = 'Juan'       
    AND P.apellido = 'Pérez García'; 

-- por DNI
SELECT
    T.fechaturno AS Fecha_Turno,
    T.hora AS Hora_Turno,
    TC.descripcion AS Tipo_Consulta,
    M.nombre AS Nombre_Mascota
FROM
    turno T
    
INNER JOIN propietario P ON T.idPropietario = P.idPropietario
INNER JOIN tipoconsulta TC ON T.idTipoConsulta = TC.idTipoConsulta
INNER JOIN mascota M ON T.idMascota = M.idMascota
WHERE
    P.dni = '12345678A';    

-- Consulta datos propietario
-- Por dni
SELECT
    apellido,
    nombre,
    direccion,
    pais
FROM
    propietario
WHERE
    dni = '12345678A'; 
  SELECT
    apellido,
    nombre,
    direccion,
    pais
FROM
    propietario
WHERE
    nombre = 'Juan'
    AND apellido = 'Pérez García'; -- Reemplazar por las variables de Nombre y Apellido  
    
    
-- Consulta de historia clinica
SELECT
    C.fechaconsulta AS Fecha,
    C.hora AS Hora,
    TP.descripcion AS Tipo_Practica,
    C.Diagnostico,
    C.Tratamiento,
    C.resultadoestudio,
    C.Pronostico  -- <<-- CAMPO AGREGADO
FROM
    consulta C
INNER JOIN
    tipopractica TP ON C.idTipoPractica = TP.idTipoPractica
WHERE
    C.idMascota = 1  -- Reemplazar por la variable ID de la Mascota
ORDER BY
    C.fechaconsulta DESC;
    
    
-- Consulta  propietario por id
SELECT
    idPropietario, dni, nombre, apellido, fechanacimiento, pais, ciudad, direccion
FROM
    propietario
WHERE
    idPropietario = 1; 

-- Consulta todos los datos de una mascota específica.
SELECT
    idMascota, idPropietario, nombre, fechanacimiento, especie, sexo, seniasparticulares
FROM
    mascota
WHERE
    idMascota = 2; 
    
 -- Consulta mascotas por propietario
 SELECT
    idMascota, nombre, especie, fechanacimiento
FROM
    mascota
WHERE
    idPropietario = 1; 
    
-- Consulta tipos de practica
  SELECT
    idTipoPractica, descripcion
FROM
    tipopractica
ORDER BY
    descripcion ASC;  