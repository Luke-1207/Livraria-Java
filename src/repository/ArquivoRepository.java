package repository;

import java.io.File;
import java.io.IOException;

public class ArquivoRepository {
    protected static void verificarDiretorioEArquivo(String nomeArquivo){
        File diretorio = new File("./src/dados");
        if(!diretorio.exists()) {
            diretorio.mkdirs();
        }

        try {
            File arquivo = new File(diretorio.getAbsolutePath() + "/" + nomeArquivo);
            arquivo.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
