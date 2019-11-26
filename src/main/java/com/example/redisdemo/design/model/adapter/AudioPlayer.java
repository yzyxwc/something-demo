package com.example.redisdemo.design.model.adapter;

public class AudioPlayer implements MediaPlayer {
    AdvancedMediaAdapter advancedMediaAdapter;
    @Override
    public void play(String audioType, String fileName) {
        advancedMediaAdapter = new AdvancedMediaAdapter(audioType);
        advancedMediaAdapter.play(audioType,fileName);
    }
}
