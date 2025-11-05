import hotel.*;
import hotel.enums.*;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.RoomNotFoundException;
import hotel.exceptions.ServiceNotFoundException;
import hotel.interfaces.PricedItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {

    //выводим информацию о комнате
    private static void printRooms(List<Room> rooms) {
        System.out.println("Комнаты:");
        for (Room room : rooms) {
            System.out.println(
                    "id = " + room.getId()
                            + ", Вместимость = " + room.getCapacity()
                            + ", Рейтинг = " + room.getRating()
                            + ", Цена = " + room.getPrice()
                            + ", Статус = " + room.getStatus()
                            + ", Дата въезда = " + (room.getStartDate() != null ? room.getStartDate() : "Нет")
                            + ", Дата выезда = " + (room.getEndDate() != null ? room.getEndDate() : "Нет"));
        }
        System.out.println();
    }

    //выводим детали о комнате
    private static void printRoomDetails(Map<String, Object> details) {
        System.out.println(
                "id = " + details.get("id")
                        + ", Вместимость = " + details.get("capacity")
                        + ", Рейтинг = " + details.get("rating")
                        + ", Цена = " + details.get("price")
                        + ", Статус = " + details.get("status")
                        + ", Дата въезда = " + (details.get("startDate") != null ? details.get("startDate") : "Нет")
                        + ", Дата выезда = " + (details.get("endDate") != null ? details.get("endDate") : "Нет"));

        List<RoomRecord> roomRecordList = (List<RoomRecord>) details.get("guestsHistory");
        printRoomHistory(roomRecordList, (int) details.get("id"));

        List<Guest> guests = (List<Guest>) details.get("guests");
        System.out.println("Жильцы:");
        if (guests.size() < 1) {
            System.out.println("Нет");
        } else {
            guests.forEach(Main::printGuest);
        }
        System.out.println();
    }

    //выводим информацию о заселении номера
    private static void printRoomHistory(List<RoomRecord> historyList, int id) {
        System.out.println("История заселения номера " + id + ":");

        if (historyList.size() < 1) {
            System.out.println("Нет");
            return;
        }

        historyList.forEach(roomRecord -> {
            System.out.println("Дата въезда = " + roomRecord.getStartDate()
                    + ", Дата выезда = " + roomRecord.getEndDate());
            roomRecord.getGuests().forEach(Main::printGuest);
            System.out.println();
        });
    }

    //выводим список жильцов и их номера
    private static void printGuestsAndRooms(Map<Guest, List<Room>> guestsRooms) {
        System.out.println("Список жильцов и их номеров:");
        guestsRooms.forEach((guest, rooms) -> {
            printGuest(guest);
            printRooms(rooms);
        });
        System.out.println();
    }

    //выводим информацию гостя
    private static void printGuest(Guest guest) {
        System.out.println(
                "ФИО = " + guest.getFullName()
                        + ", Паспорт = " + guest.getPassport());
    }

    //выводим информацию про услуги
    private static void printServices(List<Service> services) {
        System.out.println("Услуги:");
        for (Service service : services) {
            System.out.println(
                    "id = " + service.getId()
                            + ", Название = " + service.getName()
                            + ", Цена = " + service.getPrice()
                            + (service.getStartDate() != null ? ", Дата начала = " + service.getStartDate() : "")
                            + (service.getEndDate() != null ? ", Дата начала = " + service.getEndDate() : ""));
        }
        System.out.println();
    }

    //выводим информацию про комнаты и услуги
    private static void printPrices(List<PricedItem> itemList) {
        System.out.println("Список цен:");
        itemList.forEach(item -> System.out.println(
                "id = " + item.getId()
                        + ", Категория = " + item.getCategory()
                        + ", Цена = " + item.getPrice()));
        System.out.println();
    }

    public static void main(String[] args) {
        //создаем отель
        Hotel hotel = new Hotel();

        //добавляем комнаты
        hotel.addRoom(new Room(50, 2));
        hotel.addRoom(new Room(100, 1));
        hotel.addRoom(new Room(150, 3));
        hotel.addRoom(new Room(200, 1));
        hotel.addRoom(new Room(250, 4));
        hotel.addRoom(new Room(300, 2));
        hotel.addRoom(new Room(120, 3));
        hotel.addRoom(new Room(230, 1));
        hotel.addRoom(new Room(111, 2));

        //убираем комнату 8
        hotel.removeRoom(8);

        //меняем рейтинг комнат
        try {
            hotel.changeRoomRating(0, StarRating.TWO_STAR);
            hotel.changeRoomRating(1, StarRating.THREE_STAR);
            hotel.changeRoomRating(3, StarRating.FOUR_STAR);
            hotel.changeRoomRating(5, StarRating.FOUR_STAR);
            hotel.changeRoomRating(6, StarRating.FIVE_STAR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //меняем статус комнат
        try {
            hotel.changeRoomStatus(2, RoomStatus.REPAIR);
            hotel.changeRoomStatus(3, RoomStatus.CLEAN);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //меняем цену комнаты
        try {
            hotel.changeRoomPrice(6, 2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //выводим все комнаты
        System.out.println("Просто все комнаты");
        printRooms(hotel.getRooms());

        System.out.println("Все комнаты по цене");
        printRooms(hotel.getRooms(RoomSortType.PRICE, SortOrder.ASC));
        System.out.println("Все комнаты по вместимости");
        printRooms(hotel.getRooms(RoomSortType.CAPACITY, SortOrder.ASC));
        System.out.println("Все комнаты по рейтингу");
        printRooms(hotel.getRooms(RoomSortType.RATING, SortOrder.ASC));
        System.out.println("Свободные комнаты по цене");
        printRooms(hotel.getRooms(RoomStatus.FREE, RoomSortType.PRICE, SortOrder.ASC));
        System.out.println("Свободные комнаты по вместимости");
        printRooms(hotel.getRooms(RoomStatus.FREE, RoomSortType.CAPACITY, SortOrder.ASC));
        System.out.println("Свободные комнаты по рейтингу");
        printRooms(hotel.getRooms(RoomStatus.FREE, RoomSortType.RATING, SortOrder.ASC));

        //добавляем услуги
        hotel.addService(new Service("Завтрак", 150));
        hotel.addService(new Service("Спа", 300));
        hotel.addService(new Service("Массаж", 220));
        hotel.addService(new Service("Обед", 180));
        hotel.addService(new Service("Ужин", 200));
        hotel.addService(new Service("Газета", 150));

        //убираем услугу 5
        hotel.removeService(5);

        //меняем цену услуги
        try {
            hotel.changeServicePrice(1, 450);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //выводим все услуги
        printServices(hotel.getServices());

        //создаем гостей
        Guest alex = new Guest("Алексей", "Тарьковский", "Дмитриевич", "111", 25);
        Guest dmitriy = new Guest("Дмитрий", "Кузнецов", "Владимирович", "222", 34);
        Guest nadya = new Guest("Надежда", "Абобова", "Александровна", "333", 27);
        Guest vladimir = new Guest("Владимир", "Зорин", "Валерьевич", "444", 32);

        //заселяем гостей
        try {
            hotel.moveIn(1, new ArrayList<>(List.of(alex)), 70);
            hotel.moveIn(7, new ArrayList<>(List.of(alex)), 7);
            hotel.moveIn(4, new ArrayList<>(List.of(dmitriy)), 60);
            hotel.moveIn(5, new ArrayList<>(List.of(nadya, vladimir)), 180);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //выселяем гостей и селим гостей
        try {
            hotel.moveIn(6, new ArrayList<>(List.of(alex)), 14);
            hotel.moveOut(6);
            hotel.moveIn(6, new ArrayList<>(List.of(alex, dmitriy)), 30);
            hotel.moveOut(6);
            hotel.moveIn(6, new ArrayList<>(List.of(alex, dmitriy, nadya)), 77);
            hotel.moveOut(6);
            hotel.moveIn(6, new ArrayList<>(List.of(nadya, vladimir)), 180);
            hotel.moveOut(6);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //выводим список гостей и их номеров
        printGuestsAndRooms(hotel.getGuestsAndRooms());

        //выводим количество свободных номеров
        printRooms(hotel.getRooms());
        System.out.println("Количество свободных номеров = " + hotel.getRoomsCount(RoomStatus.FREE));

        //выводим количество жильцов
        System.out.println("Количество жильцов = " + hotel.getGuestsCount());
        System.out.println();

        //номера свободные через месяц
        printRooms(hotel.getRoomsByDate(LocalDate.now().plusDays(30)));

        //сумма за проживание
        try {
            int alexRoomId = hotel.getRooms(alex).get(0).getId();
            System.out.println("ФИО = " + alex.getFullName() + " оплатит " + hotel.getRoomTotalPrice(alexRoomId) + " денег за номер с id = " + alexRoomId);
            System.out.println();
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }

        //последние жильцы номера
        try {
            printRoomHistory(hotel.getRoomHistory(6, 3), 6);
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }

        //добавляем услуги жильцу
        try {
            hotel.addGuestService(alex.getPassport(), 2, 30);
            hotel.addGuestService(alex.getPassport(), 1, 14);
        } catch (ServiceNotFoundException | GuestNotFoundException e) {
            e.printStackTrace();
        }

        //список услуг жильца
        try {
            printGuest(alex);
            printServices(hotel.getGuestServices(alex.getPassport(), ServiceSortType.PRICE, SortOrder.ASC));
            printServices(hotel.getGuestServices(alex.getPassport(), ServiceSortType.END_DATE, SortOrder.ASC));
        } catch (GuestNotFoundException e) {
            e.printStackTrace();
        }

        //выводим список цен на комнаты и услуги
        printPrices(hotel.getPricedItems());
        printPrices(hotel.getPricedItems(PricedItemSortType.CATEGORY, SortOrder.DESC));
        printPrices(hotel.getPricedItems(PricedItemSortType.PRICE, SortOrder.ASC));

        //выводим детали комнаты
        try {
            printRoomDetails(hotel.getRoomDetails(1));
        } catch (RoomNotFoundException e) {
            e.printStackTrace();
        }
    }
}
