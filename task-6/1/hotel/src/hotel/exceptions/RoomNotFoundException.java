package hotel.exceptions;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException(int id) {
        super("Комната с id = " + id + " не найдена");
    }
}
