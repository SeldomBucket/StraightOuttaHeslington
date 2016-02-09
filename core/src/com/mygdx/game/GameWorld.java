package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.UI.UIManager;
import com.mygdx.game.UI.UIScore;
import com.mygdx.game.battle.BattleParameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class contains the high level logic for the game world and contains the level and UI objects.
 */
public class GameWorld {

    public Game game;
    public Level level;
    public UIManager uiManager;
    public GameState gameState;
    public SallyNPC sallyDuck;

    private NPC interactingNPC;
    private Interaction interaction;
    private BattleParameters battleParams;
    private int battleChance;

    /**
     * Constructor for the GameWorld generates a new level and adds the characters to be used in the game.
     * The initial state for the game is FREEROAM.
     */
    public GameWorld(Game game) {
        this.game = game;
        gameState = GameState.FREEROAM;
        level = new Level(this);
        uiManager = new UIManager(Game.party);
        battleChance = 2000;
        sallyDuck = new SallyNPC(level, new Vector2(108, 91));
        level.characters.add(sallyDuck);
        SammyNPC SammyDuck = new SammyNPC(level, new Vector2(100, 93));
        level.characters.add(SammyDuck);
        JulieNPC JulieDuck = new JulieNPC(level, new Vector2(90, 93));
        level.characters.add(JulieDuck);
        uiManager.addUIComponent(new UIScore());
        battleParams = new BattleParameters(0);
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Crazed Duck", Agent.AgentType.ENEMY,new Statistics(100,100,0,2,2,2,2,2,3),emptyList,new CurrentEquipment(0,0,0,0,0),0);
        enemyDuck.addSkill(0);
        Agent enemyDuck2 = new Agent("Crazed Duck", Agent.AgentType.ENEMY,new Statistics(100,100,0,2,2,2,2,2,3),emptyList,new CurrentEquipment(0,0,0,0,0),0);
        enemyDuck2.addSkill(0);
        battleParams.addEnemy(enemyDuck);
        battleParams.addEnemy(enemyDuck2);
    }

    /**
     * Called once per frame to update GameWorld logic.
     * This looks at the current game's current state and acts accordingly.
     * @param delta The time since the last frame was rendered.
     */
    public void update(float delta) {
        InputHandler.update();
        level.update(delta);
        uiManager.updateNotification(delta);
        switch (gameState) {
            case BATTLE_DIALOGUE:
                if (!uiManager.updateDialogue(delta)) {
                    level.stopInput = false;
                    gameState = GameState.BATTLE;
                    game.newBattle(battleParams);
                }
                break;
            case FREEROAM:
                level.stopInput = false;
                Random random = new Random();
                if (level.player.getState() == Character.CharacterState.TRANSITIONING && random.nextInt(battleChance) == 1){
                    if (!level.roadMap[(int)level.player.getCurrentTile().x][(int)level.player.getCurrentTile().y]) {
                        uiManager.createDialogue(new String[]{"You have been stopped by a group of... somethings!"});
                        level.stopInput = true;
                        battleChance = 1000;
                        BattleParameters params = new BattleParameters(0);

                        //Get a number of agents from the list of enemies, make new agent instances with their information and setup the next battle
                        for (int i = 0; i < random.nextInt(3) + 1; i++) {
                            Agent thisAgent = Game.enemies.getMember(random.nextInt(Game.enemies.size()));
                            Statistics thisAgentStats = thisAgent.getStats();
                            Statistics newStats = new Statistics(thisAgentStats.getMaxHP(), thisAgentStats.getMaxMP(), thisAgentStats.getSpeed(), thisAgentStats.getStrength(), thisAgentStats.getDexterity(), thisAgentStats.getIntelligence(), thisAgentStats.getBaseArmourVal(), thisAgentStats.getExperience(), thisAgentStats.getCurrentLevel());
                            params.addEnemy(new Agent(thisAgent.getName(), thisAgent.getType(), newStats, thisAgent.getSkills(), thisAgent.getCurrentEquipment(), thisAgent.getTexture()));
                        }

                        battleParams = params;
                        Assets.worldMusic.stop();//Stop the worldMusic
                        Assets.sfx_battleStart.play(Game.masterVolume);
                        gameState = GameState.BATTLE_DIALOGUE;
                        level.stopInput = true;
                    }else{
                        battleChance--;
                    }
                } else
                if (InputHandler.isActJustPressed()) {
                    interactingNPC = level.player.interactingNPC;
                    interaction = level.player.interaction;
                    level.stopInput = true;
                    if (interactingNPC != null) {
                        interactingNPC.initializeInteraction(delta, uiManager);
                        interaction = Interaction.NPC_DIALOGUE;
                        gameState = GameState.INTERACTION;
                    }else if (!(interaction == Interaction.NONE)){
                        switch (interaction){
                            case TEXT_SIGN:
                                uiManager.createDialogue(new String[]{"This is a sign!"});
                                gameState = GameState.INTERACTION;
                                break;
                            case VISTA_SIGN:
                                break;
                            case FLIGHT:
                                break;
                        }
                    }
                }else if (InputHandler.isMenuJustPressed()){
                    uiManager.openPartyMenu();
                    gameState = GameState.PARTY_MENU;
                }
                break;

            case PARTY_MENU:
                if (!uiManager.updatePartyMenu(delta)){
                    gameState = GameState.FREEROAM;
                }
                break;

            case INTERACTION:
                switch (interaction){
                    case NPC_DIALOGUE:
                        if (!interactingNPC.updateInteracting(delta)) {
                            interactingNPC.action(this);
                            gameState = GameState.FREEROAM;
                        }
                        break;
                    case TEXT_SIGN:
                        if(!uiManager.updateDialogue(delta)){
                            gameState = GameState.FREEROAM;
                        }
                        break;
                    case VISTA_SIGN:
                        break;
                    case FLIGHT:
                        break;
                }
                break;

            case BATTLE:
                if (game.wonBattle) {
                    uiManager.addNotification("You won the battle!");
                } else {
                    Game.party.setHealths(1);
                    level.player.setCurrentTile(new Vector2(118, 94));
                    uiManager.addNotification("You lost the battle! You have been moved backwards.");
                }
                gameState = GameState.FREEROAM;
                break;
        }
    }

    /**
     * changes the game state to BATTLE and loads a new battle in the Game object.
     * @param battleParams The parameters used to create a battle.
     */
    public void setBattle(BattleParameters battleParams){
        gameState = GameState.BATTLE;
        this.battleParams = battleParams;
        game.newBattle(battleParams);
    }


}
