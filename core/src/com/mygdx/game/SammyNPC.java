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

    public boolean isGresDead;
    public boolean doneInteraction;
    private String[] messages, back_message;

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
        if (!doneInteraction && !isGresDead) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }

        else if (!doneInteraction && isGresDead) {
            uiManager.createDialogue(back_message);
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
        if (!doneInteraction && !isGresDead) {
            uiManager.addNotification("You gained 50 points.");
            Game.pointsScore += 50;
            doneInteraction = true;
            level.characters.add((new GressinghamNPC(level, new Vector2(100, 113), this)));

            back_message = new String[2];
            back_message[0] = "Sorry! I didn't mean to put you in that much danger";
            back_message[1] = "Anyway thank you, this place is a lot safer now";
        }

        else if (!doneInteraction && isGresDead) {
            uiManager.addNotification("You gained 60 points.");
            Game.pointsScore += 60;
            doneInteraction = true;

        }


    }
}

