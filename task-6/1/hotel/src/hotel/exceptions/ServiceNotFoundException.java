package hotel.exceptions;

public class ServiceNotFoundException extends Exception {
    public ServiceNotFoundException(int id) {
        super("Сервис с id = " + id + " не найден");
    }
}
