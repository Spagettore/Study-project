package hotel.csv.imports;

import java.time.LocalDate;
import java.util.List;

public class RoomRecordImportResult {
    private final int id;
    private final List<Integer> guestIds;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public RoomRecordImportResult(int id, List<Integer> guestIds, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.guestIds = guestIds;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return this.id;
    }

    public List<Integer> getGuestIds() {
        return this.guestIds;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
}
