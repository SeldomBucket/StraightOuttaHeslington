package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents the robot boss of the game.
 */
public class RoboNPC extends NPC {

    public SallyNPC sallyNPC;
    private String[] messages;

    public RoboNPC(Level level, Vector2 currentTile, SallyNPC sallyNPC) {
        super(level, currentTile);
        this.sallyNPC = sallyNPC;
        messages = new String[2];
        messages[0] = "01011001 01101111 01110101 01110010 00100000 01101101 01101111 01110100 01101000 01100101 01110010 00100000 01101001 01110011 00100000 01100001 00100000 01110111 01101000 01101111 01110010 01100101 00001101 00001010!!!";
        messages[1] = "Robo Duck has challenged you to a battle.";
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
        uiManager.addNotification("Robo Duck has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Robo Duck", Agent.AgentType.ENEMY,new Statistics(200,500,8,2,4,5,4,2,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(10);
        enemyDuck.addSkill(4);
        enemyDuck.addSkill(6);

        params.addEnemy(enemyDuck);


        gameWorld.setBattle(params);
        level.characters.remove(this);

        //Changes the booleans in SallyNPC to update which NPC is dead and that
        //you can interact with SallyNPC again
        sallyNPC.doneInteraction = false;
        sallyNPC.isRoboDead = true;

    }
}
