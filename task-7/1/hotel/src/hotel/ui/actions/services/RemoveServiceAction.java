package hotel.ui.actions.services;

import hotel.Hotel;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class RemoveServiceAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id услуги для удаления: ", true);
        Hotel.getInstance().removeService(id);
        System.out.printf("Услуга с id - %d удалена%n", id);
    }
}
