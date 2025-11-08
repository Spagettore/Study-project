package hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Guest {
    //фио
    private final String name;
    private final String family;
    private final String secondName;

    private final String passport;    //данные паспорта
    private final int age;            //возраст

    private final ArrayList<Service> services = new ArrayList<>();                 //список услуг у жильца

    //конструктор гостя
    public Guest(String name, String family, String secondName, String passport, int age) {
        this.name = name;
        this.family = family;
        this.secondName = secondName;

        this.passport = passport;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Guest guest)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return guest.getPassport().equals(this.passport);
    }

    //получить фио
    public String getFullName() {
        return this.family + " " + this.name + " " + this.secondName;
    }

    //получить данные паспорта
    public String getPassport() {
        return this.passport;
    }

    //получить возраст
    public int getAge() {
        return this.age;
    }

    //получить услуги номера
    public List<Service> getServices() {
        return this.services;
    }

    //добавить услугу
    public boolean addService(Service service) {
        if (this.services.contains(service)) {
            return false;
        }
        this.services.add(service);
        return true;
    }

    //убрать услугу
    public boolean removeService(int id) {
        for (Service service : services) {
            if (service.getId() == id) {
                service.setEndDate(LocalDate.now());
                services.remove(service);
                return true;
            }
        }
        return false;
    }
}
