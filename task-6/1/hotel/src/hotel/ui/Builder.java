package hotel.ui;

import hotel.ui.actions.ExitAction;
import hotel.ui.actions.csv.export.ExportGuestsAction;
import hotel.ui.actions.csv.export.ExportRoomsAction;
import hotel.ui.actions.csv.export.ExportServicesAction;
import hotel.ui.actions.csv.imports.ImportGuestsAction;
import hotel.ui.actions.csv.imports.ImportRoomsAction;
import hotel.ui.actions.csv.imports.ImportServicesAction;
import hotel.ui.actions.rooms.*;
import hotel.ui.actions.root.*;
import hotel.ui.actions.services.*;

import java.util.ArrayList;

public class Builder implements IBuilder {
    private Menu rootMenu;

    @Override
    public Menu getRootMenu() {
        return this.rootMenu;
    }

    @Override
    public void buildMenu() {
        ArrayList<MenuItem> rootMenuItemList = new ArrayList<>();
        ArrayList<MenuItem> roomsMenuItemList = new ArrayList<>();
        ArrayList<MenuItem> servicesMenuItemList = new ArrayList<>();
        ArrayList<MenuItem> exportMenuItemList = new ArrayList<>();
        ArrayList<MenuItem> importMenuItemList = new ArrayList<>();

        //создаем меню
        this.rootMenu = new Menu("Главное меню", rootMenuItemList);
        Menu roomsMenu = new Menu("Информация о номерах", roomsMenuItemList);
        Menu servicesMenu = new Menu("Информация о услугах", servicesMenuItemList);
        Menu exportMenu = new Menu("Сохранение информации", exportMenuItemList);
        Menu importMenu = new Menu("Загрузка информации", importMenuItemList);

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
        rootMenuItemList.add(new MenuItem("Сохранение информации", null, exportMenu));
        rootMenuItemList.add(new MenuItem("Загрузка информации", null, importMenu));
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

        //экспорт меню
        exportMenuItemList.add(new MenuItem("Сохранить информацию о номерах", new ExportRoomsAction(), exportMenu));
        exportMenuItemList.add(new MenuItem("Сохранить информацию об услугах", new ExportServicesAction(), exportMenu));
        exportMenuItemList.add(new MenuItem("Сохранить информацию о жильцах", new ExportGuestsAction(), exportMenu));
        exportMenuItemList.add(new MenuItem("Назад", null, this.rootMenu));

        //импорт меню
        importMenuItemList.add(new MenuItem("Загрузить информацию о номерах", new ImportRoomsAction(), importMenu));
        importMenuItemList.add(new MenuItem("Загрузить информацию об услугах", new ImportServicesAction(), importMenu));
        importMenuItemList.add(new MenuItem("Загрузить информацию о жильцах", new ImportGuestsAction(), importMenu));
        importMenuItemList.add(new MenuItem("Назад", null, this.rootMenu));
    }
}
