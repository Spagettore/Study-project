package hotel;

import java.util.ArrayList;
import java.util.List;

public class Guest {
    private static int guestCount = 0;   //общее количество гостей
    private final int id;                //айди
    private int age;               //возраст
    private String passport;       //данные паспорта
    //фио
    private String name;
    private String family;
    private String secondName;
    private ArrayList<GuestService> services = new ArrayList<>();                 //список услуг у жильца

    //конструктор гостя
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
}
