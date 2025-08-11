package exceptions;

import java.io.IOException;

public class ClienteException extends RuntimeException {
    public ClienteException(String message) {
        super(message);
    }

    public ClienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
