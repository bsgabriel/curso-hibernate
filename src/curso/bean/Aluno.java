package curso.bean;

import java.util.Objects;

public class Aluno {
    private Integer codAluno;
    private String nome;
    private String cidade;
    private String telefone;
    private String curso;

    public Integer getCodAluno() {
        return codAluno;
    }

    public void setCodAluno(Integer codAluno) {
        this.codAluno = codAluno;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Aluno aluno = (Aluno) o;

        if (!Objects.equals(codAluno, aluno.codAluno))
            return false;

        if (!Objects.equals(nome, aluno.nome))
            return false;

        if (!Objects.equals(cidade, aluno.cidade))
            return false;

        if (!Objects.equals(telefone, aluno.telefone))
            return false;

        if (!Objects.equals(curso, aluno.curso))
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = codAluno != null ? codAluno.hashCode() : 0;
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (cidade != null ? cidade.hashCode() : 0);
        result = 31 * result + (telefone != null ? telefone.hashCode() : 0);
        result = 31 * result + (curso != null ? curso.hashCode() : 0);
        return result;
    }
}
