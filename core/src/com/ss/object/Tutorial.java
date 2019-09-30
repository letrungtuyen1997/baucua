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

public class Tutorial {
    TextureAtlas textureAtlas;
    Group group = new Group();
    Group group1 = new Group();
    Image btnTutorial;
    public Tutorial(TextureAtlas textureAtlas, Image btnTutorial){
        GStage.addToLayer(GLayer.top,group1);
        GStage.addToLayer(GLayer.top,group);
        this.textureAtlas = textureAtlas;
        this.btnTutorial = btnTutorial;
        showFrame();

    }
    void showFrame(){
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GMain.screenWidth/2,-GMain.screenHeight/2, GMain.screenWidth*2, GMain.screenHeight*2);
        blackOverlay.setColor(0,0,0,0.8f);
        group1.addActor(blackOverlay);
        group.setPosition(GMain.screenWidth/2,GMain.screenHeight/2,Align.center);
        group.setScale(0);
        group.setOrigin(Align.center);
        Image Panel = GUI.createImage(textureAtlas,"frameTutorial");
        Panel.setPosition(0,0,Align.center);
        group.addActor(Panel);
        Image btnClose = GUI.createImage(textureAtlas,"btX");
        btnClose.setPosition(Panel.getWidth()/2-btnClose.getWidth()/2-10,-Panel.getHeight()/2+btnClose.getHeight()/2+10,Align.center);
        group.addActor(btnClose);
        ///////// event/////////
        eventBtnExit(btnClose);
        //////// action///////
        group.addAction(Actions.scaleTo(1,1,0.5f, Interpolation.swingOut));

    }
    void eventBtnExit(Image btn){
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.panelIn);
                btn.setTouchable(Touchable.disabled);
                group.addAction(Actions.scaleTo(0,0,0.5f, Interpolation.swingIn));
                Tweens.setTimeout(group,0.5f,()->{
                    btnTutorial.setTouchable(Touchable.enabled);
                    group.clear();
                    group1.clear();
                });
            }
        });

    }
}
