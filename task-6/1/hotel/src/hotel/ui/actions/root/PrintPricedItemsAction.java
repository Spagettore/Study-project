package hotel.ui.actions.root;

import hotel.Hotel;
import hotel.enums.PricedItemSortType;
import hotel.enums.SortOrder;
import hotel.interfaces.PricedItem;
import hotel.ui.Hint;
import hotel.ui.InputHelper;
import hotel.ui.OutputHelper;
import hotel.ui.actions.IAction;

import java.util.List;
import java.util.Optional;

public class PrintPricedItemsAction implements IAction {
    @Override
    public void execute() {
        //определяем тип сортировки и как сортировать
        Optional<PricedItemSortType> sortType = InputHelper.chooseEnumValue(PricedItemSortType.class,
                new Hint(OutputHelper.getPricedItemSortTypeHint(), true));
        Optional<SortOrder> sortOrder;

        List<PricedItem> pricedItemList;
        if (sortType.isPresent()) {
            sortOrder = InputHelper.chooseSortOrder();
            pricedItemList = Hotel.getInstance().getPricedItems(sortType.get(), sortOrder.orElse(SortOrder.ASC));
        } else {
            pricedItemList = Hotel.getInstance().getPricedItems();
        }

        System.out.println("Список цен:");
        pricedItemList.forEach(item -> {
            System.out.printf("id: %d%n", item.getId());
            System.out.printf("Категория: %s%n", switch (item.getCategory()) {
                case ROOM -> "Комната";
                case SERVICE -> "Услуга";
            });
            System.out.printf("Цена: %d Рублей/Месяц%n", item.getPrice());
        });
        System.out.println();

    }
}
