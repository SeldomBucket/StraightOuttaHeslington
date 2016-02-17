package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the first objective given by SammyNPC.
 * NEW NPC
 */
public class GressinghamNPC extends NPC {

    private String[] messages;
    public BackUpNPC backUp1Duck, backUp2Duck, backUp3Duck,backUp4Duck;
    public SammyNPC sammyNPC;

    public GressinghamNPC(Level level, Vector2 currentTile, SammyNPC sammyNPC) {
        super(level, currentTile);
        this.sammyNPC = sammyNPC;
        messages = new String[3];
        messages[0] = "Oh, she's sent you after me has she?";
        messages[1] = "Even if you get past me you wont get past my back up, bring it!";
        messages[2] = "The Gressingham duck has challenged you to a battle.";
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

        Agent enemyDuck = new Agent("Gressingham Duck", Agent.AgentType.ENEMY,new Statistics(1,500,8,2,3,3,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),8);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(10);
        enemyDuck.addSkill(3);

        params.addEnemy(enemyDuck);


        gameWorld.setBattle(params);
        level.characters.remove(this);
        backUp1Duck = new BackUpNPC(level, new Vector2(100, 114), this);
        level.characters.add(backUp1Duck);
        backUp2Duck = new BackUpNPC(level, new Vector2(100, 112), this);
        level.characters.add(backUp2Duck);
        backUp3Duck = new BackUpNPC(level, new Vector2(101, 113), this);
        level.characters.add(backUp3Duck);
        backUp4Duck = new BackUpNPC(level, new Vector2(99, 113), this);
        level.characters.add(backUp4Duck);


        //Changes the booleans in SammyNPC to update which NPC is dead and that
        //you can interact with SammyNPC again
        sammyNPC.isGresDead = true;
        sammyNPC.doneInteraction = false;

    }
}
