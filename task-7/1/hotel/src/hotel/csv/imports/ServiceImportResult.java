package hotel.csv.imports;

public class ServiceImportResult {
    private final int id;
    private final String name;
    private final int price;

    public ServiceImportResult(int id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }
}
