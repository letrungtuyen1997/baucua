package com.ss.object;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.commons.Tweens;
import com.effect.SoundEffect;
import com.ss.GMain;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;

import java.text.DecimalFormat;

public class VideoAds {
    TextureAtlas textureAtlas;
    Group group = new Group();
    Group group1 = new Group();
    public VideoAds(TextureAtlas textureAtlas){
        GStage.addToLayer(GLayer.top,group1);
        GStage.addToLayer(GLayer.top,group);
        this.textureAtlas = textureAtlas;
        showFrame();

    }
    void showFrame(){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GMain.screenWidth/2,-GMain.screenHeight/2, GMain.screenWidth*2, GMain.screenHeight*2);
        blackOverlay.setColor(0,0,0,0.8f);
        group1.addActor(blackOverlay);
        group.setPosition(-200, GMain.screenHeight/2, Align.center);
        group.setOrigin(Align.center);
        Image Panel = GUI.createImage(textureAtlas,"panelAds");
        Panel.setPosition(0,0,Align.center);
        group.addActor(Panel);
        Image btnWatch = GUI.createImage(textureAtlas,"btnVideoAds");
        btnWatch.setPosition(0,0,Align.center);
        group.addActor(btnWatch);
        Image btnClose = GUI.createImage(textureAtlas,"Skip");
        btnClose.setPosition(0,btnClose.getHeight()*2-20,Align.center);
        group.addActor(btnClose);
        ///////// event/////////
        Tweens.setTimeout(group,0.5f,()->{
            eventBtnWatch(btnWatch);
            eventBtnClose(btnClose);
        });

        //////// action///////
        group.addAction(Actions.moveTo(GMain.screenWidth/2,GMain.screenHeight/2,0.5f, Interpolation.swingOut));

    }
    void eventBtnWatch(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                btn.setTouchable(Touchable.disabled);
                //gamePlay.MyMoney +=100000;
                //gamePlay.MyMonneyLabel.setText(FortmartPrice(gamePlay.MyMoney));
                showAds();
            }
        });

    }
    void eventBtnClose(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.panelOut);
                btn.setTouchable(Touchable.disabled);
                group.addAction(Actions.moveTo(1500,GMain.screenHeight/2,0.5f, Interpolation.swingIn));
                Tweens.setTimeout(group,0.5f,()->{
                    group.clear();
                    group1.clear();
                });
            }
        });

    }
    void showAds(){
        if(GMain.platform.isVideoRewardReady()){
            GMain.platform.ShowVideoReward((boolean success) -> {
                if(success){
                    gamePlay.MyMoney +=1000000;
                    gamePlay.OldMonney = gamePlay.MyMoney;
                    gamePlay.MyMonneyLabel.setText(FortmartPrice(gamePlay.MyMoney));
                    group.addAction(Actions.moveTo(1500,GMain.screenHeight/2,0.5f, Interpolation.swingIn));
                    Tweens.setTimeout(group,0.5f,()->{
                        group.clear();
                        group1.clear();
                    });
                }

            });
        }
    }
    private String FortmartPrice(Long Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }

}
