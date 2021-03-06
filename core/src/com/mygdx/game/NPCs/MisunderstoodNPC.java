package com.mygdx.game.NPCs;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.*;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents the second objective given by DannyNPC.
 * NEW NPC

 */
public class MisunderstoodNPC extends NPC {

    public DannyNPC dannyNPC;
    private String[] messages;

    public MisunderstoodNPC(Level level, Vector2 currentTile, DannyNPC dannyNPC) {
        super(level, currentTile);
        this.dannyNPC = dannyNPC;
        messages = new String[2];
        messages[0] = "You thief and murderer!";
        messages[1] = "Robo duck has challenged you to a battle.";
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
        uiManager.addNotification("The Misunderstood duck has been defeated.");
        BattleParameters params = new BattleParameters(0);
        //Enemy ducks
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Misunderstood Duck", Agent.AgentType.ENEMY,new Statistics(150,110,8,3,5,4,3,3,3),emptyList,new CurrentEquipment(0,0,0,0,0),9);
//        enemyDuck.equipEquipment(0);
//        enemyDuck.equipEquipment(1);
        enemyDuck.addSkill(10);
        enemyDuck.addSkill(6);

        params.addEnemy(enemyDuck);

        gameWorld.setBattle(params);
        level.characters.remove(this);
        uiManager.addNotification("You gained 100 points.");
        Game.pointsScore += 100;

    }
}
