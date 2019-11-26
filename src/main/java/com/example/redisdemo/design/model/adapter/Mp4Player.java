package com.example.redisdemo.design.model.adapter;

/**
 * 适配器实现
 */
public class Mp4Player implements AdvancedMediaPlayer {
    @Override
    public void aviPlay(String fileName) {

    }

    @Override
    public void mp4Play(String fileName) {
        System.out.println("MP4 播放" + fileName);
    }
}
