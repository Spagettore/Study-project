package hotel.ui.actions.services;

import hotel.Hotel;
import hotel.Service;
import hotel.exceptions.ServiceNotFoundException;
import hotel.ui.Hint;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;
import hotel.ui.enums.ServiceDataType;

import java.util.Optional;

public class ChangeServiceAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id услуги для изменения: ", true);
        Service service;
        try {
            service = Hotel.getInstance().getService(id);
        } catch (ServiceNotFoundException e) {
            System.out.println(e.getMessage());
            return;
        }

        while (true) {
            Optional<ServiceDataType> dataType = InputHelper.chooseEnumValue(ServiceDataType.class,
                    new Hint(OutputHelper.getServiceDataTypeHint(), true));

            if (dataType.isEmpty()) {
                break;
            }

            switch (dataType.get()) {
                case NAME -> Hotel.getInstance().changeServiceName(service,
                        InputHelper.enterString("Введите новое название услуги"));
                case PRICE -> Hotel.getInstance().changeServicePrice(service,
                        InputHelper.enterNumber("Введите новую цену услуги: ", true));
            }
            OutputHelper.printService(service);
        }
    }
}
