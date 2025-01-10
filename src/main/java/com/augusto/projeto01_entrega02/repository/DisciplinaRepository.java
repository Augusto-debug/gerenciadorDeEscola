package com.augusto.projeto01_entrega02.repository;

import com.augusto.projeto01_entrega02.domain.Disciplina;
import com.augusto.projeto01_entrega02.domain.Professor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DisciplinaRepository {

    private final JdbcTemplate conexao;

    public DisciplinaRepository(JdbcTemplate conexao) {
        this.conexao = conexao;
    }

    public List<Disciplina> listar() {
        String sql = """
                select d.codigoDisciplina, 
                       d.nome, 
                       d.codigoProfessor, 
                       p.nome as nomeProfessor
                from disciplina d
                left join professor p on d.codigoProfessor = p.codigoProfessor;
                """;
        return conexao.query(sql, (rs, rowNum) -> {
            Disciplina disciplina = new Disciplina();
            disciplina.setCodigoDisciplina(rs.getInt("codigoDisciplina"));
            disciplina.setNome(rs.getString("nome"));
            Professor professor = new Professor();
            professor.setCodigoProfessor(rs.getInt("codigoProfessor"));
            professor.setNome(rs.getString("nomeProfessor"));
            disciplina.setProfessor(professor);
            return disciplina;
        });
    }

    public List<Disciplina> buscaPorNome(String nome) {
        String sql = """
                select codigoDisciplina as codigoDisciplina,
                       nome,
                       codigoProfessor as professor
                from disciplina
                where lower(nome) like ?
                """;
        return conexao.query(sql, new BeanPropertyRowMapper<>(Disciplina.class), "%" + nome.toLowerCase() + "%");
    }

    public void novo(Disciplina disciplina) {
        String sql = "insert into disciplina(nome, codigoProfessor) values (?, ?)";
        conexao.update(sql, disciplina.getNome(), disciplina.getProfessor().getCodigoProfessor());
    }

    public void atualizar(Disciplina disciplina) {
        String sql = "update disciplina set nome = ?, codigoProfessor = ? where codigoDisciplina = ?";
        conexao.update(sql, disciplina.getNome(), disciplina.getProfessor().getCodigoProfessor(), disciplina.getCodigoDisciplina());
    }

    public void excluir(Integer codigo) {
        String sql = "delete from disciplina where codigoDisciplina = ?";
        conexao.update(sql, codigo);
    }

    public Disciplina buscaPorCodigo(Integer codigo) {
        String sql = """
        SELECT d.codigoDisciplina,
               d.nome,
               d.codigoProfessor,
               p.nome AS nomeProfessor
        FROM disciplina d
        LEFT JOIN professor p ON d.codigoProfessor = p.codigoProfessor
        WHERE d.codigoDisciplina = ?
    """;

        return conexao.queryForObject(sql, (rs, rowNum) -> {
            Disciplina disciplina = new Disciplina();
            disciplina.setCodigoDisciplina(rs.getInt("codigoDisciplina"));
            disciplina.setNome(rs.getString("nome"));

            Integer codigoProfessor = rs.getInt("codigoProfessor");
            if (codigoProfessor != null) {
                Professor professor = new Professor();
                professor.setCodigoProfessor(codigoProfessor);
                professor.setNome(rs.getString("nomeProfessor"));
                disciplina.setProfessor(professor);
            }

            return disciplina;
        }, codigo);
    }
}