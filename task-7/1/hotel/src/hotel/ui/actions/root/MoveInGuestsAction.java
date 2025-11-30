package hotel.ui.actions.root;

import hotel.Guest;
import hotel.Hotel;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.actions.IAction;

import java.util.ArrayList;

public class MoveInGuestsAction implements IAction {
    @Override
    public void execute() {
        int id = InputHelper.enterNumber("Введите id комнаты для заселения: ", true);
        ArrayList<Guest> guests = new ArrayList<>();
        do {
            String passport = InputHelper.enterPassport("Введите паспорт жильца: ");
            Guest guest;
            try {
                guest = Hotel.getInstance().getCurrentGuest(passport);
                guests.add(guest);
            } catch (GuestNotFoundException e) {
                String name = InputHelper.enterString("Введите имя жильца: ");
                String family = InputHelper.enterString("Введите фамилию жильца: ");
                String secondName = InputHelper.enterString("Введите отчество жильца: ");
                int age = InputHelper.enterNumber("Введите возраст жильца: ", true);
                guests.add(new Guest(name, family, secondName, passport, age));
            }
        } while (InputHelper.enterNumber("Введите 0 чтобы остановить добавление жильцов: ", true) != 0);

        int days = InputHelper.enterNumber("Введите количество дней проживания: ", true);
        try {
            System.out.println(Hotel.getInstance().moveIn(id, guests, days) ? "Успех" : "Провал");
        } catch (RoomNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }
}
