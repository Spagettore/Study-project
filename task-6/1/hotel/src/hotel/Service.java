package hotel;

import hotel.enums.PricedItemCategory;
import hotel.interfaces.PricedItem;

public class Service implements PricedItem {
    private static int serviceCount = 0;    //общее количество услуг
    private final int id;                   //айди
    private String name;                    //название услуги
    private int price;                      //цена услуги

    //конструктор услуги
    public Service(String name, int price) {
        //получение уникального айди для услуги
        this.id = serviceCount++;
        this.name = name;
        this.price = price;
    }

    public Service(int id, String name, int price) {
        if (id > serviceCount) {
            serviceCount = id + 1;
        }
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Service service)) {
            return false;
        }
        if (this == o) {
            return true;
        }
        return service.getId() == this.id;
    }

    //получить айди
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public PricedItemCategory getCategory() {
        return PricedItemCategory.SERVICE;
    }

    //получить название услуги
    public String getName() {
        return this.name;
    }

    //изменить название услуги
    public void setName(String name) {
        this.name = name;
    }

    //получить цену услуги
    @Override
    public int getPrice() {
        return this.price;
    }

    //изменить цену услуг
    @Override
    public void setPrice(int price) {
        this.price = price;
    }
}
