package menu;

import java.util.Scanner;

import static util.Util.escreverLinhasSeparadoras;

public class ClientesMenu {

    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
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

            System.out.println("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    // Abrir menu de Clientes
                    break;
                case "2":
                    // Abrir menu de Livros
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


}
