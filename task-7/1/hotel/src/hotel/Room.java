package hotel;

import hotel.enums.PricedItemCategory;
import hotel.enums.RoomStatus;
import hotel.enums.StarRating;
import hotel.exceptions.GuestNotFoundException;
import hotel.interfaces.PricedItem;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Room implements Comparable<Room>, PricedItem, Externalizable {
    private static final long serialVersionUID = 1L;
    private static int roomCount = 0;                                               //общее количество комнат
    private int id;                                                                 //айди
    private int capacity;                                                           //максимальное количество жильцов
    private ArrayList<RoomRecord> guestsHistory = new ArrayList<>();                //последние жильцы и дата их проживания
    private RoomStatus status = RoomStatus.FREE;                                    //статус комнаты
    private int price;                                                              //цена
    private ArrayList<Guest> guests = new ArrayList<>();                            //список жильцов
    private StarRating rating = StarRating.ONE_STAR;                                //рейтинг комнаты
    private LocalDate startDate;                                                    //дата заселения
    private LocalDate endDate;                                                      //дата выселения

    //конструктор комнаты
    public Room() {
    }

    public Room(int price, int capacity) {
        this.id = roomCount++;
        this.price = price;
        this.capacity = capacity;
    }

    public Room(int id, ArrayList<RoomRecord> guestsHistory, int capacity,
                RoomStatus status, int price, ArrayList<Guest> guests, StarRating rating,
                LocalDate startDate, LocalDate endDate) {
        if (id > roomCount) {
            roomCount = id + 1;
        }
        this.id = id;
        this.guestsHistory = guestsHistory;
        this.capacity = capacity;
        this.status = status;
        this.price = price;
        this.guests = guests;
        this.rating = rating;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public void setGuests(ArrayList<Guest> guests) {
        this.guests = guests;
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
        if (this.guests.size() < 1) {
            return;
        }
        if (early) {
            this.endDate = LocalDate.now();
        }
        this.guestsHistory.add(new RoomRecord(this.guests, this.startDate, this.endDate));
        this.guests.clear();
        this.startDate = null;
        this.endDate = null;
        this.setStatus(RoomStatus.FREE);
    }

    //выселить жильца
    public void removeGuest(int id) throws GuestNotFoundException {
        if (this.guests.size() < 1) {
            return;
        }
        Guest guest = this.guests.stream().filter(g -> g.getId() == id).findAny().orElseThrow(() -> new GuestNotFoundException(id));
        if (this.guests.size() == 1) {
            removeGuests(true);
            return;
        }
        LocalDate endDate = LocalDate.now();
        this.guestsHistory.add(new RoomRecord(List.of(guest), this.startDate, endDate));
        this.guests.remove(guest);
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

    //установить максимальное количество жильцов
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    //получить дату когда заняли номер
    public LocalDate getStartDate() {
        return this.startDate;
    }

    //установить дату когда заняли номер
    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    //получить дату освобождения номера
    public LocalDate getEndDate() {
        return this.endDate;
    }

    //установить дату освобождения номера
    public void setEndDate(LocalDate date) {
        this.endDate = date;
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

    //установить историю проживания
    public void setGuestsHistory(ArrayList<RoomRecord> roomRecords) {
        this.guestsHistory = roomRecords;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeInt(this.capacity);
        out.writeObject(this.status);
        out.writeInt(this.price);
        out.writeObject(this.rating);
        out.writeObject(this.startDate);
        out.writeObject(this.endDate);

        out.writeInt(this.guestsHistory.size());
        for (RoomRecord history : this.guestsHistory) {
            out.writeObject(history);
        }

        out.writeInt(this.guests.size());
        for (Guest guest : this.guests) {
            out.writeInt(guest.getId());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        if (this.id > roomCount) {
            roomCount = this.id + 1;
        }
        this.capacity = in.readInt();
        this.status = (RoomStatus) in.readObject();
        this.price = in.readInt();
        this.rating = (StarRating) in.readObject();
        this.startDate = (LocalDate) in.readObject();
        this.endDate = (LocalDate) in.readObject();

        int historyCount = in.readInt();
        for (int i = 0; i < historyCount; i++) {
            this.guestsHistory.add((RoomRecord) in.readObject());
        }

        int guestsCount = in.readInt();
        for (int i = 0; i < guestsCount; i++) {
            try {
                this.guests.add(Hotel.getInstance().getGuest(in.readInt()));
            } catch (GuestNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
