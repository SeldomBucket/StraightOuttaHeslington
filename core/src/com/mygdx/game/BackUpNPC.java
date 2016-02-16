package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the robot boss of the game.
 */
public class BackUpNPC extends NPC {

    public GressinghamNPC gressinghamNPC;
    private String[] messages;



    public BackUpNPC(Level level, Vector2 currentTile, GressinghamNPC gressinghamNPC) {
        super(level, currentTile);
        messages = new String[3];
        this.gressinghamNPC = gressinghamNPC;
        messages[0] = "You killed our master, you will pay";
        messages[1] = "Bring it";
        messages[2] = "A gresigham duck has challenged you to a battle.";
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
        uiManager.addNotification("A back up duck has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Gressingham Duck", Agent.AgentType.ENEMY,new Statistics(80,90,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
        Agent enemyDuck2 = new Agent("Gressingham Duck", Agent.AgentType.ENEMY,new Statistics(80,90,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
        Agent enemyDuck3 = new Agent("Gressingham Duck", Agent.AgentType.ENEMY,new Statistics(80,90,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
        Agent enemyDuck4 = new Agent("Gressingham Duck", Agent.AgentType.ENEMY,new Statistics(80,90,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(10);
        enemyDuck.addSkill(7);
        enemyDuck2.addSkill(10);
        enemyDuck2.addSkill(8);
        enemyDuck3.addSkill(10);
        enemyDuck3.addSkill(9);
        enemyDuck4.addSkill(10);
        enemyDuck4.addSkill(3);

        params.addEnemy(enemyDuck);
        params.addEnemy(enemyDuck2);
        params.addEnemy(enemyDuck3);
        params.addEnemy(enemyDuck4);


        gameWorld.setBattle(params);
        level.characters.remove(gressinghamNPC.backUp1Duck);
        level.characters.remove(gressinghamNPC.backUp2Duck);
        level.characters.remove(gressinghamNPC.backUp3Duck);
        level.characters.remove(gressinghamNPC.backUp4Duck);



    }
}
