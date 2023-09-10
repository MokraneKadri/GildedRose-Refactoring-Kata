package com.gildedrose.items;

import com.gildedrose.Item;

public class AgedBrieItem extends SpecialItem {

    public AgedBrieItem(final Item item) {
        super(item);
    }

    @Override
    protected void updateExpiredItemQuality() {
        updateQuality();
    }
}
