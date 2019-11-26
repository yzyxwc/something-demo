package com.example.redisdemo.design.model.abstractfactory;

/**
 * 依据不同类型从抽象工厂返回不同的工厂
 * @author wc
 */
public class FactoryProvider {
    public AbstractFactory getFactory(String factory){
        if(null == factory){
            return null;
        }else if (factory.equalsIgnoreCase("color")){
            return new ColorFactory();
        }else if (factory.equalsIgnoreCase("shape")){
            return new ShapeFactory();
        }else{
            return null;
        }
    }
}
