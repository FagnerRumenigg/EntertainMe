package entertain_me.app.exception;

public class AlreadyExistsException extends Exception {
    public AlreadyExistsException() {
        super("Can't create because already exists");
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }
}