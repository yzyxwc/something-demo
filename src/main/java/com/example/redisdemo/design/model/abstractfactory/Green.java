package com.example.redisdemo.design.model.abstractfactory;

/**
 * @author wc
 * Color接口实现类:Green
 */
public class Green implements Color {
    @Override
    public void fill() {
        System.out.println("Green Color is fill");
    }
}
