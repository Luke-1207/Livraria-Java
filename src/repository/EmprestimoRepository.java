package repository;

import exceptions.EmprestimoException;
import model.Emprestimo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static util.Util.EMPRESTIMOS_CSV;

public class EmprestimoRepository extends ArquivoRepository {
    public static List<String> listar() throws IOException {
        Path arquivoOrigem = obterArquivo();
        return Files.readAllLines(arquivoOrigem, StandardCharsets.UTF_8);
    }

    public static void cadastrar(Emprestimo emprestimo) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(emprestimo.getId() + ";");
        conteudo.append(emprestimo.getCliente().getId() + ";");
        conteudo.append(emprestimo.getLivro().getId() + ";");
        conteudo.append(emprestimo.getDataHoraEmprestimo() + ";");
        conteudo.append(System.lineSeparator());

        Path arquivo = obterArquivo();
        Files.write(arquivo, conteudo.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void cancelar(Integer id) throws IOException {
        Path arquivo = obterArquivo();

        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        String linha = linhas.stream()
                .filter(e -> {
                    String[] partes = e.split(";");
                    return partes[0].equals(id.toString());
                })
                .findFirst().orElseThrow(() -> new EmprestimoException(String.format("Empréstimo com id '%d' não encontrado", id)));

        linhas.remove(linha);
        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    private static Path obterArquivo() {
        verificarDiretorioEArquivo(EMPRESTIMOS_CSV);
        return Paths.get("./src/dados/" + EMPRESTIMOS_CSV);
    }
}
