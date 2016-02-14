package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the robot boss of the game.
 */
public class SomeDucksNPC extends NPC {

    public JulieNPC julieNPC;
    private String[] messages;

    public SomeDucksNPC(Level level, Vector2 currentTile, JulieNPC julieNPC) {
        super(level, currentTile);
        this.julieNPC = julieNPC;
        messages = new String[2];
        messages[0] = "Seriously?! You're trying to help Sally? We'll teach you a lesson on how to mind your own business";
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
        uiManager.addNotification("Some Ducks has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck1 = new Agent("Some Ducks", Agent.AgentType.ENEMY,new Statistics(10,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
        Agent enemyDuck2 = new Agent("Some Ducks", Agent.AgentType.ENEMY,new Statistics(10,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck1.addSkill(4);
        enemyDuck2.addSkill(4);

        params.addEnemy(enemyDuck1);
        params.addEnemy(enemyDuck2);


        gameWorld.setBattle(params);
        level.characters.remove(this);
        julieNPC.doneInteraction = false;
        julieNPC.isSomeDucksDead = true;

    }
}
