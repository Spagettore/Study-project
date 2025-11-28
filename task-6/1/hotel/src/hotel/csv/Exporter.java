package hotel.csv;

import hotel.*;
import hotel.csv.exceptions.CantExportException;
import hotel.csv.exceptions.NoExportDataException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Exporter {
    //чтение файла с указанием функции для разбора данных
    public static <T> void exportCsv(String fileName, List<T> data, Function<T, List<String>> mapper) throws NoExportDataException, CantExportException {
        if (data == null || data.size() == 0) {
            throw new NoExportDataException();
        }
        String className = data.get(0).getClass().getSimpleName();
        String path = String.format("%s/SavedData/%s", new File("").getAbsolutePath(), className);

        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        File file = new File(path, fileName + ".csv");
        if (file.exists() && !file.isDirectory()) {
            if (!file.delete()) {
                throw new CantExportException("Невозможно перезаписать файл");
            }
        }

        try (FileWriter writer = new FileWriter(file)) {
            for (T item : data) {
                List<String> values = mapper.apply(item);
                writer.write(String.join(";", values));
                writer.write("\n");
            }
        } catch (IOException ioException) {
            throw new CantExportException(ioException.getMessage());
        }
    }

    //парсер для номера
    public static List<String> parseRoom(Room room) {
        return List.of(
                String.valueOf(room.getId()),
                room.getGuestsHistory().stream().map(RoomRecord::getId).map(String::valueOf).collect(Collectors.joining(",")),
                String.valueOf(room.getCapacity()),
                String.valueOf(room.getStatus()),
                String.valueOf(room.getPrice()),
                room.getGuests().stream().map(Guest::getId).map(String::valueOf).collect(Collectors.joining(",")),
                String.valueOf(room.getRating()),
                String.valueOf(room.getStartDate() != null ? room.getStartDate() : ""),
                String.valueOf(room.getEndDate() != null ? room.getEndDate() : "")
        );
    }

    //парсер для записи о проживании в номере
    public static List<String> parseRoomRecord(RoomRecord roomRecord) {
        return List.of(
                String.valueOf(roomRecord.getId()),
                roomRecord.getGuests().stream().map(Guest::getId).map(String::valueOf).collect(Collectors.joining(",")),
                String.valueOf(roomRecord.getStartDate()),
                String.valueOf(roomRecord.getEndDate())
        );
    }

    //парсер для жильца
    public static List<String> parseGuest(Guest guest) {
        return List.of(
                String.valueOf(guest.getId()),
                String.valueOf(guest.getName()),
                String.valueOf(guest.getFamily()),
                String.valueOf(guest.getSecondName()),
                String.valueOf(guest.getPassport()),
                String.valueOf(guest.getAge()),
                guest.getServices().stream().map(GuestService::getId).map(String::valueOf).collect(Collectors.joining(","))
        );
    }

    //парсер для услуги
    public static List<String> parseService(Service service) {
        return List.of(
                String.valueOf(service.getId()),
                String.valueOf(service.getName()),
                String.valueOf(service.getPrice())
        );
    }

    //парсер для услуги жильца
    public static List<String> parseGuestService(GuestService record) {
        return List.of(
                String.valueOf(record.getId()),
                String.valueOf(record.getServiceId()),
                String.valueOf(record.getStartDate()),
                String.valueOf(record.getEndDate())
        );
    }
}
