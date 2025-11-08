package hotel.ui.actions.rooms;

import hotel.Hotel;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

public class PrintRoomHistoryAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id комнаты для вывода истории заселения: ", true);
        int number = InputHelper.enterNumber("Введите количество логов в истории заселения комнаты (0 для вывода всех): ", true);

        try {
            if (number > 0) {
                OutputHelper.printRoomHistory(Hotel.getInstance().getRoomHistory(id, number));
                return;
            }
            OutputHelper.printRoomHistory(Hotel.getInstance().getRoomHistory(id));
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
