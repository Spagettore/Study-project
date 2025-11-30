package hotel.ui.actions.root;

import hotel.Hotel;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

public class PrintGuestsAndRoomsAction implements IAction {
    @Override
    public void execute() {
        System.out.println("Список жильцов и их номеров:");
        Hotel.getInstance().getGuestsAndRooms().forEach((guest, rooms) -> {
            OutputHelper.printGuest(guest);
            rooms.forEach(OutputHelper::printRoom);
        });
    }
}
