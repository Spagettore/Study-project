package hotel.exceptions;

public class GuestNotFoundException extends Exception {
    public GuestNotFoundException(String passport) {
        super("Гость с passport = " + passport + " не найден");
    }
}
