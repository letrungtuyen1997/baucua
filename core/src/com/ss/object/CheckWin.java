package com.ss.object;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.commons.Tweens;
import com.effect.SoundEffect;
import com.effect.effectWin;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.action.exAction.GTemporalAction;
import com.ss.core.exSprite.GShapeSprite;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.scene.gamePlay;

import java.text.DecimalFormat;

public class CheckWin {
    Group groupOverLay = new Group();
    Group groupEffect = new Group();
    Group group = new Group();
    TextureAtlas textureAtlas;
    BitmapFont fontwin, fontlose;
    effectWin Win;
    Label donate;
    long monneyDonate=0;
    long newMonney =0;
    int tic=0;


    boolean checkDone=false;
    public CheckWin(String type, TextureAtlas textureAtlas ){
        initFont();
        this.textureAtlas = textureAtlas;
        GStage.addToLayer(GLayer.top,groupOverLay);
        GStage.addToLayer(GLayer.top,groupEffect);
        GStage.addToLayer(GLayer.top,group);
        final GShapeSprite blackOverlay = new GShapeSprite();
        blackOverlay.createRectangle(true, -GMain.screenWidth/2,-GMain.screenHeight/2, GMain.screenWidth*2, GMain.screenHeight*2);
        blackOverlay.setColor(0,0,0,0.8f);
        groupOverLay.addActor(blackOverlay);
        if(gamePlay.newMonney>=0){
            particleWin(1);
            SoundEffect.Play(SoundEffect.Win);


        }else {
            particleWin(2);
            SoundEffect.Play(SoundEffect.fail);


        }

        Tweens.setTimeout(group,1f,()->{
            if(gamePlay.newMonney>=0){
                animationWin();

            }else {
                animationLose();

            }
            donateMonney();
        });

    }
    void particleWin(int id){
        Win = new effectWin(id,GMain.screenWidth/2,GMain.screenHeight/2,groupEffect);
        groupEffect.addActor(Win);
        Win.start();

    }
    void animationWin(){
        Image win = GUI.createImage(textureAtlas,"winVi");
        win.setOrigin(Align.center);
        win.setPosition(GMain.screenWidth/2,GMain.screenHeight/2-win.getHeight()/2+50, Align.center);
        group.addActor(win);
        win.setScale(0);
        win.addAction(Actions.scaleTo(1,1,0.5f, Interpolation.bounceOut));


    }
    void animationLose(){
        Image lose = GUI.createImage(textureAtlas,"loseVi");
        lose.setOrigin(Align.center);
        lose.setPosition(GMain.screenWidth/2,GMain.screenHeight/2-lose.getHeight()/2+50, Align.center);
        group.addActor(lose);
        lose.setScale(0);
        lose.addAction(Actions.scaleTo(1,1,0.5f, Interpolation.bounceOut));


    }
    void donateMonney(){
        if(gamePlay.newMonney>0){
            donate = new Label(""+monneyDonate,new Label.LabelStyle(fontwin,null));

        }else {
            donate = new Label(""+monneyDonate,new Label.LabelStyle(fontlose,null));

        }
        donate.setPosition(GMain.screenWidth/2,GMain.screenHeight/2,Align.center);
        donate.setAlignment(Align.center);
        group.addActor(donate);
        donate.setScale(0);
        donate.addAction(Actions.scaleTo(1,1,0.5f,Interpolation.bounceOut));
        Tweens.setTimeout(group,0.5f,()->{
            for(int i=0;i<10;i++){
                Tweens.setTimeout(group,0.05f*i,()->{
                    SoundEffect.Play(SoundEffect.coins);
                });
            }
            counterMonney();
        });

    }
    /*
    Label l = new Lable(2000);
    diemGoc = l.getDiem(); //2000
    diemTang = 2000;
    l.addAction(GTemporalAction.add(1, (percent, actor) -> {
          0.1 2200
          0.2 2400
          0.3 2600
          1     2000 + 2000*1 = 4000
         l.setText(diemGoc + diemTang*percent)
    });
     */


    void counterMonney(){

        group.addAction(
                GTemporalAction.add(1, (percent, actor) -> {

                if(monneyDonate!=gamePlay.newMonney){
                    if(gamePlay.newMonney>0){
                        donate.setText("+"+FortmartPrice((long)(monneyDonate+gamePlay.newMonney*percent)));
                    }else if(gamePlay.newMonney<0){
                        donate.setText(FortmartPrice((long)(monneyDonate+gamePlay.newMonney*percent)));

                    }else {
                        donate.setText(FortmartPrice((long)(monneyDonate+gamePlay.newMonney*percent)));

                    }


                }
        }));
        Tweens.setTimeout(group,1.5f,()->{
                        groupOverLay.clear();
                        group.clear();
                        gamePlay.newMonney =0;
                        gamePlay.OldMonney=gamePlay.MyMoney;
        });




    }

    void initFont(){
        fontwin = GAssetsManager.getBitmapFont("gold.fnt");
        fontlose = GAssetsManager.getBitmapFont("silver.fnt");

    }
    private String FortmartPrice(Long Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
}
