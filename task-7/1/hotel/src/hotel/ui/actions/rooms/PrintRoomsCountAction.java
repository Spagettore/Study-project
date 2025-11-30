package hotel.ui.actions.rooms;

import hotel.Hotel;
import hotel.enums.RoomStatus;
import hotel.ui.Hint;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

import java.util.Optional;

public class PrintRoomsCountAction implements IAction {
    @Override
    public void execute() {
        //определяем фильтр по статусу комнаты
        Optional<RoomStatus> status = InputHelper.chooseEnumValue(RoomStatus.class, new Hint(OutputHelper.getRoomStatusHint(), true));

        if (status.isEmpty()) {
            System.out.printf("Всего комнат: %d%n", Hotel.getInstance().getRoomsCount());
            return;
        }
        System.out.printf("Количество комнат с статусом %s: %d%n",
                OutputHelper.RoomStatusToString(status.get()), Hotel.getInstance().getRoomsCount(status.get()));
    }
}
