package hotel.csv.imports;

import hotel.csv.exceptions.CantImportException;
import hotel.enums.RoomStatus;
import hotel.enums.StarRating;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class Importer {
    //чтение файла с указанием функции и класса для разбора данных
    public static <T, C> List<T> importCsv(String fileName, Class<C> dataType, Function<List<String>, T> mapper) throws CantImportException {
        String className = dataType.getSimpleName();
        String path = String.format("%s/SavedData/%s", new File("").getAbsolutePath(), className);
        File file = new File(path, fileName);
        if (!file.exists()) {
            throw new CantImportException(String.format("Файл: %s не обнаружен", file.getPath()));
        }

        List<T> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                List<String> data = List.of(line.split(";", -1));

                T obj = mapper.apply(data);
                result.add(obj);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    //парсер для услуг гостей
    public static GuestServiceImportResult parseGuestService(List<String> data) {
        int id = Integer.parseInt(data.get(0));
        int serviceId = Integer.parseInt(data.get(1));
        LocalDate startDate = LocalDate.parse(data.get(2));
        LocalDate endDate = LocalDate.parse(data.get(3));
        return new GuestServiceImportResult(id, serviceId, startDate, endDate);
    }

    //парсер для гостей
    public static GuestImportResult parseGuest(List<String> data) {
        int id = Integer.parseInt(data.get(0));
        String name = data.get(1);
        String family = data.get(2);
        String secondName = data.get(3);
        String passport = data.get(4);
        int age = Integer.parseInt(data.get(5));
        List<Integer> guestServiceIds = Arrays.stream(data.get(6).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
        return new GuestImportResult(id, name, family, secondName, passport, age, guestServiceIds);
    }

    //парсер для услуг
    public static ServiceImportResult parseService(List<String> data) {
        int id = Integer.parseInt(data.get(0));
        String name = data.get(1);
        int price = Integer.parseInt(data.get(2));
        return new ServiceImportResult(id, name, price);
    }

    //парсер для записей о проживании в номере
    public static RoomRecordImportResult parseRoomRecord(List<String> data) {
        int id = Integer.parseInt(data.get(0));
        List<Integer> guestIds = Arrays.stream(data.get(1).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
        LocalDate startDate = LocalDate.parse(data.get(2));
        LocalDate endDate = LocalDate.parse(data.get(3));
        return new RoomRecordImportResult(id, guestIds, startDate, endDate);
    }

    //парсер номера
    public static RoomImportResult parseRoom(List<String> data) {
        int id = Integer.parseInt(data.get(0));
        List<Integer> roomRecordIds = Arrays.stream(data.get(1).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
        int capacity = Integer.parseInt(data.get(2));
        RoomStatus status = RoomStatus.valueOf(data.get(3));
        int price = Integer.parseInt(data.get(4));
        List<Integer> guestIds = Arrays.stream(data.get(5).split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Integer::parseInt)
                .toList();
        StarRating rating = StarRating.valueOf(data.get(6));
        LocalDate startDate = !data.get(7).isEmpty() ? LocalDate.parse(data.get(7)) : null;
        LocalDate endDate = !data.get(8).isEmpty() ? LocalDate.parse(data.get(7)) : null;
        return new RoomImportResult(id, roomRecordIds, capacity, status, price, guestIds, rating, startDate, endDate);
    }
}