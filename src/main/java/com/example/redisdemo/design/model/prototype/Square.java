package com.example.redisdemo.design.model.prototype;

/**
 * 正方形实现抽象接口
 * @author wc
 */
public class Square extends AbstractShape {
    public Square() {
        type = "Square";
    }

    @Override
    void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
