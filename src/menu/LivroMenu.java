package menu;

import exceptions.LivroException;
import model.Livro;
import service.LivroService;

import java.util.List;
import java.util.Scanner;

import static util.Util.escreverLinhasSeparadoras;

public class LivroMenu {

    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() throws LivroException {
        int sair = 0;
        do {
            System.out.println("\n");
            escreverLinhasSeparadoras();
            System.out.println("MENU - Livros");
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

    private static void listar() throws LivroException {
        List<Livro> livros = LivroService.listar();

        for (Livro livro : livros) {
            System.out.println(livro);
        }
    }

    private static void cadastrar() {
        try {
            Livro livroCadastrado = LivroService.cadastrar();
            System.out.println(String.format("Livro %d - %s cadastrado com sucesso.", livroCadastrado.getId(), livroCadastrado.getTitulo()));
        } catch (LivroException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void excluir() {
        try {
            Integer idExcluido = LivroService.excluir();
            System.out.println(String.format("Cliente %d excluído com sucesso.", idExcluido));
        } catch (LivroException e) {
            System.err.println(e.getMessage());
        }
    }


}
