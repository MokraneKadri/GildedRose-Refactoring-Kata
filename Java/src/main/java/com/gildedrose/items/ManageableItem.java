package com.gildedrose.items;

public interface ManageableItem {


    /** item update logic allowing to update both expiry and item quality
     * Always update expiry (sellIn)
     * Update item quality when possible (quality within the allowed limits)
     */
     void doUpdateItem() ;

    /**
     * The item expiry date update logic
     * override if required per item specificity to handle item expiry date evolution
     */
    void updateExpiry();

    /**
     * the item quantity update logic
     * override per item specificity to handle item quality increase and decrease
     */
    void updateQuality();

    /**
     * checks if a given item quality can be updated
     * @return either quantity is updatable or not
     */
    boolean canIncrementQuantity();


}
