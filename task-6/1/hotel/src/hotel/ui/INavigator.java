package hotel.ui;

public interface INavigator {
    int getCurrentMenuItemsCount();

    void changeCurrentMenu(Menu menu);

    String getMenuHint();

    void navigate(int index);
}
