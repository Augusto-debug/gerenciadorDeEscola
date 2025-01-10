package com.augusto.projeto01_entrega02.domain;

import java.util.Objects;

public class Aluno {
    private Integer codigoAluno;
    private String nome;
    private String email;
    private String numeroMatricula;

    public Aluno() {
    }

    public Aluno(Integer codigoAluno, String nome, String email, String numeroMatricula) {
        this.codigoAluno = codigoAluno;
        this.nome = nome;
        this.email = email;
        this.numeroMatricula = numeroMatricula;
    }

    public Integer getCodigoAluno() {
        return codigoAluno;
    }

    public void setCodigoAluno(int codigoAluno) {
        this.codigoAluno = codigoAluno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroMatricula() {
        return numeroMatricula;
    }

    public void setNumeroMatricula(String numeroMatricula) {
        this.numeroMatricula = numeroMatricula;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Aluno aluno = (Aluno) o;
        return codigoAluno == aluno.codigoAluno;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigoAluno);
    }
}
