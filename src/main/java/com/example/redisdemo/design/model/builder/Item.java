package com.example.redisdemo.design.model.builder;

public interface Item {
    /**
     * 商品名称
     * @return String 名称
     */
    String name();

    /**
     * 包装
     * @return Packing 包装的抽象接口
     */
    Packing packing();

    /**
     * 商品价格
     * @return float 价格
     */
    float price();
}
