package menu;

import exceptions.EmprestimoException;
import model.Emprestimo;
import service.EmprestimoService;

import java.util.List;
import java.util.Scanner;

import static util.Util.escreverLinhasSeparadoras;

public class EmprestimoMenu {

    private static Scanner scanner = new Scanner(System.in);

    public static void exibirMenu() throws EmprestimoException {
        int sair = 0;
        do {
            System.out.println("\n");
            escreverLinhasSeparadoras();
            System.out.println("MENU - Empréstimo");
            System.out.println("1 - Cadastrar");
            System.out.println("2 - Listar");
            System.out.println("3 - Listar por Livro");
            System.out.println("4 - Cancelar Empréstimo");
            System.out.println("5 - Sair");
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
                    listarPorLivro();
                    break;
                case "4":
                    cancelar();
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

    private static void listar() throws EmprestimoException {
        List<Emprestimo> emprestimos = EmprestimoService.listar();

        for (Emprestimo emprestimo : emprestimos) {
            System.out.println(emprestimo);
        }
    }

    private static void listarPorLivro() throws EmprestimoException {
        System.out.print("Digite o id do livro:");
        Integer idLivro = Integer.valueOf(scanner.nextLine());

        Emprestimo emprestimo = EmprestimoService.listarPorLivroId(idLivro);
        System.out.println(emprestimo);
    }

    private static void cadastrar() {
        try {
            Emprestimo emprestimoCadastrado = EmprestimoService.cadastrar();
            System.out.println(
                    String.format("Empréstimo %d, do livro %d - %s para o cliente %d - %s cadastrado com sucesso."
                            , emprestimoCadastrado.getId(), emprestimoCadastrado.getLivro().getId(), emprestimoCadastrado.getLivro().getTitulo(),
                            emprestimoCadastrado.getCliente().getId(), emprestimoCadastrado.getCliente().getNome()));
        } catch (EmprestimoException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void cancelar() {
        try {
            Integer idExcluido = EmprestimoService.cancelar();
            System.out.println(String.format("Empréstimo %d cancelado com sucesso.", idExcluido));
        } catch (EmprestimoException e) {
            System.err.println(e.getMessage());
        }
    }

}
