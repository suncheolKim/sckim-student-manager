package camp.common;

public final class StringHelper {
    private StringHelper() {
        // Do not make instance
    }

    public static boolean isDigit(String str) {
        return str.matches("^-?\\d+$");
    }

    public static boolean isNotDigit(String str) {
        return !isDigit(str);
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
