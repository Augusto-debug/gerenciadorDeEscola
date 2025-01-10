package com.augusto.projeto01_entrega02.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.augusto.projeto01_entrega02.domain.Professor;
import com.augusto.projeto01_entrega02.repository.ProfessorRepository;

@Controller
@RequestMapping("/professor")
public class ProfessorController {

    private final ProfessorRepository professorRepository;

    public static final String URL_LISTA = "professor/lista";
    public static final String URL_FORM = "professor/form";
    public static final String URL_REDIRECT_LISTA = "redirect:/professor";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "professor";
    public static final String ATRIBUTO_LISTA = "professores";

    public ProfessorController(ProfessorRepository professorRepository) {
        this.professorRepository = professorRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Professor> professores = professorRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, professores);
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model) {
        List<Professor> professoresBusca = professorRepository.buscaPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, professoresBusca);
        if (professoresBusca.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, nome + " não encontrado.");
        }
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String abrirFormNovo(Model model) {
        Professor professor = new Professor();
        model.addAttribute(ATRIBUTO_OBJETO, professor);
        return URL_FORM;
    }

    @GetMapping("/editar/{codigo}")
    public String abrirFormEditar(@PathVariable("codigo") Integer codigo, Model model, RedirectAttributes redirectAttributes) {
        Professor professorBusca = professorRepository.buscaPorCodigo(codigo);
        if (professorBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + " não encontrado.");
            return URL_REDIRECT_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, professorBusca);
            return URL_FORM;
        }
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute("professor") Professor professor, RedirectAttributes redirectAttributes) {
        professorRepository.novo(professor);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, professor.getNome() + " salvo com sucesso");
        return URL_REDIRECT_LISTA;
    }

    @PostMapping(value = "/excluir/{id}")
    public String excluir(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        professorRepository.excluir(id);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Professor excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable("id") Integer id, @ModelAttribute("professor") Professor professor, RedirectAttributes redirectAttributes) {
        professor.setCodigoProfessor(id);
        professorRepository.atualizar(professor);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, professor.getCodigoProfessor() + " atualizado com sucesso");
        return URL_REDIRECT_LISTA;
    }
}