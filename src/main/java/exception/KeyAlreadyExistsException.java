package exception;

public class KeyAlreadyExistsException extends Exception {
    public static final String message = "Key already exists in our database.";
    public KeyAlreadyExistsException() {
        super(message);
    }
}
