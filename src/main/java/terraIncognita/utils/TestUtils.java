package terraIncognita.utils;

public class TestUtils {

    private static boolean isTestScope = false;
    /**
     * Set parameter to true if logging is needed to be outputted in console
     */
    public static void setIsTestScope(boolean val) {
        isTestScope = val;
    }

    public static boolean isTestScope() {
        return isTestScope;
    }
}
