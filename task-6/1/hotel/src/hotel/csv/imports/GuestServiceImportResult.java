package hotel.csv.imports;

import java.time.LocalDate;

public class GuestServiceImportResult {
    private final int id;

    private final int serviceId;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public GuestServiceImportResult(int id, int serviceId, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.serviceId = serviceId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return this.id;
    }

    public int getServiceId() {
        return this.serviceId;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }
}
