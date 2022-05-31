package terraIncognita.Utils.Exceptions;

public class NothingThrownException extends Exception{

    public NothingThrownException() {
        super();
    }
    public NothingThrownException(String message) {
        super(message);
    }
    public NothingThrownException(String message, Throwable cause) {
        super(message, cause);
    }
    public NothingThrownException(Throwable cause) {
        super(cause);
    }


}
