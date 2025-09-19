CREATE DATABASE IF NOT EXISTS `vitalis` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `vitalis`;

-- -----------------------------------------------------
-- Table `vitalis`.`pessoas`
-- -----------------------------------------------------

-- Pessoas para personal_trainers (IDs: 1 a 5)
INSERT INTO vitalis.pessoas (nome, email, senha, celular, url_foto_perfil, data_nascimento, genero ) VALUES
                                                                                                         ('Lucas Andrade', 'lucas.andrade@example.com', 'senha1234', '11987654321', 'https://img.com/lucas.jpg', '1990-03-15', 'HOMEM_CISGENERO'),
                                                                                                         ('Mariana Silva', 'mariana.silva@example.com', 'senha5678', '21988887777', 'https://img.com/mariana.jpg', '1988-07-22', 'MULHER_CISGENERO'),
                                                                                                         ('João Pereira', 'joao.pereira@example.com', 'joao3210', '31999990000', NULL, '1995-11-05', 'HOMEM_TRANSGENERO'),
                                                                                                         ('Ana Costa', 'ana.costa@example.com', 'anaana123', '21912345678', 'https://img.com/ana.jpg', '1992-05-18', 'MULHER_TRANSGENERO'),

-- INSERT COM JWT (ID: 5)
                                                                                                         ('Ricardo Gomes da Silva', 'ricardo@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '72987654323', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/imagem-ricardo.png', '1999-12-25', 'HOMEM_CISGENERO'),

-- Pessoas para alunos (IDs: 6 a 10)
                                                                                                         ('Carla Mendes', 'carla.mendes@example.com', 'mendes456', '11944556677', 'https://img.com/carla.jpg', '1985-02-10', 'MULHER_TRANSGENERO'),
                                                                                                         ('Alex Rocha', 'alex.rocha@example.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '11999887766', NULL, '2000-12-01', 'NAO_BINARIO'),
                                                                                                         ('Bianca Lima', 'bianca.lima@example.com', 'b1ancaL', NULL, 'https://img.com/bianca.jpg', '1997-09-12', 'MULHER_TRANSGENERO'),
                                                                                                         ('Diego Santos', 'diego.santos@example.com', 'diegosenha', '21988776655', 'https://img.com/diego.jpg', NULL, 'HOMEM_TRANSGENERO'),
                                                                                                         ('Luna Martins', 'luna.martins@example.com', 'martins123', '31977665544', NULL, '1993-04-25', 'HOMEM_CISGENERO');




-- -----------------------------------------------------
-- Table `vitalis`.`personal_trainers`
-- -----------------------------------------------------


INSERT INTO personal_trainers (id, cref, experiencia) VALUES
                                                          (1, 'CREF123456-SP', 15),
                                                          (2, 'CREF654321-SP', 5),
                                                          (3, 'CREF987654-RJ', 10),
                                                          (4, 'CREF112233-SP', 25),
                                                          (5, '123456-G/SP', 16);
-- -----------------------------------------------------
-- Table `vitalis`.`alunos`
-- -----------------------------------------------------


INSERT INTO vitalis.alunos (id, peso, altura, nivel_atividade, nivel_experiencia) VALUES
                                                                                      (6, 75.50, 1.75, 'MODERADAMENTE_ATIVO', 'INTERMEDIARIO'),
                                                                                      (7, 65.20, 1.68, 'LEVEMENTE_ATIVO', 'INICIANTE'),
                                                                                      (8, 82.00, 1.80, 'MUITO_ATIVO', 'AVANCADO'),
                                                                                      (9, 90.30, 1.85, 'SEDENTARIO', 'INICIANTE'),
                                                                                      (10, 70.00, 1.65, 'EXTREMAMENTE_ATIVO', 'INTERMEDIARIO');

-- -----------------------------------------------------
-- Table `vitalis`.`especialidades`
-- -----------------------------------------------------


INSERT INTO especialidades (nome)
VALUES
    ("Musculação"),
    ("Treinamento Funcional"),
    ("HIIT (Treino Intervalado de Alta Intensidade)"),
    ("Treinamento de Core"),
    ("Treinamento para Emagrecimento"),
    ("Corrida e Caminhada"),
    ("Ciclismo Indoor (Spinning)"),
    ("Treinamento Esportivo"),
    ("Treinamento para Atletas de Alto Rendimento"),
    ("Pilates"),
    ("Alongamento e Mobilidade"),
    ("Reabilitação e Prevenção de Lesões"),
    ("Hipertrofia Muscular"),
    ("Modelagem Corporal"),
    ("Treinamento para Pessoas com Deficiência"),
    ("Treinamento para Idosos"),
    ("Treinamento Pré e Pós-Parto"),
    ("Treinamento para Saúde Metabólica");

-- -----------------------------------------------------
-- Table `vitalis`.`personal_trainer_especialidade`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS personal_trainers_especialidades (
                                                                personal_trainers_id INT NOT NULL,
                                                                especialidades_id INT NOT NULL,
                                                                PRIMARY KEY (personal_trainers_id, especialidades_id),
    FOREIGN KEY (personal_trainers_id) REFERENCES personal_trainers(id) ON DELETE CASCADE,
    FOREIGN KEY (especialidades_id) REFERENCES especialidades(id) ON DELETE CASCADE
    );

-- Personal 1: Musculação, Treinamento Funcional
INSERT INTO personal_trainers_especialidades VALUES (1, 1), (1, 2);

-- Personal 2: Musculação, Emagrecimento
INSERT INTO personal_trainers_especialidades VALUES (2, 1), (2, 5);

-- Personal 3: Condicionamento Físico ≈ Treinamento de Core + Saúde Metabólica?
INSERT INTO personal_trainers_especialidades VALUES (3, 4), (3, 16);

-- Personal 4: Condicionamento Físico, Treinamento para Idosos
INSERT INTO personal_trainers_especialidades VALUES (4, 4), (4, 16);

-- Personal 5: Reabilitação e Alongamento ≈ Reabilitação + Mobilidade
INSERT INTO personal_trainers_especialidades VALUES (5, 11), (5, 12);

-- -----------------------------------------------------
-- Table `vitalis`.`notificacoes`
-- -----------------------------------------------------


INSERT INTO notificacoes (pessoas_id, tipo, titulo, visualizada, data_criacao)
VALUES
-- (1, 'FEEDBACK_TREINO', 'Feedback de Treino', FALSE, CURRENT_TIMESTAMP),
(2, 'PAGAMENTO_REALIZADO', 'Pagamento Confirmado', FALSE, CURRENT_TIMESTAMP),
(3, 'PLANO_PROXIMO_VENCIMENTO', 'Plano Próximo Vencimento', FALSE, CURRENT_TIMESTAMP),
(5, 'PLANO_PROXIMO_VENCIMENTO', 'Plano Próximo Vencimento', FALSE, CURRENT_TIMESTAMP),
-- (4, 'NOVA_FOTO_PROGRESSO', 'Nova Foto de Progresso', TRUE, CURRENT_TIMESTAMP),
(5, 'TREINO_PROXIMO_VENCIMENTO', 'Treino Próximo Vencimento', FALSE, CURRENT_TIMESTAMP);

-- -----------------------------------------------------
-- Table `vitalis`.`preferencias_notificacao`
-- -----------------------------------------------------


INSERT INTO preferencias_notificacao (pessoas_id, tipo, ativada)
VALUES
-- (1, 'FEEDBACK_TREINO', TRUE),
(1, 'PAGAMENTO_REALIZADO', TRUE),
(1, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
-- (1, 'NOVA_FOTO_PROGRESSO', TRUE),
(1, 'TREINO_PROXIMO_VENCIMENTO', TRUE),
(6, 'TREINO_PROXIMO_VENCIMENTO', TRUE),
(7, 'TREINO_PROXIMO_VENCIMENTO', TRUE),
(8, 'TREINO_PROXIMO_VENCIMENTO', TRUE),
(9, 'TREINO_PROXIMO_VENCIMENTO', TRUE),
(10, 'TREINO_PROXIMO_VENCIMENTO', TRUE),

-- (2, 'FEEDBACK_TREINO', FALSE),
(2, 'PAGAMENTO_REALIZADO', TRUE),
(2, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
-- (2, 'NOVA_FOTO_PROGRESSO', FALSE),
(2, 'TREINO_PROXIMO_VENCIMENTO', TRUE),

-- (3, 'FEEDBACK_TREINO', FALSE),
(3, 'PAGAMENTO_REALIZADO', TRUE),
(3, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
-- (3, 'NOVA_FOTO_PROGRESSO', TRUE),
(3, 'TREINO_PROXIMO_VENCIMENTO', TRUE),

-- (4, 'FEEDBACK_TREINO', FALSE),
(4, 'PAGAMENTO_REALIZADO', TRUE),
(4, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
-- (4, 'NOVA_FOTO_PROGRESSO', FALSE),
(4, 'TREINO_PROXIMO_VENCIMENTO', TRUE),

-- (5, 'FEEDBACK_TREINO', TRUE),
(5, 'PAGAMENTO_REALIZADO', TRUE),
(5, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
-- (5, 'NOVA_FOTO_PROGRESSO', TRUE),
(5, 'TREINO_PROXIMO_VENCIMENTO', TRUE),

(6, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
(7, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
(8, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
(9, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
(10, 'PLANO_PROXIMO_VENCIMENTO', TRUE);

-- -----------------------------------------------------
-- Table `vitalis`.`exercicios`


INSERT INTO vitalis.exercicios (nome, grupo_muscular, url_video, observacoes, favorito, origem) VALUES
-- Peitoral
('Supino Inclinado', 'PEITORAL', 'https://example.com/supino-inclinado', NULL, FALSE, 'BIBLIOTECA'),
('Crucifixo com Halteres', 'PEITORAL', 'https://example.com/crucifixo', 'Executar com controle', TRUE, 'BIBLIOTECA'),
('Flexões de Braço', 'PEITORAL', 'https://example.com/flexoes', 'Evitar descer demais', FALSE, 'BIBLIOTECA'),
('Peck Deck', 'PEITORAL', 'https://example.com/peck-deck', NULL, FALSE, 'PERSONAL'),

-- Costas
('Afundo', 'COSTAS', 'https://example.com/afundo', 'Manter joelho alinhado', FALSE, 'PERSONAL'),
('Stiff com Halteres', 'COSTAS', 'https://example.com/stiff', 'Manter costas retas', TRUE, 'PERSONAL'),
('Leg Press', 'COSTAS', 'https://example.com/leg-press', NULL, FALSE, 'PERSONAL'),

-- Pernas
('Afundo', 'PERNAS', 'https://example.com/afundo', 'Manter joelho alinhado', FALSE, 'BIBLIOTECA'),
('Stiff com Halteres', 'PERNAS', 'https://example.com/stiff', 'Manter costas retas', TRUE, 'BIBLIOTECA'),
('Leg Press', 'PERNAS', 'https://example.com/leg-press', NULL, FALSE, 'BIBLIOTECA'),

-- Ombro
('Elevação Lateral', 'OMBRO', 'https://example.com/elevacao-lateral', NULL, TRUE, 'PERSONAL'),
('Desenvolvimento Arnold', 'OMBRO', 'https://example.com/arnold', 'Evitar estender totalmente', FALSE, 'PERSONAL'),
('Elevação Frontal', 'OMBRO', 'https://example.com/elevacao-frontal', NULL, FALSE, 'BIBLIOTECA'),

-- Braco
('Elevação Lateral', 'BRACO', 'https://example.com/elevacao-lateral', NULL, TRUE, 'PERSONAL'),
('Desenvolvimento Arnold', 'BRACO', 'https://example.com/arnold', 'Evitar estender totalmente', FALSE, 'PERSONAL'),
('Elevação Frontal', 'BRACO', 'https://example.com/elevacao-frontal', NULL, FALSE, 'PERSONAL'),

-- Core
('Abdominal Bicicleta', 'CORE', 'https://example.com/abdominal-bicicleta', NULL, FALSE, 'BIBLIOTECA'),
('Elevação de Pernas', 'CORE', 'https://example.com/elevacao-pernas', NULL, FALSE, 'BIBLIOTECA'),
('Abdominal Infra', 'CORE', 'https://example.com/infra', NULL, TRUE, 'BIBLIOTECA'),
('Abdominal Bicicleta', 'CORE', 'https://example.com/abdominal-bicicleta', NULL, FALSE, 'BIBLIOTECA'),
('Elevação de Pernas', 'CORE', 'https://example.com/elevacao-pernas', NULL, FALSE, 'BIBLIOTECA'),
('Abdominal Infra', 'CORE', 'https://example.com/infra', NULL, TRUE, 'BIBLIOTECA'),

-- Cardio
('Polichinelo', 'CARDIO', 'https://example.com/polichinelo', NULL, FALSE, 'BIBLIOTECA'),
('Burpee', 'CARDIO', 'https://example.com/burpee', 'Explosão ao subir', TRUE, 'BIBLIOTECA'),
('Corrida Estacionária', 'CARDIO', 'https://example.com/corrida', NULL, FALSE, 'BIBLIOTECA'),
('Mountain Climbers', 'CARDIO', 'https://example.com/mountain', NULL, FALSE, 'BIBLIOTECA'),
('Corrida Estacionária', 'CARDIO', 'https://example.com/corrida', NULL, FALSE, 'BIBLIOTECA'),
('Mountain Climbers', 'CARDIO', 'https://example.com/mountain', NULL, FALSE, 'BIBLIOTECA');


-- -----------------------------------------------------
-- Table `vitalis`.`treinos`
-- -----------------------------------------------------

INSERT INTO vitalis.treinos (nome, descricao, favorito, personal_id) VALUES
                                                                         ('Treino de Peito Avançado', 'Hipertrofia do peitoral com barra', FALSE, 1),
                                                                         ('Treino de Pernas Intermediário', 'Força e resistência nas pernas', FALSE, 2),
                                                                         ('Treino de Ombro Iniciante', 'Mobilidade e força básica', TRUE, 3),
                                                                         ('Cardio Funcional', 'Gasto calórico intenso com circuitos', TRUE, 4),
                                                                         ('Core e Estabilização', 'Foco em abdômen e lombar', TRUE, 5),
                                                                         ('Peito Funcional', 'Treino de resistência com foco funcional', TRUE, 1),
                                                                         ('Posterior Reforçado', 'Ênfase em posterior e glúteo', TRUE, 2),
                                                                         ('Ombros Ativos', 'Melhora da estabilidade do ombro', TRUE, 3),
                                                                         ('Cardio HIIT', 'Treino em alta intensidade intervalada', FALSE, 4),
                                                                         ('Core Explosivo', 'Abdômen com movimentos rápidos', FALSE, 5);

-- -----------------------------------------------------
-- Table `vitalis`.`treinos_exercicios`
-- -----------------------------------------------------g


-- Treino 1 (Peitoral)
INSERT INTO vitalis.treinos_exercicios (treino_id, exercicio_id, carga, repeticoes, series, descanso, origem, grau_dificuldade) VALUES
                                                                                                                                    (1, 6, 50, 10, 4, 90, 'PERSONAL', 'AVANCADO'),
                                                                                                                                    (1, 7, 12, 12, 4, 60, 'BIBLIOTECA', 'AVANCADO'),
                                                                                                                                    (1, 8, 0, 20, 3, 45, 'PERSONAL', 'AVANCADO'),
                                                                                                                                    (1, 9, 35, 10, 3, 60, 'BIBLIOTECA', 'AVANCADO'),

-- Treino 2 (Pernas)
                                                                                                                                    (2, 10, 30, 10, 3, 60, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (2, 11, 25, 10, 3, 45, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (2, 12, 80, 12, 4, 90, 'PERSONAL', 'INTERMEDIARIO'),

-- Treino 3 (Ombro)
                                                                                                                                    (3, 13, 6, 15, 3, 30, 'PERSONAL', 'INICIANTE'),
                                                                                                                                    (3, 14, 8, 12, 3, 45, 'BIBLIOTECA', 'INICIANTE'),
                                                                                                                                    (3, 15, 6, 12, 3, 45, 'BIBLIOTECA', 'INICIANTE'),

-- Treino 4 (Cardio)
                                                                                                                                    (4, 16, 0, 60, 3, 20, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (4, 17, 0, 15, 3, 30, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (4, 18, 0, 60, 3, 20, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (4, 19, 0, 40, 3, 30, 'PERSONAL', 'INTERMEDIARIO'),

-- Treino 5 (Core)
                                                                                                                                    (5, 20, 0, 20, 3, 20, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (5, 21, 0, 15, 3, 30, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (5, 22, 0, 20, 3, 30, 'BIBLIOTECA', 'INTERMEDIARIO'),

-- Treino 6 a 10 (novos treinos de cada personal)
                                                                                                                                    (6, 6, 50, 10, 4, 60, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (6, 7, 14, 10, 3, 60, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (6, 8, 0, 20, 3, 45, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (6, 9, 30, 10, 3, 60, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (6, 1, 60, 10, 4, 90, 'PERSONAL', 'INTERMEDIARIO'),

                                                                                                                                    (7, 2, 70, 12, 3, 60, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (7, 10, 35, 10, 3, 45, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (7, 11, 20, 12, 4, 60, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (7, 12, 90, 10, 3, 75, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (7, 3, 40, 15, 3, 45, 'PERSONAL', 'INTERMEDIARIO'),

                                                                                                                                    (8, 13, 10, 12, 3, 45, 'PERSONAL', 'INICIANTE'),
                                                                                                                                    (8, 14, 12, 12, 3, 45, 'PERSONAL', 'INICIANTE'),
                                                                                                                                    (8, 4, 15, 12, 3, 60, 'BIBLIOTECA', 'INICIANTE'),
                                                                                                                                    (8, 15, 10, 15, 3, 45, 'PERSONAL', 'INICIANTE'),
                                                                                                                                    (8, 1, 40, 10, 3, 60, 'BIBLIOTECA', 'INICIANTE'),

                                                                                                                                    (9, 16, 0, 60, 3, 30, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (9, 17, 0, 15, 3, 30, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (9, 18, 0, 50, 3, 30, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (9, 19, 0, 40, 3, 30, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (9, 2, 50, 10, 3, 60, 'PERSONAL', 'INTERMEDIARIO'),

                                                                                                                                    (10, 20, 0, 20, 3, 20, 'PERSONAL', 'INTERMEDIARIO'),
                                                                                                                                    (10, 21, 0, 20, 3, 20, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (10, 5, 0, 30, 4, 30, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (10, 22, 0, 20, 3, 20, 'BIBLIOTECA', 'INTERMEDIARIO'),
                                                                                                                                    (10, 4, 10, 15, 3, 60, 'PERSONAL', 'INTERMEDIARIO');
-- -----------------------------------------------------
-- Table `vitalis`.`anamnese`
-- -----------------------------------------------------


INSERT INTO vitalis.anamnese (
    alunos_id, objetivo_treino, lesao, lesao_descricao, frequencia_treino,
    experiencia, experiencia_descricao, desconforto, desconforto_descricao,
    fumante, proteses, proteses_descricao, doenca_metabolica, doenca_metabolica_descricao,
    deficiencia, deficiencia_descricao
) VALUES
      (6, 'Perder peso e ganhar resistência.', FALSE, NULL, '3', TRUE, 'Musculação recreativa.', FALSE, NULL, FALSE, FALSE, NULL, FALSE, NULL, FALSE, NULL),
      (7, 'Ganhar massa muscular.', TRUE, 'Lesão no ombro direito.', '4', FALSE, NULL, TRUE, 'Desconforto nos joelhos.', TRUE, FALSE, NULL, FALSE, NULL, FALSE, NULL),
-- (8, 'Melhorar performance em corridas.', FALSE, NULL, '5', TRUE, 'Participou de treinos intensivos.', FALSE, NULL, FALSE, FALSE, NULL, FALSE, NULL, FALSE, NULL),
-- (9, 'Reabilitação física.', TRUE, 'Hérnia de disco.', '2', FALSE, NULL, TRUE, 'Desconforto lombar.', TRUE, TRUE, 'Prótese no joelho esquerdo.', TRUE, 'Diabetes tipo 2', FALSE, NULL),
      (10, 'Condicionamento geral.', FALSE, NULL, '5', TRUE, 'Fazia funcional com personal.', FALSE, NULL, FALSE, FALSE, NULL, FALSE, NULL, FALSE, NULL);


-- -----------------------------------------------------
-- Table `vitalis`.`alunos_treinos`
-- -----------------------------------------------------


INSERT INTO vitalis.alunos_treinos (alunos_id, treinos_exercicios_id, dias_semana, data_vencimento) VALUES
                                                                                                        (6, 1, JSON_ARRAY('Segunda', 'Quarta'), '2025-08-01'),
                                                                                                        (7, 1, JSON_ARRAY('Segunda', 'Quarta', 'Sexta'), '2025-08-01'),
                                                                                                        (7, 2, JSON_ARRAY('Segunda', 'Quarta', 'Sexta'), '2025-08-01'),
                                                                                                        (8, 3, JSON_ARRAY('Segunda', 'Quarta', 'Sábado'), '2025-06-05'),-- (4)
                                                                                                        (8, 3, JSON_ARRAY('Segunda', 'Quarta', 'Sábado'), '2025-08-01'), -- (5)
                                                                                                        (9, 4, JSON_ARRAY('Segunda'), '2025-06-15'),
                                                                                                        (7, 3, JSON_ARRAY('Segunda', 'Quarta', 'Sexta'), '2025-08-01'), -- mantido único (7)
                                                                                                        (10, 5, JSON_ARRAY('Todos'), '2025-08-01'),
                                                                                                        (6, 6, JSON_ARRAY('Terça', 'Quinta'), '2025-06-11'),
                                                                                                        (7, 6, JSON_ARRAY('Segunda', 'Quarta'), '2025-06-10'),
                                                                                                        (8, 6, JSON_ARRAY('Quarta'), '2025-08-01'), -- (11)
                                                                                                        (9, 7, JSON_ARRAY('Sexta'), '2025-08-01'),
                                                                                                        (10, 7, JSON_ARRAY('Quinta'), '2025-08-01'),

-- Treinos avulsos e pendentes
                                                                                                        (6, 38, JSON_ARRAY('Segunda', 'Quarta', 'Sexta'), '2025-05-31'),
                                                                                                        (7, 39, JSON_ARRAY('Terça', 'Quinta'), '2025-06-15'),
                                                                                                        (8, 40, JSON_ARRAY('Sábado'), '2025-05-10'), -- (16)
                                                                                                        (9, 41, JSON_ARRAY('Segunda', 'Quarta'), '2025-06-18'),
                                                                                                        (10, 42, JSON_ARRAY('Domingo'), '2025-06-10');




INSERT INTO vitalis.treinos_finalizados (data_horario_inicio, data_horario_fim, alunos_treinos_id) VALUES
                                                                                                       ('2025-05-10 08:00:00', '2025-05-10 09:00:00', 1),
                                                                                                       ('2025-05-10 12:00:00', '2025-05-10 13:00:00', 2),
                                                                                                       ('2025-05-15 08:00:00', '2025-05-15 09:00:00', 3),
                                                                                                       ('2025-05-14 08:00:00', '2025-05-14 09:00:00', 5),
                                                                                                       ('2025-05-10 10:00:00', '2025-05-10 11:00:00', 6),
                                                                                                       ('2025-05-21 08:00:00', '2025-05-21 09:00:00', 8),
                                                                                                       ('2025-05-23 08:00:00', '2025-05-23 09:00:00', 9),
                                                                                                       ('2025-06-01 07:30:00', '2025-06-01 08:15:00', 10),
                                                                                                       ('2025-05-24 08:00:00', '2025-05-24 09:30:00', 12),
                                                                                                       ('2025-05-04 10:00:00', NULL, 13),
                                                                                                       ('2025-06-02 06:30:00', '2025-06-02 07:15:00', 14),
                                                                                                       ('2025-05-25 08:00:00', '2025-05-25 09:00:00', 15),
                                                                                                       ('2025-05-27 08:00:00', '2025-05-27 09:00:00', 17),
                                                                                                       ('2025-05-28 08:00:00', '2025-05-28 09:00:00', 18),

-- Treinos específicos avulsos e finalizados
                                                                                                       ('2025-06-10 08:00:00', '2025-06-10 09:00:00', 2),
                                                                                                       ('2025-05-13 10:00:00', '2025-05-13 11:00:00', 3);

-- -----------------------------------------------------
-- Table `vitalis`.`planos`
-- Tabela de planos criados pelo personal trainer (modelos de plano)
-- -----------------------------------------------------


INSERT INTO vitalis.planos (personal_trainers_id, nome, periodo, quantidade_aulas, valor_aulas) VALUES
                                                                                                    (1, 'Plano Básico', 'MENSAL', 8, 60.00),
                                                                                                    (2, 'Plano Avulso', 'AVULSO', 1, 80.00),
                                                                                                    (3, 'Plano Intensivo', 'SEMESTRAL', 48, 50.00),
                                                                                                    (4, 'Reabilitação', 'MENSAL', 4, 70.00),
                                                                                                    (5, 'Mensal Fit', 'MENSAL', 8, 65.00),
                                                                                                    (5, 'Semestral Power', 'SEMESTRAL', 48, 50.00),
                                                                                                    (5, 'Avulso Especial', 'AVULSO', 1, 85.00);

-- -----------------------------------------------------
-- Table `vitalis`.`planos`
-- Tabela de planos contratados pelos alunos
-- -----------------------------------------------------


INSERT INTO vitalis.planos_contratados (planos_id, alunos_id, status, data_contratacao, data_fim) VALUES
                                                                                                      (1, 6, 'ATIVO', '2025-08-01', '2025-09-15'), -- vencimento amanhã
                                                                                                      (5, 6, 'ATIVO', '2025-08-15', '2025-09-14'), -- vencimento hoje
                                                                                                      (3, 7, 'ATIVO', '2025-07-01', '2025-09-16'),
                                                                                                      (5, 7, 'ATIVO', '2025-08-20', '2025-09-18'),
                                                                                                      (2, 7, 'PENDENTE', '2025-09-17', NULL), -- começará depois
                                                                                                      (3, 8, 'ATIVO', '2025-07-01', '2025-09-14'), -- vencimento hoje
                                                                                                      (5, 8, 'ATIVO', '2025-08-01', '2025-09-16'),
                                                                                                      (7, 8, 'EM_PROCESSO', '2025-09-17', NULL),
                                                                                                      (5, 9, 'ATIVO', '2025-08-01', '2025-09-15'), -- vencimento amanhã
                                                                                                      (4, 9, 'INATIVO', '2025-06-01', '2025-07-01'), -- histórico
                                                                                                      (5, 10, 'ATIVO', '2025-08-01', '2025-09-18');

-- -----------------------------------------------------
-- Table `vitalis`.`cidades`
-- -----------------------------------------------------

-- EXEMPLO ATÉ PEGAR DA API
INSERT INTO vitalis.cidades (nome) VALUES
                                       ('São Paulo'),
                                       ('Campinas'),
                                       ('Rio de Janeiro'),
                                       ('Belo Horizonte'),
                                       ('Curitiba');


-- -----------------------------------------------------
-- Table `vitalis`.`bairros`
-- -----------------------------------------------------


-- EXEMPLO ATÉ PEGAR DA API
INSERT INTO vitalis.bairros (nome, cidades_id) VALUES
                                                   ('Vila Mariana', 1),
                                                   ('Centro', 2),
                                                   ('Copacabana', 3),
                                                   ('Savassi', 4),
                                                   ('Batel', 5);


-- -----------------------------------------------------
-- Table `vitalis`.`personal_trainers_bairros`
-- -----------------------------------------------------


INSERT INTO vitalis.personal_trainers_bairros (personal_trainers_id, bairro_id) VALUES
                                                                                    (1, 1),
                                                                                    (2, 1),
                                                                                    (3, 2),
                                                                                    (4, 3),
                                                                                    (5, 5);

-- -----------------------------------------------------
-- Table `vitalis`.`feedbacks`
-- -----------------------------------------------------

INSERT INTO vitalis.feedbacks (
    titulo, descricao, data_criacao, alunos_treinos_id
) VALUES
      ('Ótimo treino', 'Me senti muito bem após os exercícios!', '2025-04-01 10:00:00', 1),
      ('Dificuldade com carga', 'Achei a carga pesada no agachamento.', '2025-04-02 11:00:00', 2),
      ('Foco no core', 'Preciso de mais exercícios para o core.', '2025-04-03 08:30:00', 3),
      ('Treino leve', 'Foi um treino bem tranquilo, gostei.', '2025-04-04 10:30:00', 4),
      ('Muito intenso', 'Suei bastante, foi puxado.', '2025-04-05 07:30:00', 5);


-- -----------------------------------------------------
-- Table `vitalis`.`comentarios`
-- -----------------------------------------------------


INSERT INTO vitalis.comentarios (
    feedbacks_id, pessoas_id, descricao, tipo_autor, intensidade
) VALUES
      (1, 1, 'Parabéns pelo empenho!', 'PERSONAL', 'MODERADO'),
      (2, 2, 'Vamos ajustar a carga.', 'PERSONAL', 'INTENSA'),
      (3, 8, 'Senti que foi fraco, quero mais.', 'ALUNO', 'LEVE'),
      (4, 4, 'Bom trabalho, siga assim.', 'PERSONAL', 'MODERADO'),
      (5, 10, 'Realmente cansativo, mas ótimo.', 'ALUNO', 'MUITO_INTENSA');




-- -----------------------------------------------------
-- Table `vitalis`.`evolucao_corporal`
-- -----------------------------------------------------


INSERT INTO vitalis.evolucao_corporal (
    id, tipo, url_foto_shape, data_envio, periodo_avaliacao, alunos_id
) VALUES
      (1, 'FRONTAL', 'https://img.com/aluno6-frontal.jpg', '2025-04-01 08:00:00', 1, 6),
      (2, 'PERFIL_DIREITO', 'https://img.com/aluno7-perfil.jpg', '2025-04-02 09:00:00', 1, 7),
      (3, 'COSTAS', 'https://img.com/aluno8-costas.jpg', '2025-04-03 07:30:00', 2, 8),
      (4, 'FRONTAL', 'https://img.com/aluno9-frontal.jpg', '2025-04-04 10:00:00', 1, 9),
      (5, 'PERFIL_ESQUERDO', 'https://img.com/aluno10-perfil.jpg', '2025-04-05 06:30:00', 3, 10);

