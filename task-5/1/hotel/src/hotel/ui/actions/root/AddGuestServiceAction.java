package hotel.ui.actions.root;

import hotel.Hotel;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.ServiceNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class AddGuestServiceAction implements IAction {
    @Override
    public void execute() {
        String passport = InputHelper.enterPassport("Введите паспорт жильца для добавления услуги: ");
        int serviceId = InputHelper.enterNumber("Введите id услуги: ", true);
        int days = InputHelper.enterNumber("Введите сколько дней будет действовать услуга: ", true);

        try {
            System.out.println(Hotel.getInstance().addGuestService(passport, serviceId, days) ? "Успех" : "Провал");
        } catch (ServiceNotFoundException | GuestNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
