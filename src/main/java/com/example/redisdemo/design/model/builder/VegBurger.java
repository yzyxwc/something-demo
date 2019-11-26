package com.example.redisdemo.design.model.builder;

/**
 * 汉堡的具体实现
 * @author wc
 */
public class VegBurger extends BaseBurger {
    @Override
    public String name() {
        return "VegBurger";
    }

    @Override
    public float price() {
        return 25.0f;
    }
}
