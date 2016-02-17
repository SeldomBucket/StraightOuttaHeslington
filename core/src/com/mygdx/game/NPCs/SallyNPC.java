package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Level;
import com.mygdx.game.NPC;
import com.mygdx.game.UI.UIManager;

/**
 * This class represents the a friendly npc sally, which gives the quests corresponding to Robo duck
 * and his friend.
 */
public class SallyNPC extends NPC {

    public boolean isRoboDead = false, interactedBefore = false, isFriendDead = false;
    public boolean doneInteraction;
    private String[] messages, second_messages, last_messages;

    public SallyNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
            messages = new String[3];
            messages[0] = "Help! There is a Robo Duck on the loose!";
            messages[1] = "Please help us by finding and defeating him!";
            messages[2] = "The last time I saw him was by the Catalyst building.";
            doneInteraction = false;
    }


    //This method checks what quests have been done by checking if certain NPCs are alive or dead
    //and then picks the correct messages given the information
    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        if (!doneInteraction && !isRoboDead) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }
        else if ((!doneInteraction && isRoboDead) && !isFriendDead){
            uiManager.createDialogue(second_messages);
            this.uiManager = uiManager;
            Game.party.getMember(2).addSkill(4);
            uiManager.addNotification("Ryan the Duck can now use the Incredibly Close Range Laser Attack!");
        }

        else if ((!doneInteraction && isRoboDead) && isFriendDead){
            uiManager.createDialogue(last_messages);
            this.uiManager = uiManager;
            Game.party.getMember(1).addSkill(6);
            uiManager.addNotification("Ryan the Duck can now use Gamma Ray!");
        }

    }

    @Override
    public boolean updateInteracting(float delta) {
        if (doneInteraction) {
            return false;
        }
        return uiManager.updateDialogue(delta);
    }


    //Again this method checks to see which quests you have and havent done by checking which
    //enemies are dead to give you the correct amount opf points and which enemy (if any) to spawn
    @Override
    public void action(GameWorld gameWorld) {
            if ((!doneInteraction && !isRoboDead) && !interactedBefore) {
                uiManager.addNotification("You gained 40 points.");
                Game.pointsScore += 40;
                interactedBefore = true;
                level.characters.add((new RoboNPC(level, new Vector2(75, 98), this)));

                second_messages = new String[3];
                second_messages[0] = "Thanks, he was really scaring me!";
                second_messages[1] = "You seem to be good at this, can you take care of his nasty friend too?";
                second_messages[2] = "He'll be over there now looking at Robo Duck's body";

                doneInteraction = true;
            }

            else if ((!doneInteraction && isRoboDead) && !isFriendDead){
                uiManager.addNotification("You gained 50 points.");
                Game.pointsScore += 50;
                level.characters.add((new RoboFriendNPC(level, new Vector2(75, 98), this)));
                last_messages = new String[2];
                last_messages[0] = "Thank you so much for all your help!";
                last_messages[1] = "You definitely deserve a first for all that you just did.";
                doneInteraction = true;
            }

            else if ((!doneInteraction && isRoboDead) && isFriendDead){
                uiManager.addNotification("You gained 60 points.");
                Game.pointsScore += 60;
                doneInteraction = true;
            }

        }
    }

