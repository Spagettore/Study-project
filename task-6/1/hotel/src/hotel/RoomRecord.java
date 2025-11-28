package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomRecord {
    private static int roomRecordCount = 0;   //общее количество записей
    private final int id;                     //айди

    private final List<Guest> guests;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public RoomRecord(List<Guest> guests, LocalDate startDate, LocalDate endDate) {
        this.id = roomRecordCount++;
        this.guests = new ArrayList<>(guests);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RoomRecord(int id, List<Guest> guests, LocalDate startDate, LocalDate endDate) {
        if (id > roomRecordCount) {
            roomRecordCount = id + 1;
        }
        this.id = id;
        this.guests = new ArrayList<>(guests);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return this.id;
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
