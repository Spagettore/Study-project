package hotel.ui;

import hotel.ui.exceptions.MenuItemNotFoundException;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final String name;
    private final List<MenuItem> menuItems;

    public Menu(String name, ArrayList<MenuItem> menuItems) {
        this.name = name;
        this.menuItems = menuItems;
    }

    public String getName() {
        return this.name;
    }

    public List<MenuItem> getMenuItems() {
        return this.menuItems;
    }

    public int getMenuItemsCount() {
        return this.menuItems.size();
    }

    public MenuItem getMenuItem(int index) throws MenuItemNotFoundException {
        return this.menuItems.stream()
                .skip(index)
                .findAny().orElseThrow(() -> new MenuItemNotFoundException(index, this.name));
    }
}
