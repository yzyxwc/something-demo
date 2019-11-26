package com.example.redisdemo.design.model.builder;

/**
 * 冷饮抽象类
 * @author wc
 */
public abstract class BaseColdDrink implements Item{
    /**
     * 包装
     * @return Packing 包装的抽象接口
     */
    @Override
    public Packing packing(){
        return new Battle();
    }

    /**
     * 商品价格
     * @return float 价格
     */
    @Override
    public abstract float price();
}
