package terraIncognita.utils.exceptions;

public class SceneNotFoundException extends RuntimeException {

    public SceneNotFoundException() {
        super();
    }
    public SceneNotFoundException(String message) {
        super(message);
    }
    public SceneNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public SceneNotFoundException(Throwable cause) {
        super(cause);
    }
}
