package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.Consumable;
import com.mygdx.game.Skill;

/**
 * The skill menu is part of the party menu and show information about a particular skill.
 */
public class UIBattleItemInfo extends UIComponent {

    private BitmapFont font;

    private final float LINE_HEIGHT = 19f;
    private Consumable consumable;

    float paddingX;
    float paddingY;

    /**
     * CHANGE- added height parameter for instantiation
     * @param x
     * @param y
     * @param width
     * @param height
     * @param consumable
     */

    public UIBattleItemInfo(float x, float y, float width, float height, Consumable consumable) {
        super(x, y, width, height);
        this.consumable = consumable;
        paddingX = 20;
        paddingY = 20;
        font = Assets.consolas16;
    }

    /**
     * Called once per frame to render the skill information.
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {
        patch.draw(batch, x, y, width, height + (paddingY * 2));
        renderText(batch, consumable.getName(), x, y + (LINE_HEIGHT / 2), Color.WHITE);
        renderText(batch, consumable.getDescription(), x, y - LINE_HEIGHT, Color.LIGHT_GRAY);
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
        GlyphLayout layout = new GlyphLayout(font, message, Color.BLACK, width - paddingX * 2, Align.left, true);
        font.draw(batch, layout, x + paddingX, y + height + paddingY - 2);
        layout.setText(font, message, color, width - paddingX * 2, Align.left, true);
        font.draw(batch, layout, x + paddingX, y + height + paddingY);
    }
}
