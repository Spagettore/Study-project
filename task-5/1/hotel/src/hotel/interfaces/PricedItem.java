package hotel.interfaces;

import hotel.enums.PricedItemCategory;

public interface PricedItem {
    int getId();

    PricedItemCategory getCategory();

    int getPrice();

    void setPrice(int price);
}
