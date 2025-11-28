package hotel.ui.actions.csv.imports;

import hotel.Hotel;
import hotel.Service;
import hotel.csv.exceptions.CantImportException;
import hotel.csv.imports.Importer;
import hotel.csv.imports.ServiceImportResult;
import hotel.exceptions.ServiceNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

import java.util.List;

public class ImportServicesAction implements IAction {
    @Override
    public void execute() {
        //читаем айди услуги и берем нужное название файла для услуг
        int serviceId = InputHelper.enterNumber("Введите id услуги (-1 для всех):", false);
        String servicesFileName = "Services.csv";
        if (serviceId >= 0) {
            servicesFileName = String.format("Service_%d.csv", serviceId);
        }

        //читаем услуги из файла
        List<ServiceImportResult> serviceImportResultList;
        try {
            serviceImportResultList = Importer.importCsv(servicesFileName, Service.class, Importer::parseService);
        } catch (CantImportException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (serviceImportResultList.isEmpty()) {
            System.out.println("Файл пустой");
            return;
        }

        //добавляем или обновляем услуги
        for (ServiceImportResult serviceImportResult : serviceImportResultList) {
            int id = serviceImportResult.getId();
            String name = serviceImportResult.getName();
            int price = serviceImportResult.getPrice();

            try {
                Service service = Hotel.getInstance().getService(id);
                service.setName(name);
                service.setPrice(price);
            } catch (ServiceNotFoundException e) {
                Hotel.getInstance().addService(new Service(id, name, price));
            }
        }
        System.out.println("Успешно загружено");
    }
}
