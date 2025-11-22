package hotel.ui.actions.root;

import hotel.Hotel;
import hotel.enums.ServiceSortType;
import hotel.enums.SortOrder;
import hotel.exceptions.GuestNotFoundException;
import hotel.ui.Hint;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

import java.util.Optional;

public class PrintServicesByGuestAction implements IAction {
    @Override
    public void execute() {
        String passport = InputHelper.enterPassport("Укажите пасспорт жильца для отображения его услуг: ");

        //определяем тип сортировки и как сортировать
        Optional<ServiceSortType> sortType = InputHelper.chooseEnumValue(ServiceSortType.class,
                new Hint(OutputHelper.getServiceSortTypeHint(), true));
        Optional<SortOrder> sortOrder = Optional.empty();
        if (sortType.isPresent()) {
            sortOrder = InputHelper.chooseSortOrder();
        }

        try {
            String fullName = Hotel.getInstance().getGuest(passport).getFullName();
            System.out.printf("Услуги %s:%n", fullName);
            if (sortType.isPresent() && sortOrder.isPresent()) {
                Hotel.getInstance().getGuestServices(passport,
                        sortType.get(),
                        sortOrder.get()).forEach(OutputHelper::printService);
                return;
            }
            Hotel.getInstance().getGuestServices(passport).forEach(OutputHelper::printService);
        } catch (GuestNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
