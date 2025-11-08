package hotel.ui.exceptions;

public class MenuItemNotFoundException extends Exception {
    public MenuItemNotFoundException(int index, String menuName) {
        super("В меню " + menuName + " не найден MenuItem с index = " + index);
    }
}
