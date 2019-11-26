package com.example.redisdemo.design.model.prototype;

/**
 * 圆形实现抽象接口
 * @author wc
 */
public class Circle extends AbstractShape {
    public Circle() {
        type = "Circle";
    }

    @Override
    void draw() {
        System.out.println("Inside Circle::draw() method.");
    }
}
