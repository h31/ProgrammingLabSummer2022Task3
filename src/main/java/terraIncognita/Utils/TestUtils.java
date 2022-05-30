package terraIncognita.Utils;

import java.util.LinkedList;
import java.util.Queue;

public class TestUtils {

    private static boolean isConsoleOutput = false;
    private static final Queue<Exception> exitErrors = new LinkedList ();
    /**
     * Set parameter to true if logging is needed to be outputted in console
     */
    public static void setIsConsoleOutput(boolean val) {
        isConsoleOutput = val;
    }

    public static boolean isConsoleOutput() {
        return isConsoleOutput;
    }

    public static void pushExitException(Exception e) {
        exitErrors.offer(e);
    }

    public static Queue<Exception> popExceptions() {
        Queue<Exception> res = new LinkedList<>(exitErrors);
        exitErrors.clear();
        return res;
    }
}
