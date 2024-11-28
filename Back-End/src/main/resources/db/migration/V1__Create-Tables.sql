CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    matricula BIGINT NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    cargo VARCHAR(50),
    id_departamento INTEGER,
    ativo BOOLEAN
);

CREATE TABLE departamentos (
    id_departamento SERIAL PRIMARY KEY,
    nome_departamento VARCHAR(100) NOT NULL,
    id_gerente INTEGER,
    FOREIGN KEY (id_gerente) REFERENCES usuarios(id) ON DELETE SET NULL
);

ALTER TABLE usuarios ADD CONSTRAINT fk_departamento FOREIGN KEY (id_departamento) REFERENCES departamentos(id_departamento) ON DELETE SET NULL;

CREATE TABLE presencas (
    id_presenca SERIAL PRIMARY KEY,
    dia DATE NOT NULL,
    hora_entrada TIME,
    hora_intervalo TIME,
    hora_retorno TIME,
    hora_saida TIME,
    justificativa VARCHAR(255),
    id_usuario INTEGER NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE CASCADE
);
