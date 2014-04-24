package com.cschryer.eaterofjsons.classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OfferItem {

    /**
     * An array of items.
     */
    public static List<SingleItem> ITEMS = new ArrayList<SingleItem>();

    /**
     * A map of items, by ID.
     */
    public static Map<String, SingleItem> ITEM_MAP = new HashMap<String, SingleItem>();

    static {
        // Add 3 sample items.
        addItem(new SingleItem("1", "Item 1"));
        addItem(new SingleItem("2", "Item 2"));
        addItem(new SingleItem("3", "Item 3"));
    }

    private static void addItem(SingleItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class SingleItem {
        public String id;
        public String content;

        public SingleItem(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
