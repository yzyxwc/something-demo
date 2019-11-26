package com.example.redisdemo.design.model.builder;

/**
 * 冷饮的具体实现
 * @author wc
 */
public class Pepsi extends BaseColdDrink {
    @Override
    public String name() {
        return "Pepsi";
    }

    @Override
    public float price() {
        return 35.0f;
    }
}
