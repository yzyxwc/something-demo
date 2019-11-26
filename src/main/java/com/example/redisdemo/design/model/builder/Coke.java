package com.example.redisdemo.design.model.builder;

/**
 * 冷饮的具体实现
 * @author wc
 */
public class Coke extends BaseColdDrink {
    @Override
    public String name() {
        return "Coke";
    }

    @Override
    public float price() {
        return 30.0f;
    }
}
