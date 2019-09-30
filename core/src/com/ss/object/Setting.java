package com.ss.object;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.commons.Tweens;
import com.commons._ToggleButton;
import com.effect.SoundEffect;
import com.platform.ToggleHandler;
import com.ss.GMain;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.transitions.GTransitionSlide;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;
import com.ss.scene.startSence;

public class Setting implements ToggleHandler {
    TextureAtlas textureAtlas;
    GScreen gScreen;
    Group group = new Group();
    Group group1 = new Group();
    Image btnSeeting;

   public Setting(GScreen gScreen,TextureAtlas textureAtlas, Image btnSetting){
        GStage.addToLayer(GLayer.top,group1);
        GStage.addToLayer(GLayer.top,group);
        this.textureAtlas = textureAtlas;
        this.gScreen = gScreen;
        this.btnSeeting = btnSetting;
        showFrame();
    }
    void showFrame(){
       final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GMain.screenWidth/2,-GMain.screenHeight/2, GMain.screenWidth*2, GMain.screenHeight*2);
        blackOverlay.setColor(0,0,0,0.8f);
        group1.addActor(blackOverlay);
        group.setPosition(-200, GMain.screenHeight/2, Align.center);
        group.setOrigin(Align.center);
        Image Panel = GUI.createImage(textureAtlas,"panel");
        Panel.setPosition(0,0,Align.center);
        group.addActor(Panel);
        Image btnExit = GUI.createImage(textureAtlas,"btnThoat");
        btnExit.setPosition(-btnExit.getWidth()/2,btnExit.getHeight()*2-20,Align.center);
        group.addActor(btnExit);
        Image btnClose = GUI.createImage(textureAtlas,"btnClose");
        btnClose.setPosition(+btnClose.getWidth()/2,btnClose.getHeight()*2-20,Align.center);
        group.addActor(btnClose);
        ///////// event/////////
        eventBtnClose(btnClose);
        eventBtnExit(btnExit);
        //////// action///////
        group.addAction(Actions.moveTo(GMain.screenWidth/2,GMain.screenHeight/2,0.5f,Interpolation.swingOut));
        ///////////// toggle button/////
        loadToggleBtn();

    }
    void eventBtnExit(Image btn){
       btn.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               super.clicked(event, x, y);
               SoundEffect.Play(SoundEffect.select);
               btn.setTouchable(Touchable.disabled);
               gScreen.setScreen(new startSence(),GTransitionSlide.init(0.5f,3,true,Interpolation.slowFast));
           }
       });

    }
    void eventBtnClose(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.panelIn);
                btn.setTouchable(Touchable.disabled);
                group.addAction(Actions.moveTo(1500,GMain.screenHeight/2,0.5f, Interpolation.swingIn));
                Tweens.setTimeout(group,0.5f,()->{
                    btnSeeting.setTouchable(Touchable.enabled);
                    group.clear();
                    group1.clear();
                });
            }
        });

    }
    void loadToggleBtn(){

        Image TurnOffSound = GUI.createImage(textureAtlas,"turnOff");
        TurnOffSound.setPosition(TurnOffSound.getWidth()/2+35,-TurnOffSound.getHeight()-10,Align.center);
        group.addActor(TurnOffSound);
        Image TurnOnSound = GUI.createImage(textureAtlas,"turnOn");
        TurnOnSound.setPosition(TurnOnSound.getWidth()/2+35,-TurnOnSound.getHeight()-10,Align.center);
        group.addActor(TurnOnSound);
        /////////////// music////////
        Image TurnOnMusic = GUI.createImage(textureAtlas,"turnOn");
        TurnOnMusic.setPosition(TurnOnMusic.getWidth()/2+35,TurnOnMusic.getHeight()/2+5,Align.center);
        group.addActor(TurnOnMusic);
        Image TurnOffMusic = GUI.createImage(textureAtlas,"turnOff");
        TurnOffMusic.setPosition(TurnOffMusic.getWidth()/2+35,TurnOffMusic.getHeight()/2+5,Align.center);
        group.addActor(TurnOffMusic);
        new _ToggleButton(TurnOnSound,TurnOffSound,"sound",this);
        new _ToggleButton(TurnOnMusic,TurnOffMusic,"music",this);
        setDefautSound(TurnOnSound,TurnOffSound,TurnOnMusic,TurnOffMusic);

    }

    void setDefautSound(Image mute, Image unmute, Image Music , Image unMusic){
        if(SoundEffect.mute==true){
            mute.setVisible(false);
        }else {
            unmute.setVisible(false);
        }
        if(SoundEffect.music==true){
            Music.setVisible(false);
        }else {
            unMusic.setVisible(false);
        }

    }


    @Override
    public void activeHandler(String str) {
       if(str=="sound"){
           SoundEffect.mute = false;

       }
        if(str=="music"){
            SoundEffect.music = false;
            SoundEffect.Playmusic();

        }

    }

    @Override
    public void deactiveHandler(String str) {
        if(str=="sound"){
            SoundEffect.mute = true;

        }
        if(str=="music"){
            SoundEffect.music = true;
            SoundEffect.Stopmusic();



        }
    }
}
