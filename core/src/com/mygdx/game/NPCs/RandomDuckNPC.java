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
public class RandomDuckNPC extends NPC {

    public SammyNPC sammyNPC;
    private String[] messages;

    public RandomDuckNPC(Level level, Vector2 currentTile, SammyNPC sammyNPC) {
        super(level, currentTile);
        this.sammyNPC = sammyNPC;
        messages = new String[2];
        messages[0] = "Hey, I don't like your attitude";
        messages[1] = "The Random Duck has challenged you to a battle.";
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
        uiManager.addNotification("The Random Duck has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck1 = new Agent("Random Duck", Agent.AgentType.ENEMY,new Statistics(50,70,6,2,2,1,1,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),10);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck1.addSkill(3);
        enemyDuck1.addSkill(10);


        params.addEnemy(enemyDuck1);



        gameWorld.setBattle(params);
        level.characters.remove(this);
        level.characters.add((new TealNPC(level, new Vector2(115, 100), sammyNPC)));




    }
}

