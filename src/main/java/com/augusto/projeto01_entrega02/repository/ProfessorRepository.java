package com.augusto.projeto01_entrega02.repository;

import com.augusto.projeto01_entrega02.domain.Professor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProfessorRepository {

    private final JdbcTemplate conexao;

    public ProfessorRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public List<Professor> listar() {
        String sql = """
                      select codigoProfessor ,
                             nome,
                             email
                      from professor;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Professor.class));
    }

    public List<Professor> buscaPorNome(String nome) {
        String sql = """
                      select codigoProfessor ,
                             nome,
                             email
                      from professor
                      where lower(nome) like ?
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Professor.class), "%" + nome.toLowerCase() + "%");
    }

    public void novo(Professor professor) {
        String sql = "insert into professor(nome, email) values (?, ?)";
        conexao.update(sql, professor.getNome(), professor.getEmail());
    }

    public void atualizar(Professor professor) {
        String sql = "update professor set nome = ?, email = ?, titulacao = ?  where codigoProfessor = ?";
        conexao.update(sql, professor.getNome(), professor.getEmail(), professor.getTitulacao(), professor.getCodigoProfessor());
    }

    public void excluir(Integer codigo) {
        String sql = "delete from professor where codigoProfessor = ?";
        conexao.update(sql, codigo);
    }

    public Professor buscaPorCodigo(Integer codigo) {
        String sql = """
                      select codigoProfessor,
                             nome,
                             email,
                             titulacao
                      from professor
                      where codigoProfessor = ?
                      """;
        return conexao.queryForObject(sql, new BeanPropertyRowMapper<>(Professor.class), codigo);
    }
}