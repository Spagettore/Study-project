package hotel;

public class Guest {
    //фио
    private String name;
    private String family;
    private String secondName;

    private String passport;    //данные паспорта
    private int age;            //возраст

    //конструктор гостя
    public Guest(String name, String family, String secondName, String passport, int age)
    {
        this.name = name;
        this.family = family;
        this.secondName = secondName;

        this.passport = passport;
        this.age = age;
    }
    //получить фио
    public String getFullName()
    {
        return this.family + " " + this.name + " " + this.secondName;
    }
    //получить данные паспорта
    public String getPassport()
    {
        return this.passport;
    }
    //получить возраст
    public int getAge()
    {
        return this.age;
    }
}
