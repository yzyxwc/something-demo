package com.example.redisdemo.design.model.abstractfactory;


import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;


/**
 * 形状工厂 依据传入类型不同 返回不同的实体给调用方
 * @author wc
 */
public class ShapeFactory extends AbstractFactory{
    @Override
    public Shape getShape(String shape){
        if(StringUtils.isBlank(shape)){
            return null;
        }else if (shape.equalsIgnoreCase("circle")){
            return new Circle();
        }else if (shape.equalsIgnoreCase("square")){
            return new Square();
        }else {
            return ()-> System.out.println("none implement to return");
        }
    }

    @Override
    Color getColor(String color) {
        return null;
    }
}
