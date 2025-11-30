package hotel.csv.exceptions;

public class CantImportException extends Exception {
    public CantImportException(String reason) {
        super("Невозможно загрузить: " + reason);
    }
}
