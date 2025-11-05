package hotel;

import hotel.enums.PricedItemCategory;
import hotel.enums.RoomStatus;
import hotel.enums.StarRating;
import hotel.interfaces.PricedItem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Room implements Comparable<Room>, PricedItem {
    private static int roomCount = 0;                                               //общее количество комнат
    private final int id;                                                           //айди
    private final ArrayList<RoomRecord> guestsHistory = new ArrayList<>();        //последние жильцы и дата их проживания
    private RoomStatus status = RoomStatus.FREE;                                    //статус комнаты
    private int price;                                                              //цена
    private ArrayList<Guest> guests = new ArrayList<>();                           //список жильцов
    private StarRating rating = StarRating.ONE_STAR;                                //рейтинг комнаты
    private int capacity = 1;                                                       //максимальное количество жильцов
    private LocalDate startDate;                                                    //дата заселения
    private LocalDate endDate;                                                      //дата выселения

    //конструктор комнаты
    public Room(int price, int capacity) {
        this.id = roomCount++;
        this.price = price;
        this.capacity = capacity;
    }

    //дефолт сравнение по цене
    @Override
    public int compareTo(Room other) {
        return Integer.compare(this.price, other.price);
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public PricedItemCategory getCategory() {
        return PricedItemCategory.ROOM;
    }

    //получить статус комнаты
    public RoomStatus getStatus() {
        return this.status;
    }

    //установить статус комнаты
    public void setStatus(RoomStatus status) {
        this.status = status;
    }

    //получить цену комнаты
    @Override
    public int getPrice() {
        return this.price;
    }

    //изменить цену комнаты
    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    //получить список жильцов
    public ArrayList<Guest> getGuests() {
        return this.guests;
    }

    //заселить жильцов
    public void setGuests(ArrayList<Guest> guests, int days) {
        this.guests = guests;
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(days);
        this.setStatus(RoomStatus.OCCUPIED);
    }

    //выселить жильцов
    public void removeGuests(boolean early) {
        if (early) {
            this.endDate = LocalDate.now();
        }
        this.guestsHistory.add(new RoomRecord(guests, startDate, endDate));
        this.guests.clear();
        this.startDate = null;
        this.endDate = null;
        this.setStatus(RoomStatus.FREE);
    }

    //получить рейтинг
    public StarRating getRating() {
        return this.rating;
    }

    //установить рейтинг
    public void setRating(StarRating rating) {
        this.rating = rating;
    }

    //получить максимальное количество жильцов
    public int getCapacity() {
        return this.capacity;
    }

    //получить дату когда заняли номер
    public LocalDate getStartDate() {
        return this.startDate;
    }

    //получить дату освобождения номера
    public LocalDate getEndDate() {
        return this.endDate;
    }

    //получить историю проживания
    public List<RoomRecord> getGuestsHistory(int count) {
        return this.guestsHistory.stream()
                .skip(Math.max(guestsHistory.size() - count, 0))
                .collect(Collectors.toList());
    }

    public List<RoomRecord> getGuestsHistory() {
        return this.guestsHistory;
    }
}
