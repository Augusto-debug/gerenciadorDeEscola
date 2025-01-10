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

import com.augusto.projeto01_entrega02.domain.Aluno;
import com.augusto.projeto01_entrega02.repository.AlunoRepository;

@Controller
@RequestMapping("/aluno")
public class AlunoController {

    private final AlunoRepository alunoRepository;

    public static final String URL_LISTA = "aluno/lista";
    public static final String URL_FORM = "aluno/form";
    public static final String URL_REDIRECT_LISTA = "redirect:/aluno";

    public static final String ATRIBUTO_MENSAGEM = "mensagem";
    public static final String ATRIBUTO_OBJETO = "aluno";
    public static final String ATRIBUTO_LISTA = "alunos";

    public AlunoController(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @GetMapping
    public String listar(Model model) {
        List<Aluno> alunos = alunoRepository.listar();
        model.addAttribute(ATRIBUTO_LISTA, alunos);
        return URL_LISTA;
    }

    @GetMapping("/buscar")
    public String buscarPorNome(@RequestParam("nome") String nome, Model model) {
        List<Aluno> alunosBusca = alunoRepository.buscaPorNome(nome);
        model.addAttribute(ATRIBUTO_LISTA, alunosBusca);
        if (alunosBusca.isEmpty()) {
            model.addAttribute(ATRIBUTO_MENSAGEM, nome + " não encontrado.");
        }
        return URL_LISTA;
    }

    @GetMapping("/novo")
    public String abrirFormNovo(Model model) {
        Aluno aluno = new Aluno();
        model.addAttribute(ATRIBUTO_OBJETO, aluno);
        return URL_FORM;
    }

    @GetMapping("/editar/{codigo}")
    public String abrirFormEditar(@PathVariable("codigo") Integer codigo, Model model, RedirectAttributes redirectAttributes) {
        Aluno alunoBusca = alunoRepository.buscaPorCodigo(codigo);
        if (alunoBusca == null) {
            redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, codigo + " não encontrado.");
            return URL_REDIRECT_LISTA;
        } else {
            model.addAttribute(ATRIBUTO_OBJETO, alunoBusca);
            return URL_FORM;
        }
    }

    @PostMapping("/novo")
    public String salvar(@ModelAttribute("aluno") Aluno aluno, RedirectAttributes redirectAttributes) {
        alunoRepository.novo(aluno);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, aluno.getNome() + " salvo com sucesso");
        return URL_REDIRECT_LISTA;
    }

    @PostMapping(value = "/excluir/{id}")
    public String excluir(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        alunoRepository.excluir(id);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, "Aluno excluído com sucesso.");
        return URL_REDIRECT_LISTA;
    }

    @PostMapping("/editar/{id}")
    public String atualizar(@PathVariable("id") Integer id, @ModelAttribute("aluno") Aluno aluno, RedirectAttributes redirectAttributes) {
        aluno.setCodigoAluno(id);
        alunoRepository.atualizar(aluno);
        redirectAttributes.addFlashAttribute(ATRIBUTO_MENSAGEM, aluno.getNome() + " atualizado com sucesso");
        return URL_REDIRECT_LISTA;
    }

}