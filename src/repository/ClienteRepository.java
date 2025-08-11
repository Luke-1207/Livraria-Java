package repository;

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
        verificarDiretorioEArquivo();
        Path arquivoOrigem = Paths.get("./src/dados/clientes.csv");

        return Files.readAllLines(arquivoOrigem, StandardCharsets.UTF_8);
    }

    public static void cadastrar(Cliente cliente) throws IOException {
        StringBuilder conteudo = new StringBuilder();
        conteudo.append(cliente.getId() + ";");
        conteudo.append(cliente.getNome() + ";");
        conteudo.append(cliente.getDataNascimento() + ";");
        conteudo.append(cliente.getEmail() + ";");
        conteudo.append(System.lineSeparator());

        verificarDiretorioEArquivo();
        Path arquivo = Paths.get("./src/dados/clientes.csv");
        Files.write(arquivo, conteudo.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
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
