package peaksoft.exception;

public class AlreadyExistException extends Throwable {
    public AlreadyExistException() {
        super();
    }

    public AlreadyExistException(String message) {
        super(message);
    }
}
