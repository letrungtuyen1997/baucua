package com.ss.scene;

import com.badlogic.gdx.Gdx;
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
import com.effect.effectWin;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.transitions.GTransition;
import com.ss.core.transitions.GTransitionSlide;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class startSence extends GScreen {
    TextureAtlas textureAtlas;
    Group group = new Group();
    effectWin effectBgLeft, getEffectBgRight;
    @Override
    public void dispose() {
        group.clear();

    }
    @Override
    public void init() {

        SoundEffect.Playmusic();

        GStage.addToLayer(GLayer.ui,group);
        textureAtlas= GAssetsManager.getTextureAtlas("startAtlas.atlas");
        Image bg = GUI.createImage(textureAtlas,"bgStart");
        bg.setPosition(0,0, Align.bottomLeft);
        group.addActor(bg);
        /////////// effect////////
        effectBgLeft = new effectWin(3,100,150,group);
        group.addActor(effectBgLeft);
        getEffectBgRight = new effectWin(3,GMain.screenWidth-100,150,group);
        group.addActor(getEffectBgRight);
        ///////////////
        Image btnStart = GUI.createImage(textureAtlas,"btnStart");
        btnStart.setOrigin(Align.center);
        btnStart.setPosition(GMain.screenWidth/2,GMain.screenHeight/2+200,Align.center);
        group.addActor(btnStart);
        Image logo = GUI.createImage(textureAtlas,"logoStart");
        logo.setPosition(GMain.screenWidth-logo.getWidth()+50,GMain.screenHeight/2-100,Align.center);
        group.addActor(logo);
        animation(logo);
        eventBtnStart(btnStart);
    }

    @Override
    public void run() {

    }

    void animation(Image ani){
        ani.addAction(
                Actions.sequence(
                        Actions.moveBy(0,50,1f, Interpolation.linear),
                        Actions.moveBy(0,-50,1f,Interpolation.linear),
                        GSimpleAction.simpleAction((d,a)->{
                            animation(ani);
                            return true;
                        })
                ));
    }
    void eventBtnStart(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                btn.setTouchable(Touchable.disabled);
                btn.addAction(Actions.sequence(
                        Actions.scaleTo(0.8f,0.8f,0.2f),
                        Actions.scaleTo(1,1,0.2f)
                ));
                Tweens.setTimeout(group,0.4f,()->{
                    setScreen(new gamePlay(), GTransitionSlide.init(0.5f,3,true,Interpolation.slowFast));

                });
            }
        });

    }

}
