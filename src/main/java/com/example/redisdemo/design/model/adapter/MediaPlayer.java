package com.example.redisdemo.design.model.adapter;

/**
 * MP3播放器接口
 */
public interface MediaPlayer {
    /**
     * 播放方法
     * @param audioType 播放的文件类型
     * @param fileName 播放的文件名
     */
    void play(String audioType,String fileName);
}
