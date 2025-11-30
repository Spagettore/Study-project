package hotel.ui.actions.csv.export;

import hotel.Hotel;
import hotel.Service;
import hotel.csv.Exporter;
import hotel.exceptions.ServiceNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

import java.util.List;

public class ExportServicesAction implements IAction {
    @Override
    public void execute() {
        //читаем айди услуги и берем нужное название файла для услуг
        int id = InputHelper.enterNumber("Введите id услуги(-1 для всех):", false);
        String servicesFileName = "Services";
        List<Service> services;
        if (id >= 0) {
            servicesFileName = String.format("Service_%d", id);
            try {
                services = List.of(Hotel.getInstance().getService(id));
            } catch (ServiceNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            services = Hotel.getInstance().getServices();
        }

        try {
            System.out.println("Сохранения данных об услугах");
            Exporter.exportCsv(servicesFileName, services, Exporter::parseService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println("Успешно сохранено");
    }
}
