package hotel.exceptions;

public class GuestNotFoundException extends Exception {
    public GuestNotFoundException(String passport) {
        super("Гость с id = " + passport + " не найден");
    }

    public GuestNotFoundException(int id) {
        super("Гость с id = " + id + " не найден");
    }
}
