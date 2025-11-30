package hotel.ui;

import hotel.*;
import hotel.enums.*;
import hotel.ui.enums.RoomDataType;
import hotel.ui.enums.ServiceDataType;

import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class OutputHelper {
    private OutputHelper() {
    }

    //выводим данные о комнате
    public static void printRoom(Room room) {
        System.out.println("id: " + room.getId());
        System.out.println("Вместимость: " + room.getCapacity() + " Человек");
        System.out.println("Рейтинг: " + RoomRatingToString(room.getRating()));
        System.out.println("Цена: " + room.getPrice() + " Рублей/Месяц");
        System.out.println("Статус: " + RoomStatusToString(room.getStatus()));

        if (room.getGuests().size() > 0) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

            System.out.println("Дата въезда: " + room.getStartDate().format(formatter));
            System.out.println("Дата выезда: " + room.getEndDate().format(formatter));
        }
        System.out.println();
    }

    //выводим информацию гостя
    public static void printGuest(Guest guest) {
        System.out.println("ФИО: " + guest.getFullName());
        System.out.println("Паспорт: " + guest.getPassport());
        System.out.println("Возраст: " + guest.getAge());
        System.out.println();
    }

    //выводим данные о услуге
    public static void printService(Service service) {
        System.out.println("id: " + service.getId());
        System.out.println("Название: " + service.getName());
        System.out.println("Цена: " + service.getPrice() + " Рублей/Месяц");
        System.out.println();
    }

    //выводим данные о услуге гостя
    public static void printService(GuestService guestService) {
        printService(guestService.getService());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println("Дата оформления: " + guestService.getStartDate().format(formatter));
        System.out.println("Дата окончания: " + guestService.getEndDate().format(formatter));
        System.out.println();
    }

    //выводим информацию о заселении номера
    public static void printRoomHistory(List<RoomRecord> roomRecordList) {
        System.out.print("История заселения номера: ");
        if (roomRecordList.size() < 1) {
            System.out.println("Нет");
            return;
        }
        System.out.println();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        AtomicInteger index = new AtomicInteger(1);
        roomRecordList.forEach(roomRecord -> {
            System.out.println("Запись: " + index.getAndIncrement());
            System.out.println("Дата въезда: " + roomRecord.getStartDate().format(formatter));
            System.out.println("Дата выезда: " + roomRecord.getEndDate().format(formatter));
            System.out.println();
            roomRecord.getGuests().forEach(OutputHelper::printGuest);
            System.out.println();
        });
    }

    //статус комнаты в текст
    public static String RoomStatusToString(RoomStatus status) {
        String text = "Неизвестно";
        switch (status) {
            case FREE -> text = "Свободно";
            case OCCUPIED -> text = "Занято";
            case REPAIR -> text = "Ремонт";
            case CLEAN -> text = "Уборка";
        }
        return text;
    }

    //рейтинг комнаты в текст
    public static String RoomRatingToString(StarRating rating) {
        return "✮".repeat(rating.ordinal() + 1);
    }

    //получить подсказку по изменяемым данным комнаты
    public static String getRoomDataTypeHint() {
        StringBuilder hint = new StringBuilder("""
                Введите какие данные будут изменены в комнате:
                0. Завершение
                """);
        for (RoomDataType dataType : RoomDataType.values()) {
            String text = switch (dataType) {
                case STATUS -> "Статус";
                case PRICE -> "Цена";
                case RATING -> "Рейтинг";
            };
            hint.append(dataType.ordinal() + 1).append(". ").append(text).append("\n");
        }
        return hint.toString();
    }

    //получить подсказку по фильтру статус комнаты
    public static String getRoomStatusHint() {
        StringBuilder hint = new StringBuilder("""
                Введите фильтр комнат по статусу:
                0. Ничего
                """);
        for (RoomStatus status : RoomStatus.values()) {
            String text = switch (status) {
                case FREE -> "Свободно";
                case OCCUPIED -> "Занято";
                case REPAIR -> "Ремонт";
                case CLEAN -> "Уборка";
            };
            hint.append(status.ordinal() + 1).append(". ").append(text).append("\n");
        }
        return hint.toString();
    }

    //получить подсказку по признакам сортировки комнат
    public static String getRoomSortTypeHint() {
        StringBuilder hint = new StringBuilder("""
                Введите по какому признаку будет сортировка комнат:
                0. Нет
                """);
        for (RoomSortType sortType : RoomSortType.values()) {
            String text = switch (sortType) {
                case PRICE -> "Цена";
                case CAPACITY -> "Вместимость";
                case RATING -> "Рейтинг";
                case STATUS -> "Статус комнаты";
                case START_DATE -> "Дата заселения";
                case END_DATE -> "Дата выселения";
                case DEFAULT -> "Id";
            };
            hint.append(sortType.ordinal() + 1).append(". ").append(text).append("\n");
        }
        return hint.toString();
    }

    //получить подсказку по рейтингу комнаты
    public static String getRoomRatingHint() {
        StringBuilder hint = new StringBuilder("""
                Введите какой рейтинг будет у комнаты:
                """);
        for (StarRating rating : StarRating.values()) {
            String text = RoomRatingToString(rating);
            hint.append(rating.ordinal()).append(". ").append(text).append("\n");
        }
        return hint.toString();
    }

    //получить подсказку по изменяемым данным услуги
    public static String getServiceDataTypeHint() {
        StringBuilder hint = new StringBuilder("""
                Введите какие данные будут изменены в услуге:
                0. Завершение
                """);
        for (ServiceDataType dataType : ServiceDataType.values()) {
            String text = switch (dataType) {
                case NAME -> "Название";
                case PRICE -> "Цена";
            };
            hint.append(dataType.ordinal() + 1).append(". ").append(text).append("\n");
        }
        return hint.toString();
    }

    //получить подсказку по признакам сортировки услуг
    public static String getServiceSortTypeHint() {
        StringBuilder hint = new StringBuilder("""
                Введите по какому признаку будет сортировка услуг:
                0. Нет
                """);
        for (ServiceSortType sortType : ServiceSortType.values()) {
            String text = switch (sortType) {
                case PRICE -> "Цена";
                case START_DATE -> "Дата оформления";
                case END_DATE -> "Дата окончания";
                case DEFAULT -> "Id";
            };
            hint.append(sortType.ordinal() + 1).append(". ").append(text).append("\n");
        }
        return hint.toString();
    }

    //получить подсказку по признакам сортировки услуг и комнат
    public static String getPricedItemSortTypeHint() {
        StringBuilder hint = new StringBuilder("""
                Введите по какому признаку будет сортировка комнат и услуг:
                0. Нет
                """);
        for (PricedItemSortType sortType : PricedItemSortType.values()) {
            String text = switch (sortType) {
                case PRICE -> "Цена";
                case CATEGORY -> "Категория";
                case DEFAULT -> "Id";
            };
            hint.append(sortType.ordinal() + 1).append(". ").append(text).append("\n");
        }
        return hint.toString();
    }

    //экспорт объекта в csv
    public static <T> void exportCsv(String path, List<T> data, Function<T, List<String>> mapper) {
        path = "SavedData/" + path + ".csv";
        try (FileWriter writer = new FileWriter(path)) {
            for (T item : data) {
                List<String> values = mapper.apply(item);
                writer.write(String.join(";", values));
                writer.write("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
