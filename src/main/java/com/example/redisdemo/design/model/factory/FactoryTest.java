package com.example.redisdemo.design.model.factory;

import java.util.Objects;

public class FactoryTest {
    public static void main(String[] args) {
        ShapeFactory factory = new ShapeFactory();
        String s = null;
        Shape circle = factory.getShape(s);
        if(Objects.deepEquals(circle,null)){
            System.out.println("return obj is null");
        }else{
            circle.draw();
        }
    }
}
