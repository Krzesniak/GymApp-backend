package pl.krzesniak.gymapp.exceptions;

public class NotFoundUserException extends RuntimeException {
    public NotFoundUserException(String message) {
        super(message);
    }
}
