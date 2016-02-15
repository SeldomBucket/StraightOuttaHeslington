package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.Equipable;

/**
 * Created by Thomas on 09/02/2016.
 */

/**
 * The equipment menu is part of the party menu and shows information about a particular piece of equipment currently equipped.
 */
public class UIEquipment extends UIComponent {

    private BitmapFont font;

    private final float LINE_HEIGHT = 19f;
    private Equipable equipable;
    private Color colorMain, colorDesc;

    float paddingX;
    float paddingY;

    public boolean selected;

    public UIEquipment(float x, float y, float width, float height, Equipable equipable) {
        super(x, y, width, height);
        this.equipable = equipable;
        selected = false;
        paddingX = 20;
        paddingY = 20;
        font = Assets.consolas16;
    }

    /**
     * Called once per frame to render the skill information.
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {
        if (selected == true) {
            colorMain = Color.WHITE;
            colorDesc = Color.LIGHT_GRAY;
        }
        else {
            colorMain = Color.LIGHT_GRAY;
            colorDesc = Color.GRAY;
        }
        patch.draw(batch, x, y, width, height + (paddingY * 2));
        /**
         * switch case to determine what type of equipment it is and display correct information based on that
         * NONE refers to an empty equipment slot
         */
        switch (equipable.getType()) {
            case HEAD:
                renderText (batch, "HEAD: " + equipable.getName(), x, y + (LINE_HEIGHT / 2), colorMain);
                renderModifiers(batch);
                break;
            case CHEST:
                renderText (batch, "CHEST: " + equipable.getName(), x, y + (LINE_HEIGHT / 2), colorMain);
                renderModifiers(batch);
                break;
            case FEET:
                renderText (batch, "FEET: " + equipable.getName(), x, y + (LINE_HEIGHT / 2), colorMain);
                renderModifiers(batch);
                break;
            case WEAPON:
                renderText (batch, "WEAPON: " + equipable.getName(), x, y + (LINE_HEIGHT / 2), colorMain);
                renderModifiers(batch);
                break;
            case ACCESSORY:
                renderText (batch, "ACCESSORY: " + equipable.getName(), x, y + (LINE_HEIGHT / 2), colorMain);
                renderModifiers(batch);
                break;
            default:
                renderText (batch, "EMPTY SLOT", x, y + (LINE_HEIGHT / 2), colorMain);
                break;
        }
    }

    /**
     * Helper function for render that actually does the rendering.
     * @param batch the spritebatch to use.
     * @param message The string to add.
     * @param x The x location.
     * @param y The y location.
     * @param color The colour to render the text as.
     */
    private void renderText(SpriteBatch batch, String message, float x, float y, Color color) {
        GlyphLayout layout = new GlyphLayout(font, message,
                Color.BLACK, width - paddingX * 2, Align.left, true);

        font.draw(batch, layout, x + paddingX, y + height + paddingY - 2);
        layout.setText(font, message,
                color, width - paddingX * 2, Align.left, true);
        font.draw(batch, layout, x + paddingX, y + height + paddingY);
    }

    /**
     * Helper function for render using renderText to display an equipment's stats and description
     * @param batch
     */
    private void renderModifiers(SpriteBatch batch) {
        int[] modifiers = equipable.getModifiers();
        renderText (batch, "SPD:" + modifiers[0], x, y - (LINE_HEIGHT / 2), colorMain);
        renderText (batch, "STR:" + modifiers[1], x + 80, y - (LINE_HEIGHT / 2), colorMain);
        renderText (batch, "DEX:" + modifiers[2], x + 160, y - (LINE_HEIGHT / 2), colorMain);
        renderText (batch, "INT:" + modifiers[3], x + 240, y - (LINE_HEIGHT / 2), colorMain);
        renderText (batch, "ARM:" + modifiers[4], x + 320, y - (LINE_HEIGHT / 2), colorMain);
        renderText (batch, equipable.getDescription(), x, y - (LINE_HEIGHT * 3 / 2), colorDesc);
    }

}
