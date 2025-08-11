package exceptions;

import java.io.IOException;

public class ClienteException extends IOException {
    public ClienteException(String message) {
        super(message);
    }

    public ClienteException(String message, Throwable cause) {
        super(message, cause);
    }
}
