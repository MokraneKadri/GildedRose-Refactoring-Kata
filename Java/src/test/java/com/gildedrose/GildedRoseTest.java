package com.gildedrose;

import com.gildedrose.items.ManageableItemAware;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    public static final String SULFURAS_ITEM_NAME = ManageableItemAware.ManageableItemType.SULFURAS_ITEM.itemName();
    public static final String AGED_BRIE_ITEM_NAME = ManageableItemAware.ManageableItemType.AGED_BRIE_ITEM.itemName();
    public static final String CONJURED_ITEM_NAME = ManageableItemAware.ManageableItemType.CONJURED_ITEM.itemName();
    public static final String BACKSTAGE_PASSES_ITEM_NAME = ManageableItemAware.ManageableItemType.BACKSTAGE_PASSES_ITEM.itemName();

    @Test
    public void foo() {
        GildedRose app = setupWithItem("foo", 0, 0);

        app.updateQuality();

        assertEquals("foo", app.items[0].name);
    }


    @Test
    void normalItemShouldNotDecreaseSellIn() {
        //given
        final GildedRose gildedRose = setupWithItem("custom Item", 0, 3);
        //when
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        //then
        checkExpiryUpdateResult(-3, gildedRose);
    }
    @Test
    void normalItemShouldDecreaseQualityBy1WhenNotExpired() {
        //given
        final GildedRose gildedRose = setupWithItem("My Custom Item", 6, 40);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(39, gildedRose);
    }

    @Test
    void normalItemShouldNotDecreaseQualityWhenQuantityZero() {
        //given
        final GildedRose gildedRose = setupWithItem("New Item", 6, 0);
        //when
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(0, gildedRose);
    }
    @Test
    void normalItemItemShouldDecreaseQualityBy2WhenExpired() {
        //given
        final GildedRose gildedRose = setupWithItem("5 Dexterity Vest", -6, 4);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(2, gildedRose);
    }

    @Test
    void normalItemShouldNotDecreaseQualityWhenExpiredAndQualityAtMinimum() {
        //given
        final GildedRose gildedRose = setupWithItem("5 Dexterity Vest", -6, 0);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(0, gildedRose);
    }
    @Test
    void normalItemItemShouldDecreaseQualityOnceOnlyWhenExpiredAndQualityReachingLimit() {
        //given
        final GildedRose gildedRose = setupWithItem("Elixir of the Mongoose", -6, 1);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(0, gildedRose);
    }
    @Test
    void agedBrieItemShouldDecreaseSellInByOneDaily() {
        //given
        final GildedRose gildedRose = setupWithItem(AGED_BRIE_ITEM_NAME, 3, 3);
        //when
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        //then
        checkExpiryUpdateResult(1, gildedRose);
    }
    @Test
    void agedBrieItemShouldIncreaseQualityByOneWhenNotExpired() {
        //given
        final GildedRose gildedRose = setupWithItem(AGED_BRIE_ITEM_NAME, 3, 3);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(4, gildedRose);
    }

    @Test
    void agedBrieItemShouldNotIncreaseQualityWhenMaxed() {
        //given
        final GildedRose gildedRose = setupWithItem(AGED_BRIE_ITEM_NAME, 3, 50);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(50, gildedRose);
    }
    @Test
    void agedBrieItemShouldIncreaseQualityTwiceWhenExpired() {
        //given
        final GildedRose gildedRose = setupWithItem(AGED_BRIE_ITEM_NAME, -3, 5);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(7, gildedRose);
    }

    @Test
    void sulfuraItemShouldNeverDecreaseSellIn() {
        //given
        final GildedRose gildedRose = setupWithItem(SULFURAS_ITEM_NAME, 0, 3);
        //when
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        //then
        checkExpiryUpdateResult(0, gildedRose);
    }
    @Test
    void sulfuraItemShouldNotChangeQualityWhenNotExpired() {
        //given
        final GildedRose gildedRose = setupWithItem(SULFURAS_ITEM_NAME, 1, 80);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(80, gildedRose);
    }
    @Test
    void sulfuraItemShouldNotChangeQualityWhenExpired() {
        //given
        final GildedRose gildedRose = setupWithItem(SULFURAS_ITEM_NAME, -551, 80);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(80, gildedRose);
    }
      @Test
    void backstageItemShouldDecreaseSellInByOneDaily() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 0, 3);
        //when
        gildedRose.updateQuality();
        //then
        checkExpiryUpdateResult(-1, gildedRose);
    }
    @Test
    void backstageItemShouldIncreaseQualityByOneWhenExpiryDateGreaterThan10() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 15, 20);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(21, gildedRose);
    }

    @Test
    void backstageItemShouldIncreaseQualityBy3WhenLessThan5daysRemainToExpiry() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 1, 20);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(23, gildedRose);
    }
    @Test
    void backstageItemShouldIncreaseQualityBy2WhenLessThan10daysRemainToExpiry() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 1, 48);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(50, gildedRose);
    }
    @Test
    void backstageItemShouldIncreaseQualityBy1OnlyWhenLessThan5daysRemainToExpiryAndQualityAlmostMaxed() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 1, 49);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(50, gildedRose);
    }

    @Test
    void backstageItemShouldIncreaseQualityBy2WhenExpiryBetween5And10Days() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 6, 20);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(22, gildedRose);
    }
    @Test
    void backstageItemShouldIncreaseQualityBy1WhenExpiryBetween5And10DaysAndQualityNearlyMaxed() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 6, 49);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(50, gildedRose);
    }
    @Test
    void backstageItemShouldNotIncreaseQualityWhenQualityMaxed() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, 6, 50);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(50, gildedRose);
    }

    @Test
    void backstageItemShouldSetQualityToZeroWhenExpired() {
        //given
        final GildedRose gildedRose = setupWithItem(BACKSTAGE_PASSES_ITEM_NAME, -1, 20);
        //when
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(0, gildedRose);
    }

    @Test
    void conjuredItemShouldDecreaseSellInByOneDaily() {
        //given
        final GildedRose gildedRose = setupWithItem(CONJURED_ITEM_NAME, 0, 3);
        //when
        gildedRose.updateQuality();
        //then
        checkExpiryUpdateResult(-1, gildedRose);
    }
    @Test
    void conjuredItemShouldDecreaseQualityBy2WhenNotExpiredAndQuantityNoMaxed() {
        //given
        final GildedRose gildedRose = setupWithItem(CONJURED_ITEM_NAME, 3, 3);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(1, gildedRose);
    }
    @Test
    void conjuredItemShouldDecreaseQualityBy4WhenExpiredAndQuantityNoMaxed() {
        //given
        final GildedRose gildedRose = setupWithItem(CONJURED_ITEM_NAME, -3, 7);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(3, gildedRose);
    }

    @Test
    void conjuredItemShouldNotDecreaseQualityWhenQuantityIsZero() {
        //given
        final GildedRose gildedRose = setupWithItem(CONJURED_ITEM_NAME, 3, 0);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(0, gildedRose);
    }

    @Test
    void conjuredItemShouldDecreaseQualityOnceOnlyWhenCloseToExpiry() {
        //given
        final GildedRose gildedRose = setupWithItem(CONJURED_ITEM_NAME, 3, 1);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(0, gildedRose);
    }

    @Test
    void conjuredItemShouldDecreaseQualityBy3TimesOnlyWhenCloseToExpiry() {
        //given
        final GildedRose gildedRose = setupWithItem(CONJURED_ITEM_NAME, -43, 3);
        //when
        gildedRose.updateQuality();
        //then
        checkQualityUpdateResult(0, gildedRose);
    }
    private GildedRose setupWithItem(String name, int sellIn, int quality) {
        final Item[] items = new Item[]{new Item(name, sellIn, quality)};
        return new GildedRose(items);
    }

    private void checkQualityUpdateResult(int expected, GildedRose gildedRose) {
        assertEquals(expected, gildedRose.items[0].quality);
    }
    private void checkExpiryUpdateResult(int expected, GildedRose gildedRose) {
        assertEquals(expected, gildedRose.items[0].sellIn);
    }


}
