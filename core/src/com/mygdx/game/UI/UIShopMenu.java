package com.mygdx.game.UI;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.InputHandler;
import com.mygdx.game.ItemManager;

/**
 * This class handles and renders the shop menu
 */

public class UIShopMenu extends UIComponent {

    private boolean show;

    private boolean[] items;
    private String[] itemNames;

    private int itemSelected;

    public UIShopMenu(float x, float y, float width, float height, boolean[] items, String[] itemNames) {
        super(x, y, width, height);
        show = false;
        itemSelected = 0;
        this.items = items;
        this.itemNames = itemNames;
    }

    /**
     * Called once per frame to render item information.
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {
        if (show) {
            for (int i = 0;i<5;i++){
                if (items[i]){
                    if (i == itemSelected){
                        new UIMessageBox("\n\n"+itemNames[i], Assets.consolas22, Color.GOLD, Align.center, x, y+((height/5)*i), width/2, height/6).render(batch, patch);
                    }else{
                        new UIMessageBox("\n\n"+itemNames[i], Assets.consolas22, Color.WHITE, Align.center, x, y+((height/5)*i), width/2, height/6).render(batch, patch);
                    }
                }else{
                    if (i == itemSelected){
                        new UIMessageBox("\n\nNot High Enough Level", Assets.consolas22, Color.GOLD, Align.center, x, y+((height/5)*i), width/2, height/6).render(batch, patch);
                    }else{
                        new UIMessageBox("\n\nNot High Enough Level", Assets.consolas22, Color.WHITE, Align.center, x, y+((height/5)*i), width/2, height/6).render(batch, patch);
                    }
                }
            }
            for (int i = 0;i<5;i++){
                if (items[i+5]) {
                    if (i + 5 == itemSelected) {
                        new UIMessageBox("\n\n"+itemNames[i + 4], Assets.consolas22, Color.GOLD, Align.center, x + (width / 2), y + ((height / 5) * i), width / 2, height / 6).render(batch, patch);
                    }else {
                        new UIMessageBox("\n\n"+itemNames[i + 4], Assets.consolas22, Color.WHITE, Align.center, x + (width / 2), y + ((height / 5) * i), width / 2, height / 6).render(batch, patch);
                    }
                }else{
                    if (i + 5 == itemSelected) {
                        new UIMessageBox("\n\nNot High Enough Level", Assets.consolas22, Color.GOLD, Align.center, x+(width/2), y+((height/5)*i), width/2, height/6).render(batch, patch);
                    }else {
                        new UIMessageBox("\n\nNot High Enough Level", Assets.consolas22, Color.WHITE, Align.center, x+(width/2), y+((height/5)*i), width/2, height/6).render(batch, patch);
                    }
                }
            }

        }
    }

    /**
     * Makes the UI component visible on screen.
     */
    public void show(int item) {
        itemSelected = item;
        show = true;
    }

    public int update(float delta) {
        if (InputHandler.isActJustPressed()) {
            show = false;
            return itemSelected;
        } else if (InputHandler.isEscJustPressed()) {
            show = false;
            return 974;
        } else {
            optionUpdate();
            return -1;
        }

    }

    private void optionUpdate() {
        if (InputHandler.isUpJustPressed()) {
            if (itemSelected!=4){
                itemSelected++;
            }
        } else if (InputHandler.isDownJustPressed()) {
            if (itemSelected!=5){
                itemSelected--;
            }
        } else if (InputHandler.isLeftJustPressed()) {
            if (itemSelected>4){
                itemSelected-=5;
            }
        } else if (InputHandler.isRightJustPressed()) {
            if (itemSelected<5){
                itemSelected+=5;
            }
        }
        if (itemSelected < 0) {
            itemSelected = 0;
        }
        if (itemSelected > 9) {
            itemSelected = 9;
        }
        if (itemSelected < 0) {
            itemSelected = 0;
        }
    }

}