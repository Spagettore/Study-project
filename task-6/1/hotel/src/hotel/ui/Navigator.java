package hotel.ui;

import hotel.ui.exceptions.MenuItemNotFoundException;

import java.util.concurrent.atomic.AtomicInteger;

public class Navigator {
    private Menu currentMenu;

    public int getCurrentMenuItemsCount() {
        return this.currentMenu.getMenuItemsCount();
    }

    public void changeCurrentMenu(Menu menu) {
        this.currentMenu = menu;
    }

    public String getMenuHint() {
        StringBuilder hint = new StringBuilder();
        hint.append(String.format("=%s=\n", this.currentMenu.getName()));
        AtomicInteger counter = new AtomicInteger(0);
        this.currentMenu.getMenuItems().forEach(item -> hint.append(counter.getAndIncrement()).append(". ").append(item.getName()).append("\n"));
        hint.append("Выберите пункт меню: ");
        return hint.toString();
    }

    public void navigate(int index) {
        if (index < 0 || index > this.currentMenu.getMenuItems().size()) {
            System.out.println("Неверно введен index");
            return;
        }

        try {
            MenuItem menuItem = this.currentMenu.getMenuItem(index);
            menuItem.doAction();
            this.currentMenu = menuItem.getNextMenu();
        } catch (MenuItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
