package hotel.ui;

public class Hint {
    private final String text;
    private final boolean containUniqueOption;

    public Hint(String text, boolean containUniqueOption) {
        this.text = text;
        this.containUniqueOption = containUniqueOption;
    }

    public String getText() {
        return this.text;
    }

    public boolean hasUniqueOption() {
        return this.containUniqueOption;
    }
}
