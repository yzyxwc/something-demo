package com.example.redisdemo.design.model.factory;

/**
 * 圆形
 * @author wc
 */
public class Circle implements Shape{
    @Override
    public void draw() {
        System.out.println("draw a shape of circle");
    }
}
