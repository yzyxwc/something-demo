package com.example.redisdemo.design.model.builder;

/**
 * 汉堡抽象类
 * @author wc
 */
public abstract class BaseBurger implements Item{
    /**
     * 包装
     * @return Packing 包装的抽象接口
     */
    @Override
    public Packing packing(){
        return new Wrapper();
    }

    /**
     * 商品价格
     * @return float 价格
     */
    @Override
    public abstract float price();
}
