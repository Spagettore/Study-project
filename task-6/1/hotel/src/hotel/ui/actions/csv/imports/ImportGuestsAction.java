package hotel.ui.actions.csv.imports;

import hotel.Guest;
import hotel.GuestService;
import hotel.Hotel;
import hotel.Service;
import hotel.csv.exceptions.CantImportException;
import hotel.csv.imports.GuestImportResult;
import hotel.csv.imports.GuestServiceImportResult;
import hotel.csv.imports.Importer;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.ServiceNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImportGuestsAction implements IAction {
    @Override
    public void execute() {
        //читаем айди жильца и берем нужное название файла для жильцов и их услуг
        int guestId = InputHelper.enterNumber("Введите id жильца(-1 для всех):", false);
        String guestsFileName = "Guests.csv";
        String guestServicesFileName = "GuestsServices.csv";
        if (guestId >= 0) {
            guestsFileName = String.format("Guest_%d.csv", guestId);
            guestServicesFileName = String.format("Guest_%d_services.csv", guestId);
        }

        //читаем жильцов из фаайла
        List<GuestImportResult> guestImportResultList;
        try {
            guestImportResultList = Importer.importCsv(guestsFileName, Guest.class, Importer::parseGuest);
        } catch (CantImportException e) {
            System.out.println(e.getMessage());
            return;
        }

        if (guestImportResultList.isEmpty()) {
            System.out.println("Пустой файл");
            return;
        }

        //читаем услуги жильцов если у кого-то они есть
        List<GuestServiceImportResult> guestServiceImportResultList = null;
        if (guestImportResultList.stream()
                .anyMatch(g -> !g.getGuestServicesIds().isEmpty())) {
            try {
                guestServiceImportResultList = Importer.importCsv(guestServicesFileName, GuestService.class, Importer::parseGuestService);
            } catch (CantImportException e) {
                System.out.println(e.getMessage());
                return;
            }
        }

        //собираем услуги жильцов
        List<GuestService> guestServices = new ArrayList<>();
        if (guestServiceImportResultList != null) {
            for (GuestServiceImportResult guestServiceImportResult : guestServiceImportResultList) {
                int guestServiceId = guestServiceImportResult.getId();
                Service service;
                try {
                    service = Hotel.getInstance().getService(guestServiceImportResult.getServiceId());
                } catch (ServiceNotFoundException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                LocalDate startDate = guestServiceImportResult.getStartDate();
                LocalDate endDate = guestServiceImportResult.getEndDate();
                guestServices.add(new GuestService(guestServiceId, service, startDate, endDate));
            }
        }

        //добавляем или обновляем жильцов
        for (GuestImportResult guestImportResult : guestImportResultList) {
            int id = guestImportResult.getId();
            String name = guestImportResult.getName();
            String family = guestImportResult.getFamily();
            String secondName = guestImportResult.getSecondName();
            String passport = guestImportResult.getPassport();
            int age = guestImportResult.getAge();
            ArrayList<GuestService> services = guestServices.stream()
                    .filter(s -> guestImportResult.getGuestServicesIds().contains(s.getId()))
                    .collect(Collectors.toCollection(ArrayList::new));

            try {
                Guest guest = Hotel.getInstance().getGuest(id);
                guest.setName(name);
                guest.setFamily(family);
                guest.setSecondName(secondName);
                guest.setPassport(passport);
                guest.setAge(age);
                guest.setServices(services);
            } catch (GuestNotFoundException e) {
                Hotel.getInstance().addGuest(new Guest(
                        id,
                        name,
                        family,
                        secondName,
                        passport,
                        age,
                        services
                ));
            }
        }
        System.out.println("Успешно загружено");
    }
}
