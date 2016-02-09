package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the robot boss of the game.
 */
public class GressinghamNPC extends NPC {

    private String[] messages;

    public GressinghamNPC(Level level, Vector2 currentTile) {
        super(level, currentTile);
        messages = new String[3];
        messages[0] = "Oh she's sent you after me has she?";
        messages[1] = "Even if you get passed me you wont get passed my back up, bring it!";
        messages[2] = "The gresigham duck has challenged you to a battle.";
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
        uiManager.addNotification("Gressingham Duck has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Gressingham Duck", Agent.AgentType.ENEMY,new Statistics(10,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),1);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(4);

        params.addEnemy(enemyDuck);


        gameWorld.setBattle(params);
        level.characters.remove(this);
        level.characters.add(new BackUpNPC(level, new Vector2(100, 115)));
        level.characters.add(new BackUpNPC(level, new Vector2(98, 113)));
        level.characters.add(new BackUpNPC(level, new Vector2(100, 111)));
        level.characters.add(new BackUpNPC(level, new Vector2(102, 113)));

    }
}