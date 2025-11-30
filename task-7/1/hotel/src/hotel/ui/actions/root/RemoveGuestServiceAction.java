package hotel.ui.actions.root;

import hotel.Hotel;
import hotel.exceptions.GuestNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

public class RemoveGuestServiceAction implements IAction {
    @Override
    public void execute() {
        String passport = InputHelper.enterPassport("Введите паспорт жильца для добавления услуги: ");
        int serviceId = InputHelper.enterNumber("Введите id услуги: ", true);

        try {
            System.out.println(Hotel.getInstance().removeGuestService(passport, serviceId) ? "Успех" : "Провал");
        } catch (GuestNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
