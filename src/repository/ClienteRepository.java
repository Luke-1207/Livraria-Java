package repository;

import exceptions.ClienteException;
import model.Cliente;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class ClienteRepository {

    public static List<String> listar() throws IOException {
        Path arquivoOrigem = obterArquivo();
        return Files.readAllLines(arquivoOrigem, StandardCharsets.UTF_8);
    }

    public static void cadastrar(Cliente cliente) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(cliente.getId() + ";");
        conteudo.append(cliente.getNome() + ";");
        conteudo.append(cliente.getDataNascimento() + ";");
        conteudo.append(cliente.getEmail() + ";");
        conteudo.append(System.lineSeparator());

        Path arquivo = obterArquivo();
        Files.write(arquivo, conteudo.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }

    public static void excluir(Integer id) throws IOException {
        Path arquivo = ClienteRepository.obterArquivo();

        List<String> linhas = Files.readAllLines(arquivo, StandardCharsets.UTF_8);
        String linha = linhas.stream()
                .filter(c -> {
                    String[] partes = c.split(";");
                    return partes[0].equals(id.toString());
                })
                .findFirst().orElseThrow(() -> new ClienteException(String.format("Cliente com id '%d' não encontrado", id)));

        linhas.remove(linha);
        Files.write(arquivo, linhas, StandardCharsets.UTF_8);
    }

    private static Path obterArquivo() {
        verificarDiretorioEArquivo();
        return Paths.get("./src/dados/clientes.csv");
    }

    private static void verificarDiretorioEArquivo(){
        File diretorio = new File("./src/dados");
        if(!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try {
            File arquivo = new File(diretorio.getAbsolutePath() + "/clientes.csv");
            arquivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
