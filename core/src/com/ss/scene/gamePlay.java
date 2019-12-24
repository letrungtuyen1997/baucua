package com.ss.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.commons.Tweens;
import com.effect.SoundEffect;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;
import com.ss.object.CheckWin;
import com.ss.object.Setting;
import com.ss.object.Tutorial;
import com.ss.object.VideoAds;

import java.text.DecimalFormat;


public class gamePlay extends GScreen {
    GScreen gScreen;
    TextureAtlas gameplayAtlas;
    Group gameGroup = new Group();
    Group baucuaGroup = new Group();
    Group monneyGroup = new Group();
    Group BowlGroup = new Group();
    Group BaucuaInDiskGroup = new Group();
    Group CoinStakeGroup_Nai = new Group();
    Group CoinStakeGroup_Bau = new Group();
    Group CoinStakeGroup_Ga = new Group();
    Group CoinStakeGroup_Cua = new Group();
    Group CoinStakeGroup_Tom = new Group();
    Group CoinStakeGroup_Ca = new Group();
    Group CoinStakeGroup = new Group();
    Group frameClearGroup = new Group();
    Group frameClearTempGroup = new Group();
    Group groupLable = new Group();


    Array<Image> overlaymonneyArr= new Array<>();
    Array<Image> overlayArr= new Array<>();
    Array<Image> frameClearArr= new Array<>();
    Array<Image> monneyInBottomArr= new Array<>();
    Array<Image> baucuaArr= new Array<>();
    Array<Integer> valueCoin= new Array<>();
    int item1=0, item2=0,item3=0, inc=0;
    long StakeMoney =0;
    public static long MyMoney =GMain.prefs.getLong("myMonney");
    //public static long MyMoney =100000;
    public static long OldMonney =0;
    public static long newMonney =0;
    long cuocNai=0,cuocBau=0,cuocGa=0,cuocCa=0,cuocCua=0,cuocTom=0;
    BitmapFont fontmonney, fontText;
    Label StakeMonneyLabel;
    public static Label MyMonneyLabel;
    int isCoins = 0,quanlityNai=0,quanlityBau=0,quanlityGa=0,quanlityCa=0,quanlityCua=0,quanlityTom=0;
    Boolean checkIsStake=false, checkRing =false;
    Boolean clicktemp=false;
    Image disk,bowl,xilau1,xilau2,xilau3;
    float durationsXocDia = 0.1f;
    int countShowAds =0;
    int countShowAds2 =0;
    @Override
    public void dispose() {
        gameGroup.clear();
        baucuaGroup.clear();
        monneyGroup.clear();
        BowlGroup.clear();
        BaucuaInDiskGroup.clear();
        frameClearTempGroup.clear();
        frameClearGroup.clear();

    }

    @Override
    public void init() {
        SoundEffect.Playmusic();
        OldMonney = MyMoney;
        this.gScreen = this;
        initFont();
        GStage.addToLayer(GLayer.ui,gameGroup);
        GStage.addToLayer(GLayer.top,CoinStakeGroup);

        GStage.addToLayer(GLayer.top,CoinStakeGroup_Ca);
        GStage.addToLayer(GLayer.top,CoinStakeGroup_Cua);
        GStage.addToLayer(GLayer.top,CoinStakeGroup_Tom);
        GStage.addToLayer(GLayer.top,CoinStakeGroup_Nai);
        GStage.addToLayer(GLayer.top,CoinStakeGroup_Bau);
        GStage.addToLayer(GLayer.top,CoinStakeGroup_Ga);
        GStage.addToLayer(GLayer.top,frameClearGroup);
        GStage.addToLayer(GLayer.top,frameClearTempGroup);
        GStage.addToLayer(GLayer.top,groupLable);


        /////////////////// StakeCoins//////

        CoinStakeGroup.addActor(CoinStakeGroup_Ca);
        CoinStakeGroup.addActor(CoinStakeGroup_Cua);
        CoinStakeGroup.addActor(CoinStakeGroup_Tom);
        CoinStakeGroup.addActor(CoinStakeGroup_Nai);
        CoinStakeGroup.addActor(CoinStakeGroup_Bau);
        CoinStakeGroup.addActor(CoinStakeGroup_Ga);

        gameGroup.addActor(baucuaGroup);
        gameGroup.addActor(monneyGroup);
        BowlGroup.addActor(monneyGroup);
        gameplayAtlas = GAssetsManager.getTextureAtlas("gameplay.atlas");
        showUi();
        showdiskAndBowl();
        showBauCua();
        showMonney();
        setNameArrBaucua();
        setValueCoins();
        showBtnSetting();
        showBtnTutorial();
        /////////////// event click//////
        setClickDefault();
        eventClick();
        //////donate frist play/////
        DonateMonneyFirst();


    }

    @Override
    public void run() {

    }
    void DonateMonneyFirst(){
        if(GMain.prefs.getInteger("checkFrist")==0){
            GMain.prefs.putInteger("checkFrist",1);
            GMain.prefs.flush();
            MyMoney=1000000;
            OldMonney=MyMoney;
            MyMonneyLabel.setText(FortmartPrice(MyMoney));

        }
    }
    void showUi(){
        Image bg = GUI.createImage(gameplayAtlas,"bgGameScene");
        bg.setPosition(0,0, Align.bottomLeft);
        gameGroup.addActor(bg);
        Image frameCenter = GUI.createImage(gameplayAtlas,"frameCenter1");
        frameCenter.setPosition(GMain.screenWidth/2,GMain.screenHeight/2,Align.center);
        gameGroup.addActor(frameCenter);
        Image frameBottom = GUI.createImage(gameplayAtlas,"frameBottom");
        frameBottom.setPosition(GMain.screenWidth/2,GMain.screenHeight-frameBottom.getHeight()/2-10,Align.center);
        gameGroup.addActor(frameBottom);
        Image label = GUI.createImage(gameplayAtlas,"label");
        label.setPosition(280,GMain.screenHeight-(label.getHeight()+30),Align.center);
        gameGroup.addActor(label);
        ///////////// load monneyStake///////

        StakeMonneyLabel = new Label(""+StakeMoney,new Label.LabelStyle(fontmonney, Color.YELLOW));
        StakeMonneyLabel.setPosition(280,GMain.screenHeight-(StakeMonneyLabel.getHeight()+20),Align.center);
        StakeMonneyLabel.setAlignment(Align.center);
        gameGroup.addActor(StakeMonneyLabel);

        ///////// load myMonney ///////////
        MyMonneyLabel = new Label(FortmartPrice(MyMoney),new Label.LabelStyle(fontmonney, Color.YELLOW));
        MyMonneyLabel.setPosition(GMain.screenWidth/2+50,MyMonneyLabel.getHeight()+45,Align.center);
        MyMonneyLabel.setAlignment(Align.center);
        gameGroup.addActor(MyMonneyLabel);


    }
    void showdiskAndBowl(){
        GStage.addToLayer(GLayer.top,BowlGroup);
        disk = GUI.createImage(gameplayAtlas,"plate");
        disk.setPosition(350,450,Align.center);
        gameGroup.addActor(disk);
        bowl = GUI.createImage(gameplayAtlas,"bowl");
        bowl.setOrigin(Align.center);
        bowl.setZIndex(1000);
        bowl.setPosition(70,300,Align.center);
        BowlGroup.addActor(bowl);
        eventBowl(bowl,disk);
        showBauCuaInDisk();


    }

    void showBauCua(){
        GStage.addToLayer(GLayer.ui,baucuaGroup);
        int load;
        for (int i =0; i<2;i++){
            for(int j =0;j<3;j++){
                load = (i==0)? 1 :4;
                Image btn = GUI.createImage(gameplayAtlas,"bt"+(load+j));
                btn.setPosition(GMain.screenWidth/2+(j*(btn.getWidth()+20))+100,60+200*(i+1),Align.center);
                baucuaGroup.addActor(btn);
                //////////
                Image frameClear = GUI.createImage(gameplayAtlas,"fameClear");
                frameClear.setPosition(GMain.screenWidth/2+(j*(btn.getWidth()+20))+100,60+200*(i+1),Align.center);
                frameClearGroup.addActor(frameClear);
                Image frameClearTemp = GUI.createImage(gameplayAtlas,"fameClear");
                frameClearTemp.setPosition(GMain.screenWidth/2+(j*(btn.getWidth()+20))+100,60+200*(i+1),Align.center);
                frameClearTempGroup.addActor(frameClearTemp);
                frameClearArr.add(frameClearTemp);
                ///////////////
                baucuaArr.add(frameClear);
                Image overlay = GUI.createImage(gameplayAtlas,"overLay");
                overlay.setPosition(GMain.screenWidth/2+(j*(overlay.getWidth()))+100,60+200*(i+1),Align.center);
                overlayArr.add(overlay);
                baucuaGroup.addActor(overlay);
                overlay.setVisible(false);

            }
        }

    }
    ///////////////////// cuoc tien ///////
    void setClickDefault(){
        for(int i=0;i<frameClearArr.size;i++){
            frameClearArr.get(i).addListener(new InputListener(){
                @Override
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    bowl.setTouchable(Touchable.disabled);
                    disk.setTouchable(Touchable.disabled);
                    SoundEffect.Play(SoundEffect.select);
                    frameClearTempGroup.setVisible(false);
                    animationRing2();
                    return super.touchDown(event, x, y, pointer, button);
                }
            });
        }
    }
    void eventClick(){
        baucuaArr.get(0).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                checkEndMonney();
                groupLable.clear();
                for (int i = 0;i<overlaymonneyArr.size;i++){
                    if(overlaymonneyArr.get(i).isVisible()==true){
                        checkMonney(valueCoin.get(i),overlaymonneyArr.get(i).getX(),overlaymonneyArr.get(i).getY(),i);
                        if(checkIsStake==true){
                            animationStakeMonney(isCoins, baucuaArr.get(0).getX(),baucuaArr.get(0).getY(),baucuaArr.get(0).getName());
                            if(i==0){StakeMoney+=10000; MyMoney-=10000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney)); isCoins=1;}
                            else if(i==1){StakeMoney+=20000;MyMoney-=20000; MyMonneyLabel.setText(FortmartPrice(MyMoney)); StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=2;}
                            else if(i==2){StakeMoney+=50000; MyMoney-=50000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=3;}
                            else if(i==3){StakeMoney+=100000; MyMoney-=100000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=4;}
                            else if(i==4){StakeMoney+=200000; MyMoney-=200000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=5;}
                            else if(i==5){StakeMoney+=500000; MyMoney-=500000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=6;}
                        }


                    }
                }

                ;
            }
        });
        baucuaArr.get(1).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                checkEndMonney();
                groupLable.clear();

                for (int i = 0;i<overlaymonneyArr.size;i++){
                    if(overlaymonneyArr.get(i).isVisible()==true){
                        checkMonney(valueCoin.get(i),overlaymonneyArr.get(i).getX(),overlaymonneyArr.get(i).getY(),i);
                        if(checkIsStake==true){
                            animationStakeMonney(isCoins, baucuaArr.get(1).getX(),baucuaArr.get(1).getY(),baucuaArr.get(1).getName());
                            if(i==0){StakeMoney+=10000; MyMoney-=10000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney)); isCoins=1;}
                            else if(i==1){StakeMoney+=20000;MyMoney-=20000; MyMonneyLabel.setText(FortmartPrice(MyMoney)); StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=2;}
                            else if(i==2){StakeMoney+=50000; MyMoney-=50000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=3;}
                            else if(i==3){StakeMoney+=100000; MyMoney-=100000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=4;}
                            else if(i==4){StakeMoney+=200000; MyMoney-=200000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=5;}
                            else if(i==5){StakeMoney+=500000; MyMoney-=500000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=6;}
                        }
                    }
                }

                ;
            }
        });
        baucuaArr.get(2).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                checkEndMonney();
                groupLable.clear();

                for (int i = 0;i<overlaymonneyArr.size;i++){
                    if(overlaymonneyArr.get(i).isVisible()==true){
                        checkMonney(valueCoin.get(i),overlaymonneyArr.get(i).getX(),overlaymonneyArr.get(i).getY(),i);
                        if(checkIsStake==true){
                            animationStakeMonney(isCoins, baucuaArr.get(2).getX(),baucuaArr.get(2).getY(),baucuaArr.get(2).getName());
                            if(i==0){StakeMoney+=10000; MyMoney-=10000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney)); isCoins=1;}
                            else if(i==1){StakeMoney+=20000;MyMoney-=20000; MyMonneyLabel.setText(FortmartPrice(MyMoney)); StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=2;}
                            else if(i==2){StakeMoney+=50000; MyMoney-=50000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=3;}
                            else if(i==3){StakeMoney+=100000; MyMoney-=100000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=4;}
                            else if(i==4){StakeMoney+=200000; MyMoney-=200000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=5;}
                            else if(i==5){StakeMoney+=500000; MyMoney-=500000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=6;}
                        }
                    }
                }

                ;
            }
        });
        baucuaArr.get(3).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                checkEndMonney();
                groupLable.clear();

                for (int i = 0;i<overlaymonneyArr.size;i++){
                    if(overlaymonneyArr.get(i).isVisible()==true){
                        checkMonney(valueCoin.get(i),overlaymonneyArr.get(i).getX(),overlaymonneyArr.get(i).getY(),i);
                        if(checkIsStake==true){
                            animationStakeMonney(isCoins, baucuaArr.get(3).getX(),baucuaArr.get(3).getY(),baucuaArr.get(3).getName());
                            if(i==0){StakeMoney+=10000; MyMoney-=10000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney)); isCoins=1;}
                            else if(i==1){StakeMoney+=20000;MyMoney-=20000; MyMonneyLabel.setText(FortmartPrice(MyMoney)); StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=2;}
                            else if(i==2){StakeMoney+=50000; MyMoney-=50000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=3;}
                            else if(i==3){StakeMoney+=100000; MyMoney-=100000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=4;}
                            else if(i==4){StakeMoney+=200000; MyMoney-=200000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=5;}
                            else if(i==5){StakeMoney+=500000; MyMoney-=500000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=6;}
                        }

                    }
                }

                ;
            }
        });
        baucuaArr.get(4).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                checkEndMonney();
                groupLable.clear();

                for (int i = 0;i<overlaymonneyArr.size;i++){
                    if(overlaymonneyArr.get(i).isVisible()==true){
                        checkMonney(valueCoin.get(i),overlaymonneyArr.get(i).getX(),overlaymonneyArr.get(i).getY(),i);
                        if(checkIsStake==true){
                            animationStakeMonney(isCoins, baucuaArr.get(4).getX(),baucuaArr.get(4).getY(),baucuaArr.get(4).getName());
                            if(i==0){StakeMoney+=10000; MyMoney-=10000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney)); isCoins=1;}
                            else if(i==1){StakeMoney+=20000;MyMoney-=20000; MyMonneyLabel.setText(FortmartPrice(MyMoney)); StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=2;}
                            else if(i==2){StakeMoney+=50000; MyMoney-=50000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=3;}
                            else if(i==3){StakeMoney+=100000; MyMoney-=100000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=4;}
                            else if(i==4){StakeMoney+=200000; MyMoney-=200000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=5;}
                            else if(i==5){StakeMoney+=500000; MyMoney-=500000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=6;}
                        }
                    }
                }

                ;
            }
        });
        baucuaArr.get(5).addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);
                checkEndMonney();
                groupLable.clear();

                for (int i = 0;i<overlaymonneyArr.size;i++){
                    if(overlaymonneyArr.get(i).isVisible()==true){
                        checkMonney(valueCoin.get(i),overlaymonneyArr.get(i).getX(),overlaymonneyArr.get(i).getY(),i);
                        if(checkIsStake==true){
                            animationStakeMonney(isCoins, baucuaArr.get(5).getX(),baucuaArr.get(5).getY(),baucuaArr.get(5).getName());
                            if(i==0){StakeMoney+=10000; MyMoney-=10000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney)); isCoins=1;}
                            else if(i==1){StakeMoney+=20000;MyMoney-=20000; MyMonneyLabel.setText(FortmartPrice(MyMoney)); StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=2;}
                            else if(i==2){StakeMoney+=50000; MyMoney-=50000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=3;}
                            else if(i==3){StakeMoney+=100000; MyMoney-=100000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=4;}
                            else if(i==4){StakeMoney+=200000; MyMoney-=200000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=5;}
                            else if(i==5){StakeMoney+=500000; MyMoney-=500000; MyMonneyLabel.setText(FortmartPrice(MyMoney));StakeMonneyLabel.setText(FortmartPrice(StakeMoney));isCoins=6;}
                        }

                    }
                }

                ;
            }
        });
    }

    void showBauCuaInDisk(){
        GStage.addToLayer(GLayer.ui,BaucuaInDiskGroup);
        BaucuaInDiskGroup.setVisible(true);
        item1 = (int)(Math.random()*6+1);
        item2 = (int)(Math.random()*6+1);
        item3 = (int)(Math.random()*6+1);
        xilau1 = GUI.createImage(gameplayAtlas,""+item1);
        xilau1.setWidth(xilau1.getWidth()*0.9f);
        xilau1.setHeight(xilau1.getHeight()*0.9f);
        xilau1.setOrigin(Align.center);
        xilau1.setPosition(200,370);
        xilau2 = GUI.createImage(gameplayAtlas,""+item2);
        xilau2.setWidth(xilau2.getWidth()*0.9f);
        xilau2.setHeight(xilau2.getHeight()*0.9f);
        xilau2.setOrigin(Align.center);
        xilau2.setPosition(285,320);
        xilau3 = GUI.createImage(gameplayAtlas,""+item3);
        xilau3.setWidth(xilau3.getWidth()*0.9f);
        xilau3.setHeight(xilau3.getHeight()*0.9f);
        xilau3.setOrigin(Align.center);
        xilau3.setPosition(370,360);

        BaucuaInDiskGroup.addActor(xilau2);
        BaucuaInDiskGroup.addActor(xilau1);
        BaucuaInDiskGroup.addActor(xilau3);

    }


    void showMonney(){
            GStage.addToLayer(GLayer.ui, monneyGroup);
            for(int i=1;i<7;i++){
                Image monney = GUI.createImage(gameplayAtlas,i+"Vi");
                monney.setOrigin(Align.center);
                monney.setPosition(GMain.screenWidth/2-200+((monney.getWidth()+20)*i),GMain.screenHeight-monney.getHeight()/2-20,Align.center);
                monneyGroup.addActor(monney);
                monneyInBottomArr.add(monney);
                Image OverLayMonney = GUI.createImage(gameplayAtlas,"overLayMonney");
                OverLayMonney.setOrigin(Align.center);
                OverLayMonney.setPosition(GMain.screenWidth/2-200+((monney.getWidth()+20)*i),GMain.screenHeight-monney.getHeight()/2-23,Align.center);
                monneyGroup.addActor(OverLayMonney);
                overlaymonneyArr.add(OverLayMonney);
                OverLayMonney.setVisible(false);
                eventMonney(monney,OverLayMonney);

            }

    }

    //////////////////// envent monney ///////////////
    void eventMonney(Image btn ,Image overlay){
        btn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.select);

                for (int i = 0;i<overlaymonneyArr.size;i++){
                    overlaymonneyArr.get(i).setVisible(false);
                    overlaymonneyArr.get(i).clearListeners();
                }

                overlay.setVisible(true);

                for (int i = 0;i<overlaymonneyArr.size;i++){
                    if(overlaymonneyArr.get(i).isVisible()==true){

                        checkMonney(valueCoin.get(i),overlaymonneyArr.get(i).getX(),overlaymonneyArr.get(i).getY(),i);


                    }
                }
            }
        });

    }

    //////////////////// event Bowl ////////////////
    void animationRing2(){

        if(checkRing==false){
            bowl.addAction(Actions.sequence(
                    Actions.moveBy(280,90,0.5f),
                    GSimpleAction.simpleAction((d,a)->{
                        BaucuaInDiskGroup.setVisible(false);
                        setOverlaydefault();
//                        setDefautMonneyStake();
                        animationRing(bowl,disk);
                        return true;
                    })

            ));
        }else {
            if(clicktemp==true){
                animationRingDone(bowl,disk);
                clicktemp=false;
            }

        }


    }

    void eventBowl(Image bowl,Image disk){
        bowl.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                clicktemp=true;
                bowl.setTouchable(Touchable.disabled);
                disk.setTouchable(Touchable.disabled);
                frameClearTempGroup.setVisible(false);
                animationRing2();
                groupLable.clear();

            }
        });
        disk.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                clicktemp=true;
                disk.setTouchable(Touchable.disabled);
                bowl.setTouchable(Touchable.disabled);
                frameClearTempGroup.setVisible(false);
                animationRing2();
                groupLable.clear();
            }
        });
    }
    void animationRing(Image bowl,Image disk){
        Gdx.input.vibrate(1000);
        for (int i=0;i<11;i++){
            Tweens.setTimeout(gameGroup,0.07f*i,()->{
                SoundEffect.Play(SoundEffect.Xocdia);
            });
        }
        bowl.addAction(Actions.sequence(
                Actions.moveBy(30,0,durationsXocDia),
                Actions.moveBy(-30,0,durationsXocDia),
                Actions.moveBy(30,0,durationsXocDia),
                Actions.moveBy(-30,0,durationsXocDia),
                Actions.moveBy(30,0,durationsXocDia),
                Actions.moveBy(-30,0,durationsXocDia),
                Actions.moveBy(30,0,durationsXocDia),
                Actions.moveBy(-30,0,durationsXocDia),
                Actions.moveBy(30,0,durationsXocDia),
                Actions.moveBy(-30,0,durationsXocDia)

        ));
        disk.addAction(Actions.sequence(
                Actions.moveBy(29,0,durationsXocDia),
                Actions.moveBy(-29,0,durationsXocDia),
                Actions.moveBy(29,0,durationsXocDia),
                Actions.moveBy(-29,0,durationsXocDia),
                Actions.moveBy(29,0,durationsXocDia),
                Actions.moveBy(-29,0,durationsXocDia),
                Actions.moveBy(29,0,durationsXocDia),
                Actions.moveBy(-29,0,durationsXocDia),
                Actions.moveBy(29,0,durationsXocDia),
                Actions.moveBy(-29,0,durationsXocDia),
                GSimpleAction.simpleAction((d,a)->{
                    checkRing=true;
                    bowl.setTouchable(Touchable.enabled);
                    disk.setTouchable(Touchable.enabled);
                    BaucuaInDiskGroup.clearChildren();
                    showBauCuaInDisk();
                    return true;
                })

        ));
    }
    void animationRingDone(Image bowl,Image disk){
        setInputDisable();
        bowl.addAction(Actions.sequence(
                Actions.moveBy(-280,-90,0.5f),
                GSimpleAction.simpleAction((d,a)->{
                    bowl.setTouchable(Touchable.enabled);
                    disk.setTouchable(Touchable.enabled);
                    setInputEnable();
                    checkRing=false;
                    checkQuanLity();
                    checkbaucua();
                    checkWin();
                    setLabel();
                    newMonney=MyMoney-OldMonney;
                    setDefautMonneyStake();
                    setQuanlityBauCua();
                    if(newMonney!=0){
                        new CheckWin("ok",gameplayAtlas);
                    }
                    frameClearTempGroup.setVisible(true);
                    countShowAds++;
                    showAds();
                    return true;
                })
        ));

    }
    void checkbaucua(){
        overlayArr.get(item1-1).setVisible(true);
        overlayArr.get(item2-1).setVisible(true);
        overlayArr.get(item3-1).setVisible(true);
    }
    void setOverlaydefault(){
        for (int i=0;i<overlayArr.size;i++){
            overlayArr.get(i).setVisible(false);
        }
    }

    void initFont(){
        fontmonney = GAssetsManager.getBitmapFont("font_white.fnt");
        fontText = GAssetsManager.getBitmapFont("font_O.fnt");

    }
    void animationStakeMonney(int iCoins,float x, float y,String name){
        Image coinsTake = GUI.createImage(gameplayAtlas,isCoins+"Vi");

        coinsTake.setOrigin(Align.bottomLeft);
        coinsTake.setPosition(monneyInBottomArr.get(iCoins-1).getX(),monneyInBottomArr.get(isCoins-1).getY(),Align.bottomLeft);

        if(name=="nai"){
            CoinStakeGroup_Nai.addActor(coinsTake);
            for(int i=0;i<overlaymonneyArr.size;i++){
                if(overlaymonneyArr.get(i).isVisible()==true){
                    cuocNai+=valueCoin.get(i);
                    Gdx.app.log("okok","cuocNai:    "+cuocNai);
                }
            }
        }
        if(name=="bau"){
            CoinStakeGroup_Bau.addActor(coinsTake);
            for(int i=0;i<overlaymonneyArr.size;i++){
                if(overlaymonneyArr.get(i).isVisible()==true){
                    cuocBau+=valueCoin.get(i);
                    Gdx.app.log("okok","cuocBau:    "+cuocBau);
                }
            }
        }
        if(name=="ga"){
            CoinStakeGroup_Ga.addActor(coinsTake);
            for(int i=0;i<overlaymonneyArr.size;i++){
                if(overlaymonneyArr.get(i).isVisible()==true){
                    cuocGa+=valueCoin.get(i);
                    Gdx.app.log("okok","cuocGa:    "+cuocGa);
                }
            }
        }
        if(name=="ca"){
            CoinStakeGroup_Ca.addActor(coinsTake);
            for(int i=0;i<overlaymonneyArr.size;i++){
                if(overlaymonneyArr.get(i).isVisible()==true){
                    cuocCa+=valueCoin.get(i);
                    Gdx.app.log("okok","cuocCa:    "+cuocCa);
                }
            }
        }
        if(name=="cua"){
            CoinStakeGroup_Cua.addActor(coinsTake);
            for(int i=0;i<overlaymonneyArr.size;i++){
                if(overlaymonneyArr.get(i).isVisible()==true){
                    cuocCua+=valueCoin.get(i);
                    Gdx.app.log("okok","cuocCua:    "+cuocCua);
                }
            }
        }
        if(name=="tom"){
            CoinStakeGroup_Tom.addActor(coinsTake);
            for(int i=0;i<overlaymonneyArr.size;i++){
                if(overlaymonneyArr.get(i).isVisible()==true){
                    cuocTom+=valueCoin.get(i);
                    Gdx.app.log("okok","cuocTom:    "+cuocTom);
                }
            }
        }
        coinsTake.addAction(Actions.parallel(
                Actions.moveTo(x+(float) Math.floor(Math.random()*140-10),y+(float) Math.floor(Math.random()*140-10),0.5f),
                Actions.scaleTo(0.6f,0.6f,0.5f)
        ));
    }

    void setNameArrBaucua(){
        baucuaArr.get(0).setName("nai");
        baucuaArr.get(1).setName("bau");
        baucuaArr.get(2).setName("ga");
        baucuaArr.get(3).setName("ca");
        baucuaArr.get(4).setName("cua");
        baucuaArr.get(5).setName("tom");

    }
    void checkWin(){
        for(int i=0 ;i<overlayArr.size;i++){
            if(overlayArr.get(i).isVisible()==true){
                if(i==0){ CoinStakeGroup_Nai.addAction(Actions.moveBy(0,500,0.5f)); MyMoney+=cuocNai;MyMonneyLabel.setText(FortmartPrice(MyMoney));}
                else if(i==1){ CoinStakeGroup_Bau.addAction(Actions.moveBy(0,500,0.5f)); MyMoney+=cuocBau;MyMonneyLabel.setText(FortmartPrice(MyMoney));}
                else if(i==2){ CoinStakeGroup_Ga.addAction(Actions.moveBy(0,500,0.5f)); MyMoney+=cuocGa;MyMonneyLabel.setText(FortmartPrice(MyMoney));}
                else if(i==3){ CoinStakeGroup_Ca.addAction(Actions.moveBy(0,500,0.5f)); MyMoney+=cuocCa;MyMonneyLabel.setText(FortmartPrice(MyMoney));}
                else if(i==4){ CoinStakeGroup_Cua.addAction(Actions.moveBy(0,500,0.5f)); MyMoney+=cuocCua;MyMonneyLabel.setText(FortmartPrice(MyMoney));}
                else if(i==5){ CoinStakeGroup_Tom.addAction(Actions.moveBy(0,500,0.5f)); MyMoney+=cuocTom;MyMonneyLabel.setText(FortmartPrice(MyMoney));}
            }else {
                if(i==0){ CoinStakeGroup_Nai.addAction(Actions.moveBy(-500,-500,0.5f)); }
                else if(i==1){ CoinStakeGroup_Bau.addAction(Actions.moveBy(-500,-500,0.5f)); }
                else if(i==2){ CoinStakeGroup_Ga.addAction(Actions.moveBy(-500,-500,0.5f)); }
                else if(i==3){ CoinStakeGroup_Ca.addAction(Actions.moveBy(-500,-500,0.5f)); }
                else if(i==4){ CoinStakeGroup_Cua.addAction(Actions.moveBy(-500,-500,0.5f)); }
                else if(i==5){ CoinStakeGroup_Tom.addAction(Actions.moveBy(-500,-500,0.5f)); }

            }
        }
        Tweens.setTimeout(gameGroup,0.5f,()->{
            for (int i=0;i<CoinStakeGroup.getChildren().size;i++){
                CoinStakeGroup.getChildren().get(i).clear();
            }
            setPosisionGroup();
        });
        ///////////  save monney//////
        GMain.prefs.putLong("myMonney",MyMoney);
        GMain.prefs.flush();
//        for (int i=0;i<CoinStakeGroup_Nai.getChildren().size;i++){
//            Gdx.app.log("okok","here!!!"+CoinStakeGroup_Nai.getChildren().get(0).getName());
//
//        }
    }
    void setPosisionGroup(){
        CoinStakeGroup_Nai.setPosition(0,0);
        CoinStakeGroup_Bau.setPosition(0,0);
        CoinStakeGroup_Ga.setPosition(0,0);
        CoinStakeGroup_Ca.setPosition(0,0);
        CoinStakeGroup_Cua.setPosition(0,0);
        CoinStakeGroup_Tom.setPosition(0,0);
    }
    //FORTMART PRICE
    private String FortmartPrice(Long Price) {

        DecimalFormat mDecimalFormat = new DecimalFormat("###,###,###,###");
        String mPrice = mDecimalFormat.format(Price);

        return mPrice;
    }
    void checkMonney(long target, float x, float y,int index){
        if(MyMoney-target<0){
            countShowAds2++;
            if(countShowAds2==3){
                countShowAds2=0;
                checkEndMonney();
            }
            Image text = GUI.createImage(gameplayAtlas,"labelEndMonney");
            text.setOrigin(Align.center);
            text.setPosition(x+text.getWidth()/2-20,y,Align.center);
            text.addAction(Actions.parallel(
                    Actions.moveBy(0,-50,0.5f),
                    Actions.alpha(0,0.5f)
            ));
            gameGroup.addActor(text);
            Tweens.setTimeout(gameGroup,0.5f,()->{
                text.clear();
                text.remove();
            });
            for (int i = 0;i<overlaymonneyArr.size;i++){
                overlaymonneyArr.get(i).setVisible(false);
                overlaymonneyArr.get(i).clearListeners();
            }
            checkIsStake=false;

        }else {
                    checkIsStake=true;
                    if(index==0){isCoins=1;}
                    else if(index==1){isCoins=2;}
                    else if(index==2){isCoins=3;}
                    else if(index==3){isCoins=4;}
                    else if(index==4){isCoins=5;}
                    else if(index==5){isCoins=6;}
        }
    }
    void setValueCoins(){
        valueCoin.add(10000);
        valueCoin.add(20000);
        valueCoin.add(50000);
        valueCoin.add(100000);
        valueCoin.add(200000);
        valueCoin.add(500000);


    }
    void setQuanlityBauCua(){
        quanlityNai=0;
        quanlityBau=0;
        quanlityGa=0;
        quanlityCa=0;
        quanlityCua=0;
        quanlityTom=0;
        cuocNai=0;
        cuocBau=0;
        cuocGa=0;
        cuocCa=0;
        cuocCua=0;
        cuocTom=0;
    }

    void checkQuanLity() {
        for (int i=1;i<7;i++){
            if (item1 == i) {
                if(i==1) {
                    quanlityNai += 1;
                }else if(i==2) {
                    quanlityBau += 1;
                }else if(i==3) {
                    quanlityGa += 1;
                }else if(i==4) {
                    quanlityCa += 1;
                }else if(i==5) {
                    quanlityCua += 1;
                }else if(i==6) {
                    quanlityTom += 1;
                }
            }if (item2 == i) {
                if(i==1) {
                    quanlityNai += 1;
                }else if(i==2) {
                    quanlityBau += 1;
                }else if(i==3) {
                    quanlityGa += 1;
                }else if(i==4) {
                    quanlityCa += 1;
                }else if(i==5) {
                    quanlityCua += 1;
                }else if(i==6) {
                    quanlityTom += 1;
                }

            } if (item3 == i) {
                if(i==1) {
                    quanlityNai += 1;
                }else if(i==2) {
                    quanlityBau += 1;
                }else if(i==3) {
                    quanlityGa += 1;
                }else if(i==4) {
                    quanlityCa += 1;
                }else if(i==5) {
                    quanlityCua += 1;
                }else if(i==6) {
                    quanlityTom += 1;
                }

            }

        }
        if(quanlityNai>=1){quanlityNai+=1;}
        if(quanlityBau>=1){quanlityBau+=1;}
        if(quanlityGa>=1){quanlityGa+=1;}
        if(quanlityCa>=1){quanlityCa+=1;}
        if(quanlityCua>=1){quanlityCua+=1;}
        if(quanlityTom>=1){quanlityTom+=1;}

        cuocNai=cuocNai*quanlityNai;
        cuocBau=cuocBau*quanlityBau;
        cuocGa=cuocGa*quanlityGa;
        cuocCa=cuocCa*quanlityCa;
        cuocCua=cuocCua*quanlityCua;
        cuocTom=cuocTom*quanlityTom;

        Gdx.app.log("okok","mymonne: "+MyMoney);
    }
    void setDefautMonneyStake(){
        StakeMoney=0;
        StakeMonneyLabel.setText(0);


    }
    void showBtnSetting(){
        Image setting =GUI.createImage(gameplayAtlas,"btSettingInGame");
        setting.setPosition(GMain.screenWidth-setting.getWidth()/2,setting.getHeight()/2,Align.center);
        gameGroup.addActor(setting);
        setting.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.panelOut);
                setting.setTouchable(Touchable.disabled);
                new Setting(gScreen,gameplayAtlas,setting);
            }
        });

    }
    void showBtnTutorial(){
        Image btnTutorial =GUI.createImage(gameplayAtlas,"btnTutorial");
        btnTutorial.setWidth(btnTutorial.getWidth()*0.8f);
        btnTutorial.setHeight(btnTutorial.getHeight()*0.8f);
        btnTutorial.setOrigin(Align.center);
        btnTutorial.setPosition(GMain.screenWidth/2+310,btnTutorial.getHeight()-15,Align.center);
        gameGroup.addActor(btnTutorial);
        btnTutorial.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.panelOut);
                btnTutorial.setTouchable(Touchable.disabled);
                new Tutorial(gameplayAtlas, btnTutorial);
            }
        });

    }
    void checkEndMonney(){
        if(MyMoney==0&& StakeMoney ==0){
            SoundEffect.Play(SoundEffect.panelOut);
            new VideoAds(gameplayAtlas);

        }
    }
    void showAds(){
        if(countShowAds==5){
            GMain.platform.ShowFullscreen();
            countShowAds=0;
        }
    }
    void setInputDisable(){
        for (int i=0;i<baucuaArr.size;i++){
            baucuaArr.get(i).setTouchable(Touchable.disabled);

        }
    }
    void setInputEnable(){
        for (int i=0;i<baucuaArr.size;i++){
            baucuaArr.get(i).setTouchable(Touchable.enabled);

        }
    }
    void setLabel(){
        float w = frameClearArr.get(0).getWidth()/2;
        float h = frameClearArr.get(0).getHeight()/2;

        if(quanlityNai>1){
            Label nai = new Label((quanlityNai-1)+"",new Label.LabelStyle(fontText,Color.GREEN));
            nai.setPosition(frameClearArr.get(0).getX()+w,frameClearArr.get(0).getY()+h,Align.center);
            groupLable.addActor(nai);
        }
        if(quanlityBau>1){
            Label bau = new Label((quanlityBau-1)+"",new Label.LabelStyle(fontText,Color.GREEN));
            bau.setPosition(frameClearArr.get(1).getX()+w,frameClearArr.get(1).getY()+h,Align.center);
            groupLable.addActor(bau);

        }
        if(quanlityGa>1){
            Label ga = new Label((quanlityGa-1)+"",new Label.LabelStyle(fontText,Color.GREEN));
            ga.setPosition(frameClearArr.get(2).getX()+w,frameClearArr.get(2).getY()+h,Align.center);
            groupLable.addActor(ga);


        }
        if(quanlityCa>1){
            Label ca = new Label((quanlityCa-1)+"",new Label.LabelStyle(fontText,Color.GREEN));
            ca.setPosition(frameClearArr.get(3).getX()+w,frameClearArr.get(3).getY()+h,Align.center);
            groupLable.addActor(ca);


        }
        if(quanlityCua>1){
            Label cua = new Label((quanlityCua-1)+"",new Label.LabelStyle(fontText,Color.GREEN));
            cua.setPosition(frameClearArr.get(4).getX()+w,frameClearArr.get(4).getY()+h,Align.center);
            groupLable.addActor(cua);


        }
        if(quanlityTom>1){
            Label tom = new Label(""+(quanlityTom-1),new Label.LabelStyle(fontText,Color.GREEN));
            tom.setPosition(frameClearArr.get(5).getX()+w,frameClearArr.get(5).getY()+h,Align.center);
            groupLable.addActor(tom);


        }

    }



}
