package com.example.redisdemo.design.model.adapter;

/**
 * @author wc
 * 适配器测试类
 * 适配器:   将一个类的接口转换成客户希望的另外一个接口。
 *          适配器模式使得原本由于接口不兼容而不能一起工作的那些类可以一起工作
 *           (个人理解:业务功能拓展 原有接口不满足当前业务需要 这是需要适配器 对原有接口 进行功能的拓展
 *           其功效类似于之前的读卡器一端为usb插口一端为内存卡读取接口 这里的usb接口就为老项目功能 读卡器
 *           就是适配器)
 * 用途:主要解决在软件系统中，常常要将一些"现存的对象"放到新的环境中，而新环境要求的接口是现对象不能满足的。
 * url:https://edu.aliyun.com/course/471/learn#lesson/4538
 */
public class AdapterTest {
    public static void main(String[] args) {
        AudioPlayer aduio = new AudioPlayer();
        aduio.play("mP4","卧虎藏龙.mp4");
    }
}
