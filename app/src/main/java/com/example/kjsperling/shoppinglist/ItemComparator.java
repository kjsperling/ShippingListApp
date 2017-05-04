package com.example.kjsperling.shoppinglist;

import java.util.Comparator;

public class ItemComparator implements Comparator<Item> {

    @Override
    public int compare(Item item1, Item item2) {
        return item1.getPriority() - item2.getPriority();
    }
}