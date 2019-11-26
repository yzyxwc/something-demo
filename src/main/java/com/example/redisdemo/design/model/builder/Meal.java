package com.example.redisdemo.design.model.builder;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wc
 */
public class Meal {
    private List<Item> items = Lists.newArrayList();

    void addItem(Item item){
        items.add(item);
    }

    float getCost(){
        float totalCost = 0.0f;
        for (Item item: items) {
            totalCost += item.price();
        }
        return totalCost;
    }

    public void showItems(){
        items.stream().peek(item->{
            System.out.print("item:" +item.name());
            System.out.print("packing:" +item.packing().pack());
            System.out.print("price:" +item.price());
        }).collect(Collectors.toList());
    }
}
