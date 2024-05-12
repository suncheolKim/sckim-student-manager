package camp.common;

public final class StringHelper {
    private StringHelper() {
        // Do not make instance
    }

    public static boolean isDigit(String str) {
        return str.matches("^-?[0-9]+$");
    }
}
