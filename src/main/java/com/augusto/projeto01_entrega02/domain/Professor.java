package com.augusto.projeto01_entrega02.domain;

import java.util.Objects;

public class Professor {
    private Integer codigoProfessor;
    private String nome;
    private String email;
    private String titulacao;

    public Professor() {
    }

    public Professor(Integer codigoProfessor, String nome, String email, String titulacao) {
        this.codigoProfessor = codigoProfessor;
        this.nome = nome;
        this.email = email;
        this.titulacao = titulacao;
    }

    public Integer getCodigoProfessor() {
        return codigoProfessor;
    }

    public void setCodigoProfessor(Integer codigoProfessor) {
        this.codigoProfessor = codigoProfessor;
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

    public String getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(String titulacao) {
        this.titulacao = titulacao;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Professor professor = (Professor) o;
        return Objects.equals(codigoProfessor, professor.codigoProfessor);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(codigoProfessor);
    }
}