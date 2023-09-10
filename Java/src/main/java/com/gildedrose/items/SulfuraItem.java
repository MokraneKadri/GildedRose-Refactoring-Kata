package com.gildedrose.items;

import com.gildedrose.Item;

public class SulfuraItem extends SpecialItem{


    public SulfuraItem(final Item item) {
        super(item);
    }

    @Override
    public void doUpdateItem() {
        // do nothing sulfura legendary items never change either in expiry or quality !
    }

    @Override
    protected void updateExpiredItemQuality() {
        // do nothing sulfura legendary items never decrease  quality !
    }
}
