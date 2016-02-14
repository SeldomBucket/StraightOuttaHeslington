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

    public boolean isMalardDead = false, isSomeDucksDead = false;
    public boolean doneInteraction;
    private String[] messages, second_messages, last_messages;

    public JulieNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Help! I just got pecked by some malards!";
        messages[1] = "Please teach them a lesson!";
        messages[2] = "The when I called for help I think they flew south east towrads the lake";
        doneInteraction = false;
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        if (!doneInteraction && !isMalardDead) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }

        else if ((!doneInteraction && isMalardDead) && !isSomeDucksDead) {
            uiManager.createDialogue(second_messages);
            this.uiManager = uiManager;
        }

        else if (!doneInteraction && isMalardDead) {
            uiManager.createDialogue(last_messages);
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
        if (!doneInteraction && !isMalardDead) {
            uiManager.addNotification("You gained 60 points.");
            Game.pointsScore += 60;
            level.characters.add(new MalardsNPC(level, new Vector2(108, 71), this));

            second_messages = new String [3];
            second_messages[0] = "Thank you, can you help me some more?";
            second_messages[1] = "I can see some ducks over to my left planning to attack Sally";
            second_messages[2] = "Please get rid of them before they hurt her!";

            doneInteraction = true;
        }

        else if((!doneInteraction && isMalardDead) && !isSomeDucksDead){
            uiManager.addNotification("You gained 70 points.");
            Game.pointsScore += 70;

            last_messages = new String [2];
            last_messages[0] = "Thanks";
            last_messages [1] = "Youre making this place a lot safer";

            doneInteraction = true;

        }

        else if((!doneInteraction && isMalardDead) && isSomeDucksDead) {
            uiManager.addNotification("You gained 100 points.");
            Game.pointsScore += 100;
            doneInteraction = true;
        }
    }
}

