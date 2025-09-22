package service;

import exceptions.AutorException;
import exceptions.LivroException;
import model.Autor;
import model.Emprestimo;
import model.Livro;
import repository.LivroRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class LivroService {

    private static Scanner scanner = new Scanner(System.in);

    public static List<Livro> listar() throws LivroException {
        List<String> linhasCsv;
        try {
            linhasCsv = LivroRepository.listar();
        } catch (IOException e) {
            e.printStackTrace();
            throw new LivroException("Erro ao obter livros do CSV.", e);
        }

        List<Livro> livros = new ArrayList<>();
        for(String linha : linhasCsv) {
            String[] colunas = linha.split("\\;");
            Integer id = Integer.valueOf(colunas[0]);
            String titulo = colunas[1];
            Autor autor = AutorService.listarPorId(Integer.valueOf(colunas[2]));
            Boolean disponivel = Boolean.valueOf(colunas[3]);
            LocalDateTime dataCadastro = LocalDateTime.parse(colunas[4]);
            LocalDateTime dataAtualizacao = LocalDateTime.parse(colunas[5]);

            livros.add(new Livro(id, titulo, autor, disponivel, dataCadastro, dataAtualizacao));
        }

        return livros;
    }

    public static Livro listarPorId(Integer id) throws LivroException {
        List<Livro> livros = listar();
        Livro livro = livros.stream()
                .filter(l -> Objects.equals(l.getId(), id))
                .findFirst().orElseThrow(() -> new LivroException(String.format("Livro com id '%d' não encontrado", id)));

        return livro;
    }

    public static Livro cadastrar() {
        System.out.println("Cadastrar Livro");

        try {
            System.out.print("Digite o título do livro: ");
            String titulo = scanner.nextLine();

            System.out.print("Digite o id do autor do livro: ");
            Integer idAutor = Integer.valueOf(scanner.nextLine());
            Autor autor = AutorService.listarPorId(idAutor);

            LocalDateTime dataCadastro = LocalDateTime.now();
            LocalDateTime dataAtualizacao = LocalDateTime.now();

            Livro livro = new Livro(gerarNovoId(), titulo, autor, true,  dataCadastro, dataAtualizacao);
            LivroRepository.cadastrar(livro);

            return livro;
        } catch (IOException | LivroException e) {
            throw new LivroException("Erro ao cadastrar livro no CSV: " + e.getMessage(), e);
        }
    }

    public static Integer excluir(){
        System.out.println("Excluir Livro");

        try {
            System.out.print("Digite o id do livro:");
            Integer id = Integer.valueOf(scanner.nextLine());

            Emprestimo emprestimo = EmprestimoService.listarPorLivroId(id);
            if (emprestimo != null)
                throw new AutorException("O Livro está atrelado a um empréstimo. Impossível excluir.");

            LivroRepository.excluir(id);
            return id;
        } catch (IOException | LivroException e) {
            throw new LivroException("Erro ao excluir livro no CSV: " + e.getMessage(), e);
        }
    }

    public static void marcarComoIndisponivel(Integer idLivro) throws LivroException {
        try {
            Livro livro = listarPorId(idLivro);
            livro.setDisponivel(false);
            livro.setDataAtualizacao(LocalDateTime.now());
            LivroRepository.atualizar(livro);
        } catch (IOException e) {
            throw new LivroException("Erro ao marcar livro como indisponível: " + e.getMessage(), e);
        }
    }

    public static void marcarComoDisponivel(Integer idLivro) throws LivroException {
        try {
            Livro livro = listarPorId(idLivro);
            livro.setDisponivel(true);
            livro.setDataAtualizacao(LocalDateTime.now());
            LivroRepository.atualizar(livro);
        } catch (IOException e) {
            throw new LivroException("Erro ao marcar livro como disponível: " + e.getMessage(), e);
        }
    }

    private static Integer gerarNovoId() throws LivroException {
        List<Livro> livros = listar();
        return (livros.isEmpty()) ? 1 : livros.get(livros.size() - 1).getId() + 1;
    }

}
