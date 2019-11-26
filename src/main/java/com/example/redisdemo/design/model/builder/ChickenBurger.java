package com.example.redisdemo.design.model.builder;

/**
 * 汉堡的具体实现
 * @author wc
 */
public class ChickenBurger extends BaseBurger {
    @Override
    public String name() {
        return "Chicken Burger";
    }

    @Override
    public float price() {
        return 50.5f;
    }
}
