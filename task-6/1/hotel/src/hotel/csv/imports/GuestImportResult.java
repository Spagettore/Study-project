package hotel.csv.imports;

import java.util.List;

public class GuestImportResult {
    private final List<Integer> guestServicesIds;
    private final int id;
    private final String name;
    private final String family;
    private final String secondName;
    private final String passport;
    private final int age;

    public GuestImportResult(int id, String name, String family, String secondName, String passport, int age, List<Integer> guestServicesIds) {
        this.id = id;
        this.name = name;
        this.family = family;
        this.secondName = secondName;
        this.passport = passport;
        this.age = age;
        this.guestServicesIds = guestServicesIds;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getFamily() {
        return this.family;
    }

    public String getSecondName() {
        return this.secondName;
    }

    public String getPassport() {
        return this.passport;
    }

    public int getAge() {
        return this.age;
    }

    public List<Integer> getGuestServicesIds() {
        return this.guestServicesIds;
    }
}
