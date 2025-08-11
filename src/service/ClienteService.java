package service;

import exceptions.ClienteException;
import model.Cliente;
import repository.ClienteRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            Integer id = Integer.getInteger(colunas[0]);
            String nome = colunas[1];
            LocalDate dataNascimento = LocalDate.parse(colunas[2]);
            String email = colunas[3];

            clientes.add(new Cliente(id, nome, dataNascimento, email));
        }

        return clientes;
    }


}
