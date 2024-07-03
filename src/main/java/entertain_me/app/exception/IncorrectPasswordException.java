package entertain_me.app.exception;

public class IncorrectPasswordException extends Exception {
    public IncorrectPasswordException() {
        super("Password incorrect");
    }

    public IncorrectPasswordException(String message) {
        super(message);
    }

    public IncorrectPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectPasswordException(Throwable cause) {
        super(cause);
    }
}