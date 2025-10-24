package hotel;

import java.io.Serializable;
import java.util.ArrayList;

public class Hotel {
    private ArrayList<Room> rooms = new ArrayList<Room>();              //список комнат
    private ArrayList<Service> services = new ArrayList<Service>();     //список услуг

    //добавить комнату
    public void addRoom(Room room)
    {
        rooms.add(room);
    }
    //убрать комнату
    public void removeRoom(int id)
    {
        this.rooms.removeIf(r -> r.getId() == id);
    }
    //получить список комнат
    public ArrayList<Room> getRooms()
    {
        return this.rooms;
    }
    //получить комнату
    public Room getRoom(int id)
    {
        return this.rooms.stream().filter(r -> r.getId() == id).findFirst().orElse(null);
    }
    //добавить услугу
    public void addService(Service service)
    {
        this.services.add(service);
    }
    //убрать услугу
    public void removeService(int id)
    {
        this.services.removeIf(s -> s.getId() == id);
    }
    //получить список услуг
    public ArrayList<Service> getServices()
    {
        return this.services;
    }
    //получить услугу
    public Service getService(int id)
    {
        return this.services.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }
    //заселить жильцов
    public boolean moveIn(int room_id, ArrayList<Guest> guests)
    {
        Room room = getRoom(room_id);
        if(room.getStatus() == RoomStatus.REPAIR)
        {
            return false;
        }
        this.getRoom(room_id).setGuests(guests);
        return true;
    }
    //выселить жильцов
    public boolean moveOut(int room_id)
    {
        this.getRoom(room_id).removeGuests();
        return true;
    }
    //поменять статус комнаты
    public void changeRoomStatus(int id, RoomStatus status)
    {
        this.getRoom(id).setStatus(status);
    }
    //поменять цену комнаты
    public void changeRoomPrice(int id, Double price)
    {
        this.getRoom(id).setPrice(price);
    }
    //поменять цену услуги
    public void changeServicePrice(int id, Double price)
    {
        this.getService(id).setPrice(price);
    }
}
