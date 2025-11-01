package hotel.enums;

public enum StarRating {
    ONE_STAR(1),
    TWO_STAR(2),
    THREE_STAR(3),
    FOUR_STAR(4),
    FIVE_STAR(5);

    private final int value;

    StarRating(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
