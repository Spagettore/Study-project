package hotel.ui.actions.rooms;

import hotel.Hotel;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PrintRoomsByDateAction implements IAction {
    @Override
    public void execute() {
        String hint = "Введите дату, чтобы узнать какие номера будут свободны: ";
        LocalDate date = InputHelper.enterDate(hint);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        System.out.println("Номера свободные в " + date.format(formatter));
        Hotel.getInstance().getRoomsByDate(date).forEach(OutputHelper::printRoom);
    }
}
