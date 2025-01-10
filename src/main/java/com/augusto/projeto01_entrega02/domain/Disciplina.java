package com.augusto.projeto01_entrega02.domain;

import java.util.List;
import java.util.Objects;

public class Disciplina {
    private Integer codigoDisciplina;
    private String nome;
    private Professor professor;
    private List<Aluno> alunos;

    public Disciplina() {
    }

    public Disciplina(Integer codigoDisciplina, String nome, Professor professor, List<Aluno> alunos) {
        this.codigoDisciplina = codigoDisciplina;
        this.nome = nome;
        this.professor = professor;
        this.alunos = alunos;
    }

    public Integer getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(Integer codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Disciplina that = (Disciplina) o;
        return Objects.equals(codigoDisciplina, that.codigoDisciplina);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigoDisciplina);
    }
}
