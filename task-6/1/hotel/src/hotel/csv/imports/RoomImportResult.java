package hotel.csv.imports;

import hotel.enums.RoomStatus;
import hotel.enums.StarRating;

import java.time.LocalDate;
import java.util.List;

public class RoomImportResult {
    private final int id;
    private final List<Integer> roomRecordIds;
    private final int capacity;
    private final RoomStatus status;
    private final int price;
    private final List<Integer> guestIds;
    private final StarRating rating;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public RoomImportResult(
            int id,
            List<Integer> roomRecordIds,
            int capacity,
            RoomStatus status,
            int price,
            List<Integer> guestIds,
            StarRating rating,
            LocalDate startDate,
            LocalDate endDate
    ) {
        this.id = id;
        this.roomRecordIds = roomRecordIds;
        this.capacity = capacity;
        this.status = status;
        this.price = price;
        this.guestIds = guestIds;
        this.rating = rating;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public List<Integer> getRoomRecordIds() {
        return roomRecordIds;
    }

    public int getCapacity() {
        return capacity;
    }

    public RoomStatus getStatus() {
        return status;
    }

    public int getPrice() {
        return price;
    }

    public List<Integer> getGuestIds() {
        return guestIds;
    }

    public StarRating getRating() {
        return rating;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
