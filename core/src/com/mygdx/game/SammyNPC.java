package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the first npc of the game.
 */
public class SammyNPC extends NPC {

    private boolean doneInteraction;
    private String[] messages;

    public SammyNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Help! A gressingham duck just attacked me";
        messages[1] = "Please sort him out for me, I'll be very grateful!";
        messages[2] = "I think he flew north of my position";
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
            uiManager.addNotification("You gained 50 points.");
            Game.pointsScore += 50;
            doneInteraction = true;
        }
        level.characters.add(new GressinghamNPC(level, new Vector2(100, 113)));
    }
}

