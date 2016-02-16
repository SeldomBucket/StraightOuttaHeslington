package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the robot boss of the game.
 */
public class RoboFriendNPC extends NPC {

    public SallyNPC sallyNPC;
    private String[] messages;

    public RoboFriendNPC(Level level, Vector2 currentTile, SallyNPC sallyNPC) {
        super(level, currentTile);
        this.sallyNPC = sallyNPC;
        messages = new String[2];
        messages[0] = "YOU KILLED MY FRIEND";
        messages[1] = "Robo's friend has challenged you to a battle.";
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
        uiManager.addNotification("Robo ducks friend has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Robo Duck's friend", Agent.AgentType.ENEMY,new Statistics(10,500,6,2,2,2,1,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(5);
        enemyDuck.addSkill(10);
        enemyDuck.addSkill(2);

        params.addEnemy(enemyDuck);


        gameWorld.setBattle(params);
        level.characters.remove(this);
        sallyNPC.doneInteraction = false;
        sallyNPC.isFriendDead = true;



    }
}
