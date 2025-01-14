package com.augusto.projeto01_entrega02.repository;

import com.augusto.projeto01_entrega02.domain.Disciplina;
import com.augusto.projeto01_entrega02.domain.Professor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DisciplinaRepository {

    private final JdbcTemplate jdbcTemplate;

    public DisciplinaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Disciplina> listar() {
        String sql = "SELECT d.codigoDisciplina, d.nome, p.codigoProfessor, p.nome AS nomeProfessor, p.email " +
                "FROM Disciplina d " +
                "LEFT JOIN Professor p ON d.codigoProfessor = p.codigoProfessor";
        return jdbcTemplate.query(sql, new DisciplinaRowMapper());
    }

    public Disciplina buscaPorCodigo(Integer codigo) {
        String sql = "SELECT d.codigoDisciplina, d.nome, p.codigoProfessor, p.nome AS nomeProfessor, p.email " +
                "FROM Disciplina d " +
                "LEFT JOIN Professor p ON d.codigoProfessor = p.codigoProfessor " +
                "WHERE d.codigoDisciplina = ?";
        return jdbcTemplate.queryForObject(sql, new DisciplinaRowMapper(), codigo);
    }

    public List<Disciplina> buscaPorNome(String nome) {
        String sql = "SELECT d.codigoDisciplina, d.nome, p.codigoProfessor, p.nome AS nomeProfessor, p.email " +
                "FROM Disciplina d " +
                "LEFT JOIN Professor p ON d.codigoProfessor = p.codigoProfessor " +
                "WHERE d.nome LIKE ?";
        return jdbcTemplate.query(sql, new DisciplinaRowMapper(), "%" + nome + "%");
    }

    public void novo(Disciplina disciplina) {
        String sql = "INSERT INTO Disciplina (nome, codigoProfessor) VALUES (?, ?)";
        jdbcTemplate.update(sql,
                disciplina.getNome(),
                disciplina.getProfessor() != null ? disciplina.getProfessor().getCodigoProfessor() : null);
    }

    public void atualizar(Disciplina disciplina) {
        String sql = "UPDATE Disciplina SET nome = ?, codigoProfessor = ? WHERE codigoDisciplina = ?";
        jdbcTemplate.update(sql,
                disciplina.getNome(),
                disciplina.getProfessor() != null ? disciplina.getProfessor().getCodigoProfessor() : null,
                disciplina.getCodigoDisciplina());
    }

    public void excluir(Integer codigo) {
        String sql = "DELETE FROM Disciplina WHERE codigoDisciplina = ?";
        jdbcTemplate.update(sql, codigo);
    }

    static class DisciplinaRowMapper implements RowMapper<Disciplina> {
        @Override
        public Disciplina mapRow(ResultSet rs, int rowNum) throws SQLException {
            Disciplina disciplina = new Disciplina();
            disciplina.setCodigoDisciplina(rs.getInt("codigoDisciplina"));
            disciplina.setNome(rs.getString("nome"));

            Integer codigoProfessor = rs.getObject("codigoProfessor", Integer.class);
            if (codigoProfessor != null) {
                Professor professor = new Professor();
                professor.setCodigoProfessor(codigoProfessor);
                professor.setNome(rs.getString("nomeProfessor"));
                professor.setEmail(rs.getString("email"));
                disciplina.setProfessor(professor);
            }

            return disciplina;
        }
    }
}
