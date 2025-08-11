package service;

import exceptions.ClienteException;
import model.Cliente;
import repository.ClienteRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static util.Util.converterParaLocalDate;

public class ClienteService {

    private static Scanner scanner = new Scanner(System.in);

    public static List<Cliente> listar() throws ClienteException {
        List<String> linhasCsv;
        try {
            linhasCsv = ClienteRepository.listar();
        } catch (IOException e) {
            e.printStackTrace();
            throw new ClienteException("Erro ao obter clientes do CSV.", e);
        }

        List<Cliente> clientes = new ArrayList<>();
        for(String linha : linhasCsv) {
            String[] colunas = linha.split("\\;");
            Integer id = Integer.valueOf(colunas[0]);
            String nome = colunas[1];
            LocalDate dataNascimento = LocalDate.parse(colunas[2]);
            String email = colunas[3];

            clientes.add(new Cliente(id, nome, dataNascimento, email));
        }

        return clientes;
    }

    public static Cliente cadastrar() {
        System.out.println("Cadastrar Cliente");

        try {
            System.out.print("Digite o nome do cliente: ");
            String nome = scanner.nextLine();

            System.out.print("Digite a data de nascimento do cliente (dd/MM/yyyy) : ");
            LocalDate dataNascimento = converterParaLocalDate(scanner.nextLine());

            System.out.print("Digite o email do cliente: ");
            String email = scanner.nextLine();

            Cliente cliente = new Cliente(gerarNovoId(), nome, dataNascimento, email);
            ClienteRepository.cadastrar(cliente);

            return cliente;
        } catch (IOException | ClienteException e) {
            throw new ClienteException("Erro ao cadastrar cliente no CSV: " + e.getMessage(), e);
        }
    }

    private static Integer gerarNovoId() throws ClienteException {
        List<Cliente> clientes = listar();
        return (clientes.isEmpty()) ? 1 : clientes.get(clientes.size() - 1).getId() + 1;
    }

}
