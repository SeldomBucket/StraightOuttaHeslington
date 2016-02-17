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
 * NEW CLASS
 */

public class UIShopMenu extends UIComponent {

    private boolean show;

    private String[] itemNames;
    private int[] itemPrices;
    private int[] levelRequirements;

    private int itemSelected;
    private int highestLevel;

    public UIShopMenu(float x, float y, float width, float height, String[] itemNames, int[] itemPrices, int[] levelRequirements) {
        super(x, y, width, height);
        show = false;
        itemSelected = 0;
        this.itemNames = itemNames;
        this.itemPrices = itemPrices;
        this.levelRequirements = levelRequirements;
    }

    /**
     * Called once per frame to render item information. Uses highestLevel to decide if the item should be displayed or not
     * @param batch The batch used to render the information
     * @param patch The patch used as the background for the information
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {
        if (show) {
            new UIMessageBox("\nWelcome to the shop!", Assets.consolas22, Color.WHITE, Align.center,x,y+((height/6)*5),width, height/10).render(batch,patch);
            for (int i = 0;i<5;i++){
                if (highestLevel>=levelRequirements[i]){
                    if (i == itemSelected){
                        new UIMessageBox(itemNames[i]+"\nCost: "+itemPrices[i], Assets.consolas22, Color.GOLD, Align.center, x, y+((height/6)*i), width/2, height/10).render(batch, patch);
                    }else{
                        new UIMessageBox(itemNames[i]+"\nCost: "+itemPrices[i], Assets.consolas22, Color.WHITE, Align.center, x, y+((height/6)*i), width/2, height/10).render(batch, patch);
                    }
                }else{
                    if (i == itemSelected){
                        new UIMessageBox("\nNot High Enough Level", Assets.consolas22, Color.GOLD, Align.center, x, y+((height/6)*i), width/2, height/10).render(batch, patch);
                    }else{
                        new UIMessageBox("\nNot High Enough Level", Assets.consolas22, Color.WHITE, Align.center, x, y+((height/6)*i), width/2, height/10).render(batch, patch);
                    }
                }
            }
            for (int i = 0;i<5;i++){
                if (highestLevel>=levelRequirements[i+5]) {
                    if (i + 5 == itemSelected) {
                        new UIMessageBox(itemNames[i + 5]+"\nCost: "+itemPrices[i+5], Assets.consolas22, Color.GOLD, Align.center, x + (width / 2), y + ((height / 6) * i), width / 2, height / 10).render(batch, patch);
                    }else {
                        new UIMessageBox(itemNames[i + 5]+"\nCost: "+itemPrices[i+5], Assets.consolas22, Color.WHITE, Align.center, x + (width / 2), y + ((height / 6) * i), width / 2, height / 10).render(batch, patch);
                    }
                }else{
                    if (i + 5 == itemSelected) {
                        new UIMessageBox("\nNot High Enough Level", Assets.consolas22, Color.GOLD, Align.center, x+(width/2), y+((height/6)*i), width/2, height/10).render(batch, patch);
                    }else {
                        new UIMessageBox("\nNot High Enough Level", Assets.consolas22, Color.WHITE, Align.center, x+(width/2), y+((height/6)*i), width/2, height/10).render(batch, patch);
                    }
                }
            }

        }
    }

    /**
     * Makes the UI component visible on screen.
     * @param highestLevel The level of the character in the party with the highest level
     */
    public void show(int highestLevel) {
        itemSelected = 0;
        this.highestLevel = highestLevel;
        show = true;
    }

    /**
     * Updates the shop menu
     * @return Returns -1 if the menu is not to be closed, 974 if no item was selected, or the index of the item selected if one was selected
     */
    public int update() {
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