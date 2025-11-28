package hotel.ui;

public class MenuController {
    private static MenuController instance;
    private final IBuilder builder;
    private final INavigator navigator;

    private MenuController(IBuilder builder, INavigator navigator) {
        this.builder = builder;
        this.builder.buildMenu();

        this.navigator = navigator;
        this.navigator.changeCurrentMenu(this.builder.getRootMenu());
    }

    public static synchronized MenuController getInstance() {
        if (instance == null) {
            instance = new MenuController(new Builder(), new Navigator());
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
