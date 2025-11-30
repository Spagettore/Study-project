package hotel.ui.actions.services;

import hotel.Hotel;
import hotel.exceptions.ServiceNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

public class PrintServiceAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id услуги: ", true);

        try {
            OutputHelper.printService(Hotel.getInstance().getService(id));
        } catch (ServiceNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
