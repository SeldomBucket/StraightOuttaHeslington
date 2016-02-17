package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the robot boss of the game.
 */
public class MallardsNPC extends NPC {

    public JulieNPC julieNPC;
    private String[] messages;

    public MallardsNPC(Level level, Vector2 currentTile, JulieNPC julieNPC) {
        super(level, currentTile);
        this.julieNPC = julieNPC;
        messages = new String[2];
        messages[0] = "Why are you even trying to help her?";
        messages[1] = "The mallards have challenged you to a battle.";
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

        uiManager.addNotification("The mallards have been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck1 = new Agent("Mallard Duck", Agent.AgentType.ENEMY,new Statistics(5,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),6);
        Agent enemyDuck2 = new Agent("Mallard Duck", Agent.AgentType.ENEMY,new Statistics(5,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),6);
        Agent enemyDuck3 = new Agent("Mallard Duck", Agent.AgentType.ENEMY,new Statistics(5,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),6);
        Agent enemyDuck4 = new Agent("Mallard Duck", Agent.AgentType.ENEMY,new Statistics(5,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),6);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck1.addSkill(10);
        enemyDuck1.addSkill(7);
        enemyDuck2.addSkill(10);
        enemyDuck2.addSkill(8);
        enemyDuck3.addSkill(10);
        enemyDuck3.addSkill(9);
        enemyDuck4.addSkill(10);
        enemyDuck4.addSkill(2);

        params.addEnemy(enemyDuck1);
        params.addEnemy(enemyDuck2);
        params.addEnemy(enemyDuck3);
        params.addEnemy(enemyDuck4);

        gameWorld.setBattle(params);

        //Changes the booleans in JulieNPC to update which NPC is dead and that
        //you can interact with JulieNPC again
        level.characters.remove(this);
        julieNPC.isMallardDead = true;
        julieNPC.doneInteraction = false;

    }
}