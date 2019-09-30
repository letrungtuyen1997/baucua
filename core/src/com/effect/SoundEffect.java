package com.effect;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;

/* renamed from: com.ss.effect.SoundEffect */
public class SoundEffect {
    public static int MAX_COMMON = 10;
    public static Music bgSound = null;
    public static Sound[] commons = null;
    public static boolean music = false;
    public static boolean mute = false;
    public static int panelIn = 0;
    public static int panelOut = 1;
    public  static int select = 2;
    public  static int toggle = 3;
    public  static int Xocdia = 4;
    public  static int Win = 5;
    public  static int fail = 6;
    public  static int coins = 7;



    public static void initSound() {
        commons = new Sound[MAX_COMMON];
        commons[panelIn] = GAssetsManager.getSound("Panel in.mp3");
        commons[panelOut] = GAssetsManager.getSound("Panel out.mp3");
        commons[select] = GAssetsManager.getSound("Select.mp3");
        commons[toggle] = GAssetsManager.getSound("Toggle.mp3");
        commons[Xocdia] = GAssetsManager.getSound("xocdia.mp3");
        commons[Win] = GAssetsManager.getSound("win2.mp3");
        commons[fail] = GAssetsManager.getSound("fail.mp3");
        commons[coins] = GAssetsManager.getSound("Coin.mp3");
        commons[coins].setVolume(2,5);
        bgSound = GAssetsManager.getMusic("soundBauCua.mp3");

    }

    public static void Play(int i) {
        if (!mute) {
            commons[i].play();
        }
    }

    public static void Playmusic() {
        music = false;
        bgSound.play();
        bgSound.setLooping(true);
        bgSound.setVolume(0.2f);
    }

    public static void Stopmusic() {
        music = true;
        bgSound.pause();
    }
}
