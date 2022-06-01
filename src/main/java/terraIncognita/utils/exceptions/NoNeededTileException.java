package terraIncognita.utils.exceptions;

public class NoNeededTileException extends RuntimeException {

    public NoNeededTileException() {
        super();
    }
    public NoNeededTileException(String message) {
        super(message);
    }
    public NoNeededTileException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoNeededTileException(Throwable cause) {
        super(cause);
    }

}
