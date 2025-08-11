package menu;

import exceptions.ClienteException;
import model.Cliente;
import service.ClienteService;

import java.util.List;
import java.util.Scanner;

import static util.Util.escreverLinhasSeparadoras;

public class ClientesMenu {

    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() throws ClienteException {
        int sair = 0;
        do {
            System.out.println("\n");
            escreverLinhasSeparadoras();
            System.out.println("MENU - Clientes");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Sair");
            escreverLinhasSeparadoras();

            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    cadastrar();
                    break;
                case "2":
                    listar();
                    break;
                case "3":
                    break;
                case "4":
                    sair = 1;
                    break;
                default:
                    System.out.println("Digite uma opção válida!");
                    break;
            }
        } while (sair == 0);
    }

    private static void listar() throws ClienteException {
        List<Cliente> clientes = ClienteService.listar();

        for (Cliente cliente : clientes) {
            System.out.println(cliente);
        }
    }

    private static void cadastrar() {
        try {
            Cliente clienteCadastrado = ClienteService.cadastrar();
            System.out.println(String.format("Cliente %d - %s cadastrado com sucesso.", clienteCadastrado.getId(), clienteCadastrado.getNome()));
        } catch (ClienteException e) {
            System.err.println(e.getMessage());
        }
    }

}
