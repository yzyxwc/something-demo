package com.example.redisdemo.design.model.single;

public class SingleObject {
    /**
     * 单例全局变量
     * volatile是关键
     */
    private static volatile SingleObject ourInstance = new SingleObject();

    /**
     * 添加同步锁防止多线程的时候多个线程同时进入该方法导致同时多次实例化 从而打破 单例
     * 但是这种实现会有序列化与反序列化的bug
     * @return
     */
    public static synchronized SingleObject getInstance() {
        return ourInstance;
    }
    /**
     * 核心代码实现 将无参构造器 私有化 防止外部对其进行实例化
     */
    private SingleObject() {
    }
}
