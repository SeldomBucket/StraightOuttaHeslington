package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Level;
import com.mygdx.game.NPC;
import com.mygdx.game.UI.UIManager;

/**
 * This class represents the first npc of the game.
 */
public class SammyNPC extends NPC {

    public boolean isGresDead = false, isTealDead = false;
    public boolean doneInteraction;
    private String[] messages, second_message, last_message;

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

        else if ((!doneInteraction && isGresDead) && !isTealDead) {
            uiManager.createDialogue(second_message);
            this.uiManager = uiManager;
        }

        else if ((!doneInteraction && isGresDead) && isTealDead) {
            uiManager.createDialogue(last_message);
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

            second_message = new String[4];
            second_message[0] = "Sorry! I didn't mean to put you in that much danger";
            second_message[1] = "You handled it well though, can you make this place even safer?";
            second_message[2] = "The teal duck is acting like he owns this place and is being big headed, get him to attack you by killing another nasty duck";
            second_message[3] = "Attack that duck to the north east then the Teal duck will come for your blood";

        }

        else if ((!doneInteraction && isGresDead) && !isTealDead) {
            uiManager.addNotification("You gained 60 points.");
            Game.pointsScore += 60;
            level.characters.add((new RandomDuckNPC(level, new Vector2(115, 102), this)));

            last_message = new String [2];
            last_message[0] = "Well done you really are good at this";
            last_message[1] = "I hope I can help you sometime too";

            doneInteraction = true;

        }

        else if ((!doneInteraction && isGresDead) && isTealDead) {
            uiManager.addNotification("You gained 100 points.");
            Game.pointsScore += 100;

            doneInteraction = true;

        }


    }
}

