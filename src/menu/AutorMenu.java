package menu;

import exceptions.AutorException;
import model.Autor;
import service.AutorService;

import java.util.List;
import java.util.Scanner;

import static util.Util.escreverLinhasSeparadoras;

public class AutorMenu {

    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() throws AutorException {
        int sair = 0;
        do {
            System.out.println("\n");
            escreverLinhasSeparadoras();
            System.out.println("MENU - Autores");
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
                    excluir();
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

    private static void listar() throws AutorException {
        List<Autor> autores = AutorService.listar();

        for (Autor autor : autores) {
            System.out.println(autor);
        }
    }

    private static void cadastrar() {
        try {
            Autor autorCadastrado = AutorService.cadastrar();
            System.out.println(String.format("Autor %d - %s cadastrado com sucesso.", autorCadastrado.getId(), autorCadastrado.getNome()));
        } catch (AutorException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void excluir() {
        try {
            Integer idExcluido = AutorService.excluir();
            System.out.println(String.format("Autor %d excluído com sucesso.", idExcluido));
        } catch (AutorException e) {
            System.err.println(e.getMessage());
        }
    }

}
