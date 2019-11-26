package com.example.redisdemo.design.model.adapter;

/**
 * 适配器
 * @author wc
 */
public class AdvancedMediaAdapter implements MediaPlayer{
    AdvancedMediaPlayer advancedMediaPlayer;

    public AdvancedMediaAdapter(String audioType) {
        if(audioType.equalsIgnoreCase("avi")){
            advancedMediaPlayer = new AviPlayer();
        }
        if(audioType.equalsIgnoreCase("mp4")){
            advancedMediaPlayer = new Mp4Player();
        }
    }

    @Override
    public void play(String audioType, String fileName) {
        if(audioType.equalsIgnoreCase("MP3")){
            System.out.println("MP3 audio play");
        }else if(audioType.equalsIgnoreCase("avi")){
            advancedMediaPlayer.aviPlay(fileName);
        }else if(audioType.equalsIgnoreCase("mp4")){
            advancedMediaPlayer.mp4Play(fileName);
        }else {
            System.out.println("none media for play");
        }
    }
}
