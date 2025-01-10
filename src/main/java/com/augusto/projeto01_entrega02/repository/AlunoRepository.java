package com.augusto.projeto01_entrega02.repository;

import com.augusto.projeto01_entrega02.domain.Aluno;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AlunoRepository {

    private final JdbcTemplate conexao;

    public AlunoRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public List<Aluno> listar() {
        String sql = """
                      select codigoAluno as codigoAluno,
                             nome,
                             email,
                             numero_matricula as numeroMatricula
                      from aluno;
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Aluno.class));
    }

    public List<Aluno> buscaPorNome(String nome) {
        String sql = """
                      select codigoAluno as codigoAluno,
                             nome,
                             email,
                             numero_matricula as numeroMatricula
                      from aluno
                      where lower(nome) like ?
                      """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Aluno.class), "%" + nome.toLowerCase() + "%");
    }

    public void novo(Aluno aluno) {
        String sql = "insert into aluno(nome, email, numero_matricula) values (?, ?, ?)";
        conexao.update(sql, aluno.getNome(), aluno.getEmail(), aluno.getNumeroMatricula());
    }

    public void atualizar(Aluno aluno) {
        String sql = "update aluno set nome = ?, email = ?, numero_matricula = ? where codigoAluno = ?";
        conexao.update(sql, aluno.getNome(), aluno.getEmail(), aluno.getNumeroMatricula(), aluno.getCodigoAluno());
    }

    public void excluir(Integer codigo) {
        String sql = "delete from aluno where codigoAluno = ?";
        conexao.update(sql, codigo);
    }

    public Aluno buscaPorCodigo(Integer codigo) {
        String sql = """
                      select codigoAluno as codigoAluno,
                             nome,
                             email,
                             numero_matricula as numeroMatricula
                      from aluno
                      where codigoAluno = ?
                      """;
        return conexao.queryForObject(sql, new BeanPropertyRowMapper<>(Aluno.class), codigo);
    }
}