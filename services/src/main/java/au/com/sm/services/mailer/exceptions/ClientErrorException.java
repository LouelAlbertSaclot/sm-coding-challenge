package au.com.sm.services.mailer.exceptions;

import java.util.List;

public class ClientErrorException extends Exception {

    private List<String> errors;

    public ClientErrorException(final String message, final List<String> errors) {
        super(message);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
