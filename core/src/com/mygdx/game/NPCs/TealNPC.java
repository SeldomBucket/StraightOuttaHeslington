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
public class TealNPC extends NPC {

    public SammyNPC sammyNPC;
    private String[] messages;

    public TealNPC(Level level, Vector2 currentTile, SammyNPC sammyNPC) {
        super(level, currentTile);
        this.sammyNPC = sammyNPC;
        messages = new String[2];
        messages[0] = "Hey you can't do that around here without punishment!";
        messages[1] = "The Teal Duck challenged you to a battle.";
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
        uiManager.addNotification("The Teal Duck has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Teal Duck", Agent.AgentType.ENEMY,new Statistics(150,100,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(3);
        enemyDuck.addSkill(10);
        enemyDuck.addSkill(7);


        params.addEnemy(enemyDuck);



        gameWorld.setBattle(params);
        level.characters.remove(this);
        sammyNPC.isTealDead = true;
        sammyNPC.doneInteraction = false;




    }
}
