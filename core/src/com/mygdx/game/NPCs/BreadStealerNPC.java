package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the first objective given by DannyNPC.
 *
 */
public class BreadStealerNPC extends NPC {

    public DannyNPC dannyNPC;
    private String[] messages;

    public BreadStealerNPC(Level level, Vector2 currentTile, DannyNPC dannyNPC) {
        super(level, currentTile);
        this.dannyNPC = dannyNPC;
        messages = new String[2];
        messages[0] = "Oh who cares? The bread is mine now.";
        messages[1] = "The Bread Stealer has challenged you to a battle.";
    }

    @Override
    public void initializeInteraction(float delta, UIManager uiManager) {
        uiManager.createDialogue(messages);
        this.uiManager = uiManager;
    }

    @Override
    public boolean updateInteracting(float delta) {
        return uiManager.updateDialogue(delta);
    }

    @Override
    public void action(GameWorld gameWorld) {
        Assets.sfx_battleStart.play(Game.masterVolume);
        uiManager.addNotification("Bread Stealer has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();


        Agent enemyDuck = new Agent("Bread stealer", Agent.AgentType.ENEMY,new Statistics(1,100,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),7);


//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(10);
        enemyDuck.addSkill(5);
        enemyDuck.addSkill(6);


        params.addEnemy(enemyDuck);

        //Changes the booleans in DannyNPC to update which NPC is dead and that
        //you can interact with DannyNPC again
        gameWorld.setBattle(params);
        level.characters.remove(this);
        dannyNPC.doneInteraction = false;
        dannyNPC.isBreadStealerDead = true;

    }
}
