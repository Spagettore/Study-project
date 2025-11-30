package hotel.ui.actions.csv.export;

import hotel.Hotel;
import hotel.Room;
import hotel.csv.Exporter;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

import java.util.List;
import java.util.stream.Collectors;

public class ExportRoomsAction implements IAction {
    @Override
    public void execute() {
        //читаем айди номера и берем нужное название файла для номеров и их записей проживания
        int id = InputHelper.enterNumber("Введите id номера(-1 для всех):", false);
        String roomsFileName = "Rooms";
        String roomsRecordsFileName = "RoomsRecords";
        List<Room> rooms;
        if (id >= 0) {
            roomsFileName = String.format("Room_%d", id);
            roomsRecordsFileName = String.format("Room_%d_records", id);
            try {
                rooms = List.of(Hotel.getInstance().getRoom(id));
            } catch (RoomNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            rooms = Hotel.getInstance().getRooms();
        }

        try {
            System.out.println("Сохранения данных о номерах");
            Exporter.exportCsv(roomsFileName, rooms, Exporter::parseRoom);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            System.out.println("Сохранения данных о записях проживания в номере");
            Exporter.exportCsv(
                    roomsRecordsFileName,
                    rooms.stream()
                            .flatMap(room -> room.getGuestsHistory().stream())
                            .collect(Collectors.toList()),
                    Exporter::parseRoomRecord);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Успешно сохранено");
    }
}
