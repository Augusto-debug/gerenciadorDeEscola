create
    database db_usuarios;

CREATE TABLE Aluno
(
    codigoAluno      INT PRIMARY KEY AUTO_INCREMENT,
    nome             VARCHAR(100) NOT NULL,
    email            VARCHAR(150) NOT NULL UNIQUE,
    numero_matricula VARCHAR(50) UNIQUE
);

INSERT INTO Aluno (nome, email, numero_matricula)
VALUES ('João Silva', 'joao.silva@email.com', '2025001'),
       ('Maria Oliveira', 'maria.oliveira@email.com', '2025002'),
       ('Ana Silva', 'ana.silva@email.com', '20231001'),
       ('Carlos Souza', 'carlos.souza@email.com', '20231002'),
       ('João Santos', 'joao.santos@email.com', NULL),
       ('Beatriz Almeida', 'beatriz.almeida@email.com', NULL);

CREATE TABLE Professor
(
    codigoProfessor INT PRIMARY KEY AUTO_INCREMENT,
    nome            VARCHAR(100) NOT NULL,
    email           VARCHAR(150) NOT NULL UNIQUE,
    titulacao       VARCHAR(100) NULL
);

INSERT INTO Professor (nome, email)
VALUES ('Carlos Silva', 'carlos.silva@example.com'),
       ('Ana Santos', 'ana.santos@example.com'),
       ('João Pereira', 'joao.pereira@example.com'),
       ('Mariana Costa', 'mariana.costa@example.com'),
       ('Pedro Oliveira', 'pedro.oliveira@example.com');

CREATE TABLE Disciplina
(
    codigoDisciplina INT PRIMARY KEY AUTO_INCREMENT,
    nome             VARCHAR(100) NOT NULL,
    codigoAluno      INT,
    codigoProfessor  INT,
    FOREIGN KEY (codigoAluno) REFERENCES Aluno (codigoAluno),
    FOREIGN KEY (codigoProfessor) REFERENCES Professor (codigoProfessor)
);

INSERT INTO Disciplina (nome, codigoAluno, codigoProfessor)
VALUES ('Matemática', 1, 1),
       ('Física', 2, 2),
       ('Química', 3, 3),
       ('História', 4, 4),
       ('Biologia', NULL, 5);