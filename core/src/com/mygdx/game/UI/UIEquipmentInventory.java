package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.Equipable;
import com.mygdx.game.Game;

import java.util.List;

/**
 * This class handles and renders a list of non-equipped equipables in the player's inventory
 */
public class UIEquipmentInventory extends UIComponent {

    private BitmapFont font;

    private final float LINE_HEIGHT = 19f;
    private List<Integer> inventory;
    public int selected;

    float paddingX;
    float paddingY;

    public UIEquipmentInventory(float x, float y, float width, float height, List<Integer> inventory) {
        super(x, y, width, height);
        this.inventory = inventory;
        paddingX = 20;
        paddingY = 20;
        selected = 0;
        font = Assets.consolas16;
    }

    /**
     * Called once per frame to render the skill information.
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {

        patch.draw(batch, x, y, width, height + (paddingY * 2));
        for (int i=0;i<inventory.size();i++) {
            if (i == selected) {
                    renderText(batch, Game.items.getEquipable(inventory.get(i)).getName(), x,
                            y + (LINE_HEIGHT / 2) - (LINE_HEIGHT * i), Color.WHITE);
            }
            else {
                    renderText(batch, Game.items.getEquipable(inventory.get(i)).getName(), x,
                            y + (LINE_HEIGHT / 2) - (LINE_HEIGHT * i), Color.LIGHT_GRAY);
            }
        }

        //Handle highlighting of "Remove Equipment"
        if (selected == inventory.size()) {
            renderText(batch, "Remove Equipment", x,
                    y + (LINE_HEIGHT / 2) - (LINE_HEIGHT * inventory.size()), Color.WHITE);
        }
        else {
            renderText(batch, "Remove Equipment", x,
                    y + (LINE_HEIGHT / 2) - (LINE_HEIGHT * inventory.size()), Color.LIGHT_GRAY);
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
}

