package hotel.enums;

public enum PricedItemCategory {
    ROOM(0),
    SERVICE(1);

    private final int value;

    PricedItemCategory(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
