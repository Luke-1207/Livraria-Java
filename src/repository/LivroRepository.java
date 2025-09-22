package repository;

import exceptions.LivroException;
import model.Livro;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Objects;

import static util.Util.LIVROS_CSV;

public class LivroRepository extends ArquivoRepository {

    public static List<String> listar() throws IOException {
        Path arquivoOrigem = obterArquivo();
        return Files.readAllLines(arquivoOrigem, StandardCharsets.UTF_8);
    }

    public static void cadastrar(Livro livro) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(livro.getId() + ";");
        conteudo.append(livro.getTitulo() + ";");
        conteudo.append(livro.getAutor().getId() + ";");
        conteudo.append(livro.getDisponivel() + ";");
        conteudo.append(livro.getDataCadastro() + ";");
        conteudo.append(livro.getDataAtualizacao() + ";");
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
                .findFirst().orElseThrow(() -> new LivroException(String.format("Livro com id '%d' não encontrado", id)));

        linhas.remove(linha);
        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    public static void atualizar(Livro livroAtualizado) throws IOException {
        Path arquivo = obterArquivo();
        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);

        for (int i = 0; i < linhas.size(); i++) {
            String[] partes = linhas.get(i).split(";");
            Integer id = Integer.valueOf(partes[0]);

            if (Objects.equals(id, livroAtualizado.getId())) {
                String novaLinha = String.format("%d;%s;%d;%s;%s;%s;",
                        livroAtualizado.getId(),
                        livroAtualizado.getTitulo(),
                        livroAtualizado.getAutor().getId(),
                        livroAtualizado.getDisponivel(),
                        livroAtualizado.getDataCadastro(),
                        livroAtualizado.getDataAtualizacao());

                linhas.set(i, novaLinha);
                break;
            }
        }

        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    private static Path obterArquivo() {
        verificarDiretorioEArquivo(LIVROS_CSV);
        return Paths.get("./src/dados/" + LIVROS_CSV);
    }

}
