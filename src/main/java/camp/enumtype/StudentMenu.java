package camp.enumtype;

import java.util.Arrays;
import java.util.List;

public enum StudentMenu {
    CREATE, INQUIRY, TO_MAIN;

    private final static List<StudentMenu> menus = Arrays.stream(values()).toList();

    public static StudentMenu get(int menuNum) {
        return menus.get(menuNum-1);
    }
}
