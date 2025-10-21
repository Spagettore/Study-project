public class Main {
    private static final int MIN_NUM = 100;
    private static final int MAX_NUM = 900;
    public static void main(String[] args) {
        //берем случайное трехзначное число, от 100 до 999 и выводим его
        int random_num = MIN_NUM + (new java.util.Random()).nextInt(MAX_NUM);
        System.out.println("Случайное число: " + random_num);

        //берем цифры в числе
        int num3 = random_num / 100;
        int num2 = (random_num / 10) % 10;
        int num1 = random_num % 10;

        //считаем сумму цифр и выводим
        int sum = num1 + num2 + num3;
        System.out.println("Сумма цифр: " + sum);
    }
}
