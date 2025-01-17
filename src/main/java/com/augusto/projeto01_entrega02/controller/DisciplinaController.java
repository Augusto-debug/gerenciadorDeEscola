package com.augusto.projeto01_entrega02.controller;

import com.augusto.projeto01_entrega02.domain.Disciplina;
import com.augusto.projeto01_entrega02.domain.Professor;
import com.augusto.projeto01_entrega02.repository.DisciplinaRepository;
import com.augusto.projeto01_entrega02.repository.ProfessorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/disciplina")
public class DisciplinaController {

    private final DisciplinaRepository disciplinaRepository;
    private final ProfessorRepository professorRepository;

    public static final String URL_LISTA = "disciplina/lista";
    public static final String URL_FORM = "disciplina/form";
    public static final String URL_REDIRECT_LISTA = "redirect:/disciplina";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "disciplina";
    public static final String ATRIBUTO_LISTA = "disciplinas";

    public DisciplinaController(DisciplinaRepository disciplinaRepository, ProfessorRepository professorRepository) {
        this.disciplinaRepository = disciplinaRepository;
        this.professorRepository = professorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Disciplina> disciplinas = disciplinaRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, disciplinas);
        return URL_LISTA;
    }


    @GetMapping("/novo")
    public String abrirFormNovo(Model model) {
        Disciplina disciplina = new Disciplina();
        model.addAttribute(ATRIBUTO_OBJETO, disciplina);
        model.addAttribute("professores", professorRepository.listar());
        return URL_FORM;
    }

    @GetMapping("/editar/{codigo}")
    public String abrirFormEditar(@PathVariable("codigo") Integer codigo, Model model, RedirectAttributes redirectAttributes) {
        Disciplina disciplinaBusca = disciplinaRepository.buscaPorCodigo(codigo);
        if (disciplinaBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + " não encontrado.");
            return URL_REDIRECT_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, disciplinaBusca);
            model.addAttribute("professores", professorRepository.listar());
            return URL_FORM;
        }
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute("disciplina") Disciplina disciplina, RedirectAttributes redirectAttributes) {
        Integer codigoProfessor = disciplina.getProfessor().getCodigoProfessor();
        Professor professor = professorRepository.buscaPorCodigo(codigoProfessor);
        if (professor == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Professor não encontrado.");
            return URL_REDIRECT_LISTA;
        }
        disciplina.setProfessor(professor);
        disciplinaRepository.novo(disciplina);
        redirectAttributes.addFlashAttribute("mensagem", "Disciplina salva com sucesso!");
        return URL_REDIRECT_LISTA;
    }

    @PostMapping(value = "/excluir/{id}")
    public String excluir(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        disciplinaRepository.excluir(id);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Disciplina excluída com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/editar/{id}")
    public String atualizar(
            @PathVariable("id") Integer id,
            @ModelAttribute("disciplina") Disciplina disciplina,
            RedirectAttributes redirectAttributes) {
        disciplina.setCodigoDisciplina(id);
        if (disciplina.getProfessor() != null) {
            Integer codigoProfessor = disciplina.getProfessor().getCodigoProfessor();
            Professor professor = professorRepository.buscaPorCodigo(codigoProfessor);
            if (professor != null) {
                disciplina.setProfessor(professor);
            } else {
                redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Professor inválido.");
                return URL_REDIRECT_LISTA;
            }
        }
        disciplinaRepository.atualizar(disciplina);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, disciplina.getNome() + " atualizada com sucesso.");
        return URL_REDIRECT_LISTA;
    }
}