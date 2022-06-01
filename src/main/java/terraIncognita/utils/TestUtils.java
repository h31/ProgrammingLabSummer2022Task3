package terraIncognita.utils;

public class TestUtils {

    private static boolean isConsoleOutput = false;
    /**
     * Set parameter to true if logging is needed to be outputted in console
     */
    public static void setIsConsoleOutput(boolean val) {
        isConsoleOutput = val;
    }

    public static boolean isConsoleOutput() {
        return isConsoleOutput;
    }
}
