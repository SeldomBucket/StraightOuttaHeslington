package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the first npc of the game.
 */
public class SallyNPC extends NPC {

    public boolean isRoboDead = false, interactedBefore = false, isFriendDead = false;
    public boolean doneInteraction;
    private String[] messages, roboDead_messages, last_messages;

    public SallyNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
            messages = new String[3];
            messages[0] = "Help! There is a robo duck on the loose!";
            messages[1] = "Please help us by finding him and defeating him!";
            messages[2] = "The last time I saw him was by the Catalyst building.";
            doneInteraction = false;
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        if ((!doneInteraction && !isRoboDead) && !interactedBefore) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }
        else if (!doneInteraction && isRoboDead){
            uiManager.createDialogue(roboDead_messages);
            this.uiManager = uiManager;
        }

        else if (!doneInteraction && !isRoboDead){
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
            if ((!doneInteraction && !isRoboDead) && !interactedBefore) {
                uiManager.addNotification("You gained 40 points.");
                Game.pointsScore += 40;
                interactedBefore = true;
                level.characters.add((new RoboNPC(level, new Vector2(75, 98), this)));

                roboDead_messages = new String[3];
                roboDead_messages[0] = "thanks, he was scaring the widgey out of me!";
                roboDead_messages[1] = "You seem to be good at this, can you take care of his nasty friend too?";
                roboDead_messages[2] = "He'll be over there now probably looking at Robo Ducks body";
                doneInteraction = true;
            }

            else if (!doneInteraction && isRoboDead){
                uiManager.addNotification("You gained 50 points.");
                Game.pointsScore += 50;
                isRoboDead = false;
                level.characters.add((new RoboFriendNPC(level, new Vector2(75, 98), this)));

                last_messages = new String[2];
                last_messages[0] = "Thank you so much for all your help";
                last_messages[1] = "You definitely deserve a first for all that you just did";
                doneInteraction = true;
            }

            else if (!doneInteraction && isFriendDead){
                uiManager.addNotification("You gained 60 points.");
                Game.pointsScore += 60;
                doneInteraction = true;
            }

        }
    }

