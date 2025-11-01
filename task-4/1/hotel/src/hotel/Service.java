package hotel;

import hotel.enums.PricedItemCategory;
import hotel.interfaces.PricedItem;

import java.time.LocalDate;

public class Service implements PricedItem {
    private static int serviceCount = 0;    //общее количество услуг
    private final int id;                   //айди
    private String name;                    //название услуги
    private int price;                      //цена услуги
    private LocalDate startDate;            //дата начала услуги
    private LocalDate endDate;              //дата окончания услуги

    //конструктор услуги
    public Service(String name, int price) {
        //получение уникального айди для услуги
        this.id = serviceCount++;
        this.name = name;
        this.price = price;
    }

    public Service(Service service, int days) {
        this.id = service.getId();
        this.name = service.getName();
        this.price = service.getPrice();
        this.startDate = LocalDate.now();
        this.endDate = this.startDate.plusDays(days);
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

    //получить дату начала
    public LocalDate getStartDate() {
        return this.startDate;
    }

    //получить дату окончания
    public LocalDate getEndDate() {
        return this.endDate;
    }

    //установить дату окончания
    public void setEndDate(LocalDate date) {
        this.endDate = date;
    }
}
