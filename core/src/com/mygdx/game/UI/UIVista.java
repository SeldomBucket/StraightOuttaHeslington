package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.InputHandler;

/**
 * Created by SeldomBucket on 02-Feb-16.
 */
public class UIVista extends UIComponent {
    Texture image;

    UIVista(Texture image){
        super(0, 0, Gdx.graphics.getWidth()*0.75f, Gdx.graphics.getHeight()*0.75f);
        this.image = image;
    }

    @Override
    public void render(SpriteBatch batch, NinePatch patch) {
        batch.draw(image, 0, 0);
    }

    public boolean update() {
        if (InputHandler.isActJustPressed()) {
            return false;
        }else {
            return true;
        }
    }
}
