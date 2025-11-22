package hotel.ui;

import hotel.ui.actions.ExitAction;
import hotel.ui.actions.rooms.*;
import hotel.ui.actions.root.*;
import hotel.ui.actions.services.*;

import java.util.ArrayList;

public class Builder {
    private Menu rootMenu;

    public Menu getRootMenu() {
        return this.rootMenu;
    }

    public void buildMenu() {
        ArrayList<MenuItem> rootMenuItemList = new ArrayList<>();
        ArrayList<MenuItem> roomsMenuItemList = new ArrayList<>();
        ArrayList<MenuItem> servicesMenuItemList = new ArrayList<>();

        //создаем меню
        this.rootMenu = new Menu("Главное меню", rootMenuItemList);
        Menu roomsMenu = new Menu("Информация о номерах", roomsMenuItemList);
        Menu servicesMenu = new Menu("Информация о услугах", servicesMenuItemList);

        //создаем пункты главного меню
        rootMenuItemList.add(new MenuItem("Поселить гостей", new MoveInGuestsAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Выселить жильцов", new MoveOutGuestsAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Добавить услугу жильцу", new AddGuestServiceAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Убрать услугу у жильца", new RemoveGuestServiceAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Информация о услугах жильца", new PrintServicesByGuestAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Информация о номерах", null, roomsMenu));
        rootMenuItemList.add(new MenuItem("Информация об услугах", null, servicesMenu));
        rootMenuItemList.add(new MenuItem("Список комнат и услуг", new PrintPricedItemsAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Список гостей и их номеров", new PrintGuestsAndRoomsAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Общее количество жильцов", new PrintGuestsCountAction(), this.rootMenu));
        rootMenuItemList.add(new MenuItem("Выход", new ExitAction(), this.rootMenu));

        //создаем пункты меню Информация о номерах
        roomsMenuItemList.add(new MenuItem("Добавить номер", new AddRoomAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Удалить номер", new RemoveRoomAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Изменить номер", new ChangeRoomAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Список всех номеров", new PrintRoomsAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Список свободных номеров в указанную дату", new PrintRoomsByDateAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Количество номеров по статусу", new PrintRoomsCountAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Подробная информация о номере", new PrintRoomDetailsAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Список прошлых жильцов номера", new PrintRoomHistoryAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Расчитать оплату для гостя", new PrintRoomsPriceSumAction(), roomsMenu));
        roomsMenuItemList.add(new MenuItem("Назад", null, this.rootMenu));

        //Информация о услугах
        servicesMenuItemList.add(new MenuItem("Добавить услугу", new AddServiceAction(), servicesMenu));
        servicesMenuItemList.add(new MenuItem("Удалить услугу", new RemoveServiceAction(), servicesMenu));
        servicesMenuItemList.add(new MenuItem("Изменить услугу", new ChangeServiceAction(), servicesMenu));
        servicesMenuItemList.add(new MenuItem("Список всех услуг", new PrintServicesAction(), servicesMenu));
        servicesMenuItemList.add(new MenuItem("Информация об услуге", new PrintServiceAction(), servicesMenu));
        servicesMenuItemList.add(new MenuItem("Подробная информация о номере", new PrintRoomDetailsAction(), servicesMenu));
        servicesMenuItemList.add(new MenuItem("Список прошлых жильцов номера", new PrintRoomHistoryAction(), servicesMenu));
        servicesMenuItemList.add(new MenuItem("Назад", null, this.rootMenu));
    }
}
