package hotel.csv.exceptions;

public class CantExportException extends Exception {
    public CantExportException(String reason) {
        super("Невозможно сохранить: " + reason);
    }
}
