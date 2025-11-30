package hotel;

import hotel.exceptions.GuestNotFoundException;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RoomRecord implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static int roomRecordCount = 0;   //общее количество записей
    private List<Guest> guests;
    private int id;                           //айди
    private LocalDate startDate;
    private LocalDate endDate;

    public RoomRecord() {
    }

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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeObject(this.startDate);
        out.writeObject(this.endDate);

        out.writeInt(this.guests.size());
        for (Guest guest : this.guests) {
            out.writeInt(guest.getId());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        if (this.id > roomRecordCount) {
            roomRecordCount = this.id + 1;
        }
        this.startDate = (LocalDate) in.readObject();
        this.endDate = (LocalDate) in.readObject();

        int guestsCount = in.readInt();
        this.guests = new ArrayList<>(guestsCount);
        for (int i = 0; i < guestsCount; i++) {
            try {
                this.guests.add(Hotel.getInstance().getGuest(in.readInt()));
            } catch (GuestNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
