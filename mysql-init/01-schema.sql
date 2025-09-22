/*!40101 SET NAMES utf8mb4 */;

DROP DATABASE IF EXISTS `vitalis`;
CREATE DATABASE IF NOT EXISTS `vitalis` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
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
    `url_foto_perfil` TEXT NULL DEFAULT NULL,
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
CREATE TABLE IF NOT EXISTS `vitalis`.`personal_trainers` (
                                                             `id` INT NOT NULL,
                                                             `cref` VARCHAR(20) NOT NULL,
    `experiencia` INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `fk_personal_trainers_pessoas`
    FOREIGN KEY (`id`)
    REFERENCES `vitalis`.`pessoas` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`alunos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`alunos` (
                                                  `id` INT NOT NULL,
                                                  `peso` DECIMAL(5,2) NULL DEFAULT NULL,
    `altura` DECIMAL(3,2) NULL DEFAULT NULL,
    `nivel_atividade` ENUM('SEDENTARIO', 'LEVEMENTE_ATIVO', 'MODERADAMENTE_ATIVO', 'MUITO_ATIVO', 'EXTREMAMENTE_ATIVO') NULL DEFAULT NULL,
    `nivel_experiencia` ENUM('INICIANTE', 'INTERMEDIARIO', 'AVANCADO') NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    CONSTRAINT `alunos_ibfk_1`
    FOREIGN KEY (`id`)
    REFERENCES `vitalis`.`pessoas` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`planos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`planos` (
                                                  `id` INT NOT NULL AUTO_INCREMENT,
                                                  `personal_trainers_id` INT NOT NULL,
                                                  `nome` VARCHAR(100) NOT NULL,
    `periodo` ENUM('MENSAL', 'SEMESTRAL', 'AVULSO') NOT NULL,
    `quantidade_aulas` INT NOT NULL,
    `valor_aulas` DECIMAL(6,2) NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `personal_trainers_id` (`personal_trainers_id` ASC) VISIBLE,
    CONSTRAINT `planos_ibfk_1`
    FOREIGN KEY (`personal_trainers_id`)
    REFERENCES `vitalis`.`personal_trainers` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`planos_contratados`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`planos_contratados` (
                                                              `id` INT NOT NULL AUTO_INCREMENT,
                                                              `planos_id` INT NOT NULL,
                                                              `alunos_id` INT NOT NULL,
                                                              `status` ENUM('ATIVO', 'PENDENTE', 'INATIVO', 'EM_PROCESSO', 'CANCELADO') NOT NULL,
    `data_contratacao` DATE NULL DEFAULT NULL,
    `data_fim` DATE NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `planos_id` (`planos_id` ASC) VISIBLE,
    INDEX `alunos_id` (`alunos_id` ASC) VISIBLE,
    CONSTRAINT `planos_contratados_ibfk_1`
    FOREIGN KEY (`planos_id`)
    REFERENCES `vitalis`.`planos` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `planos_contratados_ibfk_2`
    FOREIGN KEY (`alunos_id`)
    REFERENCES `vitalis`.`alunos` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`exercicios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`exercicios` (
                                                      `id` INT NOT NULL AUTO_INCREMENT,
                                                      `personal_id` INT,
                                                      `nome` VARCHAR(100) NOT NULL,
    `grupo_muscular` ENUM('PEITORAL', 'COSTAS', 'PERNAS', 'OMBRO', 'BRACO', 'CORE', 'CARDIO') NOT NULL,
    `url_video` TEXT NULL DEFAULT NULL,
    `observacoes` TEXT NULL DEFAULT NULL,
    `favorito` BOOLEAN NULL DEFAULT '0',
    `origem` ENUM('BIBLIOTECA', 'PERSONAL') NOT NULL DEFAULT 'PERSONAL',
    PRIMARY KEY (`id`),
    INDEX `fk_exercicios_personal_trainers1_idx` (`personal_id` ASC) VISIBLE,
    CONSTRAINT `fk_exercicios_personal_trainers1`
    FOREIGN KEY (`personal_id`)
    REFERENCES `vitalis`.`personal_trainers` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `vitalis`.`treinos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`treinos` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `nome` VARCHAR(100) NOT NULL,
    `descricao` TEXT NULL DEFAULT NULL,
    `favorito` BOOLEAN NULL DEFAULT '0',
    `personal_id` INT NULL DEFAULT NULL,
    `origem` ENUM('BIBLIOTECA', 'PERSONAL') NULL,
    `grau_dificuldade` ENUM('INICIANTE', 'INTERMEDIARIO', 'AVANCADO') NULL,
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
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`treinos_exercicios` (
                                                              `id` INT NOT NULL AUTO_INCREMENT,
                                                              `treinos_id` INT NULL,
                                                              `exercicios_id` INT NULL COMMENT 'Referência direta ao exercício da biblioteca',
                                                              `carga` DECIMAL(5,2) NOT NULL,
    `repeticoes` INT NOT NULL,
    `series` INT NOT NULL,
    `descanso` INT NOT NULL COMMENT 'Descanso em segundos',
    `observacoes_personalizadas` TEXT NULL DEFAULT NULL,
    `data_modificacao` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_exercicio_idx` (`exercicios_id` ASC) VISIBLE,
    INDEX `fk_alunos_treinos_exercicios_treinos1_idx` (`treinos_id` ASC) VISIBLE,
    CONSTRAINT `fk_alunos_treinos_exercicios_exercicio`
    FOREIGN KEY (`exercicios_id`)
    REFERENCES `vitalis`.`exercicios` (`id`)
                                                                ON DELETE CASCADE,
    CONSTRAINT `fk_alunos_treinos_exercicios_treinos1`
    FOREIGN KEY (`treinos_id`)
    REFERENCES `vitalis`.`treinos` (`id`)
                                                                ON DELETE NO ACTION
                                                                ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`aulas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`aulas` (
                                                 `id` INT NOT NULL AUTO_INCREMENT,
                                                 `data_horario_inicio` DATETIME NULL DEFAULT NULL COMMENT 'Quando o treino efetivamente começou',
                                                 `data_horario_fim` DATETIME NULL DEFAULT NULL COMMENT 'Quando o treino foi encerrado',
                                                 `status` ENUM('AGENDADO', 'REALIZADO', 'CANCELADO', 'REAGENDADO', 'RASCUNHO') NOT NULL DEFAULT 'AGENDADO',
    `planos_contratados_id` INT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_aulas_planos_contratados1_idx` (`planos_contratados_id` ASC) VISIBLE,
    CONSTRAINT `fk_aulas_planos_contratados1`
    FOREIGN KEY (`planos_contratados_id`)
    REFERENCES `vitalis`.`planos_contratados` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`aulas_treinos_exercicios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`aulas_treinos_exercicios` (
                                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                                    `aulas_id` INT NULL,
                                                                    `treinos_exercicios_id` INT NULL,
                                                                    `ordem` INT NOT NULL,
                                                                    `carga` DECIMAL(5,2) NOT NULL,
    `repeticoes` INT NOT NULL,
    `series` INT NOT NULL,
    `descanso` INT NOT NULL,
    `observacoes_personalizadas` TEXT NULL,
    PRIMARY KEY (`id`),
    INDEX idx_sessao (`treinos_exercicios_id`),
    INDEX idx_exercicio (`aulas_id`),
    UNIQUE INDEX uq_aulas_exercicio (aulas_id, treinos_exercicios_id) VISIBLE,
    CONSTRAINT fk_aulas FOREIGN KEY (aulas_id)
    REFERENCES `vitalis`.`aulas` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT fk_treinos_exercicios FOREIGN KEY (treinos_exercicios_id)
    REFERENCES `vitalis`.`treinos_exercicios` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`execucoes_exercicios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`execucoes_exercicios` (
                                                                `id` INT NOT NULL AUTO_INCREMENT,
                                                                `carga_executada` DECIMAL(5,2) NULL DEFAULT NULL,
    `repeticoes_executadas` INT NULL DEFAULT NULL,
    `series_executadas` INT NULL DEFAULT NULL,
    `descanso_executado` INT NULL,
    `aulas_treinos_exercicios_id` INT NULL,
    `finalizado` BOOLEAN NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    INDEX `fk_execucoes_exercicios_sessao_treinos_exercicios1_idx` (`aulas_treinos_exercicios_id` ASC) VISIBLE,
    CONSTRAINT `fk_execucoes_exercicios_sessao_treinos_exercicios1`
    FOREIGN KEY (`aulas_treinos_exercicios_id`)
    REFERENCES `vitalis`.`aulas_treinos_exercicios` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`anamnese`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`anamnese` (
                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                    `alunos_id` INT NOT NULL,
                                                    `objetivo_treino` TEXT NOT NULL,
                                                    `lesao` BOOLEAN NOT NULL,
                                                    `lesao_descricao` TEXT NULL DEFAULT NULL,
                                                    `frequencia_treino` ENUM('1', '2', '3', '4', '5', '6', '7') NOT NULL,
    `experiencia` BOOLEAN NOT NULL,
    `experiencia_descricao` TEXT NULL DEFAULT NULL,
    `desconforto` BOOLEAN NOT NULL,
    `desconforto_descricao` TEXT NULL DEFAULT NULL,
    `fumante` BOOLEAN NOT NULL,
    `proteses` BOOLEAN NOT NULL,
    `proteses_descricao` TEXT NULL DEFAULT NULL,
    `doenca_metabolica` BOOLEAN NOT NULL,
    `doenca_metabolica_descricao` TEXT NULL DEFAULT NULL,
    `deficiencia` BOOLEAN NOT NULL,
    `deficiencia_descricao` TEXT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `alunos_id` (`alunos_id` ASC) VISIBLE,
    INDEX `fk_anamnese_alunos1_idx` (`alunos_id` ASC) VISIBLE,
    CONSTRAINT `fk_anamnese_alunos1`
    FOREIGN KEY (`alunos_id`)
    REFERENCES `vitalis`.`alunos` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`avaliacoes_personal_trainers`
-- -----------------------------------------------------

CREATE TABLE IF NOT EXISTS `vitalis`.`avaliacoes_personal_trainers` (
                                                                        `id` INT NOT NULL AUTO_INCREMENT,
                                                                        `aluno_id` INT NOT NULL,
                                                                        `personal_trainer_id` INT NOT NULL,
                                                                        `nota` DECIMAL(2,1) NOT NULL CHECK (nota >= 1.0 AND nota <= 5.0),
    `comentario` TEXT NULL,
    `data_avaliacao` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `idx_personal_trainer` (`personal_trainer_id` ASC),
    INDEX `idx_aluno_avaliacao` (`aluno_id` ASC),
    INDEX `idx_nota_data` (`nota` ASC, `data_avaliacao` DESC),
    CONSTRAINT `fk_avaliacao_aluno`
    FOREIGN KEY (`aluno_id`)
    REFERENCES `vitalis`.`alunos` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `fk_avaliacao_personal`
    FOREIGN KEY (`personal_trainer_id`)
    REFERENCES `vitalis`.`personal_trainers` (`id`)
    ON DELETE CASCADE
    );

-- -----------------------------------------------------
-- Table `vitalis`.`cidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`cidades` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `nome` VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`bairros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`bairros` (
                                                   `id` INT NOT NULL AUTO_INCREMENT,
                                                   `nome` VARCHAR(45) NOT NULL,
    `cidades_id` INT NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_bairros_cidades1_idx` (`cidades_id` ASC) VISIBLE,
    CONSTRAINT `fk_bairros_cidades1`
    FOREIGN KEY (`cidades_id`)
    REFERENCES `vitalis`.`cidades` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`personal_trainers_bairros`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`personal_trainers_bairros` (
                                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                                     `personal_trainers_id` INT NULL DEFAULT NULL,
                                                                     `bairro_id` INT NULL DEFAULT NULL,
                                                                     PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_personal_bairro` (`personal_trainers_id` ASC, `bairro_id` ASC) VISIBLE,
    INDEX `fk_ptb_bairro` (`bairro_id` ASC) VISIBLE,
    CONSTRAINT `fk_ptb_bairro`
    FOREIGN KEY (`bairro_id`)
    REFERENCES `vitalis`.`bairros` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `fk_ptb_personal`
    FOREIGN KEY (`personal_trainers_id`)
    REFERENCES `vitalis`.`personal_trainers` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`feedbacks`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`feedbacks` (
                                                     `id` INT NOT NULL AUTO_INCREMENT,
                                                     `aulas_id` INT NOT NULL,
                                                     `pessoas_id` INT NOT NULL,
                                                     `descricao` TEXT NOT NULL,
                                                     `data_criacao` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                                     `tipo_autor` ENUM('PERSONAL', 'ALUNO') NOT NULL,
    `intensidade` ENUM('MUITO_LEVE', 'LEVE', 'MODERADO', 'INTENSA', 'MUITO_INTENSA') NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_feedbacks_aulas1_idx` (`aulas_id` ASC) VISIBLE,
    INDEX `fk_feedbacks_pessoas1_idx` (`pessoas_id` ASC) VISIBLE,
    CONSTRAINT `fk_feedbacks_aulas1`
    FOREIGN KEY (`aulas_id`)
    REFERENCES `vitalis`.`aulas` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `fk_feedbacks_pessoas1`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `vitalis`.`pessoas` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`especialidades` (
                                                          `id` INT NOT NULL AUTO_INCREMENT,
                                                          `nome` VARCHAR(50) NOT NULL,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`personal_trainers_especialidades`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`personal_trainers_especialidades` (
                                                                            `personal_trainers_id` INT NOT NULL,
                                                                            `especialidades_id` INT NOT NULL,
                                                                            PRIMARY KEY (`personal_trainers_id`, `especialidades_id`),
    INDEX `especialidades_id` (`especialidades_id` ASC) VISIBLE,
    CONSTRAINT `personal_trainers_especialidades_ibfk_1`
    FOREIGN KEY (`personal_trainers_id`)
    REFERENCES `vitalis`.`personal_trainers` (`id`)
    ON DELETE CASCADE,
    CONSTRAINT `personal_trainers_especialidades_ibfk_2`
    FOREIGN KEY (`especialidades_id`)
    REFERENCES `vitalis`.`especialidades` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`evolucao_corporal`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`evolucao_corporal` (
                                                             `id` INT NOT NULL AUTO_INCREMENT,
                                                             `tipo` ENUM('FRONTAL', 'PERFIL_DIREITO', 'PERFIL_ESQUERDO', 'COSTAS') NOT NULL,
    `url_foto_shape` TEXT NULL DEFAULT NULL,
    `data_envio` DATETIME NULL DEFAULT NULL,
    `periodo_avaliacao` INT NOT NULL,
    `alunos_id` INT NULL DEFAULT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_evolucao_corporal_alunos1_idx` (`alunos_id` ASC) VISIBLE,
    CONSTRAINT `fk_evolucao_corporal_alunos1`
    FOREIGN KEY (`alunos_id`)
    REFERENCES `vitalis`.`alunos` (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`notificacoes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`notificacoes` (
                                                        `id` INT NOT NULL AUTO_INCREMENT,
                                                        `pessoas_id` INT NULL DEFAULT NULL,
                                                        `tipo` ENUM('FEEDBACK_TREINO', 'PAGAMENTO_REALIZADO', 'PLANO_PROXIMO_VENCIMENTO', 'NOVA_FOTO_PROGRESSO', 'TREINO_PROXIMO_VENCIMENTO') NOT NULL,
    `titulo` VARCHAR(200) NOT NULL,
    `visualizada` BOOLEAN NULL DEFAULT '0',
    `data_criacao` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    INDEX `fk_notificacao_usuario` (`pessoas_id` ASC) VISIBLE,
    CONSTRAINT `fk_notificacao_usuario`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `vitalis`.`pessoas` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`preferencias_notificacao`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`preferencias_notificacao` (
                                                                    `id` INT NOT NULL AUTO_INCREMENT,
                                                                    `pessoas_id` INT NULL DEFAULT NULL,
                                                                    `tipo` ENUM('FEEDBACK_TREINO', 'PAGAMENTO_REALIZADO', 'PLANO_PROXIMO_VENCIMENTO', 'NOVA_FOTO_PROGRESSO', 'TREINO_PROXIMO_VENCIMENTO') NOT NULL,
    `ativada` BOOLEAN NULL DEFAULT '1',
    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_usuario_tipo` (`pessoas_id` ASC, `tipo` ASC) VISIBLE,
    CONSTRAINT `fk_preferencia_usuario`
    FOREIGN KEY (`pessoas_id`)
    REFERENCES `vitalis`.`pessoas` (`id`)
    ON DELETE CASCADE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

-- -----------------------------------------------------
-- Table `vitalis`.`esqueci_senha`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `vitalis`.`esqueci_senha` (
                                                         `id` INT NOT NULL AUTO_INCREMENT,
                                                         `email` VARCHAR(255) NOT NULL,
    `token` VARCHAR(255) NOT NULL,
    `data_expiracao` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;