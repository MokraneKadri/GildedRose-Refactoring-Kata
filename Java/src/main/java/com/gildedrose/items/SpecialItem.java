package com.gildedrose.items;

import com.gildedrose.Item;

/**
 * Represent a Non-standard item with specific quality update logic
 */
public abstract class SpecialItem extends NormalItem {

    protected static final int BASE_QUALITY_INCREMENT_VALUE = 1;

    public SpecialItem(Item item) {
        super(item);
    }

    @Override
    public void doUpdateItem() {
        updateQuality(); // when BackStage Passes items are not expired their quality increases with a custom value
        updateExpiry();
        updateItemIfExpired(); // update logic for expired items
    }

    @Override
    public void updateQuality() { // for most special items quality increases ith time , override per specificities to introduce custom rules
        incrementQuality(item, quantityIncreaseRate());
    }

    private void updateItemIfExpired() {
        if (isExpired()) {
            updateExpiredItemQuality();
        }
    }

    protected abstract void updateExpiredItemQuality();

    protected int quantityIncreaseRate() {
        return BASE_QUALITY_INCREMENT_VALUE; // the default quality increase rate per day, override for custom items
    }
}
