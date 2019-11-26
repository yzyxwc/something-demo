package com.example.redisdemo.design.model.abstractfactory;

/**
 * 抽象工厂:生产不同类型的工厂
 * @author wc
 */
public abstract class AbstractFactory {
    abstract Shape getShape(String shape);
    abstract Color getColor(String color);
}
