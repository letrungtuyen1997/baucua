package com.effect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class effectWin extends Actor{
    private static FileHandle win = Gdx.files.internal("particleWin/particleWin");
    private static FileHandle lose = Gdx.files.internal("particleWin/Lose");
    private static FileHandle Flower = Gdx.files.internal("particleFlower/flower");

    public ParticleEffect effect;
    private Actor parent = this.parent;
    private Group stage;

    public effectWin(int id, float f, float f2, Group group) {
        this.stage = group;
        this.effect = new ParticleEffect();
        setX(f);
        setY(f2);
            if(id==1){
                this.effect.load(win, Gdx.files.internal("particleWin"));
                this.effect.scaleEffect(2.0f);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
            }else  if(id==2){
                this.effect.load(lose, Gdx.files.internal("particleWin"));
                this.effect.scaleEffect(1.5f);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
            }else  if(id==3){
                this.effect.load(Flower, Gdx.files.internal("particleFlower"));
                this.effect.scaleEffect(0.8f);
                for (int i = 0; i < this.effect.getEmitters().size; i++) {
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).flipY();
                    ((ParticleEmitter) this.effect.getEmitters().get(i)).setFlip(true,false);
                }
            }

        this.effect.setPosition(f, f2);


    }

    public void act(float f) {
        super.act(f);
        this.effect.setPosition(getX(), getY());
        this.effect.update(f);
    }

    public void draw(Batch batch, float f) {
        super.draw(batch, f);
        if (!this.effect.isComplete()) {
            this.effect.draw(batch);
            return;
        }
        this.effect.dispose();
        this.stage.clear();
    }

    public void start() {
        this.effect.start();
    }
}
