package com.gildedrose.items;

import com.gildedrose.Item;

/**
 * Represent a standard item with the expiry and quality update logic
 */
public class NormalItem implements ManageableItem {

    public static final int BASE_MIN_QUANTITY = 0;
    protected final Item item;

    protected static final int BASE_MAX_QUALITY = 50;

    public NormalItem(Item item) {
        this.item = item;
    }

    @Override
    public void doUpdateItem() {
        // instead of decreasing quality twice, one before expiry update and one after expiry update if it drops under zero
        // update expiry first then decrease quality accordingly !
        updateExpiry();
        if (canDecrementQuantity()) {
            updateQuality();
        }
    }

    @Override
    public void updateQuality() {
        // decrease quality daily by a given rate (1 for normal items when not expired, 2 when expired)
        decrementQuality(item, qualityDecreaseRate());
    }

    @Override
    public void updateExpiry() {
        // in most cases item expiry decreases by one daily (except sulfura!)
        item.sellIn -= 1;
    }

    //quality can be incremented when less than 50
    public boolean canIncrementQuantity() {
        return item.quality < BASE_MAX_QUALITY ;
    }

    //quality can be decremented when greater than zero
    public boolean canDecrementQuantity() {
        return item.quality > BASE_MIN_QUANTITY;
    }

    protected boolean isExpired() {
        return item.sellIn < BASE_MIN_QUANTITY;
    }

    protected int qualityDecreaseRate() {
        int decreaseRate = isExpired() ? 2 : 1; // when normal item is expired, quality decreases twice faster
        return Math.min(decreaseRate, item.quality); // keep quality withing allowed range [0...50]
    }

    protected void incrementQuality(final Item item, final int value) {
        if(canIncrementQuantity()) {
            item.quality += value;
        }
    }

    protected void decrementQuality(final Item item, final int value) {
        if(canDecrementQuantity()) {
            item.quality -= value;
        }
    }
}
