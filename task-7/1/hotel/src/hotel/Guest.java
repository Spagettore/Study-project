package hotel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;

public class Guest implements Externalizable {
    private static final long serialVersionUID = 1L;
    private static int guestCount = 0;   //общее количество гостей
    private int id;                //айди
    private int age;               //возраст
    private String passport;       //данные паспорта
    //фио
    private String name;
    private String family;
    private String secondName;
    private ArrayList<GuestService> services = new ArrayList<>();                 //список услуг у жильца

    //конструктор гостя
    public Guest() {
    }

    public Guest(String name, String family, String secondName, String passport, int age) {
        this.id = guestCount++;

        this.name = name;
        this.family = family;
        this.secondName = secondName;

        this.passport = passport;
        this.age = age;
    }

    public Guest(int id, String name, String family, String secondName, String passport, int age, ArrayList<GuestService> services) {
        if (id > guestCount) {
            guestCount = id + 1;
        }
        this.id = id;

        this.name = name;
        this.family = family;
        this.secondName = secondName;

        this.passport = passport;
        this.age = age;
        this.services = services;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Guest guest)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return guest.getId() == this.id;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return this.family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    //получить фио
    public String getFullName() {
        return this.family + " " + this.name + " " + this.secondName;
    }

    //получить данные паспорта
    public String getPassport() {
        return this.passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    //получить возраст
    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    //получить услуги номера
    public List<GuestService> getServices() {
        return this.services;
    }

    public void setServices(ArrayList<GuestService> services) {
        this.services = services;
    }

    //добавить услугу
    public boolean addService(Service service, int days) {
        if (this.services.stream()
                .anyMatch(s -> s.getServiceId() == service.getId())) {
            return false;
        }
        this.services.add(new GuestService(service, days));
        return true;
    }

    //убрать услугу
    public boolean removeService(int id) {
        return this.services.removeIf(s -> s.getServiceId() == id);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeInt(this.age);
        out.writeUTF(this.passport);
        out.writeUTF(this.name);
        out.writeUTF(this.family);
        out.writeUTF(this.secondName);

        //сериализация услуг гостя
        out.writeInt(this.services.size());
        for (GuestService guestService : this.services) {
            out.writeObject(guestService);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        if (this.id > guestCount) {
            guestCount = this.id + 1;
        }
        this.age = in.readInt();
        this.passport = in.readUTF();
        this.name = in.readUTF();
        this.family = in.readUTF();
        this.secondName = in.readUTF();

        int servicesCount = in.readInt();
        for (int i = 0; i < servicesCount; i++) {
            services.add((GuestService) in.readObject());
        }
    }
}
