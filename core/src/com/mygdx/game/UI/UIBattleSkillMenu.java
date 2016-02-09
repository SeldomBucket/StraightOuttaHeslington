package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Creates and manages rendering of the skill menu on the battlescreen.
 * The selected skill is highlighted and indicated with a '{@literal >}'
 * The UI is rendered as a list of the skills availabel to the agent.
 * Elements are rendered top to bottom in the order they are added to the class.
 *
 * The UIBattleSkillMenu class is a subclass of UIComponent.
 */
public class UIBattleSkillMenu extends UIComponent {
    public List<Skill> listSkills;

    private int selected = 0;
    float paddingX,paddingY;

    public UIBattleSkillMenu(boolean isSkillMenu, float x, float y, float width, float height, float paddingX, float paddingY) {
        super(x, y, width, height);

        this.paddingX = paddingX;
        this.paddingY = paddingY;

        listSkills = new ArrayList<Skill>();
    }

    public int getSelected() {
        return selected;
    }

    /**
     * Add a string to the UI.
     * @param skill the string to add.
     */
    public void addListItem(Skill skill) {
        listSkills.add(skill);
    }

    public void selectItem(int selected){
        this.selected=selected;
    }

    /**
     * Manages rendering the menu.
     * @param batch the spritebatch to use.
     * @param patch the ninepatch to use.
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {
        float leftX = x+20;
        float thisY = y;

        patch.draw(batch, x, y, width, height + (paddingY * 2));

        /**
         * CHANGE- altered skill menu so that it operates as a list rather than a 2d 'array' of skills
         */
        for(int i=0; i<listSkills.size();i++) {
            if(i==selected) {
                renderText(batch, ">", leftX - 20, thisY, Color.WHITE, Assets.consolas22);
                renderText(batch, listSkills.get(i).getName(), leftX, thisY, Color.WHITE, Assets.consolas22);
            }
            else {
                renderText(batch, listSkills.get(i).getName(), leftX, thisY, Color.LIGHT_GRAY, Assets.consolas22);
            }
            thisY-=25;
        }
        new UIBattleSkillInfo(x + width, y, Gdx.graphics.getWidth() - (x + width), height - 20,
                Game.skills.getSkill(listSkills.get(selected).getID())).render(batch, patch);
    }

    /**
     * Helper function to actually render the text.
     * @param batch the spritebatch to use.
     * @param message The string to add.
     * @param x The x location.
     * @param y The y location.
     * @param color The colour to render the text as.
     * @param font The font to use.
     */
    private void renderText(SpriteBatch batch, String message, float x, float y, Color color, BitmapFont font) {
        GlyphLayout layout = new GlyphLayout(font, message,
                Color.BLACK, width - paddingX * 2, Align.left, false);

        font.draw(batch, layout, x + paddingX, y + height + paddingY - 2);
        layout.setText(font, message,
                color, width - paddingX * 2, Align.left, false);
        font.draw(batch, layout, x + paddingX, y + height + paddingY);
    }
}
