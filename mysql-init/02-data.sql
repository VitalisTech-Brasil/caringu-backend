-- Pessoas para personal_trainers (IDs: 1 a 5)
INSERT INTO vitalis.pessoas (nome, email, senha, celular, url_foto_perfil, data_nascimento, genero ) VALUES
                                                                                                         ('Lucas Andrade', 'lucas.andrade@gmail.com', 'senha1234', '11998765432', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/personal-trainers/Lucas Andrade.png', '1990-03-15', 'HOMEM_CISGENERO'),
                                                                                                         ('Mariana Silva', 'mariana.silva@gmail.com', 'senha5678', '21998887766', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/personal-trainers/Mariana Silva.png', '1988-07-22', 'MULHER_CISGENERO'),
                                                                                                         ('João Pereira', 'joao.pereira@gmail.com', 'joao3210', '31999990000', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/personal-trainers/João Pereira.png', '1995-11-05', 'HOMEM_TRANSGENERO'),
                                                                                                         ('Ana Costa', 'ana.costa@gmail.com', 'anaana123', '21991234567', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/personal-trainers/Ana Costa.png', '1992-05-18', 'MULHER_TRANSGENERO'),

-- INSERT COM JWT (ID: 5)
                                                                                                         ('Ricardo Gomes da Silva', 'ricardo@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '11997654321', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/ricardo.png', '1999-12-25', 'HOMEM_CISGENERO'),

-- Pessoas para alunos (IDs: 6 a 10)
                                                                                                         ('Carla Mendes', 'carla.mendes@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '11994455667', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/carla.png', '1985-02-10', 'MULHER_TRANSGENERO'),
                                                                                                         ('Alex Nagano', 'alex.nagano@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '11999887766', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/Nagano/nagano.png', '2000-12-01', 'NAO_BINARIO'),
                                                                                                         ('Suellen Lima', 'suellen.lima@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', NULL, 'https://storagevitalis.blob.core.windows.net/fotos-perfil/suellen/suel.png', '1997-09-12', 'MULHER_TRANSGENERO'),
                                                                                                         ('Diego Santos', 'diego.santos@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '21998877665', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/diego.png', NULL, 'HOMEM_TRANSGENERO'),
                                                                                                         ('Luna Martins', 'luna.martins@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '31997766544', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/luna.jpg', '1993-04-25', 'HOMEM_CISGENERO'),
                                                                                                         ('Marcos Martins', 'marcos@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '31997766513', 'https://storagevitalis.blob.core.windows.net/fotos-perfil/marcos (aluno).png', '1993-04-25', 'HOMEM_CISGENERO'),
                                                                                                         ('Flavio Martins', 'flavio@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '31997766514', 'https://flavio.jpg', '1993-04-25', 'HOMEM_CISGENERO'),
                                                                                                         ('Otavio Martins', 'otavio@gmail.com', '$2a$10$pn9w6oGeHHfo8WBghLvKvuw.ed5GMYuLw0Tpy0XKPHI5lQTv2HdP.', '31997766515', 'https://otavio.jpg', '1993-04-25', 'HOMEM_CISGENERO');

INSERT INTO personal_trainers (id, cref, experiencia) VALUES
                                                          (1, 'CREF123456-SP', 15),
                                                          (2, 'CREF654321-SP', 5),
                                                          (3, 'CREF987654-RJ', 10),
                                                          (4, 'CREF112233-SP', 25),
                                                          (5, '123456-G/SP', 16);

INSERT INTO vitalis.alunos (id, peso, altura, nivel_atividade, nivel_experiencia) VALUES
                                                                                      (6, 75.50, 1.75, 'MODERADAMENTE_ATIVO', 'INTERMEDIARIO'),
                                                                                      (7, 65.20, 1.68, 'LEVEMENTE_ATIVO', 'INICIANTE'),
                                                                                      (8, 82.00, 1.80, 'MUITO_ATIVO', 'AVANCADO'),
                                                                                      (9, 90.30, 1.85, 'SEDENTARIO', 'INICIANTE'),
                                                                                      (10, 70.00, 1.65, 'EXTREMAMENTE_ATIVO', 'INTERMEDIARIO');

INSERT INTO vitalis.exercicios (personal_id, nome, grupo_muscular, url_video, observacoes, favorito, origem) VALUES
-- Peitoral
(5, 'Supino Inclinado', 'PEITORAL', 'https://www.hipertrofia.org/blog/wp-content/uploads/2023/09/barbell-incline-bench-press.gif', 'Ajustar banco entre 30° e 45°', FALSE, 'PERSONAL'),
(5, 'Crucifixo com Halteres', 'PEITORAL', 'https://www.hipertrofia.org/blog/wp-content/uploads/2020/06/dumbbell-incline-fly.gif', 'Manter braços semiflexionados e controlar o movimento', TRUE, 'PERSONAL'),
(5, 'Flexões de Braço', 'PEITORAL', 'https://www.hipertrofia.org/blog/wp-content/uploads/2018/09/pushup.gif', 'Não deixar o quadril cair durante o movimento', FALSE, 'PERSONAL'),
(5, 'Peck Deck', 'PEITORAL', 'https://i0.wp.com/omelhortreino.com.br/wp-content/uploads/2025/03/Supino-Inclinado-com-Halteres.gif?resize=500%2C500&ssl=1', 'Evitar bater os braços no final do movimento', FALSE, 'PERSONAL'),

(NULL, 'Supino Inclinado', 'PEITORAL', 'https://www.hipertrofia.org/blog/wp-content/uploads/2023/09/barbell-incline-bench-press.gif', 'Ajustar banco entre 30° e 45°', FALSE, 'BIBLIOTECA'),
(NULL, 'Crucifixo com Halteres', 'PEITORAL', 'https://www.hipertrofia.org/blog/wp-content/uploads/2020/06/dumbbell-incline-fly.gif', 'Manter braços semiflexionados e controlar o movimento', TRUE, 'BIBLIOTECA'),
(NULL, 'Flexões de Braço', 'PEITORAL', 'https://www.hipertrofia.org/blog/wp-content/uploads/2018/09/pushup.gif', 'Não deixar o quadril cair durante o movimento', FALSE, 'BIBLIOTECA'),
(NULL, 'Peck Deck', 'PEITORAL', 'https://i0.wp.com/omelhortreino.com.br/wp-content/uploads/2025/03/Supino-Inclinado-com-Halteres.gif?resize=500%2C500&ssl=1', 'Evitar bater os braços no final do movimento', FALSE, 'BIBLIOTECA'),

-- Costas
(5, 'Barra', 'COSTAS', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/costas-barra-fixa-pegada-aberta-palma-para-frente-chinup.gif', 'Evitar balanço do corpo e manter a escápula ativada', FALSE, 'PERSONAL'),
(5, 'Remada', 'COSTAS', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/09/remada-sentado-com-cabos-e-triangulo-para-costas.gif', 'Manter coluna neutra e puxar com os cotovelos', TRUE, 'PERSONAL'),
(5, 'Puxada Alta', 'COSTAS', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/costas-puxada-aberta-com-barra-no-pulley.gif', 'Puxar até a altura do queixo sem inclinar o tronco para trás', FALSE, 'PERSONAL'),

-- Pernas
(5, 'Afundo', 'PERNAS', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/pernas-afundo-tradicional-sem-pesos-1.gif', 'Joelho da frente não deve ultrapassar a ponta do pé', FALSE, 'PERSONAL'),
(NULL, 'Afundo', 'PERNAS', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/pernas-afundo-tradicional-sem-pesos-1.gif', 'Joelho da frente não deve ultrapassar a ponta do pé', FALSE, 'BIBLIOTECA'),

(5, 'Stiff com Halteres', 'PERNAS', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/09/levantamento-terra-deadlift-stiff-com-halteres-1.gif', 'Manter leve flexão nos joelhos e costas retas', TRUE, 'PERSONAL'),
(5, 'Leg Press', 'PERNAS', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/pernas-leg-press-45-tradicional.gif', 'Não estender totalmente os joelhos na subida', FALSE, 'PERSONAL'),

-- Ombro
(5, 'Elevação Lateral', 'OMBRO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/ombros-elevacao-lateral-de-ombros-com-halteres.gif', 'Elevar os braços até a linha dos ombros, sem impulso', TRUE, 'PERSONAL'),
(5, 'Desenvolvimento Arnold', 'OMBRO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/ombros-desenvolvimento-arnold-para-ombros.gif', 'Evitar estender totalmente os cotovelos no topo', FALSE, 'PERSONAL'),
(5, 'Elevação Frontal', 'OMBRO', 'https://i0.wp.com/omelhortreino.com.br/wp-content/uploads/2025/04/Elevacao-frontal-com-dois-bracos-com-halteres.gif?resize=550%2C550&ssl=1', 'Levantar até a altura dos ombros com controle', FALSE, 'PERSONAL'),

-- Braço
(5, 'Rosca direta', 'BRACO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2022/09/rosca-biceps-direta-com-halteres.gif', 'Evitar balançar o corpo; manter os cotovelos fixos', TRUE, 'PERSONAL'),
(5, 'Tríceps banco', 'BRACO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/triceps-no-banco.gif', 'Não deixar os ombros subirem durante a descida', FALSE, 'PERSONAL'),
(5, 'Rosca martelo', 'BRACO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/rosca-biceps-martelo-com-halteres.gif', 'Manter os cotovelos fixos ao lado do corpo', FALSE, 'PERSONAL'),
(NULL, 'Rosca martelo', 'BRACO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/rosca-biceps-martelo-com-halteres.gif', 'Manter os cotovelos fixos ao lado do corpo', FALSE, 'BIBLIOTECA'),

-- Core
(5, 'Prancha', 'CORE', 'https://www.mundoboaforma.com.br/wp-content/uploads/2014/12/prancha-frontal-tradicional-com-bracos-esticados.gif', 'Manter abdômen contraído e quadril na linha da coluna', FALSE, 'PERSONAL'),
(NULL, 'Prancha', 'CORE', 'https://www.mundoboaforma.com.br/wp-content/uploads/2014/12/prancha-frontal-tradicional-com-bracos-esticados.gif', 'Manter abdômen contraído e quadril na linha da coluna', FALSE, 'BIBLIOTECA'),

(5, 'Abdominal', 'CORE', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/abdominal.gif', 'Evitar forçar o pescoço; foco no abdômen', FALSE, 'PERSONAL'),
(5, 'Bicicleta no ar', 'CORE', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/03/abdominal-bicicleta-no-ar.gif', 'Rotação do tronco com pernas em movimento controlado', TRUE, 'PERSONAL'),
(5, 'Prancha lateral', 'CORE', 'https://www.mundoboaforma.com.br/wp-content/uploads/2020/12/prancha-lateral.gif', 'Alinhar o corpo e evitar que o quadril caia', FALSE, 'PERSONAL'),
(5, 'Elevação de pernas', 'CORE', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/03/abdominal-no-chao-com-elevacao-de-pernas-esticadas.gif', 'Evitar tirar a lombar do chão', FALSE, 'PERSONAL'),

(5, 'Abdominal Tesoura', 'CORE', 'https://www.hipertrofia.org/blog/wp-content/uploads/2024/09/abdominal-tesoura.gif', 'Executar com respiração controlada e sem pressa', TRUE, 'PERSONAL'),
(NULL, 'Abdominal Tesoura', 'CORE', 'https://www.hipertrofia.org/blog/wp-content/uploads/2024/09/abdominal-tesoura.gif', 'Executar com respiração controlada e sem pressa', TRUE, 'BIBLIOTECA'),

-- Cardio
(5, 'Esteira', 'CARDIO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/corrida-na-esteira-inclinada-1.gif', 'Postura ereta, respiração controlada', FALSE, 'PERSONAL'),
(NULL, 'Esteira', 'CARDIO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/corrida-na-esteira-inclinada-1.gif', 'Postura ereta, respiração controlada', FALSE, 'BIBLIOTECA'),

(5, 'Bicicleta', 'CARDIO', 'https://example.com/burpee', 'Manter cadência constante e postura correta', TRUE, 'PERSONAL'),
(5, 'Corda', 'CARDIO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/01/26121301-Jump-Rope-male_Cardio_360-logo.gif', 'Pulos baixos e ritmo regular para evitar fadiga precoce', FALSE, 'PERSONAL'),
(5, 'Escada', 'CARDIO', 'https://www.hipertrofia.org/blog/wp-content/uploads/2024/09/walking-on-stepmill.gif', 'Manter postura firme e não apoiar o peso nos braços', FALSE, 'PERSONAL'),

(5, 'Burpee', 'CARDIO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/burpee.gif', 'Controlar a lombar ao descer e manter ritmo constante', FALSE, 'PERSONAL'),
(NULL, 'Burpee', 'CARDIO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/burpee.gif', 'Controlar a lombar ao descer e manter ritmo constante', FALSE, 'BIBLIOTECA'),

(5, 'Polichinelo', 'CARDIO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/polichinelos.gif', 'Manter respiração contínua e braços sincronizados com pernas', FALSE, 'PERSONAL'),
(NULL, 'Polichinelo', 'CARDIO', 'https://www.mundoboaforma.com.br/wp-content/uploads/2021/04/polichinelos.gif', 'Manter respiração contínua e braços sincronizados com pernas', FALSE, 'BIBLIOTECA');

-- ========================================
-- TABELA: treinos
-- ========================================
INSERT INTO vitalis.treinos (nome, descricao, favorito, personal_id, origem, grau_dificuldade) VALUES

-- PERSONAIS (11 treinos)
('Peito Avançado', 'Hipertrofia do peitoral com barra', TRUE, 5, 'PERSONAL', 'AVANCADO'),
('Peito e Braço Avançado', 'Hipertrofia máxima do peitoral e bíceps/tríceps', FALSE, 5, 'PERSONAL', 'AVANCADO'),
('Pernas e Core Intermediário', 'Força nas pernas com estabilização abdominal', FALSE, 5, 'PERSONAL', 'INTERMEDIARIO'),
('Costas e Ombro Intermediário', 'Foco em dorsais e estabilidade do ombro', TRUE, 5, 'PERSONAL', 'INTERMEDIARIO'),
('Cardio e Core Funcional', 'Alta intensidade para queima calórica e abdômen', FALSE, 5, 'PERSONAL', 'INTERMEDIARIO'),
('Full Body Iniciante', 'Treino geral para iniciantes com todos os grupos', TRUE, 5, 'PERSONAL', 'INICIANTE'),
('Peito e Costas Intermediário', 'Empurrar e puxar para equilíbrio muscular', FALSE, 5, 'PERSONAL', 'INTERMEDIARIO'),
('Pernas Avançado', 'Foco em força máxima e hipertrofia de inferiores', FALSE, 5, 'PERSONAL', 'AVANCADO'),
('Braço e Ombro Iniciante', 'Força básica para braços e estabilidade do ombro', TRUE, 5, 'PERSONAL', 'INICIANTE'),
('Cardio Intenso', 'Circuito cardiovascular para condicionamento', FALSE, 5, 'PERSONAL', 'AVANCADO'),
('Core Avançado', 'Abdômen e lombar com exercícios de alto desafio', TRUE, 5, 'PERSONAL', 'AVANCADO'),

-- BIBLIOTECA (2 treinos globais)
('Treino Funcional Básico', 'Treino leve com exercícios multiarticulares', FALSE, NULL, 'BIBLIOTECA', 'INICIANTE'),
('Treino Completo Academia', 'Rotina equilibrada para todos os grupos musculares', FALSE, NULL, 'BIBLIOTECA', 'INTERMEDIARIO');

-- ========================================
-- TABELA: treinos_exercicios
-- ========================================
INSERT INTO vitalis.treinos_exercicios
(treinos_id, exercicios_id, carga, repeticoes, series, descanso, observacoes_personalizadas) VALUES

-- Treino 1: Peito Avançado
(1, 1, 50, 10, 4, 90, 'Ajustar técnica'),
(1, 2, 12, 12, 4, 60, NULL),
(1, 3, 1, 20, 3, 45, 'Foco em resistência'),
(1, 4, 35, 10, 3, 60, NULL),

-- Treino 2: Peito e Braço Avançado
(2, 5, 50, 10, 4, 90, 'Ajustar técnica'),
(2, 6, 12, 12, 4, 60, NULL),
(2, 7, 1, 20, 3, 45, 'Foco em resistência'),
(2, 8, 35, 10, 3, 60, NULL),

-- Treino 3: Pernas e Core Intermediário
(3, 1, 60, 8, 4, 90, 'Supino pesado, foco em hipertrofia'),
(3, 2, 18, 10, 4, 60, NULL),
(3, 17, 25, 8, 4, 60, NULL),
(3, 18, 20, 12, 4, 60, 'Controlar descida'),

-- Treino 4: Costas e Ombro Intermediário
(4, 13, 20, 12, 3, 60, NULL),
(4, 15, 100, 10, 4, 90, 'Não travar joelhos'),
(4, 14, 24, 10, 3, 60, NULL),
(4, 21, 0, 30, 3, 45, 'Segurar posição correta'),
(4, 23, 0, 20, 3, 45, NULL),

-- Treino 5: Cardio e Core Funcional
(5, 9, 0, 10, 4, 90, 'Amplitudes completas'),
(5, 10, 40, 12, 3, 60, NULL),
(5, 11, 45, 10, 4, 90, NULL),
(5, 16, 10, 12, 3, 60, 'Subir controlado'),
(5, 17, 20, 10, 3, 60, NULL),

-- Treino 6: Full Body Iniciante
(6, 27, 0, 60, 3, 30, 'Ritmo moderado'),
(6, 29, 0, 40, 3, 30, NULL),
(6, 31, 0, 12, 3, 45, NULL),
(6, 19, 0, 20, 3, 30, 'Ativar abdômen'),
(6, 22, 0, 20, 3, 45, NULL),

-- Treino 7: Peito e Costas Intermediário
(7, 1, 30, 12, 3, 60, NULL),
(7, 9, 0, 8, 3, 60, 'Execução assistida'),
(7, 13, 12, 12, 3, 60, NULL),
(7, 17, 6, 12, 3, 45, NULL),
(7, 19, 0, 20, 3, 45, NULL),
(7, 21, 0, 30, 3, 45, 'Manter quadril alinhado'),

-- Treino 8: Pernas Avançado
(8, 1, 50, 10, 4, 90, NULL),
(8, 3, 0, 20, 3, 60, NULL),
(8, 10, 40, 12, 4, 60, NULL),
(8, 11, 45, 10, 3, 90, 'Controle no movimento'),

-- Treino 9: Braço e Ombro Iniciante
(9, 15, 120, 8, 5, 90, 'Máxima força'),
(9, 14, 30, 10, 4, 90, NULL),
(9, 13, 25, 12, 4, 60, NULL),
(9, 23, 0, 15, 4, 45, 'Evitar tirar lombar do chão'),

-- Treino 10: Cardio Intenso
(10, 17, 8, 12, 3, 60, NULL),
(10, 18, 12, 12, 3, 60, NULL),
(10, 16, 6, 12, 3, 45, NULL),
(10, 17, 6, 12, 3, 45, 'Controle total do movimento'),

-- Treino 11: Core Avançado
(11, 25, 0, 300, 1, 0, 'Corrida contínua'),
(11, 26, 0, 10, 3, 45, 'RPM moderado'),
(11, 32, 0, 20, 3, 30, 'Explosão máxima'),
(11, 31, 0, 15, 3, 45, 'Manter postura'),

-- Treino 12: Treino Funcional Básico
(12, 24, 0, 15, 4, 45, NULL),
(12, 23, 0, 20, 4, 45, NULL),
(12, 22, 0, 20, 4, 45, NULL),
(12, 23, 0, 15, 4, 45, 'Evitar compensar com lombar');

INSERT INTO vitalis.planos (personal_trainers_id, nome, periodo, quantidade_aulas, valor_aulas) VALUES
                                                                                                    (1, 'Plano Básico', 'MENSAL', 8, 60.00),
                                                                                                    (2, 'Plano Avulso', 'AVULSO', 1, 80.00),
                                                                                                    (3, 'Plano Intensivo', 'SEMESTRAL', 48, 50.00),
                                                                                                    (4, 'Reabilitação', 'MENSAL', 4, 70.00),
                                                                                                    (5, 'Mensal Fit', 'MENSAL', 8, 65.00),
                                                                                                    (5, 'Power', 'SEMESTRAL', 48, 50.00),
                                                                                                    (5, 'Avulso Especial', 'AVULSO', 1, 85.00);

INSERT INTO vitalis.planos_contratados (planos_id, alunos_id, status, data_contratacao, data_fim) VALUES
-- Aluno 6
(5, 6, 'INATIVO', '2025-05-01', '2025-06-01'),
(6, 6, 'ATIVO', '2025-06-01', '2025-09-25');

-- Aluno 7
-- (5, 7, 'INATIVO', '2025-01-01', '2025-02-01'),
-- (6, 7, 'ATIVO', '2025-06-01', '2025-12-01'),

-- Aluno 8
-- (5, 8, 'INATIVO', '2025-02-01', '2025-07-31'),
-- (6, 8, 'ATIVO', '2025-06-01', '2025-12-01'),

-- Aluno 9
-- (5, 9, 'INATIVO', '2025-04-01', '2025-05-01'),
-- (6, 9, 'ATIVO', '2025-06-01', '2025-12-01'),
-- (4, 9, 'INATIVO', '2025-03-01', '2025-04-01'),

-- Aluno 10
-- (6, 10, 'ATIVO', '2025-06-01', '2025-12-01'),
-- (7, 10, 'INATIVO', '2025-02-01', '2025-02-02');

-- ========================================
-- TABELA: aulas
-- ========================================
INSERT INTO vitalis.aulas
(planos_contratados_id, data_horario_inicio, data_horario_fim, status) VALUES

-- Aluno 6 (plano_contratado id 1 - inativo (dia 05-01 até 06-01))
-- Primeira semana do mês de Maio
(1, '2025-05-05 08:00:00', '2025-05-05 09:00:00', 'REALIZADO'),
(1, '2025-05-07 08:00:00', '2025-05-07 09:00:00', 'REALIZADO'),
(1, '2025-05-09 08:00:00', '2025-05-09 09:00:00', 'REALIZADO'),

-- Segunda semana do mês de Maio
-- (1, '2025-05-12 08:00:00', '2025-05-12 09:00:00', 'REALIZADO'),
-- (1, '2025-05-14 08:00:00', '2025-05-14 09:00:00', 'REALIZADO'),
-- (1, '2025-05-16 08:00:00', '2025-05-16 09:00:00', 'REALIZADO'),

-- Terceira semana do mês de Maio
-- (1, '2025-05-19 08:00:00', '2025-05-19 09:00:00', 'REALIZADO'),
-- (1, '2025-05-21 08:00:00', '2025-05-21 09:00:00', 'REALIZADO'),
-- (1, '2025-05-23 08:00:00', '2025-05-23 09:00:00', 'REALIZADO'),

-- Última semana do mês de Maio
-- (1, '2025-05-26 08:00:00', '2025-05-26 09:00:00', 'REALIZADO'),
-- (1, '2025-05-28 08:00:00', '2025-05-28 09:00:00', 'REALIZADO'),
-- (1, '2025-05-30 08:00:00', '2025-05-30 09:00:00', 'REALIZADO'),

-- Aluno 6 (plano_contratado id 2 - ativo)
(2, '2025-06-10 08:00:00', '2025-06-10 09:00:00', 'REALIZADO'),
(2, '2025-07-10 08:00:00', '2025-07-10 09:00:00', 'REALIZADO'),
(2, '2025-08-10 08:00:00', '2025-08-10 09:00:00', 'REALIZADO'),
(2, '2025-09-08 09:00:00', '2025-09-08 10:30:00', 'REALIZADO'),
(2, '2025-09-10 08:00:00', '2025-09-10 09:00:00', 'REALIZADO');

-- Aluno 7 (plano_contratado id 5 - ativo)
-- (4, '2025-07-20 12:00:00', '2025-07-20 13:30:00', 'REALIZADO'),
-- (4, '2025-08-25 18:00:00', '2025-08-25 19:00:00', 'REALIZADO'),
-- (4, '2025-09-02 10:00:00', NULL, 'AGENDADO');

-- Aluno 8 (plano_contratado id 8 - ativo)
-- (8, '2025-08-12 08:00:00', '2025-08-12 09:00:00', 'REALIZADO'),
-- (8, '2025-08-29 09:00:00', NULL, 'AGENDADO'),
-- 
-- -- Aluno 9 (plano_contratado id 10 - ativo)
-- (10, '2025-08-15 08:00:00', '2025-08-15 09:00:00', 'REALIZADO'),
-- (10, '2025-08-27 18:00:00', NULL, 'AGENDADO'),
-- 
-- -- Aluno 10 (plano_contratado id 12 - ativo)
-- (12, '2025-08-20 07:00:00', '2025-08-20 08:00:00', 'REALIZADO'),
-- (12, '2025-08-31 08:00:00', NULL, 'AGENDADO');

-- ========================================
-- TABELA: aulas_treinos_exercicios (colocar novos inserts aqui)
-- ========================================
INSERT INTO vitalis.aulas_treinos_exercicios
(aulas_id, treinos_exercicios_id, ordem, carga, repeticoes, series, descanso, observacoes_personalizadas) VALUES

-- Sessão 1
(1, 1, 1, 50, 10, 4, 90, 'Ajustar técnica'),
(1, 2, 2, 12, 12, 4, 60, NULL),
(1, 3, 3, 1, 20, 3, 45, 'Foco em resistência'),
(1, 4, 4, 35, 10, 3, 60, NULL),

-- Sessão 2
(2, 5, 1, 50, 10, 4, 90, 'Ajustar técnica'),
(2, 6, 2, 12, 12, 4, 60, NULL),
(2, 7, 3, 1, 20, 3, 45, 'Foco em resistência'),
(2, 8, 4, 35, 10, 3, 60, NULL),

-- Sessão 3
(3, 9, 1, 50, 10, 4, 90, 'Ajustar técnica'),
(3, 10, 2, 12, 12, 4, 60, NULL),
(3, 11, 3, 1, 20, 3, 45, 'Foco em resistência'),
(3, 12, 4, 35, 10, 3, 60, NULL),

-- Sessão 4
(4, 13, 1, 50, 10, 4, 90, 'Ajustar técnica'),
(4, 14, 2, 12, 12, 4, 60, NULL),
(4, 15, 3, 1, 20, 3, 45, 'Foco em resistência'),
(4, 16, 4, 35, 10, 3, 60, NULL),

-- Sessão 5
(5, 18, 1, 50, 10, 4, 90, 'Ajustar técnica'),
(5, 19, 2, 12, 12, 4, 60, NULL),
(5, 20, 3, 1, 20, 3, 45, 'Foco em resistência'),
(5, 21, 4, 35, 10, 3, 60, NULL),

-- Sessão 6
(6, 23, 1, 50, 10, 4, 90, 'Ajustar técnica'),
(6, 24, 2, 12, 12, 4, 60, NULL),
(6, 25, 3, 1, 20, 3, 45, 'Foco em resistência'),
(6, 26, 4, 35, 10, 3, 60, NULL);

-- ========================================
-- TABELA: execucoes_exercicios
-- ========================================
INSERT INTO vitalis.execucoes_exercicios
(aulas_treinos_exercicios_id, carga_executada, repeticoes_executadas, series_executadas, descanso_executado) VALUES

-- Sessão 1
(1, 45.00, 8, 4, 90),
(2, 10.00, 10, 4, 60),
(3, 1.00, 15, 3, 120),
(4, 30.00, 10, 3, 60),

-- Sessão 2
(5, 47.50, 9, 4, 90),
(6, 12.00, 12, 4, 60),
(7, 1.25, 18, 3, 45),
(8, 32.50, 10, 3, 60),

-- Sessão 3
(9, 48.50, 10, 4, 90),
(10, 15.00, 12, 4, 60),
(11, 1.50, 18, 3, 45),
(12, 33.50, 10, 3, 60),

-- Sessão 4
(13, 49.50, 9, 4, 90),
(14, 17.00, 12, 4, 60),
(15, 1.75, 18, 3, 45),
(16, 36.50, 10, 3, 60),

-- Sessão 5
(17, 47.50, 9, 4, 90),
(18, 14.00, 12, 4, 60),
(19, 1.50, 18, 3, 45),
(20, 37.50, 10, 3, 60),

-- Sessão 6
(21, 50.50, 12, 7, 88),
(22, 15.00, 14, 6, 56),
(23, 1.75, 24, 4, 64),
(24, 34.40, 15, 5, 68);

-- ========================================
-- TABELA: feedbacks
-- ========================================
INSERT INTO vitalis.feedbacks
(aulas_id, pessoas_id, descricao, data_criacao, tipo_autor, intensidade) VALUES

-- Sessão 1
(1, 6, 'Me senti muito bem após os exercícios!', '2025-04-01 10:00:00', 'ALUNO', 'MODERADO'),
(1, 1, 'Parabéns pelo empenho!', '2025-04-01 10:05:00', 'PERSONAL', 'MODERADO'),

-- Sessão 2
(2, 6, 'Achei a carga pesada no agachamento.', '2025-04-02 11:00:00', 'ALUNO', 'INTENSA'),
(2, 2, 'Vamos ajustar a carga.', '2025-04-02 11:10:00', 'PERSONAL', 'INTENSA');

-- Sessão 3
-- (3, 8, 'Preciso de mais exercícios para o core.', '2025-04-03 08:30:00', 'ALUNO', 'LEVE'),

-- Sessão 4
-- (4, 9, 'Foi um treino bem tranquilo, gostei.', '2025-04-04 10:30:00', 'ALUNO', 'MODERADO'),
-- (4, 4, 'Bom trabalho, siga assim.', '2025-04-04 10:35:00', 'PERSONAL', 'MODERADO'),

-- Sessão 5
-- (5, 10, 'Suei bastante, foi puxado.', '2025-04-05 07:30:00', 'ALUNO', 'MUITO_INTENSA'),
-- (5, 10, 'Realmente cansativo, mas ótimo.', '2025-04-05 07:40:00', 'ALUNO', 'MUITO_INTENSA');

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

INSERT INTO personal_trainers_especialidades
VALUES
-- Personal 1: Musculação, Treinamento Funcional
(1, 1),
(1, 2),
-- Personal 2: Musculação, Emagrecimento
(2, 1),
(2, 5),
-- Personal 3: Condicionamento Físico ≈ Treinamento de Core + Saúde Metabólica?
(3, 4),
(3, 16),
-- Personal 4: Condicionamento Físico, Treinamento para Idosos
(4, 4),
(4, 16),
-- Personal 5: Reabilitação e Alongamento ≈ Reabilitação + Mobilidade
(5, 11),
(5, 12);

INSERT INTO notificacoes (pessoas_id, tipo, titulo, visualizada, data_criacao)
VALUES
-- (1, 'FEEDBACK_TREINO', 'Feedback de Treino', FALSE, CURRENT_TIMESTAMP),
(2, 'PAGAMENTO_REALIZADO', 'Pagamento Confirmado', FALSE, CURRENT_TIMESTAMP),
(3, 'PLANO_PROXIMO_VENCIMENTO', 'Plano Próximo Vencimento', FALSE, CURRENT_TIMESTAMP);
-- (4, 'NOVA_FOTO_PROGRESSO', 'Nova Foto de Progresso', TRUE, CURRENT_TIMESTAMP),
-- (5, 'TREINO_PROXIMO_VENCIMENTO', 'O treino Core Explosivo do aluno Alex Rocha vencerá em breve (15/06/2025)', FALSE, CURRENT_TIMESTAMP);

INSERT INTO preferencias_notificacao (pessoas_id, tipo, ativada)
VALUES
-- (1, 'FEEDBACK_TREINO', TRUE),
(1, 'PAGAMENTO_REALIZADO', TRUE),
(1, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
-- (1, 'NOVA_FOTO_PROGRESSO', TRUE),
(1, 'TREINO_PROXIMO_VENCIMENTO', FALSE),

-- (2, 'FEEDBACK_TREINO', FALSE),
(2, 'PAGAMENTO_REALIZADO', TRUE),
(2, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
-- (2, 'NOVA_FOTO_PROGRESSO', FALSE),
(2, 'TREINO_PROXIMO_VENCIMENTO', FALSE),

-- (3, 'FEEDBACK_TREINO', FALSE),
(3, 'PAGAMENTO_REALIZADO', TRUE),
(3, 'PLANO_PROXIMO_VENCIMENTO', FALSE),
-- (3, 'NOVA_FOTO_PROGRESSO', TRUE),
(3, 'TREINO_PROXIMO_VENCIMENTO', FALSE),

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

-- Adiciona as outras preferências para pessoa 6
(6, 'PAGAMENTO_REALIZADO', TRUE),
(6, 'PLANO_PROXIMO_VENCIMENTO', TRUE),
(6, 'TREINO_PROXIMO_VENCIMENTO', FALSE);

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

INSERT INTO vitalis.cidades (nome) VALUES
                                       ('São Paulo'),
                                       ('Campinas'),
                                       ('Santos'),
                                       ('Sorocaba'),
                                       ('São José dos Campos');

INSERT INTO vitalis.bairros (nome, cidades_id) VALUES
                                                   ('Vila Madalena', 1),
                                                   ('Cambuí', 2),
                                                   ('Gonzaga', 3),
                                                   ('Parque Campolim', 4),
                                                   ('Jardim Aquarius', 5);

INSERT INTO vitalis.personal_trainers_bairros (personal_trainers_id, bairro_id) VALUES
                                                                                    (1, 1),
                                                                                    (2, 1),
                                                                                    (3, 2),
                                                                                    (4, 3),
                                                                                    (5, 5);

INSERT INTO vitalis.evolucao_corporal (
    id, tipo, url_foto_shape, data_envio, periodo_avaliacao, alunos_id
) VALUES
      (1, 'FRONTAL', 'https://img.com/aluno6-frontal.jpg', '2025-04-01 08:00:00', 1, 6),
      (2, 'PERFIL_DIREITO', 'https://img.com/aluno7-perfil.jpg', '2025-04-02 09:00:00', 1, 7),
      (3, 'COSTAS', 'https://img.com/aluno8-costas.jpg', '2025-04-03 07:30:00', 2, 8),
      (4, 'FRONTAL', 'https://img.com/aluno9-frontal.jpg', '2025-04-04 10:00:00', 1, 9),
      (5, 'PERFIL_ESQUERDO', 'https://img.com/aluno10-perfil.jpg', '2025-04-05 06:30:00', 3, 10);