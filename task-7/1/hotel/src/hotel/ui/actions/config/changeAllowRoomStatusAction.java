package hotel.ui.actions.config;

import hotel.Hotel;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class changeAllowRoomStatusAction implements IAction {
    @Override
    public void execute() {
        boolean allow = 0 != InputHelper.chooseAnswer(1, new StringBuilder("""
                Выберите разрешить ли изменять статус номера:
                0. Запретить
                1. Разрешить
                """).toString());
        Hotel.getInstance().getConfig().setAllowChangeStatus(allow);
    }
}
