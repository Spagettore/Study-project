package hotel.ui.actions.rooms;

import hotel.Hotel;
import hotel.Room;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class AddRoomAction implements IAction {
    @Override
    public void execute() {
        int price = InputHelper.enterNumber("Введите цену комнаты: ", true);
        int capacity = InputHelper.enterNumber("Введите сколько человек вмещает комната: ", true);
        Room room = new Room(price, capacity);
        Hotel.getInstance().addRoom(room);
        System.out.printf("Комната id - %d добавлена%n", room.getId());
    }
}
