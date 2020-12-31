package exception;

public class KeyNotFoundException extends Exception {
    public static final String message = "Key does not exists in our database";

    public KeyNotFoundException() {
        super(message);
    }
}
