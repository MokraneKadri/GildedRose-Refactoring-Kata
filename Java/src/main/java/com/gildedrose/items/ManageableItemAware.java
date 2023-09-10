package com.gildedrose.items;

import com.gildedrose.Item;

import java.util.Arrays;
import java.util.Objects;

public class ManageableItemAware {


    private final ManageableItem manageableItem;

    private ManageableItemAware(final Item item) {
        manageableItem = ManageableItemType.wrap(item);
    }

    public static ManageableItemAware forItem(final Item item) {
        return new ManageableItemAware(item);
    }

    public void doUpdateItem() {
        Objects.requireNonNull(manageableItem, "ManageableItem cannot be empty or null !");
        manageableItem.doUpdateItem();
    }

   public enum ManageableItemType {
        SULFURAS_ITEM("Sulfuras, Hand of Ragnaros") {
            @Override
            ManageableItem initialize(Item item) {
                return new SulfuraItem(item);
            }
        },

        AGED_BRIE_ITEM("Aged Brie") {
            @Override
            ManageableItem initialize(Item item) {
                return new AgedBrieItem(item);
            }
        },
        BACKSTAGE_PASSES_ITEM("Backstage passes to a TAFKAL80ETC concert") {
            @Override
            ManageableItem initialize(Item item) {
                return new BackstagePassesItem(item);
            }
        },
        CONJURED_ITEM("Conjured Mana Cake") {
            @Override
            ManageableItem initialize(Item item) {
                return new ConjuredItem(item);
            }
        },
        NORMAL("") {
            @Override
            ManageableItem initialize(Item item) {
                return new NormalItem(item);
            }
        };

        private final String itemName;

        public String itemName(){
            return itemName;
        }
        ManageableItemType(String itemName) {
            this.itemName = itemName;
        }

        static ManageableItemType of(final Item item) {
            return Arrays.stream(ManageableItemType.values())
                .filter(e -> e.itemName.equals(item.name))
                .findFirst()
                .orElse(ManageableItemType.NORMAL);
        }

        abstract ManageableItem initialize(final Item item);

        static ManageableItem wrap(final Item item) {
            return of(item).initialize(item);
        }
    }
}
