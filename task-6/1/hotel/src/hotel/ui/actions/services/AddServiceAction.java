package hotel.ui.actions.services;

import hotel.Hotel;
import hotel.Service;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class AddServiceAction implements IAction {
    @Override
    public void execute() {
        String name = InputHelper.enterString("Введите название услуги: ");
        int price = InputHelper.enterNumber("Введите цену услуги: ", true);

        Service service = new Service(name, price);
        Hotel.getInstance().addService(service);
        System.out.printf("Услуга с id - %d добавлена%n", service.getId());
    }
}
