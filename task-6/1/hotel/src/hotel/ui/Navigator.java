package hotel.ui;

import hotel.ui.exceptions.MenuItemNotFoundException;

import java.util.concurrent.atomic.AtomicInteger;

public class Navigator implements INavigator {
    private Menu currentMenu;

    @Override
    public int getCurrentMenuItemsCount() {
        return this.currentMenu.getMenuItemsCount();
    }

    @Override
    public void changeCurrentMenu(Menu menu) {
        this.currentMenu = menu;
    }

    @Override
    public String getMenuHint() {
        StringBuilder hint = new StringBuilder();
        hint.append(String.format("=%s=\n", this.currentMenu.getName()));
        AtomicInteger counter = new AtomicInteger(0);
        this.currentMenu.getMenuItems().forEach(item -> hint.append(counter.getAndIncrement()).append(". ").append(item.getName()).append("\n"));
        hint.append("Выберите пункт меню: ");
        return hint.toString();
    }

    @Override
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
