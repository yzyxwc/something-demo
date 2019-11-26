package com.example.redisdemo.design.model.prototype;

/**
 * 定义抽象类Shape
 * @author wc
 */
public abstract class AbstractShape implements Cloneable {
    private String id;
    protected String type;
    abstract void draw();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    @Override
    public Object clone(){
        Object clone = null;
        try {
            clone = super.clone();
        }catch (CloneNotSupportedException e){
            System.out.println("clone exception:" + e.getMessage());
        }
        return clone;
    }
}
