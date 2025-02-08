CREATE DATABASE UGym;
USE UGym;

-- Tabla Usuarios
CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    contraseña VARCHAR(255) NOT NULL,
    imagen VARCHAR(255) DEFAULT NULL,
    rol ENUM('USUARIO', 'EMPLEADO', 'ADMINISTRADOR') NOT NULL,
    fecha_registro DATE NOT NULL,
    estado INT NOT NULL
);

-- Tabla Horarios
CREATE TABLE Horarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT,
    hora_inicio TIME NOT NULL,
    duracion_minutos INT NOT NULL, -- Columna agregada para evitar errores
    hora_fin TIME NOT NULL,
    dia_semana VARCHAR(50) NOT NULL,
    fecha DATE,
    plazas_disponibles INT NOT NULL,
    FOREIGN KEY (id_empleado) REFERENCES Usuarios(id) ON DELETE CASCADE
);

-- Tabla Clases
CREATE TABLE Clases (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_clase VARCHAR(100) NOT NULL,
    descripcion TEXT,
    categoria VARCHAR(50) NOT NULL,
    duracion_minutos INT DEFAULT 60 NOT NULL,
    capacidad_maxima INT NOT NULL,
    tipo_clase INT NOT NULL,
    imagen VARCHAR(255) DEFAULT NULL,
    id_horario INT,
    FOREIGN KEY (id_horario) REFERENCES Horarios(id) ON DELETE CASCADE
);

-- Tabla Reservas
CREATE TABLE Reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_horario INT,
    fecha_reserva DATE NOT NULL,
    estado INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_horario) REFERENCES Horarios(id) ON DELETE CASCADE
);

-- Tabla TipoBonos
CREATE TABLE TiposBonos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion VARCHAR(255),
    duracion_dias INT DEFAULT NULL
);

-- Tabla Bonos
CREATE TABLE Bonos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_tipo_bono INT NOT NULL,
    saldo_clases INT DEFAULT 0,
    fecha_inicio DATE,
    fecha_fin DATE,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (id_tipo_bono) REFERENCES TiposBonos(id) ON DELETE CASCADE
);


-- Tabla Pagos
CREATE TABLE Pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    cantidad DECIMAL(10, 2) NOT NULL,
    tipo_bono INT NOT NULL,
    fecha_pago DATE NOT NULL,
    estado INT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id) ON DELETE CASCADE
);

-- Tabla Mensajes
CREATE TABLE Mensajes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    titulo VARCHAR(100) NOT NULL,
    contenido TEXT NOT NULL,
    fecha_envio DATE NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id) ON DELETE CASCADE
);

-- Tabla ChequeoEmpleados
CREATE TABLE ChequeoEmpleados (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_empleado INT,
    fecha_chequeo DATE NOT NULL,
    hora_chequeo TIME NOT NULL,
    FOREIGN KEY (id_empleado) REFERENCES Usuarios(id) ON DELETE CASCADE
);