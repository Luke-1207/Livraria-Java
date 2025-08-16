package util;

import exceptions.ClienteException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Util {
    public static final String CLIENTES_CSV = "clientes.csv";
    public static final String LIVROS_CSV = "livros.csv";

    public static void escreverLinhasSeparadoras(){
        System.out.println("-".repeat(20));
    }

    public static LocalDate converterParaLocalDate(String data) throws ClienteException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            return LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            throw new ClienteException("Erro ao converter data de nascimento do cliente", e);
        }
    }
}
