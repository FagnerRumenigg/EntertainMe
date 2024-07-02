package entertain_me.app.exception;

public class EmailNotValidException extends Exception {
    public EmailNotValidException() {
        super("This is not a valid email");
    }

    public EmailNotValidException(String message) {
        super(message);
    }

    public EmailNotValidException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailNotValidException(Throwable cause) {
        super(cause);
    }
}