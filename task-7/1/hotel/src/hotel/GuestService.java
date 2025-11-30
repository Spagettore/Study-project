package hotel;

import hotel.exceptions.ServiceNotFoundException;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;

public class GuestService implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static int recordCount = 0;         //общее количество записей
    private int id;                             //айди

    private Service service;
    private LocalDate startDate;
    private LocalDate endDate;

    public GuestService() {
    }

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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeInt(this.service.getId());
        out.writeObject(this.startDate);
        out.writeObject(this.endDate);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        if (this.id > recordCount) {
            recordCount = this.id + 1;
        }
        try {
            this.service = Hotel.getInstance().getService(in.readInt());
        } catch (ServiceNotFoundException e) {
            e.printStackTrace();
        }
        this.startDate = (LocalDate) in.readObject();
        this.endDate = (LocalDate) in.readObject();
    }
}
