package repository;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ClienteRepository {

    public static List<String> listar() throws IOException {
        verificarDiretorioEArquivo();
        Path arquivoOrigem = Paths.get("./src/dados/clientes.csv");

        return Files.readAllLines(arquivoOrigem, StandardCharsets.UTF_8);
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
