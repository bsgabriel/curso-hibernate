CREATE TABLE ALUNOS (
	cod_aluno INTEGER PRIMARY KEY,
	nome VARCHAR(40),
	curso VARCHAR(100),
	cidade VARCHAR(100)
);

CREATE TABLE telefones_alunos (
	cod_aluno INTEGER NOT NULL,
	telefone VARCHAR(11) NOT NULL,
	CONSTRAINT pk_aluno_telefone PRIMARY KEY (cod_aluno, telefone),
	CONSTRAINT fk_aluno_telefone FOREIGN KEY (cod_aluno) REFERENCES alunos (cod_aluno)
);