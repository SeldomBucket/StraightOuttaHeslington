package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the first npc of the game.
 */
public class JulieNPC extends NPC {

    private boolean doneInteraction;
    private String[] messages;

    public JulieNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Help! I just got pecked by some malards!";
        messages[1] = "Please teach him a lesson!";
        messages[2] = "The when I called for help I think they flew south east towrads the lake";
        doneInteraction = false;
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        if (!doneInteraction) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }
    }

    @Override
    public boolean updateInteracting(float delta) {
        if (doneInteraction) {
            return false;
        }
        return uiManager.updateDialogue(delta);
    }

    @Override
    public void action(GameWorld gameWorld) {
        if (!doneInteraction) {
            uiManager.addNotification("You gained 60 points.");
            Game.pointsScore += 60;
            doneInteraction = true;
        }
        level.characters.add(new MalardsNPC(level, new Vector2(108, 71)));
    }
}

