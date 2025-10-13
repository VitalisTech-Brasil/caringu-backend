INSERT INTO vitalis.aulas (planos_contratados_id, data_horario_inicio, data_horario_fim, status) VALUES
-- Semana 1
(2, '2025-10-01 08:00:00', '2025-10-01 09:00:00', 'REALIZADO'),
(2, '2025-10-03 08:00:00', '2025-10-03 09:00:00', 'REALIZADO'),
(2, '2025-10-05 08:00:00', '2025-10-05 09:00:00', 'REALIZADO'),
(2, '2025-10-07 08:00:00', '2025-10-07 09:00:00', 'REALIZADO'),
-- Semana 2
(2, '2025-10-08 08:00:00', '2025-10-08 09:00:00', 'REALIZADO'),
(2, '2025-10-10 08:00:00', '2025-10-10 09:00:00', 'REALIZADO'),
(2, '2025-10-12 08:00:00', '2025-10-12 09:00:00', 'REALIZADO'),
(2, '2025-10-14 08:00:00', '2025-10-14 09:00:00', 'REALIZADO'),
-- Semana 3
(2, '2025-10-15 08:00:00', '2025-10-15 09:00:00', 'REALIZADO'),
(2, '2025-10-17 08:00:00', '2025-10-17 09:00:00', 'REALIZADO'),

(2, '2025-10-19 08:00:00', '2025-10-19 09:00:00', 'REALIZADO'),
(2, '2025-10-21 08:00:00', '2025-10-21 09:00:00', 'REALIZADO'),
-- Semana 4
(2, '2025-10-22 08:00:00', '2025-10-22 09:00:00', 'REALIZADO'),
(2, '2025-10-24 08:00:00', '2025-10-24 09:00:00', 'REALIZADO'),
(2, '2025-10-26 08:00:00', '2025-10-26 09:00:00', 'REALIZADO'),
(2, '2025-10-28 08:00:00', '2025-10-28 09:00:00', 'REALIZADO');

-- PASSO 1: Inserir todas as aulas do aluno para o mês de outubro

/*
INSERT INTO vitalis.aulas (data_horario_inicio, status, planos_contratados_id)
VALUES
    -- Aulas até 17/10
    ('2024-10-08 14:00:00', 'AGENDADO', 2),
    ('2024-10-15 14:00:00', 'AGENDADO', 2),
    ('2024-10-17 14:00:00', 'AGENDADO', 2),
    ('2024-10-22 14:00:00', 'AGENDADO', 2),
    ('2024-10-29 14:00:00', 'AGENDADO', 2);
    */

-- ========================================
-- AULAS_TREINOS_EXERCICIOS DE OUTUBRO (Aulas IDs 9 a 24)
-- ========================================

-- SEMANA 1: Carga Base
INSERT INTO vitalis.aulas_treinos_exercicios
(aulas_id, treinos_exercicios_id, ordem, carga, repeticoes, series, descanso, observacoes_personalizadas) VALUES

-- AULA 9 (Peito) - Supino Inclinado 60.00kg
(9, 1, 1, 60.00, 10, 4, 90, 'Out S1: Supino Inclinado'), (9, 2, 2, 12.00, 12, 4, 60, NULL), (9, 3, 3, 1.00, 20, 3, 45, NULL), (9, 4, 4, 35.00, 10, 3, 60, NULL),
-- AULA 10 (Pernas) - Leg Press 120.00kg
(10, 14, 1, 120.00, 10, 4, 90, 'Out S1: Leg Press'), (10, 15, 2, 24.00, 10, 3, 60, NULL), (10, 13, 3, 20.00, 12, 3, 60, NULL), (10, 40, 4, 25.00, 12, 4, 60, NULL),
-- AULA 11 (Costas/Ombro) - Barra (12 Reps)
(11, 29, 1, 0.00, 12, 3, 60, 'Out S1: Barra'), (11, 19, 2, 40.00, 12, 3, 60, NULL), (11, 20, 3, 45.00, 10, 4, 90, NULL), (11, 21, 4, 10.00, 12, 3, 60, NULL),
-- AULA 12 (Core/Cardio) - Abdominal (350 Reps)
(12, 46, 1, 0.00, 350, 1, 0, 'Out S1: Abdominal'), (12, 50, 2, 0.00, 15, 4, 45, NULL), (12, 26, 3, 0.00, 20, 3, 30, NULL), (12, 25, 4, 0.00, 12, 3, 45, NULL);

-- SEMANA 2: Progressão (+5%)
INSERT INTO vitalis.aulas_treinos_exercicios
(aulas_id, treinos_exercicios_id, ordem, carga, repeticoes, series, descanso, observacoes_personalizadas) VALUES
-- AULA 13 (Peito) - Supino Inclinado 63.00kg
(13, 1, 1, 63.00, 10, 4, 90, 'Out S2: Supino +5%'), (13, 2, 2, 12.00, 12, 4, 60, NULL), (13, 3, 3, 1.00, 20, 3, 45, NULL), (13, 4, 4, 35.00, 10, 3, 60, NULL),
-- AULA 14 (Pernas) - Leg Press 126.00kg
(14, 14, 1, 126.00, 10, 4, 90, 'Out S2: Leg Press +5%'), (14, 15, 2, 24.00, 10, 3, 60, NULL), (14, 13, 3, 20.00, 12, 3, 60, NULL), (14, 40, 4, 25.00, 12, 4, 60, NULL),
-- AULA 15 (Costas/Ombro) - Barra (13 Reps)
(15, 29, 1, 0.00, 13, 3, 60, 'Out S2: Barra +Rep'), (15, 19, 2, 40.00, 12, 3, 60, NULL), (15, 20, 3, 45.00, 10, 4, 90, NULL), (15, 21, 4, 10.00, 12, 3, 60, NULL),
-- AULA 16 (Core/Cardio) - Abdominal (370 Reps)
(16, 46, 1, 0.00, 370, 1, 0, 'Out S2: Abdominal +Rep'), (16, 50, 2, 0.00, 15, 4, 45, NULL), (16, 26, 3, 0.00, 20, 3, 30, NULL), (16, 25, 4, 0.00, 12, 3, 45, NULL);

-- SEMANA 3: Progressão (+10%)
INSERT INTO vitalis.aulas_treinos_exercicios
(aulas_id, treinos_exercicios_id, ordem, carga, repeticoes, series, descanso, observacoes_personalizadas) VALUES
-- AULA 17 (Peito) - Supino Inclinado 66.00kg
(17, 1, 1, 66.00, 10, 4, 90, 'Out S3: Supino +10%'), (17, 2, 2, 12.00, 12, 4, 60, NULL), (17, 3, 3, 1.00, 20, 3, 45, NULL), (17, 4, 4, 35.00, 10, 3, 60, NULL),
-- AULA 18 (Pernas) - Leg Press 132.00kg
(18, 14, 1, 132.00, 10, 4, 90, 'Out S3: Leg Press +10%'), (18, 15, 2, 24.00, 10, 3, 60, NULL), (18, 13, 3, 20.00, 12, 3, 60, NULL), (18, 40, 4, 25.00, 12, 4, 60, NULL),
-- AULA 19 (Costas/Ombro) - Barra (14 Reps)
(19, 29, 1, 0.00, 14, 3, 60, 'Out S3: Barra +Rep'), (19, 19, 2, 40.00, 12, 3, 60, NULL), (19, 20, 3, 45.00, 10, 4, 90, NULL), (19, 21, 4, 10.00, 12, 3, 60, NULL),
-- AULA 20 (Core/Cardio) - Abdominal (390 Reps)
(20, 46, 1, 0.00, 390, 1, 0, 'Out S3: Abdominal +Rep'), (20, 50, 2, 0.00, 15, 4, 45, NULL), (20, 26, 3, 0.00, 20, 3, 30, NULL), (20, 25, 4, 0.00, 12, 3, 45, NULL);

-- SEMANA 4: Progressão (+15%)
INSERT INTO vitalis.aulas_treinos_exercicios
(aulas_id, treinos_exercicios_id, ordem, carga, repeticoes, series, descanso, observacoes_personalizadas) VALUES
-- AULA 21 (Peito) - Supino Inclinado 69.00kg
(21, 1, 1, 69.00, 10, 4, 90, 'Out S4: Supino +15%'), (21, 2, 2, 12.00, 12, 4, 60, NULL), (21, 3, 3, 1.00, 20, 3, 45, NULL), (21, 4, 4, 35.00, 10, 3, 60, NULL),
-- AULA 22 (Pernas) - Leg Press 138.00kg
(22, 14, 1, 138.00, 10, 4, 90, 'Out S4: Leg Press +15%'), (22, 15, 2, 24.00, 10, 3, 60, NULL), (22, 13, 3, 20.00, 12, 3, 60, NULL), (22, 40, 4, 25.00, 12, 4, 60, NULL),
-- AULA 23 (Costas/Ombro) - Barra (15 Reps)
(23, 29, 1, 0.00, 15, 3, 60, 'Out S4: Barra +Rep'), (23, 19, 2, 40.00, 12, 3, 60, NULL), (23, 20, 3, 45.00, 10, 4, 90, NULL), (23, 21, 4, 10.00, 12, 3, 60, NULL),
-- AULA 24 (Core/Cardio) - Abdominal (410 Reps)
(24, 46, 1, 0.00, 410, 1, 0, 'Out S4: Abdominal +Rep'), (24, 50, 2, 0.00, 15, 4, 45, NULL), (24, 26, 3, 0.00, 20, 3, 30, NULL), (24, 25, 4, 0.00, 12, 3, 45, NULL);






-- ========================================
-- EXECUÇÕES DE EXERCÍCIOS DE OUTUBRO
-- ========================================

-- SEMANA 1 (Aulas 9-12)
INSERT INTO vitalis.execucoes_exercicios
(aulas_treinos_exercicios_id, carga_executada, repeticoes_executadas, series_executadas, descanso_executado, finalizado) VALUES

-- AULA 9 (Peito) - Supino 60kg executado
(25, 60.00, 10, 4, 90, TRUE),
(26, 12.00, 12, 4, 60, TRUE),
(27, 1.00, 20, 3, 45, TRUE),
(28, 35.00, 10, 3, 60, TRUE),

-- AULA 10 (Pernas) - Leg Press 120kg executado
(29, 120.00, 10, 4, 90, TRUE),
(30, 24.00, 10, 3, 60, TRUE),
(31, 20.00, 12, 3, 60, TRUE),
(32, 25.00, 12, 4, 60, TRUE),

-- AULA 11 (Costas/Ombro)
(33, 0.00, 12, 3, 60, TRUE),
(34, 40.00, 12, 3, 60, TRUE),
(35, 45.00, 10, 4, 90, TRUE),
(36, 10.00, 12, 3, 60, TRUE),

-- AULA 12 (Core/Cardio)
(37, 0.00, 350, 1, 0, TRUE),
(38, 0.00, 15, 4, 45, TRUE),
(39, 0.00, 20, 3, 30, TRUE),
(40, 0.00, 12, 3, 45, TRUE);

-- SEMANA 2 (Aulas 13-16)
INSERT INTO vitalis.execucoes_exercicios
(aulas_treinos_exercicios_id, carga_executada, repeticoes_executadas, series_executadas, descanso_executado, finalizado) VALUES

-- AULA 13 (Peito) - Supino 63kg executado
(41, 63.00, 10, 4, 90, TRUE),
(42, 12.00, 12, 4, 60, TRUE),
(43, 1.00, 20, 3, 45, TRUE),
(44, 35.00, 10, 3, 60, TRUE),

-- AULA 14 (Pernas) - Leg Press 126kg executado
(45, 126.00, 10, 4, 90, TRUE),
(46, 24.00, 10, 3, 60, TRUE),
(47, 20.00, 12, 3, 60, TRUE),
(48, 25.00, 12, 4, 60, TRUE),

-- AULA 15 (Costas/Ombro)
(49, 0.00, 13, 3, 60, TRUE),
(50, 40.00, 12, 3, 60, TRUE),
(51, 45.00, 10, 4, 90, TRUE),
(52, 10.00, 12, 3, 60, TRUE),

-- AULA 16 (Core/Cardio)
(53, 0.00, 370, 1, 0, TRUE),
(54, 0.00, 15, 4, 45, TRUE),
(55, 0.00, 20, 3, 30, TRUE),
(56, 0.00, 12, 3, 45, TRUE);

-- SEMANA 3 (Aulas 17-20)
INSERT INTO vitalis.execucoes_exercicios
(aulas_treinos_exercicios_id, carga_executada, repeticoes_executadas, series_executadas, descanso_executado, finalizado) VALUES

-- AULA 17 (Peito) - Supino 66kg executado
(57, 66.00, 10, 4, 90, TRUE),
(58, 12.00, 12, 4, 60, TRUE),
(59, 1.00, 20, 3, 45, TRUE),
(60, 35.00, 10, 3, 60, TRUE),

-- AULA 18 (Pernas) - Leg Press 132kg executado
(61, 132.00, 10, 4, 90, TRUE),
(62, 24.00, 10, 3, 60, TRUE),
(63, 20.00, 12, 3, 60, TRUE),
(64, 25.00, 12, 4, 60, TRUE),

-- AULA 19 (Costas/Ombro)
(65, 0.00, 14, 3, 60, TRUE),
(66, 40.00, 12, 3, 60, TRUE),
(67, 45.00, 10, 4, 90, TRUE),
(68, 10.00, 12, 3, 60, TRUE),

-- AULA 20 (Core/Cardio)
(69, 0.00, 390, 1, 0, TRUE),
(70, 0.00, 15, 4, 45, TRUE),
(71, 0.00, 20, 3, 30, TRUE),
(72, 0.00, 12, 3, 45, TRUE);

-- SEMANA 4 (Aulas 21-24)
INSERT INTO vitalis.execucoes_exercicios
(aulas_treinos_exercicios_id, carga_executada, repeticoes_executadas, series_executadas, descanso_executado, finalizado) VALUES

-- AULA 21 (Peito) - Supino 69kg executado
(73, 69.00, 10, 4, 90, TRUE),
(74, 12.00, 12, 4, 60, TRUE),
(75, 1.00, 20, 3, 45, TRUE),
(76, 35.00, 10, 3, 60, TRUE),

-- AULA 22 (Pernas) - Leg Press 138kg executado
(77, 138.00, 10, 4, 90, TRUE),
(78, 24.00, 10, 3, 60, TRUE),
(79, 20.00, 12, 3, 60, TRUE),
(80, 25.00, 12, 4, 60, TRUE),

-- AULA 23 (Costas/Ombro)
(81, 0.00, 15, 3, 60, TRUE),
(82, 40.00, 12, 3, 60, TRUE),
(83, 45.00, 10, 4, 90, TRUE),
(84, 10.00, 12, 3, 60, TRUE),

-- AULA 24 (Core/Cardio)
(85, 0.00, 410, 1, 0, TRUE),
(86, 0.00, 15, 4, 45, TRUE),
(87, 0.00, 20, 3, 30, TRUE),
(88, 0.00, 12, 3, 45, TRUE);