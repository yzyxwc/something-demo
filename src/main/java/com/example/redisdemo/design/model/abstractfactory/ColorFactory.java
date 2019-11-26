package com.example.redisdemo.design.model.abstractfactory;

import org.apache.commons.lang3.StringUtils;

public class ColorFactory extends AbstractFactory {
    @Override
    Shape getShape(String shape) {
        return null;
    }

    @Override
    Color getColor(String color) {
        if(StringUtils.isBlank(color)){
            return null;
        }else if (color.equalsIgnoreCase("red")){
            return new Red();
        }else if (color.equalsIgnoreCase("green")){
            return new Green();
        }else {
            return ()-> System.out.println("none implement to return");
        }
    }
}
