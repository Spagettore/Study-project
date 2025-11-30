package hotel;

import hotel.enums.*;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.RoomNotFoundException;
import hotel.exceptions.ServiceNotFoundException;
import hotel.interfaces.PricedItem;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Hotel implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static Hotel instance;                                //ссылка на единственный отель
    private final List<Guest> guests = new ArrayList<>();         //список гостей
    private final List<Room> rooms = new ArrayList<>();           //список комнат
    private final List<Service> services = new ArrayList<>();     //список услуг
    private final HotelConfig config;

    public Hotel() {
        this.config = new HotelConfig();
    }

    //получить отель и при необходимости создать его
    public static Hotel getInstance() {
        if (instance == null) {
            instance = new Hotel();
        }
        return instance;
    }

    //получить список комнат
    public List<Room> getRooms() {
        return this.rooms;
    }

    public List<Room> getRooms(RoomSortType sortType, SortOrder order) {
        Comparator<Room> comparator = switch (sortType) {
            case PRICE -> Comparator.comparing(Room::getPrice);
            case CAPACITY -> Comparator.comparing(Room::getCapacity);
            case RATING -> Comparator.comparing(r -> r.getRating().ordinal());
            case STATUS -> Comparator.comparing(r -> r.getStatus().ordinal());
            case START_DATE -> Comparator.comparing(Room::getStartDate);
            case END_DATE -> Comparator.comparing(Room::getEndDate);
            default -> Comparator.comparing(Room::getId);
        };

        if (order == SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        return this.getRooms().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public List<Room> getRooms(RoomStatus status, RoomSortType sortType, SortOrder order) {
        return this.getRooms(sortType, order).stream()
                .filter(r -> r.getStatus() == status)
                .collect(Collectors.toList());
    }

    public List<Room> getRooms(RoomStatus status) {
        return this.getRooms(status, RoomSortType.DEFAULT, SortOrder.ASC);
    }

    public List<Room> getRooms(Guest guest) {
        return this.getRooms().stream()
                .filter(r -> r.getGuests().contains(guest))
                .collect(Collectors.toList());
    }

    //получить свободную комнату к определенной дате
    public List<Room> getRoomsByDate(LocalDate date) {
        return this.getRooms().stream()
                .filter(r -> r.getEndDate() == null || r.getEndDate().isBefore(date))
                .collect(Collectors.toList());
    }

    //получить количество комнат
    public int getRoomsCount() {
        return this.getRooms().size();
    }

    public int getRoomsCount(RoomStatus status) {
        return this.getRooms(status).size();
    }

    //получить комнату
    public Room getRoom(int id) throws RoomNotFoundException {
        return this.rooms.stream()
                .filter(r -> r.getId() == id)
                .findAny().orElseThrow(() -> new RoomNotFoundException(id));
    }

    //получить детали комнаты
    public Map<String, Object> getRoomDetails(int id) throws RoomNotFoundException {
        Room room = this.getRoom(id);

        Map<String, Object> details = new HashMap<>();
        details.put("id", room.getId());
        details.put("status", room.getStatus());
        details.put("price", room.getPrice());
        details.put("guests", room.getGuests());
        details.put("rating", room.getRating());
        details.put("capacity", room.getCapacity());
        details.put("startDate", room.getStartDate());
        details.put("endDate", room.getEndDate());
        details.put("guestsHistory", room.getGuestsHistory());
        return details;
    }

    //получить сумму за номер
    public int getRoomTotalPrice(int id) throws RoomNotFoundException {
        Room room = getRoom(id);
        if (room.getStartDate() == null || room.getEndDate() == null) {
            return 0;
        }
        long days = room.getStartDate().datesUntil(room.getEndDate()).count();
        return (int) (room.getPrice() * (days / 30.0));
    }

    //получить историю проживания в номере
    public List<RoomRecord> getRoomHistory(int id) throws RoomNotFoundException {
        return this.getRoom(id).getGuestsHistory();
    }

    public List<RoomRecord> getRoomHistory(int id, int count) throws RoomNotFoundException {
        return this.getRoom(id).getGuestsHistory(count);
    }

    //добавить комнату
    public void addRoom(Room room) {
        rooms.add(room);
    }

    //убрать комнату
    public void removeRoom(int id) {
        this.rooms.removeIf(r -> r.getId() == id);
    }

    //поменять статус комнаты
    public void changeRoomStatus(int id, RoomStatus status) throws RoomNotFoundException {
        this.getRoom(id).setStatus(status);
    }

    public void changeRoomStatus(Room room, RoomStatus status) {
        room.setStatus(status);
    }

    //поменять цену комнаты
    public void changeRoomPrice(int id, int price) throws RoomNotFoundException {
        this.getRoom(id).setPrice(price);
    }

    public void changeRoomPrice(Room room, int price) {
        room.setPrice(price);
    }

    //поменять рейтинг комнаты
    public void changeRoomRating(int id, StarRating rating) throws RoomNotFoundException {
        this.getRoom(id).setRating(rating);
    }

    public void changeRoomRating(Room room, StarRating rating) {
        room.setRating(rating);
    }

    //получить список жильцов и их номеров
    public Map<Guest, List<Room>> getGuestsAndRooms() {
        return getCurrentGuests().stream()
                .collect(Collectors.toMap(
                        guest -> guest,
                        guest -> this.getRooms(guest).stream()
                                .sorted(Comparator.comparing(Room::getEndDate))
                                .collect(Collectors.toList()),
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }

    //получить список услуг
    public List<Service> getServices() {
        return this.services;
    }

    //получить услугу
    public Service getService(int id) throws ServiceNotFoundException {
        return this.services.stream()
                .filter(s -> s.getId() == id)
                .findAny().orElseThrow(() -> new ServiceNotFoundException(id));
    }

    //добавить услугу
    public void addService(Service service) {
        this.services.add(service);
    }

    //убрать услугу
    public void removeService(int id) {
        this.services.removeIf(s -> s.getId() == id);
        for (Guest guest : this.getCurrentGuests()) {
            guest.removeService(id);
        }
    }

    //поменять цену услуги
    public void changeServicePrice(int id, int price) throws ServiceNotFoundException {
        this.getService(id).setPrice(price);
    }

    public void changeServicePrice(Service service, int price) {
        service.setPrice(price);
    }

    //поменять название услуги
    public void changeServiceName(int id, String name) throws ServiceNotFoundException {
        this.getService(id).setName(name);
    }

    public void changeServiceName(Service service, String name) {
        service.setName(name);
    }

    //получить общий список жильцов
    public List<Guest> getGuests() {
        return this.guests.stream()
                .sorted(Comparator.comparing(Guest::getFullName))
                .collect(Collectors.toList());
    }

    //получить список жильцов в номерах
    public List<Guest> getCurrentGuests() {
        return this.rooms.stream()
                .flatMap(room -> room.getGuests().stream())
                .distinct()
                .sorted(Comparator.comparing(Guest::getFullName))
                .collect(Collectors.toList());
    }

    //получить жильца из общего списка
    public Guest getGuest(int id) throws GuestNotFoundException {
        return this.guests.stream()
                .filter(g -> g.getId() == id)
                .findAny().orElseThrow(() -> new GuestNotFoundException(id));
    }

    //получить жильца из номеров
    public Guest getCurrentGuest(String passport) throws GuestNotFoundException {
        return this.getCurrentGuests().stream()
                .filter(g -> g.getPassport().equals(passport))
                .findAny().orElseThrow(() -> new GuestNotFoundException(passport));
    }

    public void addGuest(Guest guest) {
        if (this.guests.contains(guest)) {
            return;
        }
        this.guests.add(guest);
    }

    //получить количество жильцов в номерах
    public int getCurrentGuestsCount() {
        return (int) rooms.stream()
                .flatMap(room -> room.getGuests().stream())
                .distinct()
                .count();
    }

    //получить список услуг жильца
    public List<GuestService> getGuestServices(String passport) throws GuestNotFoundException {
        return this.getCurrentGuest(passport).getServices();
    }

    public List<GuestService> getGuestServices(String passport, ServiceSortType sortType, SortOrder order) throws GuestNotFoundException {
        Comparator<GuestService> comparator = switch (sortType) {
            case START_DATE -> Comparator.comparing(GuestService::getStartDate);
            case END_DATE -> Comparator.comparing(GuestService::getEndDate);
            case PRICE -> Comparator.comparing(GuestService::getServicePrice);
            default -> Comparator.comparing(GuestService::getId);
        };

        if (order == SortOrder.DESC) {
            comparator = comparator.reversed();
        }

        return this.getGuestServices(passport).stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    //получить список цен товаров и услуг
    public List<PricedItem> getPricedItems() {
        List<PricedItem> everything = new ArrayList<>();
        everything.addAll(rooms);
        everything.addAll(services);
        return everything;
    }

    public List<PricedItem> getPricedItems(PricedItemSortType sortType, SortOrder order) {
        Comparator<PricedItem> comparator = switch (sortType) {
            case CATEGORY -> Comparator.comparing(i -> i.getCategory().ordinal());
            case PRICE -> Comparator.comparing(PricedItem::getPrice);
            default -> Comparator.comparing(PricedItem::getId);
        };

        if (order == SortOrder.DESC) {
            comparator = comparator.reversed();
        }
        return this.getPricedItems().stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    //заселить жильцов
    public boolean moveIn(int roomId, ArrayList<Guest> guests, int days) throws RoomNotFoundException {
        Room room = getRoom(roomId);
        if (room.getStatus() != RoomStatus.FREE || room.getCapacity() < guests.size()) {
            return false;
        }
        guests.forEach(this::addGuest);
        this.getRoom(roomId).setGuests(guests, days);
        return true;
    }

    //выселить жильцов
    public boolean moveOut(int roomId) throws RoomNotFoundException {
        Room room = this.getRoom(roomId);
        if (room.getGuests().size() < 1) {
            return false;
        }
        boolean early = room.getEndDate().isAfter(LocalDate.now());
        room.removeGuests(early);
        return true;
    }

    //добавить услугу гостю
    public boolean addGuestService(String passport, int serviceId, int days) throws ServiceNotFoundException, GuestNotFoundException {
        Service service = this.getService(serviceId);
        return this.getCurrentGuest(passport).addService(service, days);
    }

    //убрать услугу у гостя
    public boolean removeGuestService(String passport, int serviceId) throws GuestNotFoundException {
        return this.getCurrentGuest(passport).removeService(serviceId);
    }

    //получить конфиг
    public HotelConfig getConfig() {
        return this.config;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        //сериализация услуг
        out.writeInt(this.services.size());
        for (Service service : this.services) {
            out.writeObject(service);
        }

        //сериализация гостей
        out.writeInt(this.guests.size());
        for (Guest guest : this.guests) {
            out.writeObject(guest);
        }

        //сериализация номеров
        out.writeInt(this.rooms.size());
        for (Room room : this.rooms) {
            out.writeObject(room);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        instance = this;
        //десериализация услуг
        int servicesCount = in.readInt();
        for (int i = 0; i < servicesCount; i++) {
            this.services.add((Service) in.readObject());
        }

        //десериализация гостей
        int guestsCount = in.readInt();
        for (int i = 0; i < guestsCount; i++) {
            this.guests.add((Guest) in.readObject());
        }

        //десериализация номеров
        int roomsCount = in.readInt();
        for (int i = 0; i < roomsCount; i++) {
            rooms.add((Room) in.readObject());
        }
    }
}
