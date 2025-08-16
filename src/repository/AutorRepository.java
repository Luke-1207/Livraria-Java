package repository;

import exceptions.AutorException;
import model.Autor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import static util.Util.AUTORES_CSV;

public class AutorRepository extends ArquivoRepository {

    public static List<String> listar() throws IOException {
        Path arquivoOrigem = obterArquivo();
        return Files.readAllLines(arquivoOrigem, StandardCharsets.UTF_8);
    }

    public static void cadastrar(Autor autor) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(autor.getId() + ";");
        conteudo.append(autor.getNome() + ";");
        conteudo.append(autor.getDataNascimento() + ";");
        conteudo.append(System.lineSeparator());

        Path arquivo = obterArquivo();
        Files.write(arquivo, conteudo.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void excluir(Integer id) throws IOException {
        Path arquivo = obterArquivo();

        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        String linha = linhas.stream()
                .filter(c -> {
                    String[] partes = c.split(";");
                    return partes[0].equals(id.toString());
                })
                .findFirst().orElseThrow(() -> new AutorException(String.format("Autor com id '%d' não encontrado", id)));

        linhas.remove(linha);
        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    private static Path obterArquivo() {
        verificarDiretorioEArquivo(AUTORES_CSV);
        return Paths.get("./src/dados/" + AUTORES_CSV);
    }
}
