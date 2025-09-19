USE `vitalis`;

-- -----------------------------------------------------
-- Table `vitalis`.`pessoas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`pessoas` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `nome` VARCHAR(100) NOT NULL,
    `email` VARCHAR(100) NOT NULL,
    `senha` VARCHAR(255) NOT NULL,
    `celular` VARCHAR(11) NULL DEFAULT NULL,
    `url_foto_perfil` TEXT NULL,
    `data_nascimento` DATE NULL DEFAULT NULL,
    `genero` ENUM('HOMEM_CISGENERO', 'HOMEM_TRANSGENERO', 'MULHER_CISGENERO', 'MULHER_TRANSGENERO', 'NAO_BINARIO') NOT NULL,
    `data_cadastro` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email` (`email` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;



-- -----------------------------------------------------
-- Table `vitalis`.`personal_trainers`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS personal_trainers (
                                                 id INT NOT NULL,
                                                 cref VARCHAR(20) NOT NULL,
    experiencia INT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_personal_trainers_pessoas FOREIGN KEY (id)
    REFERENCES pessoas (id) ON DELETE CASCADE
    );

-- -----------------------------------------------------
-- Table `vitalis`.`alunos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vitalis.alunos (
                                              id INT NOT NULL,
                                              peso DECIMAL(5,2) NULL,
    altura DECIMAL(3,2) NULL,
    nivel_atividade ENUM("SEDENTARIO", "LEVEMENTE_ATIVO", "MODERADAMENTE_ATIVO", "MUITO_ATIVO", "EXTREMAMENTE_ATIVO") NULL,
    nivel_experiencia ENUM('INICIANTE', 'INTERMEDIARIO', 'AVANCADO') NULL,
    PRIMARY KEY (id),
    CONSTRAINT alunos_ibfk_1 FOREIGN KEY (id) REFERENCES vitalis.pessoas (id) ON DELETE CASCADE
    );

-- -----------------------------------------------------
-- Table `vitalis`.`especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS especialidades (
                                              id INT PRIMARY KEY AUTO_INCREMENT,
                                              nome VARCHAR(50) NOT NULL
    );


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

-- -----------------------------------------------------
-- Table `vitalis`.`notificacoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS notificacoes (
                                            id INT PRIMARY KEY AUTO_INCREMENT,
                                            pessoas_id INT NULL,
                                            tipo ENUM(
                                            'FEEDBACK_TREINO',
                                            'PAGAMENTO_REALIZADO',
                                            'PLANO_PROXIMO_VENCIMENTO',
                                            'NOVA_FOTO_PROGRESSO',
                                            'TREINO_PROXIMO_VENCIMENTO'
) NOT NULL,
    titulo VARCHAR(200) NOT NULL,
    visualizada BOOLEAN DEFAULT FALSE,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_notificacao_usuario FOREIGN KEY (pessoas_id)
    REFERENCES pessoas(id)
    ON DELETE CASCADE
    );
-- -----------------------------------------------------
-- Table `vitalis`.`preferencias_notificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS preferencias_notificacao (
                                                        id INT PRIMARY KEY AUTO_INCREMENT,
                                                        pessoas_id INT NULL,
                                                        tipo ENUM(
                                                        'FEEDBACK_TREINO',
                                                        'PAGAMENTO_REALIZADO',
                                                        'PLANO_PROXIMO_VENCIMENTO',
                                                        'NOVA_FOTO_PROGRESSO',
                                                        'TREINO_PROXIMO_VENCIMENTO'
) NOT NULL,
    ativada BOOLEAN DEFAULT TRUE,

    CONSTRAINT fk_preferencia_usuario FOREIGN KEY (pessoas_id)
    REFERENCES pessoas(id)
    ON DELETE CASCADE,

    CONSTRAINT uk_usuario_tipo UNIQUE (pessoas_id, tipo)
    );


-- -----------------------------------------------------
-- Table `vitalis`.`exercicios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vitalis.exercicios (
                                                  id INT NOT NULL AUTO_INCREMENT,
                                                  nome VARCHAR(100) NOT NULL,
    grupo_muscular ENUM('PEITORAL', 'COSTAS', 'PERNAS', 'OMBRO', 'BRACO', 'CORE', 'CARDIO') NOT NULL,
    url_video TEXT NOT NULL,
    observacoes TEXT NULL,
    favorito BOOLEAN NULL DEFAULT FALSE,
    origem ENUM('BIBLIOTECA', 'PERSONAL') NOT NULL,
    PRIMARY KEY (id)
    );


-- -----------------------------------------------------
-- Table `vitalis`.`treinos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`treinos` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `nome` VARCHAR(100) NOT NULL,
    `descricao` TEXT NULL DEFAULT NULL,
    `favorito` BOOLEAN NULL DEFAULT FALSE,
    `personal_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `personal_id` (`personal_id` ASC) VISIBLE,
    CONSTRAINT `treinos_ibfk_1`
    FOREIGN KEY (`personal_id`)
    REFERENCES `vitalis`.`personal_trainers` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`treinos_exercicios`
-- -----------------------------------------------------g

CREATE TABLE IF NOT EXISTS vitalis.treinos_exercicios (
                                                          id INT NOT NULL AUTO_INCREMENT,
                                                          treino_id INT,
                                                          exercicio_id INT,
                                                          carga DECIMAL(5,2) NOT NULL,
    repeticoes INT NOT NULL,
    series INT NOT NULL,
    descanso INT NOT NULL COMMENT 'Descanso em segundos',
    data_hora_criacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_hora_modificacao TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    origem ENUM('BIBLIOTECA', 'PERSONAL') NOT NULL,
    grau_dificuldade ENUM("INICIANTE", "INTERMEDIARIO", "AVANCADO") NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX treino_id (treino_id ASC, exercicio_id ASC),
    INDEX exercicio_id (exercicio_id ASC),
    CONSTRAINT treinos_exercicios_ibfk_1 FOREIGN KEY (treino_id) REFERENCES vitalis.treinos (id) ON DELETE CASCADE,
    CONSTRAINT treinos_exercicios_ibfk_2 FOREIGN KEY (exercicio_id) REFERENCES vitalis.exercicios (id) ON DELETE CASCADE
    );


-- Table `vitalis`.`anamnese`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS vitalis.anamnese (
                                                id INT NOT NULL AUTO_INCREMENT,
                                                alunos_id INT NOT NULL UNIQUE,
                                                objetivo_treino TEXT NOT NULL,
                                                lesao BOOLEAN NOT NULL,
                                                lesao_descricao TEXT NULL,
                                                frequencia_treino ENUM('1', '2', '3', '4', '5', '6', '7') NOT NULL,
    experiencia BOOLEAN NOT NULL,
    experiencia_descricao TEXT NULL,
    desconforto BOOLEAN NOT NULL,
    desconforto_descricao TEXT NULL,
    fumante BOOLEAN NOT NULL,
    proteses BOOLEAN NOT NULL,
    proteses_descricao TEXT NULL,
    doenca_metabolica BOOLEAN NOT NULL,
    doenca_metabolica_descricao TEXT NULL,
    deficiencia BOOLEAN NOT NULL,
    deficiencia_descricao TEXT NULL,
    PRIMARY KEY (id),
    INDEX fk_anamnese_alunos1_idx (alunos_id ASC),
    CONSTRAINT fk_anamnese_alunos1 FOREIGN KEY (alunos_id) REFERENCES vitalis.alunos (id)
    );

-- -----------------------------------------------------
-- Table `vitalis`.`alunos_treinos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`alunos_treinos` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `alunos_id` INT,
                                                          `treinos_exercicios_id` INT,
                                                          `dias_semana` JSON NULL,
                                                          `data_vencimento` DATE NOT NULL COMMENT 'Data do vencimento de um treino',
                                                          PRIMARY KEY (`id`),
    INDEX `aluno_id` (`alunos_id` ASC) VISIBLE,
    INDEX `fk_alunos_treinos_treinos_exercicios1_idx` (`treinos_exercicios_id` ASC) VISIBLE,
    CONSTRAINT `alunos_treinos_ibfk_1`
    FOREIGN KEY (`alunos_id`)
    REFERENCES `vitalis`.`alunos` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `fk_alunos_treinos_treinos_exercicios1`
    FOREIGN KEY (`treinos_exercicios_id`)
    REFERENCES `vitalis`.`treinos_exercicios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `vitalis`.`treinos_finalizados` (
                                                               id INT NOT NULL AUTO_INCREMENT,
                                                               data_horario_inicio DATETIME NOT NULL,
                                                               data_horario_fim DATETIME NULL DEFAULT NULL COMMENT 'Data de término do treino',
                                                               alunos_treinos_id INT,

                                                               PRIMARY KEY (id),
    CONSTRAINT `alunos_treinos_finalizados_ibfk_1`
    FOREIGN KEY (`alunos_treinos_id`)
    REFERENCES `vitalis`.`alunos_treinos` (`id`)
    );

-- -----------------------------------------------------
-- Table `vitalis`.`planos`
-- Tabela de planos criados pelo personal trainer (modelos de plano)
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vitalis.planos (
                                              id INT NOT NULL AUTO_INCREMENT,
                                              personal_trainers_id INT NOT NULL,
                                              nome VARCHAR(100) NOT NULL,
    periodo ENUM('MENSAL', 'SEMESTRAL', 'AVULSO') NOT NULL,
    quantidade_aulas INT NOT NULL,
    valor_aulas DECIMAL(6,2) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (personal_trainers_id)
    REFERENCES vitalis.personal_trainers(id)
    ON DELETE CASCADE
    );

-- -----------------------------------------------------
-- Table `vitalis`.`planos`
-- Tabela de planos contratados pelos alunos
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vitalis.planos_contratados (
                                                          id INT NOT NULL AUTO_INCREMENT,
                                                          planos_id INT NOT NULL,
                                                          alunos_id INT NOT NULL,
                                                          status ENUM('ATIVO', 'PENDENTE', 'INATIVO', 'EM_PROCESSO', 'CANCELADO') NOT NULL,
    data_contratacao DATE NULL,
    data_fim DATE NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (planos_id)
    REFERENCES vitalis.planos(id)
    ON DELETE CASCADE,
    FOREIGN KEY (alunos_id)
    REFERENCES vitalis.alunos(id)
    ON DELETE CASCADE
    );

-- -----------------------------------------------------
-- Table `vitalis`.`cidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vitalis.cidades (
                                               id INT NOT NULL AUTO_INCREMENT,
                                               nome VARCHAR(45) NOT NULL,
    PRIMARY KEY (id)
    ) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vitalis`.`bairros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vitalis.bairros (
                                               id INT NOT NULL AUTO_INCREMENT,
                                               nome VARCHAR(45) NOT NULL,
    cidades_id INT NOT NULL,
    PRIMARY KEY (id),
    INDEX fk_bairros_cidades1_idx (cidades_id ASC) VISIBLE,
    CONSTRAINT fk_bairros_cidades1
    FOREIGN KEY (cidades_id)
    REFERENCES vitalis.cidades (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
    ) ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vitalis`.`personal_trainers_bairros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS vitalis.personal_trainers_bairros (
                                                                 id INT NOT NULL AUTO_INCREMENT,
                                                                 personal_trainers_id INT NULL,
                                                                 bairro_id INT NULL,
                                                                 PRIMARY KEY (id),
    UNIQUE KEY uk_personal_bairro (personal_trainers_id, bairro_id),
    CONSTRAINT fk_ptb_personal FOREIGN KEY (personal_trainers_id)
    REFERENCES vitalis.personal_trainers (id)
    ON DELETE CASCADE,
    CONSTRAINT fk_ptb_bairro FOREIGN KEY (bairro_id)
    REFERENCES vitalis.bairros (id)
    ON DELETE CASCADE
    ) ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `vitalis`.`feedbacks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`feedbacks` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `titulo` VARCHAR(45) NULL,
    `descricao` TEXT NULL,
    `data_criacao` DATETIME NULL,
    `alunos_treinos_id` INT,
    PRIMARY KEY (`id`),
    INDEX `fk_feedbacks_alunos_treinos1_idx` (`alunos_treinos_id` ASC),
    CONSTRAINT `fk_feedbacks_alunos_treinos1`
    FOREIGN KEY (`alunos_treinos_id`)
    REFERENCES `vitalis`.`alunos_treinos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `vitalis`.`comentarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`comentarios` (
                                                       `id` INT NOT NULL AUTO_INCREMENT,
                                                       `feedbacks_id` INT,
                                                       `pessoas_id` INT,
                                                       `descricao` TEXT NOT NULL,
                                                       `data_criacao` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                       `tipo_autor` ENUM('PERSONAL', 'ALUNO') NOT NULL,
    `intensidade` ENUM('MUITO_LEVE', 'LEVE', 'MODERADO', 'INTENSA', 'MUITO_INTENSA') NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_comentarios_topicos1_idx` (`feedbacks_id` ASC) VISIBLE,
    INDEX `fk_comentarios_pessoas1_idx` (`pessoas_id` ASC) VISIBLE,
    CONSTRAINT `fk_comentarios_topicos1`
    FOREIGN KEY (`feedbacks_id`)
    REFERENCES `vitalis`.`feedbacks` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `fk_comentarios_pessoas1`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `vitalis`.`pessoas` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `vitalis`.`evolucao_corporal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`evolucao_corporal` (
                                                             `id` INT NOT NULL AUTO_INCREMENT,
                                                             `tipo` ENUM("FRONTAL", "PERFIL_DIREITO", "PERFIL_ESQUERDO", "COSTAS") NOT NULL,
    `url_foto_shape` TEXT NULL,
    `data_envio` DATETIME NULL,
    `periodo_avaliacao` INT NOT NULL,
    `alunos_id` INT,
    PRIMARY KEY (`id`),
    INDEX `fk_evolucao_corporal_alunos1_idx` (`alunos_id` ASC) VISIBLE,
    CONSTRAINT `fk_evolucao_corporal_alunos1`
    FOREIGN KEY (`alunos_id`)
    REFERENCES `vitalis`.`alunos` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB;


CREATE TABLE IF NOT EXISTS esqueci_senha (
                                             id INT AUTO_INCREMENT PRIMARY KEY,
                                             email VARCHAR(255) NOT NULL,
    token VARCHAR(255) NOT NULL,
    data_expiracao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
    );