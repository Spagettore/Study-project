package hotel.ui.actions.rooms;

import hotel.Hotel;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class RemoveRoomAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id комнаты для удаления: ", true);
        Hotel.getInstance().removeRoom(id);
        System.out.printf("Комната с id - %d удалена%n", id);
    }
}
