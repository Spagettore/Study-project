package hotel.ui.actions.rooms;

import hotel.Hotel;
import hotel.Room;
import hotel.enums.RoomStatus;
import hotel.enums.StarRating;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.Hint;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;
import hotel.ui.enums.RoomDataType;

import java.util.Optional;

public class ChangeRoomAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id комнаты для изменения: ", true);
        Room room;
        try {
            room = Hotel.getInstance().getRoom(id);
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        while (true) {
            Optional<RoomDataType> dataType = InputHelper.chooseEnumValue(RoomDataType.class,
                    new Hint(OutputHelper.getRoomDataTypeHint(), true));
            if (dataType.isEmpty()) {
                break;
            }

            switch (dataType.get()) {
                case STATUS -> {
                    if (!Hotel.getInstance().getConfig().getAllowChangeStatus()) {
                        System.out.println("Запрещено менять статус комнаты в конфиге");
                        continue;
                    }
                    Optional<RoomStatus> status = InputHelper.chooseEnumValue(RoomStatus.class,
                            new Hint(OutputHelper.getRoomStatusHint(), true));
                    if (status.isEmpty()) {
                        continue;
                    }
                    Hotel.getInstance().changeRoomStatus(room, status.get());
                }
                case PRICE -> {
                    int price = InputHelper.enterNumber("Введите новую цену комнаты: ", true);
                    Hotel.getInstance().changeRoomPrice(room, price);
                }
                case RATING -> {
                    Optional<StarRating> rating = InputHelper.chooseEnumValue(StarRating.class,
                            new Hint(OutputHelper.getRoomRatingHint(), false));
                    if (rating.isEmpty()) {
                        continue;
                    }
                    Hotel.getInstance().changeRoomRating(room, rating.get());
                }
            }
            OutputHelper.printRoom(room);
        }
    }
}
