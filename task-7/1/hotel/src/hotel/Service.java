package hotel;

import hotel.enums.PricedItemCategory;
import hotel.interfaces.PricedItem;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class Service implements PricedItem, Externalizable {
    private static final long serialVersionUID = 1L;
    private static int serviceCount = 0;    //общее количество услуг
    private int id;                         //айди
    private String name;                    //название услуги
    private int price;                      //цена услуги

    //конструктор услуги
    public Service() {
    }

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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeUTF(this.name);
        out.writeInt(this.price);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.id = in.readInt();
        if (this.id > serviceCount) {
            serviceCount = this.id + 1;
        }
        this.name = in.readUTF();
        this.price = in.readInt();
    }
}
