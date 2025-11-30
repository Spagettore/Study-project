package hotel.ui.actions.root;

import hotel.Hotel;
import hotel.ui.actions.IAction;

public class PrintGuestsCountAction implements IAction {
    @Override
    public void execute() {
        System.out.printf("Общее количество жильцов в отеле: %d%n", Hotel.getInstance().getCurrentGuestsCount());
    }
}
