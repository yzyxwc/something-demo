package com.example.redisdemo.design.model.abstractfactory;

/**
 * @author wc
 * Color接口实现类:Red
 */
public class Red implements Color {
    @Override
    public void fill() {
        System.out.println("Red Color is fill");
    }
}
