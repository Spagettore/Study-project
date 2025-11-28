package hotel.ui.actions.csv.imports;

import hotel.Guest;
import hotel.Hotel;
import hotel.Room;
import hotel.RoomRecord;
import hotel.csv.exceptions.CantImportException;
import hotel.csv.imports.Importer;
import hotel.csv.imports.RoomImportResult;
import hotel.csv.imports.RoomRecordImportResult;
import hotel.enums.RoomStatus;
import hotel.enums.StarRating;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ImportRoomsAction implements IAction {
    @Override
    public void execute() {
        //читаем айди комнаты и берем нужное название файла для комнат и их записей
        int roomId = InputHelper.enterNumber("Введите id комнаты(-1 для всех):", false);
        String roomsFileName = "Rooms.csv";
        String roomRecordsFileName = "RoomsRecords.csv";
        if (roomId >= 0) {
            roomsFileName = String.format("Room_%d.csv", roomId);
            roomRecordsFileName = String.format("Room_%d_records.csv", roomId);
        }

        //читаем комнаты из файла
        List<RoomImportResult> roomImportResultList;
        try {
            roomImportResultList = Importer.importCsv(roomsFileName, Room.class, Importer::parseRoom);
        } catch (CantImportException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (roomImportResultList.isEmpty()) {
            System.out.println("Пустой файл");
            return;
        }

        //читаем записи проживания комнат если хотя бы у 1 они есть
        List<RoomRecordImportResult> roomRecordImportResultList = null;
        if (roomImportResultList.stream()
                .anyMatch(r -> !r.getRoomRecordIds().isEmpty())) {
            try {
                roomRecordImportResultList = Importer.importCsv(roomRecordsFileName, RoomRecord.class, Importer::parseRoomRecord);
            } catch (CantImportException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        //собираем объекты записей о проживании для дальнейшего использования в комнатах
        List<RoomRecord> roomRecords = new ArrayList<>();
        if (roomRecordImportResultList != null) {
            for (RoomRecordImportResult roomRecordImportResult : roomRecordImportResultList) {
                int id = roomRecordImportResult.getId();
                ArrayList<Guest> guests = roomRecordImportResult.getGuestIds().stream()
                        .flatMap(guestId -> {
                            try {
                                return Stream.of(Hotel.getInstance().getGuest(guestId));
                            } catch (GuestNotFoundException e) {
                                System.out.println(e.getMessage());
                                return Stream.empty();
                            }
                        })
                        .collect(Collectors.toCollection(ArrayList::new));
                LocalDate startDate = roomRecordImportResult.getStartDate();
                LocalDate endDate = roomRecordImportResult.getEndDate();
                roomRecords.add(new RoomRecord(id, guests, startDate, endDate));
            }
        }

        //добавляем комнаты или обновляем их
        for (RoomImportResult roomImportResult : roomImportResultList) {
            int id = roomImportResult.getId();

            ArrayList<RoomRecord> roomHistory = roomRecords.stream()
                    .filter(r -> roomImportResult.getRoomRecordIds().contains(r.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            int capacity = roomImportResult.getCapacity();
            RoomStatus status = roomImportResult.getStatus();
            int price = roomImportResult.getPrice();

            ArrayList<Guest> guests = roomImportResult.getGuestIds().stream()
                    .flatMap(guestId -> {
                        try {
                            return Stream.of(Hotel.getInstance().getGuest(guestId));
                        } catch (GuestNotFoundException e) {
                            System.out.println(e.getMessage());
                            return Stream.empty();
                        }
                    })
                    .collect(Collectors.toCollection(ArrayList::new));

            StarRating rating = roomImportResult.getRating();
            LocalDate startDate = roomImportResult.getStartDate();
            LocalDate endDate = roomImportResult.getEndDate();

            try {
                Room room = Hotel.getInstance().getRoom(id);
                room.setGuestsHistory(roomHistory);
                room.setCapacity(capacity);
                room.setStatus(status);
                room.setPrice(price);
                room.setGuests(guests);
                room.setRating(rating);
                room.setStartDate(startDate);
                room.setEndDate(endDate);
            } catch (RoomNotFoundException e) {
                Hotel.getInstance().addRoom(new Room(
                        id,
                        roomHistory,
                        capacity,
                        status,
                        price,
                        guests,
                        rating,
                        startDate,
                        endDate)
                );
            }
        }
        System.out.println("Успешно загружено");
    }
}
