package hotel.ui.actions.config;

import hotel.Hotel;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class SetRoomHistoryCountAction implements IAction {
    @Override
    public void execute() {
        int count = InputHelper.enterNumber("Введите количество выводимых записей в конфиге: ", true);
        Hotel.getInstance().getConfig().setRoomHistoryCount(count);
    }
}
