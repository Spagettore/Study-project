package hotel.ui;

import hotel.enums.SortOrder;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    private InputHelper() {
    }

    //ввод строки
    public static String enterString(String hint) {
        System.out.println(hint);
        return scanner.nextLine();
    }

    //ввод паспорта
    public static String enterPassport(String hint) {
        String passport;
        while (true) {
            passport = enterString(hint);
            if (passport.isEmpty() ||
                    !passport.chars().allMatch(c -> Character.isDigit(c) || Character.isWhitespace(c))) {
                System.out.println("Неверный ввод");
                continue;
            }
            break;
        }
        return passport;
    }

    //ввод даты
    public static LocalDate enterDate(String hint) {
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        while (true) {
            System.out.println(hint);
            System.out.println("Формат: " + LocalDate.now().format(formatter));

            String dateText = scanner.nextLine();
            try {
                date = LocalDate.parse(dateText, formatter);
            } catch (Exception e) {
                System.out.println("Неверный ввод");
                continue;
            }
            break;
        }
        return date;
    }

    //ввод числа
    public static int enterNumber(String hint, boolean onlyPositive) {
        int number;
        while (true) {
            System.out.println(hint);
            try {
                number = scanner.nextInt();
                scanner.nextLine();
                if (onlyPositive && number < 0) {
                    System.out.println("Принимают только положительные числа");
                    continue;
                }
            } catch (Exception e) {
                System.out.println("Неверный ввод");
                scanner.nextLine();
                continue;
            }
            break;
        }
        return number;
    }

    //выбор варианта
    public static int chooseAnswer(int last, String hint) {
        int answer;
        while (true) {
            answer = enterNumber(hint, true);
            if (answer > last) {
                System.out.println("Выберите из предложенного");
                continue;
            }
            break;
        }
        return answer;
    }

    //получаем значение из enum
    public static <T extends Enum<T>> Optional<T> chooseEnumValue(Class<T> enumType, Hint hint) {
        T[] values = enumType.getEnumConstants();
        int offset = hint.hasUniqueOption() ? 1 : 0;
        int index = chooseAnswer(values.length - 1 + offset, hint.getText());
        if (index < offset) {
            return Optional.empty();
        }
        return Optional.of(values[index - offset]);
    }

    //принимаем сортировку
    public static Optional<SortOrder> chooseSortOrder() {
        String hint = """
                Введите как будет проводиться сортировка:
                0. По возрастанию
                1. По убыванию
                """;

        return chooseEnumValue(SortOrder.class, new Hint(hint, false));
    }
}
