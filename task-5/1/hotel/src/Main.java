import hotel.Guest;
import hotel.Hotel;
import hotel.Room;
import hotel.Service;
import hotel.enums.RoomStatus;
import hotel.enums.StarRating;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.ServiceNotFoundException;
import hotel.ui.MenuController;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //создаем отель
        Hotel hotel = Hotel.getInstance();

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

        //добавляем услуги жильцу
        try {
            hotel.addGuestService(alex.getPassport(), 2, 30);
            hotel.addGuestService(alex.getPassport(), 1, 14);
        } catch (ServiceNotFoundException | GuestNotFoundException e) {
            e.printStackTrace();
        }

        MenuController.getInstance().run();
    }
}
