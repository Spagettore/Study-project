import hotel.*;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static void printRooms(Hotel hotel)
    {
        //выводим цену комнат,статус,айди и жильцов
        ArrayList<Room> rooms = hotel.getRooms();
        System.out.println("Комнаты:");
        for(int i = 0; i < rooms.size(); i++)
        {
            Room room = rooms.get(i);
            System.out.println(room.getId() + ":" + room.getPrice() + ":" + room.getStatus());

            //выводим жильцов
            ArrayList<Guest> guests = room.getGuests();
            if(guests != null) {
                for (int j = 0; j < guests.size(); j++) {
                    System.out.println(guests.get(j).getFullName());
                }
            }
            else
            {
                System.out.println("Никого нет");
            }
            System.out.println();
        }
        System.out.println();
    }
    private static void printServices(Hotel hotel)
    {
        //выводим цену услуги,название,айди
        System.out.println("Услуги:");
        ArrayList<Service> services = hotel.getServices();
        for(int i = 0; i < services.size(); i++)
        {
            Service service = services.get(i);
            System.out.println(service.getId() + ":" + service.getName() + ":" + service.getPrice());
        }
        System.out.println();
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        //переводим кодировку на windows-1251 для отображения русских символов
        System.setOut(new PrintStream(System.out, true, "Cp1251"));
        //создаем отель
        Hotel hotel = new Hotel();

        //добавляем комнаты
        hotel.addRoom(new Room(50.0));
        hotel.addRoom(new Room(100.0));
        hotel.addRoom(new Room(150.0));
        hotel.addRoom(new Room(200.0));
        hotel.addRoom(new Room(250.0));
        printRooms(hotel);

        //меняем цену комнаты и статус, убираем комнату 3
        hotel.changeRoomPrice(2, 2000.0);
        hotel.changeRoomStatus(2, RoomStatus.REPAIR);
        hotel.removeRoom(3);
        printRooms(hotel);

        //добавляем услуги
        hotel.addService(new Service("Помыть полы", 150.0));
        hotel.addService(new Service("Убрать мусор", 100.0));
        hotel.addService(new Service("Завтрак", 200.0));
        hotel.addService(new Service("Обед", 300.0));
        hotel.addService(new Service("Ужин", 300.0));
        printServices(hotel);

        //изменяем цену завтрака, убираем уборку мусора
        hotel.changeServicePrice(2, 155.0);
        hotel.removeService(1);
        printServices(hotel);

        //создаем гостей
        Guest alex = new Guest("Алексей", "Кузнецов", "Владимирович", "521", 22);
        Guest dmitriy = new Guest("Дмитрий", "Шевцов", "Александрович", "123", 34);
        Guest igor = new Guest("Игорь", "Иванов", "Иванович", "421", 44);
        Guest nadya = new Guest("Надежда", "Иванова", "Александровна", "632", 41);

        //заселяем
        System.out.println(hotel.moveIn(0, new ArrayList<Guest>(Arrays.asList(alex))) ? "Успех" : "Провал");
        System.out.println(hotel.moveIn(4, new ArrayList<Guest>(Arrays.asList(igor, nadya)))  ? "Успех" : "Провал");
        System.out.println(hotel.moveIn(2, new ArrayList<Guest>(Arrays.asList(dmitriy)))  ? "Успех" : "Провал");
        System.out.println();
        printRooms(hotel);

        System.out.println(hotel.moveOut(0) ? "Успех" : "Провал");
        System.out.println(hotel.moveIn(1, new ArrayList<Guest>(Arrays.asList(dmitriy))) ? "Успех" : "Провал");
        System.out.println();
        printRooms(hotel);
    }
}
