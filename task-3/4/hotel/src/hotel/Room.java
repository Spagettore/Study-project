package hotel;

import java.util.ArrayList;

public class Room {
    private int id;                                 //айди
    private RoomStatus status = RoomStatus.FREE;    //статус комнаты
    private Double price;                           //цена
    private ArrayList<Guest> guests;                //список жильцов
    private static int roomCount = 0;               //общее количество комнат

    //конструктор комнаты
    public Room(Double price)
    {
        this.id = roomCount++;
        this.price = price;
    }
    //получить айди
    public int getId()
    {
        return this.id;
    }
    //получить статус комнаты
    public RoomStatus getStatus()
    {
        return this.status;
    }
    //установить статус комнаты
    public void setStatus(RoomStatus status)
    {
        this.status = status;
    }
    //получить цену комнаты
    public Double getPrice()
    {
        return this.price;
    }
    //изменить цену комнаты
    public void setPrice(Double price)
    {
        this.price = price;
    }
    //получить список жильцов
    public ArrayList<Guest> getGuests()
    {
        return this.guests;
    }
    //заселить жильцов
    public void setGuests(ArrayList<Guest> guests)
    {
        this.guests = guests;
        this.setStatus(RoomStatus.OCCUPIED);
    }
    //выселить жильцов
    public void removeGuests()
    {
        this.guests = null;
        this.setStatus(RoomStatus.FREE);
    }
}
