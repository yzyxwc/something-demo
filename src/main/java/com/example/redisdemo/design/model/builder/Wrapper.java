package com.example.redisdemo.design.model.builder;

/**
 * @author wc
 * 包装实现类:盒装
 */
public class Wrapper implements Packing {
    @Override
    public String pack() {
        return "Wrapper";
    }
}
