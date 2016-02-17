package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the second objective given by SammyNPC.
 * NEW NPC
 */
public class SomeDucksNPC extends NPC {

    public JulieNPC julieNPC;
    private String[] messages;

    public SomeDucksNPC(Level level, Vector2 currentTile, JulieNPC julieNPC) {
        super(level, currentTile);
        this.julieNPC = julieNPC;
        messages = new String[2];
        messages[0] = "Seriously?! You're trying to help Sally? We'll teach you a lesson on how to mind your own business.";
        messages[1] = "Some ducks has challenged you to a battle.";
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
        uiManager.addNotification("Some Ducks have been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();

        Agent enemyDuck1 = new Agent("Some Ducks", Agent.AgentType.ENEMY,new Statistics(9,70,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),12);
        Agent enemyDuck2 = new Agent("Some Ducks", Agent.AgentType.ENEMY,new Statistics(9,70,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),12);

//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck1.addSkill(8);
        enemyDuck1.addSkill(10);
        enemyDuck2.addSkill(3);
        enemyDuck2.addSkill(10);

        params.addEnemy(enemyDuck1);
        params.addEnemy(enemyDuck2);

        gameWorld.setBattle(params);

        //Changes the booleans in JuulieNPC to update which NPC is dead and that
        //you can interact with JulieNPC again
        level.characters.remove(this);
        julieNPC.doneInteraction = false;
        julieNPC.isSomeDucksDead = true;


    }
}
