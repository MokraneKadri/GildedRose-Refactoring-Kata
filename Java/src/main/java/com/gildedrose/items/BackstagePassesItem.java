package com.gildedrose.items;

import com.gildedrose.Item;

public class BackstagePassesItem extends SpecialItem {

    public BackstagePassesItem(final Item item) {
        super(item);
    }


    @Override
    protected void updateExpiredItemQuality() {
        // when BackStage Passes items expire their quality goes down to zero
        decrementQuality(item, item.quality);
    }

    @Override
    protected int quantityIncreaseRate() {
        if (expiresInMoreThan(10)) {
            return BASE_QUALITY_INCREMENT_VALUE;
        }
        if (expiresInMoreThan(5)) {
            return Math.min(2, (BASE_MAX_QUALITY - item.quality));
        }
        return Math.min(3, (BASE_MAX_QUALITY - item.quality));
    }

    private boolean expiresInMoreThan(final int x) {
        return item.sellIn > x;
    }
}
