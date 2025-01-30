package hezix.org.shaudifydemo1.exception;

public class PasswordAndPasswordConfirmationNotEquals extends RuntimeException {
    public PasswordAndPasswordConfirmationNotEquals(String message) {
        super(message);
    }
}
