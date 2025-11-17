package hotel.ui.actions.services;

import hotel.Hotel;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

public class PrintServicesAction implements IAction {
    @Override
    public void execute() {
        System.out.println("Все услуги:");
        Hotel.getInstance().getServices().forEach(OutputHelper::printService);
    }
}
