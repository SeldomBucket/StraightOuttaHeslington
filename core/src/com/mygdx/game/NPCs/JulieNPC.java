package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Level;
import com.mygdx.game.NPC;
import com.mygdx.game.UI.UIManager;

/**
 * This class represents another friendly NPC
 * NEW NPC
 */
public class JulieNPC extends NPC {

    public boolean isMallardDead = false, isSomeDucksDead = false;
    public boolean doneInteraction;
    private String[] messages, second_messages, last_messages;

    public JulieNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Help! I just got pecked by some mallards!";
        messages[1] = "Please teach them a lesson!";
        messages[2] = "The when I called for help I think they flew south east towards the lake.";
        doneInteraction = false;
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        if (!doneInteraction && !isMallardDead) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }

        else if ((!doneInteraction && isMallardDead) && !isSomeDucksDead) {
            uiManager.createDialogue(second_messages);
            uiManager.addNotification("Adrian the Duck can now use Toxic Goop!");
            Game.party.getMember(2).addSkill(5);
            this.uiManager = uiManager;
        }

        else if (!doneInteraction && isMallardDead) {
            uiManager.createDialogue(last_messages);
            Game.party.getMember(3).addSkill(9);
            uiManager.addNotification("Ahn the Duck can now use Super Fist Punch!");
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
        if (!doneInteraction && !isMallardDead) {
            uiManager.addNotification("You gained 60 points.");
            Game.pointsScore += 60;
            level.characters.add(new MallardsNPC(level, new Vector2(108, 71), this));

            second_messages = new String [3];
            second_messages[0] = "Thank you, can you help me some more?";
            second_messages[1] = "I can see some ducks over to my left planning to attack Sally";
            second_messages[2] = "Please get rid of them before they hurt her!";

            doneInteraction = true;
        }

        else if((!doneInteraction && isMallardDead) && !isSomeDucksDead){
            uiManager.addNotification("You gained 70 points.");
            Game.pointsScore += 70;
            level.characters.add(new SomeDucksNPC(level, new Vector2(85, 93), this));

            last_messages = new String [2];
            last_messages[0] = "Thanks";
            last_messages [1] = "You're making this place a lot safer";
            doneInteraction = true;

        }

        else if((!doneInteraction && isMallardDead) && isSomeDucksDead) {
            uiManager.addNotification("You gained 100 points.");
            Game.pointsScore += 100;
            doneInteraction = true;
        }
    }
}

