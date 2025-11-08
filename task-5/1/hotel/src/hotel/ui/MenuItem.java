package hotel.ui;

import hotel.ui.actions.IAction;

public class MenuItem {
    private final String name;
    private final IAction action;
    private final Menu nextMenu;

    public MenuItem(String name, IAction action, Menu nextMenu) {
        this.name = name;
        this.action = action;
        this.nextMenu = nextMenu;
    }

    public void doAction() {
        if (action == null) {
            return;
        }
        action.execute();
    }

    public String getName() {
        return this.name;
    }

    public Menu getNextMenu() {
        return this.nextMenu;
    }
}
