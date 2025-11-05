package hotel.enums;

public enum RoomStatus {
    FREE(0),
    OCCUPIED(1),
    REPAIR(2),
    CLEAN(3);

    private final int value;

    RoomStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
