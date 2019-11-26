package com.example.redisdemo.design.model.adapter;
/**
 * 适配器实现
 */
public class AviPlayer implements AdvancedMediaPlayer {
    @Override
    public void aviPlay(String fileName) {
        System.out.println("AVI 播放" + fileName);
    }

    @Override
    public void mp4Play(String fileName) {

    }
}
