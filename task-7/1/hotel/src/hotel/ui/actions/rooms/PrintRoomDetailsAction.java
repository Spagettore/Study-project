package hotel.ui.actions.rooms;

import hotel.Guest;
import hotel.Hotel;
import hotel.RoomRecord;
import hotel.enums.RoomStatus;
import hotel.enums.StarRating;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class PrintRoomDetailsAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id комнаты для вывода детальной информации: ", true);

        Map<String, Object> details;
        try {
            details = Hotel.getInstance().getRoomDetails(id);
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("id: " + details.get("id"));
        System.out.println("Вместимость: " + details.get("capacity") + " Человек");
        System.out.println("Рейтинг:" + OutputHelper.RoomRatingToString((StarRating) details.get("rating")));
        System.out.println("Цена: " + details.get("price") + " Рублей");
        System.out.println("Статус: " + OutputHelper.RoomStatusToString((RoomStatus) details.get("status")));

        List<Guest> guests = (List<Guest>) details.get("guests");
        if (guests.size() > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            LocalDate startDate = (LocalDate) details.get("startDate");
            LocalDate endDate = (LocalDate) details.get("endDate");
            System.out.println("Дата въезда: " + startDate.format(formatter));
            System.out.println("Дата выезда: " + endDate.format(formatter));
            System.out.println("Жильцы: ");
            guests.forEach(OutputHelper::printGuest);
        }

        List<RoomRecord> roomRecordList = (List<RoomRecord>) details.get("guestsHistory");
        OutputHelper.printRoomHistory(roomRecordList);
    }
}
