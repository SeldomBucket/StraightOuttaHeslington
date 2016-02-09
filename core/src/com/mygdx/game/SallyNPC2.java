package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the first npc of the game.
 */
public class SallyNPC2 extends NPC {


    private boolean doneInteraction;
    private String[] messages;

    public SallyNPC2(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Thank you so much he was really scary";
        messages[1] = "You seem good at this, can you kill another duck thats been a pain?";
        messages[2] = "It's robo ducks friend he's probably over there now looking at robo ducks body";
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
            doneInteraction = false;
            return false;
        }
        return uiManager.updateDialogue(delta);
    }

    @Override
    public void action(GameWorld gameWorld) {
        if (!doneInteraction) {
            uiManager.addNotification("You gained 40 points.");
            Game.pointsScore += 40;
            doneInteraction = true;
        }

        level.characters.add((new RoboFriendNPC(level, new Vector2(75, 98), this)));

    }
}

