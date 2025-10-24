import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class Main {
    private static final int MAX_NUM = 30;
    private static final int FLOWER_TYPE_AMOUNT = 4;
    public static void main(String[] args) throws UnsupportedEncodingException {
        //переводим кодировку на windows-1251 для отображения русских символов
        System.setOut(new PrintStream(System.out, true, "Cp1251"));
        //получаем случайное количество цветов
        int flower_num = 1 + (new java.util.Random()).nextInt(MAX_NUM);
        Flower[] flowers = new Flower[flower_num];
        System.out.println(flower_num);
        //заполняем букет цветами
        for(int i = 0; i < flower_num; i++)
        {
            int flower_type = (new java.util.Random()).nextInt(FLOWER_TYPE_AMOUNT);
            Flower flower = null;
            switch (flower_type)
            {
                case 0:
                    flower = new Rose();
                    break;
                case 1:
                    flower = new BlackRose();
                    break;
                case 2:
                    flower = new Tulip();
                    break;
                case 3:
                    flower = new Sunflower();
                    break;
            }
            flowers[i] = flower;
        }
        //смотрим что в букете и считаем стоимость
        int full_price = 0;
        System.out.println("Цветы в букете: ");
        for(int i = 0; i < flower_num; i++)
        {
            Flower flower = flowers[i];
            full_price += flower.getPrice();
            System.out.println(flower.getName() + " = " + flower.getPrice());
        }
        System.out.println("Стоимость буквета: " + full_price);
    }
}
//базовый класс цветка
abstract class Flower {
    private String name = "";   //название цветка
    private int price = 0;   //цена
    public Flower(String name, int price)
    {
        this.name = name;
        this.price = price;
    }
    //получить название
    public String getName()
    {
        return this.name;
    }
    //получить цену
    public int getPrice()
    {
        return this.price;
    }
}
class Rose extends Flower{
    public Rose(){
        super("Роза", 100);
    }
    public Rose(String name, int price){
        super(name, price);
    }
}
class BlackRose extends Rose{
    public BlackRose(){
        super("Черная Роза", 150);
    }
}
class Tulip extends Flower{
    public Tulip(){
        super("Тюльпан", 50);
    }
}
class Sunflower extends Flower{
    public Sunflower(){
        super("Подсолнух", 10);
    }
}