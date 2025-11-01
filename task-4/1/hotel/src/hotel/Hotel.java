package hotel;

import hotel.enums.*;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.RoomNotFoundException;
import hotel.exceptions.ServiceNotFoundException;
import hotel.interfaces.PricedItem;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Hotel {
    private final List<Room> rooms = new ArrayList<>();           //список комнат
    private final List<Service> services = new ArrayList<>();     //список услуг

    //получить список комнат
    public List<Room> getRooms() {
        return this.rooms;
    }

    public List<Room> getRooms(RoomSortType sortType, SortOrder order) {
        Comparator<Room> comparator = switch (sortType) {
            case PRICE -> Comparator.comparing(Room::getPrice);
            case CAPACITY -> Comparator.comparing(Room::getCapacity);
            case RATING -> Comparator.comparing(Room::getRating);
            case STATUS -> Comparator.comparing(Room::getStatus);
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
    public void changeRoomStatus(int id, RoomStatus status) throws Exception {
        this.getRoom(id).setStatus(status);
    }

    //поменять цену комнаты
    public void changeRoomPrice(int id, int price) throws Exception {
        this.getRoom(id).setPrice(price);
    }

    //поменять рейтинг комнаты
    public void changeRoomRating(int id, StarRating rating) throws Exception {
        this.getRoom(id).setRating(rating);
    }

    //получить список жильцов и их номеров
    public Map<Guest, List<Room>> getGuestsAndRooms() {
        return getGuests().stream()
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
        for (Guest guest : this.getGuests()) {
            guest.removeService(id);
        }
    }

    //поменять цену услуги
    public void changeServicePrice(int id, int price) throws Exception {
        this.getService(id).setPrice(price);
    }

    //получить список жильцов
    public List<Guest> getGuests() {
        return rooms.stream()
                .flatMap(room -> room.getGuests().stream())
                .distinct()
                .sorted(Comparator.comparing(Guest::getFullName))
                .collect(Collectors.toList());
    }

    public Guest getGuest(String passport) throws GuestNotFoundException {
        return this.getGuests().stream()
                .filter(g -> g.getPassport().equals(passport))
                .findAny().orElseThrow(() -> new GuestNotFoundException(passport));
    }

    //получить количество жильцов
    public int getGuestsCount() {
        return getGuests().size();
    }

    //получить список услуг номера
    public List<Service> getGuestServices(String passport) throws GuestNotFoundException {
        return this.getGuest(passport).getServices();
    }

    public List<Service> getGuestServices(String passport, ServiceSortType sortType, SortOrder order) throws GuestNotFoundException {
        Comparator<Service> comparator = switch (sortType) {
            case START_DATE -> Comparator.comparing(Service::getStartDate);
            case END_DATE -> Comparator.comparing(Service::getEndDate);
            case PRICE -> Comparator.comparing(Service::getPrice);
            default -> Comparator.comparing(Service::getId);
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
            case CATEGORY -> Comparator.comparing(PricedItem::getCategory);
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
    public boolean moveIn(int roomId, ArrayList<Guest> guests, int days) throws Exception {
        Room room = getRoom(roomId);
        if (room.getStatus() == RoomStatus.REPAIR || room.getCapacity() < guests.size()) {
            return false;
        }
        this.getRoom(roomId).setGuests(guests, days);
        return true;
    }

    //выселить жильцов
    public boolean moveOut(int roomId) throws Exception {
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
        return this.getGuest(passport).addService(new Service(service, days));
    }

    //убрать услугу у гостя
    public boolean removeGuestService(String passport, int serviceId) throws GuestNotFoundException {
        return this.getGuest(passport).removeService(serviceId);
    }
}
