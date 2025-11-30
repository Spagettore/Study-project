package hotel.csv.exceptions;

public class NoExportDataException extends Exception {
    public NoExportDataException() {
        super("Данные для экспорта отсутствуют");
    }
}