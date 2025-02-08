-- Insertar usuarios
INSERT INTO Usuarios (nombre, apellido, email, contraseña, rol, fecha_registro, estado)
VALUES
    ('Juan', 'Pérez', 'juan.perez@ugym.com', 'password123', 'USUARIO', '2025-01-01', 1),
    ('María', 'López', 'maria.lopez@ugym.com', 'password123', 'EMPLEADO', '2025-01-01', 1),
    ('Carlos', 'González', 'carlos.gonzalez@ugym.com', 'password123', 'ADMINISTRADOR', '2025-01-01', 1),
    ('Ana', 'Sánchez', 'ana.sanchez@ugym.com', 'password123', 'USUARIO', '2025-01-02', 1),
    ('Pedro', 'Ramírez', 'pedro.ramirez@ugym.com', 'password123', 'USUARIO', '2025-01-03', 1),
    ('Laura', 'Martínez', 'laura.martinez@ugym.com', 'password123', 'EMPLEADO', '2025-01-02', 1);

-- Insertar horarios
INSERT INTO Horarios (id_empleado, hora_inicio, duracion_minutos, hora_fin, dia_semana, fecha, plazas_disponibles)
VALUES
    (2, '08:00:00', 60, ADDTIME('08:00:00', SEC_TO_TIME(60 * 60)), 'Lunes', '2025-01-12', 10),
    (2, '10:00:00', 60, ADDTIME('10:00:00', SEC_TO_TIME(60 * 60)), 'Martes', '2025-01-13', 15),
    (3, '15:00:00', 60, ADDTIME('15:00:00', SEC_TO_TIME(60 * 60)), 'Miércoles', '2025-01-14', 12),
    (2, '11:00:00', 60, ADDTIME('11:00:00', SEC_TO_TIME(60 * 60)), 'Jueves', '2025-01-15', 10),
    (3, '14:00:00', 60, ADDTIME('14:00:00', SEC_TO_TIME(60 * 60)), 'Viernes', '2025-01-16', 20);


-- Insertar clases
INSERT INTO Clases (nombre_clase, descripcion, categoria, duracion_minutos, capacidad_maxima, tipo_clase, id_horario)
VALUES
    ('Yoga', 'Clase de yoga para principiantes', 'Bienestar', 60, 20, 1, 1),
    ('Pilates', 'Clase de pilates para fortalecimiento', 'Bienestar', 60, 20, 1, 2),
    ('Zumba', 'Clase de baile y fitness', 'Cardio', 60, 25, 2, 3),
    ('Boxeo', 'Clase de boxeo para todos los niveles', 'Fuerza', 60, 15, 3, 4),
    ('Spinning', 'Clase de ciclismo indoor', 'Cardio', 45, 30, 2, 5);

-- Insertar reservas
INSERT INTO Reservas (id_usuario, id_horario, fecha_reserva, estado)
VALUES
    (1, 1, '2025-01-12', 1),
    (2, 2, '2025-01-13', 1),
    (3, 3, '2025-01-14', 1),
    (4, 4, '2025-01-15', 1),
    (5, 5, '2025-01-16', 0),
    (6, 1, '2025-01-12', 1);

INSERT INTO TiposBonos (id, nombre, duracion_dias)
VALUES
(1, 'General', 30),  -- Bono General, válido por 30 días
(2, 'Privado', NULL); -- Bono Privado, sin duración definida

-- Insertar bonos
-- Bono General 1
INSERT INTO bonos (id, id_usuario, id_tipo_bono, saldo_clases, fecha_inicio, fecha_fin)
VALUES
(1, 1, 1, NULL, '2025-01-01', '2025-01-31');

-- Bono Privado 1
INSERT INTO bonos (id, id_usuario, id_tipo_bono, saldo_clases, fecha_inicio, fecha_fin)
VALUES
(2, 1, 2, 5, NULL, NULL);

-- Bono General 2
INSERT INTO bonos (id, id_usuario, id_tipo_bono, saldo_clases, fecha_inicio, fecha_fin)
VALUES
(3, 2, 1, NULL, '2025-01-01', '2025-01-31');



-- Insertar pagos
INSERT INTO Pagos (id_usuario, cantidad, tipo_bono, fecha_pago, estado)
VALUES
    (1, 100.00, 1, '2025-01-01', 1),
    (2, 200.00, 2, '2025-01-05', 1),
    (3, 150.00, 1, '2025-01-03', 1),
    (4, 300.00, 3, '2025-01-10', 1),
    (5, 250.00, 1, '2025-01-07', 0);

-- Insertar mensajes
INSERT INTO Mensajes (id_usuario, titulo, contenido, fecha_envio)
VALUES
    (1, 'Bienvenido', '¡Bienvenido al gimnasio UGym! Estamos felices de tenerte con nosotros.', '2025-01-01'),
    (2, 'Información de clases', 'Las clases de yoga se han reprogramado para el próximo lunes.', '2025-01-02'),
    (3, 'Oferta especial', 'Recibe un 20% de descuento en todos los bonos este mes.', '2025-01-03'),
    (4, 'Clase de Boxeo', 'La clase de boxeo del miércoles ha sido cancelada.', '2025-01-04'),
    (5, 'Nuevo horario', 'Se ha agregado un nuevo horario de spinning los viernes a las 18:00.', '2025-01-05');

-- Insertar chequeos de empleados
INSERT INTO ChequeoEmpleados (id_empleado, fecha_chequeo, hora_chequeo)
VALUES
    (2, '2025-01-01', '08:00:00'),
    (2, '2025-01-02', '10:00:00'),
    (3, '2025-01-03', '14:00:00'),
    (2, '2025-01-04', '11:00:00'),
    (3, '2025-01-05', '15:00:00');
