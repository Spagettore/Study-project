package hotel.ui.actions.rooms;

import hotel.Guest;
import hotel.Hotel;
import hotel.exceptions.GuestNotFoundException;
import hotel.exceptions.RoomNotFoundException;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

public class PrintRoomsPriceSumAction implements IAction {
    @Override
    public void execute() {
        Guest guest;
        String passport = InputHelper.enterPassport("Укажите паспорт жильца");
        try {
            guest = Hotel.getInstance().getGuest(passport);
        } catch (GuestNotFoundException e) {
            System.out.println(e.getMessage());
            return;

        }

        System.out.printf("Счет для %s%n", guest.getFullName());
        Hotel.getInstance().getRooms(guest).forEach(r -> {
            OutputHelper.printRoom(r);
            try {
                System.out.printf("Сумма оплаты за номер: %d Рублей", Hotel.getInstance().getRoomTotalPrice(r.getId()));
            } catch (RoomNotFoundException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        });
        System.out.println();
    }
}
