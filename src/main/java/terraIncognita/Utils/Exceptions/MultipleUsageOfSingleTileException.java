package terraIncognita.Utils.Exceptions;

public class MultipleUsageOfSingleTileException extends RuntimeException {

    public MultipleUsageOfSingleTileException() {
        super();
    }
    public MultipleUsageOfSingleTileException(String message) {
        super(message);
    }
    public MultipleUsageOfSingleTileException(String message, Throwable cause) {
        super(message, cause);
    }
    public MultipleUsageOfSingleTileException(Throwable cause) {
        super(cause);
    }
}
