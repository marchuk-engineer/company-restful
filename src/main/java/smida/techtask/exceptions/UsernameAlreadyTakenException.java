package smida.techtask.exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {
    private static final String MESSAGE = "We're sorry. This username cannot be used, someone has already used that";

    public UsernameAlreadyTakenException() {
        super(MESSAGE);
    }
}
