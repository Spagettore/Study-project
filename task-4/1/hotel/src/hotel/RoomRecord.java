package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomRecord {
    private final List<Guest> guests;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public RoomRecord(List<Guest> guests, LocalDate startDate, LocalDate endDate) {
        this.guests = new ArrayList<>(guests);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public List<Guest> getGuests() {
        return this.guests;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
}
