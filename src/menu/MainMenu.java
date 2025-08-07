package menu;

import java.util.Scanner;

import static util.Util.escreverLinhasSeparadoras;

public class MainMenu {

    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() {
        int sair = 0;
        do {
            escreverLinhasSeparadoras();
            System.out.println("SISTEMA - LIVRARIA");
            System.out.println("1 - Clientes");
            System.out.println("2 - Livros");
            System.out.println("3 - Autores");
            System.out.println("4 - Empréstimo");
            System.out.println("5 - Sair");
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
                    break;
                case "5":
                    sair = 1;
                    break;
                default:
                    System.out.println("Digite uma opção válida!");
                    break;
            }
        } while (sair == 0);
    }
}
