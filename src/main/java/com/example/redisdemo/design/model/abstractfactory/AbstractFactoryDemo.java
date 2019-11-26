package com.example.redisdemo.design.model.abstractfactory;

/**
 * 抽象工厂测试类
 * @author wc
 */
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        FactoryProvider provider = new FactoryProvider();
        AbstractFactory color = provider.getFactory("color");
        if(null != color){
            Color red = color.getColor("Red");
            red.fill();
        }
    }
}
