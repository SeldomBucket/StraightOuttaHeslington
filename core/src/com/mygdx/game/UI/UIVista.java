package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by SeldomBucket on 02-Feb-16.
 */
public class UIVista extends UIComponent {
    TextureRegion image;
    UIVista(TextureRegion image){
        super(0, 0, Gdx.graphics.getWidth()*0.75f, Gdx.graphics.getHeight()*0.75f);
        this.image = image;
    }
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {

    }

}
