package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.Game;
import com.mygdx.game.GameWorld;
import com.mygdx.game.Level;
import com.mygdx.game.NPC;
import com.mygdx.game.UI.UIManager;

/**
 * This class another friendly NPC in the game.
 */
public class DannyNPC extends NPC {

    public boolean isBreadStealerDead = false, interactedBefore = false, isMisunderstoodDead = false;
    public boolean doneInteraction;
    private String[] messages, second_messages, last_messages;

    public DannyNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Help! Someone stole my bread!";
        messages[1] = "Please help me by finding and defeating him!";
        messages[2] = "Look, he's just over there to the right.";
        doneInteraction = false;
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        if ((!doneInteraction && !isBreadStealerDead) && !interactedBefore) {
            uiManager.createDialogue(messages);
            this.uiManager = uiManager;
        }
        else if ((!doneInteraction && isBreadStealerDead) && !isMisunderstoodDead){
            uiManager.createDialogue(second_messages);
            Game.party.getMember(2).addSkill(12);
            uiManager.addNotification("Adrian the Duck can now use Heal Wounds!");
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
        if ((!doneInteraction && !isBreadStealerDead) && !interactedBefore) {
            uiManager.addNotification("You gained 40 points.");
            Game.pointsScore += 40;
            level.characters.add((new BreadStealerNPC(level, new Vector2(130, 93), this)));
            second_messages = new String[3];
            second_messages[0] = "Thanks for getting my bread back!";
            second_messages[1] = "Oh no! A duck thought you stole his bread, he's coming for you.";
            second_messages[2] = "I'm so sorry.";
            doneInteraction = true;
        }

        else if (!doneInteraction && isBreadStealerDead){
            uiManager.addNotification("You gained 50 points.");
            interactedBefore = true;
            Game.pointsScore += 50;
            level.characters.add((new MisunderstoodNPC(level, new Vector2(120, 91), this)));
            Game.party.getMember(3).addSkill(1);
            gameWorld.uiManager.addNotification("Ahn the Duck can now use Cure Light Wounds!");
            doneInteraction = true;
        }

    }
}


