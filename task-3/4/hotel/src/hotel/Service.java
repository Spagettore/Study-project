package hotel;

public class Service {
    private int id;                         //айди
    private String name;                    //название услуги
    private Double price;                   //цена услуги
    private static int serviceCount = 0;    //общее количество услуг

    //конструктор услуги
    public Service(String name, Double price)
    {
        //получение уникального айди для услуги
        this.id = serviceCount++;
        this.name = name;
        this.price = price;
    }
    //получить айди
    public int getId()
    {
        return this.id;
    }
    //получить название услуги
    public String getName()
    {
        return this.name;
    }
    //изменить название услуги
    public void setName(String name)
    {
        this.name = name;
    }
    //получить цену услуги
    public Double getPrice()
    {
        return this.price;
    }
    //изменить цену услуги
    public void setPrice(Double price)
    {
        this.price = price;
    }
}
