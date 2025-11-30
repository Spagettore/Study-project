package hotel.ui.actions.root;

import hotel.Hotel;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class MoveOutGuestsAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id комнаты для выселения жильцов: ", true);
        try {
            System.out.println(Hotel.getInstance().moveOut(id) ? "Успех" : "Провал");
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
