package service;

import exceptions.AutorException;
import model.Autor;
import model.Livro;
import repository.AutorRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import static util.Util.converterParaLocalDate;

public class AutorService {

    private static Scanner scanner = new Scanner(System.in);

    public static List<Autor> listar() throws AutorException {
        List<String> linhasCsv;
        try {
            linhasCsv = AutorRepository.listar();
        } catch (IOException e) {
            e.printStackTrace();
            throw new AutorException("Erro ao obter autores do CSV.", e);
        }

        List<Autor> autores = new ArrayList<>();
        for(String linha : linhasCsv) {
            String[] colunas = linha.split("\\;");
            Integer id = Integer.valueOf(colunas[0]);
            String nome = colunas[1];
            LocalDate dataNascimento = LocalDate.parse(colunas[2]);

            autores.add(new Autor(id, nome, dataNascimento));
        }

        return autores;
    }

    public static Autor listarPorId(Integer id) throws AutorException {
        List<Autor> autores = listar();
        Autor autor = autores.stream()
                .filter(a -> Objects.equals(a.getId(), id))
                .findFirst().orElseThrow(() -> new AutorException(String.format("Autor com id '%d' não encontrado", id)));

        return autor;
    }

    public static Autor cadastrar() {
        System.out.println("Cadastrar Autor");

        try {
            System.out.print("Digite o nome do autor: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a data de nascimento do autor (dd/MM/yyyy) : ");
            LocalDate dataNascimento = converterParaLocalDate(scanner.nextLine());

            Autor autor = new Autor(gerarNovoId(), nome, dataNascimento);
            AutorRepository.cadastrar(autor);

            return autor;
        } catch (IOException | AutorException e) {
            throw new AutorException("Erro ao cadastrar autor no CSV: " + e.getMessage(), e);
        }
    }

    public static Integer excluir(){
        System.out.println("Excluir Autor");

        try {
            System.out.print("Digite o id do autor:");
            Integer id = Integer.valueOf(scanner.nextLine());

            List<Livro> livros = LivroService.listar();
            if (livros.stream().anyMatch(l -> l.getAutor().getId().equals(id)))
                throw new AutorException("O Autor está atrelado a um ou mais livros. Impossível excluir.");

            AutorRepository.excluir(id);
            return id;
        } catch (IOException | AutorException e) {
            throw new AutorException("Erro ao excluir autor no CSV: " + e.getMessage(), e);
        }
    }

    private static Integer gerarNovoId() throws AutorException {
        List<Autor> autores = listar();
        return (autores.isEmpty()) ? 1 : autores.get(autores.size() - 1).getId() + 1;
    }
}
