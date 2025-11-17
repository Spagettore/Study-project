package hotel.ui;

public class MenuController {
    private static MenuController instance;
    private final Builder builder;
    private final Navigator navigator;

    public MenuController() {
        this.builder = new Builder();
        this.builder.buildMenu();

        this.navigator = new Navigator();
        this.navigator.changeCurrentMenu(this.builder.getRootMenu());
    }

    public static MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController();
        }
        return instance;
    }

    public void run() {
        while (true) {
            System.out.println("------------------------------------------------------");
            int answer = InputHelper.chooseAnswer(navigator.getCurrentMenuItemsCount(), navigator.getMenuHint());
            navigator.navigate(answer);
        }
    }
}
