package hotel;

import java.time.LocalDate;

public class GuestService {
    private static int recordCount = 0;         //общее количество записей
    private final int id;                       //айди

    private final Service service;
    private final LocalDate startDate;
    private LocalDate endDate;

    public GuestService(Service service, int days) {
        this.id = recordCount++;
        this.service = service;
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(days);
    }

    public GuestService(int id, Service service, LocalDate startDate, LocalDate endDate) {
        if (id > recordCount) {
            recordCount = id + 1;
        }
        this.id = id;
        this.service = service;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return this.id;
    }

    public Service getService() {
        return this.service;
    }

    public int getServiceId() {
        return this.service.getId();
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    //установить дату окончания
    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }

    public String getServiceName() {
        return this.service.getName();
    }

    public int getServicePrice() {
        return this.service.getPrice();
    }
}
