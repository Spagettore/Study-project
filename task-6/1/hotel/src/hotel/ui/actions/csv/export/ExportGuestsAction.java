package hotel.ui.actions.csv.export;

import hotel.Guest;
import hotel.Hotel;
import hotel.csv.Exporter;
import hotel.exceptions.GuestNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

import java.util.List;
import java.util.stream.Collectors;

public class ExportGuestsAction implements IAction {
    @Override
    public void execute() {
        //читаем айди жильца и берем нужное название файла для жильцов и их услуг
        int id = InputHelper.enterNumber("Введите id жильца(-1 для всех):", false);
        String guestsFileName = "Guests";
        String guestServicesFileName = "GuestsServices";
        List<Guest> guests;
        if (id >= 0) {
            guestsFileName = String.format("Guest_%d", id);
            guestServicesFileName = String.format("Guest_%d_services", id);
            try {
                guests = List.of(Hotel.getInstance().getGuest(id));
            } catch (GuestNotFoundException e) {
                System.out.println(e.getMessage());
                return;
            }
        } else {
            guests = Hotel.getInstance().getGuests();
        }

        try {
            System.out.println("Сохранения данных о жильцах");
            Exporter.exportCsv(guestsFileName, guests, Exporter::parseGuest);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }

        try {
            System.out.println("Сохранения данных о услугах жильцов");
            Exporter.exportCsv(
                    guestServicesFileName,
                    guests.stream()
                            .flatMap(guest -> guest.getServices().stream())
                            .collect(Collectors.toList()),
                    Exporter::parseGuestService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("Успешно сохранено");
    }
}
