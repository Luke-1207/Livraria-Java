package service;

import exceptions.EmprestimoException;
import model.Cliente;
import model.Emprestimo;
import model.Livro;
import repository.EmprestimoRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

import static util.Util.converterParaLocalDate;

public class EmprestimoService {

    private static Scanner scanner = new Scanner(System.in);

    public static List<Emprestimo> listar() throws EmprestimoException {
        List<String> linhasCsv;
        try {
            linhasCsv = EmprestimoRepository.listar();
        } catch (IOException e) {
            e.printStackTrace();
            throw new EmprestimoException("Erro ao obter empréstimos do CSV.", e);
        }

        List<Emprestimo> emprestimos = new ArrayList<>();
        for(String linha : linhasCsv) {
            String[] colunas = linha.split("\\;");
            Integer id = Integer.valueOf(colunas[0]);
            Integer idCliente = Integer.valueOf(colunas[1]);
            Integer idLivro = Integer.valueOf(colunas[2]);
            LocalDateTime dataEHora = LocalDateTime.parse(colunas[3]);

            Cliente cliente = ClienteService.listarPorId(idCliente);
            Livro livro = LivroService.listarPorId(idLivro);

            emprestimos.add(new Emprestimo(id, cliente, livro, dataEHora));
        }

        return emprestimos;
    }

    public static Emprestimo listarPorId(Integer id) throws EmprestimoException {
        List<Emprestimo> emprestimos = listar();
        Emprestimo emprestimo = emprestimos.stream()
                .filter(e -> Objects.equals(e.getId(), id))
                .findFirst().orElseThrow(() -> new EmprestimoException(String.format("Empréstimo com id '%d' não encontrado", id)));

        return emprestimo;
    }

    public static Emprestimo listarPorLivroId(Integer idLivro) throws EmprestimoException {
        List<Emprestimo> emprestimos = listar();
        Emprestimo emprestimo = emprestimos.stream()
                .filter(e -> Objects.equals(e.getLivro().getId(), idLivro))
                .findFirst()
                .orElse(null);

        return emprestimo;
    }

    public static List<Emprestimo> listarPorClienteId(Integer idCliente) {
        List<Emprestimo> emprestimos = listar();
        List<Emprestimo> emprestimosDoCliente = emprestimos.stream()
                .filter(e -> Objects.equals(e.getCliente().getId(), idCliente))
                .collect(Collectors.toList());

        return emprestimosDoCliente;
    }

    public static Emprestimo cadastrar() {
        System.out.println("Cadastrar Empréstimo");

        try {
            System.out.print("Digite o id do cliente: ");
            Integer idCliente = Integer.valueOf(scanner.nextLine());

            System.out.print("Digite o id do livro: ");
            Integer idLivro = Integer.valueOf(scanner.nextLine());

            List<Emprestimo> emprestimos = listar();
            emprestimos.stream()
                .filter(e -> Objects.equals(e.getLivro().getId(), idLivro))
                .findFirst()
                .ifPresent(e -> {
                    throw new EmprestimoException(String.format("O livro com id '%d' já está em um empréstimo", idLivro));
                });

            LocalDateTime dataEHora = LocalDateTime.now();

            Emprestimo emprestimo = new Emprestimo(
                    gerarNovoId(), ClienteService.listarPorId(idCliente), LivroService.listarPorId(idLivro), dataEHora);
            EmprestimoRepository.cadastrar(emprestimo);

            LivroService.marcarComoIndisponivel(idLivro);

            return emprestimo;
        } catch (IOException | EmprestimoException e) {
            throw new EmprestimoException("Erro ao cadastrar empréstimo no CSV: " + e.getMessage(), e);
        }
    }

    public static Integer cancelar(){
        System.out.println("Cancelar Empréstimo");

        try {
            System.out.print("Digite o id do empréstimo:");
            Integer id = Integer.valueOf(scanner.nextLine());

            LivroService.marcarComoDisponivel(listarPorId(id).getLivro().getId());
            EmprestimoRepository.cancelar(id);

            return id;
        } catch (IOException | EmprestimoException e) {
            throw new EmprestimoException("Erro ao cancelar empréstimo: " + e.getMessage(), e);
        }
    }

    private static Integer gerarNovoId() throws EmprestimoException {
        List<Emprestimo> emprestimos = listar();
        return (emprestimos.isEmpty()) ? 1 : emprestimos.get(emprestimos.size() - 1).getId() + 1;
    }

}
