package hotel.ui.actions.rooms;

import hotel.Hotel;
import hotel.Room;
import hotel.enums.RoomSortType;
import hotel.enums.RoomStatus;
import hotel.enums.SortOrder;
import hotel.ui.Hint;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

import java.util.List;
import java.util.Optional;

public class PrintRoomsAction implements IAction {
    @Override
    public void execute() {
        //определяем фильтр по статусу комнаты
        Optional<RoomStatus> status = InputHelper.chooseEnumValue(RoomStatus.class,
                new Hint(OutputHelper.getRoomStatusHint(), true));

        //определяем тип сортировки и как сортировать
        Optional<RoomSortType> sortType = InputHelper.chooseEnumValue(RoomSortType.class,
                new Hint(OutputHelper.getRoomSortTypeHint(), true));
        Optional<SortOrder> sortOrder = Optional.empty();
        if (sortType.isPresent()) {
            sortOrder = InputHelper.chooseSortOrder();
        }

        //получаем список комнат
        List<Room> rooms = Hotel.getInstance().getRooms();
        if (status.isPresent()) {
            if (sortType.isPresent() && sortOrder.isPresent()) {
                rooms = Hotel.getInstance().getRooms(status.get(), sortType.get(), sortOrder.get());
            } else {
                rooms = Hotel.getInstance().getRooms(status.get());
            }
        } else if (sortType.isPresent() && sortOrder.isPresent()) {
            rooms = Hotel.getInstance().getRooms(sortType.get(), sortOrder.get());
        }

        System.out.println("Комнаты:");
        rooms.forEach(OutputHelper::printRoom);
    }
}
