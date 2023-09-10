package com.gildedrose.items;

import com.gildedrose.Item;

public class ConjuredItem extends SpecialItem {

    public ConjuredItem(Item item) {
        super(item);
    }

    @Override
    public void updateQuality() {
        decrementQuality(item, qualityDecreaseRate());
    }

    @Override
    protected void updateExpiredItemQuality() {
        // conjured items quality decreases tw0 times faster than normal
        decrementQuality(item, qualityDecreaseRate());
    }

    @Override
    protected int qualityDecreaseRate() {
        return Math.min(item.quality, 2);
    }
}
