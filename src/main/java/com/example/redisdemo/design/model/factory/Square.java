package com.example.redisdemo.design.model.factory;

/**
 * 正方形
 * @author wc
 */
public class Square implements Shape{
    @Override
    public void draw() {
        System.out.println("draw a shape of square");
    }
}
