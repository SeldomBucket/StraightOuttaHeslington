package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.NPCs.DannyNPC;
import com.mygdx.game.NPCs.JulieNPC;
import com.mygdx.game.NPCs.SallyNPC;
import com.mygdx.game.NPCs.SammyNPC;
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
    public SammyNPC sammyDuck;
    public JulieNPC julieDuck;
    public DannyNPC dannyDuck;
    //public BackUpNPC backup1Duck, backUp2Duck, backUP3Duck, backUp4Duck;

    private NPC interactingNPC;
    private Interaction interaction;
    private BattleParameters battleParams;
    private int battleChance;
    private boolean[] vistasVisited; //True if you have seen the vista, used for points
    private boolean[] flightSpotsVisited; //True if you can fly to that particular spot
    private String[] locationNames;// [Constantine, Langwith, Goodricke, Law and Management, The Catalyst, TFTV, Computer Science, Ron Cooke Hub]
    private boolean[] itemsToShow;
    private String[] itemNames;
    private int[] levelRequirements;
    private int highestLevel;
    private int[] itemCosts;

    private Vector2 lastFlightSpot;

    private float flightTimer;

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
        sammyDuck = new SammyNPC(level, new Vector2(100, 93));
        level.characters.add(sammyDuck);
        julieDuck = new JulieNPC(level, new Vector2(90, 93));
        level.characters.add(julieDuck);
        dannyDuck = new DannyNPC(level, new Vector2(120, 93));
        level.characters.add(dannyDuck);
        uiManager.addUIComponent(new UIScore());
        //uiManager.addUIComponent(new UIDebugCoords(level.player));
        battleParams = new BattleParameters(0);
        List<Integer> emptyList = new ArrayList<Integer>();
        Agent enemyDuck = new Agent("Crazed Duck", Agent.AgentType.ENEMY,new Statistics(100,100,0,2,2,2,2,2,3),emptyList,new CurrentEquipment(0,0,0,0,0),0);
        enemyDuck.addSkill(0);
        Agent enemyDuck2 = new Agent("Crazed Duck", Agent.AgentType.ENEMY,new Statistics(100,100,0,2,2,2,2,2,3),emptyList,new CurrentEquipment(0,0,0,0,0),0);
        enemyDuck2.addSkill(0);
        battleParams.addEnemy(enemyDuck);
        battleParams.addEnemy(enemyDuck2);
        flightSpotsVisited = new boolean[]{false, false, false, false, false, false, false, false};
        vistasVisited = new boolean[]{false, false, false, false, false, false, false, false, false};
        locationNames = new String[]{"Constantine", "Langwith", "Goodricke", "Law and Management", "The Catalyst", "TFTV", "Computer Science", "Ron Cooke Hub"};
        uiManager.createFlightMenu(flightSpotsVisited, locationNames);
        itemsToShow = new boolean[]{true, true, true, true, true, true, true, true, true, true};
        itemCosts = new int[]{20,40,20,50,100,60,70,80,100,60};
        levelRequirements = new int[]{0, 0, 0, 5, 12, 0, 6, 9, 20, 10};//15
        itemNames = new String[]{"Heal Flask","Revive Potion","Mana Flask","Crowbar","Fancy Sword","Wizard's Hat","Heelys","Iron Chestplate", "Magic Wand", "Gun"};
        uiManager.createShopMenu(itemsToShow, itemNames, itemCosts, levelRequirements);
        highestLevel = 0;
        }



    /**
     * Called once per frame to update GameWorld logic.
     * This looks at the game's current state and acts accordingly.
     * Switches on gameState and the player's current interaction (which is calculated using the interaction map in the player class)
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
                if (level.player.getState() == Character.CharacterState.TRANSITIONING && random.nextInt(battleChance) == -1){
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
                            params.addEnemy(new Agent(thisAgent.getName(), thisAgent.getType(), newStats, thisAgent.getSkills(), thisAgent.getCurrentEquipment(), thisAgent.getTextureIndex()));
                        }

                        battleParams = params;
                        Assets.worldMusic.stop();//Stop the worldMusic
                        Assets.sfx_battleStart.play(Game.masterVolume);
                        gameState = GameState.BATTLE_DIALOGUE;
                        level.stopInput = true;
                    }else{
                        battleChance--;
                    }
                } else if (InputHandler.isActJustPressed()) {
                    interactingNPC = level.player.interactingNPC;
                    interaction = level.player.interaction;
                    level.stopInput = true;
                    if (interactingNPC != null) {
                        interactingNPC.initializeInteraction(delta, uiManager);
                        interaction = Interaction.NPC_DIALOGUE;
                        gameState = GameState.INTERACTION;
                    } else if (!(interaction == Interaction.NONE)) {
                        switch (interaction) {
                            case TEXT_SIGN:
                                uiManager.createDialogue(level.textSigns.get(level.player.interactionLocationHash));
                                gameState = GameState.INTERACTION;
                                break;
                            case VISTA_SIGN:
                                uiManager.addVista(level.vistaSigns.get(level.player.interactionLocationHash));
                                gameState = GameState.INTERACTION;
                                break;
                            case FLIGHT:
                                if ((level.player.getCurrentTile().x == 179.0f) && (level.player.getCurrentTile().y == 105.0f)) {
                                    if (!flightSpotsVisited[0]) {
                                        flightSpotsVisited[0] = true;
                                        uiManager.addNotification("Flight spot for Constantine unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(0);
                                        gameState = GameState.INTERACTION;
                                    }
                                } else if ((level.player.getCurrentTile().x == 145.0f) && (level.player.getCurrentTile().y == 110.0f)) {
                                    if (!flightSpotsVisited[1]) {
                                        flightSpotsVisited[1] = true;
                                        uiManager.addNotification("Flight spot for Langwith unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(1);
                                        gameState = GameState.INTERACTION;
                                    }
                                } else if ((level.player.getCurrentTile().x == 102.0f) && (level.player.getCurrentTile().y == 118.0f)) {
                                    if (!flightSpotsVisited[2]) {
                                        flightSpotsVisited[2] = true;
                                        uiManager.addNotification("Flight spot for Goodricke unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(2);
                                        gameState = GameState.INTERACTION;
                                    }
                                } else if ((level.player.getCurrentTile().x == 86.0f) && (level.player.getCurrentTile().y == 92.0f)) {
                                    if (!flightSpotsVisited[3]) {
                                        flightSpotsVisited[3] = true;
                                        uiManager.addNotification("Flight spot for Law and Management unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(3);
                                        gameState = GameState.INTERACTION;
                                    }
                                } else if ((level.player.getCurrentTile().x == 71.0f) && (level.player.getCurrentTile().y == 97.0f)) {
                                    if (!flightSpotsVisited[4]) {
                                        flightSpotsVisited[4] = true;
                                        uiManager.addNotification("Flight spot for The Catalyst unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(4);
                                        gameState = GameState.INTERACTION;
                                    }
                                } else if ((level.player.getCurrentTile().x == 72.0f) && (level.player.getCurrentTile().y == 72.0f)) {
                                    if (!flightSpotsVisited[5]) {
                                        flightSpotsVisited[5] = true;
                                        uiManager.addNotification("Flight spot for Theatre, Film, and Television unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(5);
                                        gameState = GameState.INTERACTION;
                                    }
                                } else if ((level.player.getCurrentTile().x == 89.0f) && (level.player.getCurrentTile().y == 65.0f)) {
                                    if (!flightSpotsVisited[6]) {
                                        flightSpotsVisited[6] = true;
                                        uiManager.addNotification("Flight spot for Computer Science unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(6);
                                        gameState = GameState.INTERACTION;
                                    }
                                } else if ((level.player.getCurrentTile().x == 115.0f) && (level.player.getCurrentTile().y == 84.0f)) {
                                    if (!flightSpotsVisited[7]) {
                                        flightSpotsVisited[7] = true;
                                        uiManager.addNotification("Flight spot for Ron Cooke Hub unlocked!");
                                        uiManager.addNotification("You gained 50 points.");
                                        game.pointsScore += 50;
                                    } else {
                                        uiManager.showFlightMenu(7);
                                        gameState = GameState.INTERACTION;
                                    }
                                }
                                break;
                            case SHOP:
                                for (int i = 0;i<4;i++) {
                                    if (Game.party.getMember(i).getStats().getCurrentLevel() > highestLevel) {
                                        highestLevel = Game.party.getMember(i).getStats().getCurrentLevel();
                                    }
                                }
                                uiManager.showShopMenu(highestLevel);
                                level.stopInput = true;
                                gameState = GameState.SHOP_MENU;
                                break;

                        }
                    }
                }else if (InputHandler.isMenuJustPressed()) {
                    uiManager.openPartyMenu();
                    level.stopInput = true;
                    gameState = GameState.PARTY_MENU;
                }

                break;

            case PARTY_MENU:
                if (!uiManager.updatePartyMenu(delta)){
                    gameState = GameState.FREEROAM;
                }
                break;

            case SHOP_MENU:

                int shopSelection = uiManager.updateShopMenu(delta);
                if (shopSelection == 974){
                    gameState = GameState.FREEROAM;
                }else if(!(shopSelection==-1)){
                    switch (shopSelection){
                        case 0:
                            if (levelRequirements[shopSelection] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[shopSelection]){
                                    if (!Game.party.getConsumables().contains(shopSelection)) {
                                        Game.pointsScore -= itemCosts[shopSelection];
                                        Game.party.getConsumables().add(shopSelection);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[shopSelection]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[shopSelection] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[shopSelection]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[shopSelection]);
                            }
                            break;

                        case 1:
                            if (levelRequirements[1] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[1]){
                                    if (!Game.party.getConsumables().contains(shopSelection)) {
                                        Game.pointsScore -= itemCosts[1];
                                        Game.party.getConsumables().add(shopSelection);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[1]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[1] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[1]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[1]);
                            }
                            break;

                        case 2:
                            if (levelRequirements[2] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[2]){
                                    if (!Game.party.getConsumables().contains(shopSelection)) {
                                        Game.pointsScore -= itemCosts[2];
                                        Game.party.getConsumables().add(shopSelection);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[2]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[2] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[2]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[2]);
                            }
                            break;

                        case 3:
                            if (levelRequirements[3] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[3]){
                                    if (!Game.party.getEquipables().contains(shopSelection+1)) {
                                        Game.pointsScore -= itemCosts[3];
                                        Game.party.getEquipables().add(shopSelection+1);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[3]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[3] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[3]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[3]);
                            }
                            break;

                        case 4:
                            if (levelRequirements[4] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[4]){
                                    if (!Game.party.getEquipables().contains(shopSelection+1)) {
                                        Game.pointsScore -= itemCosts[4];
                                        Game.party.getEquipables().add(shopSelection+1);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[4]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[4] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[4]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[4]);
                            }
                            break;
                        case 5:
                            if (levelRequirements[5] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[5]){
                                    if (!Game.party.getEquipables().contains(shopSelection+1)) {
                                        Game.pointsScore -= itemCosts[5];
                                        Game.party.getEquipables().add(shopSelection+1);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[5]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[5] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[5]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[5]);
                            }
                            break;

                        case 6:
                            if (levelRequirements[6] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[6]){
                                    if (!Game.party.getEquipables().contains(shopSelection+1)) {
                                        Game.pointsScore -= itemCosts[6];
                                        Game.party.getEquipables().add(shopSelection+1);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[6]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[6] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[6]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[6]);
                            }
                            break;

                        case 7:
                            if (levelRequirements[7] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[7]){
                                    if (!Game.party.getEquipables().contains(shopSelection+1)) {
                                        Game.pointsScore -= itemCosts[7];
                                        Game.party.getEquipables().add(shopSelection+1);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[7]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[7] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[7]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[7]);
                            }
                            break;

                        case 8:
                            if (levelRequirements[8] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[8]){
                                    if (!Game.party.getEquipables().contains(shopSelection+1)) {
                                        Game.pointsScore -= itemCosts[8];
                                        Game.party.getEquipables().add(shopSelection+1);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[8]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[8] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[8]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[8]);
                            }
                            break;

                        case 9:
                            if (levelRequirements[9] <= highestLevel){
                                if (Game.pointsScore >= itemCosts[9]){
                                    if (!Game.party.getEquipables().contains(shopSelection+1)) {
                                        Game.pointsScore -= itemCosts[9];
                                        Game.party.getEquipables().add(shopSelection+1);
                                        uiManager.addNotification("Successfully purchased the " + itemNames[9]);
                                    }else{
                                        uiManager.addNotification("You already have the " + itemNames[9] + " in your inventory");
                                    }
                                }else{
                                    uiManager.addNotification("You do not have enough points to buy the " + itemNames[9]);
                                }
                            }else{
                                uiManager.addNotification("None of your party members are a high enough level to purchase the " + itemNames[9]);
                            }
                            break;
                    }
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
                        if(!uiManager.updateVista()){
                            gameState = GameState.FREEROAM;
                            switch (level.player.interactionLocationHash){
                                case 288:
                                    if (vistasVisited[0]==false){
                                        vistasVisited[0] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for Constantine.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 258:
                                    if (vistasVisited[1]==false){
                                        vistasVisited[1] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for Langwith.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 223:
                                    if (vistasVisited[2]==false){
                                        vistasVisited[2] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for Goodricke.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 181:
                                    if (vistasVisited[3]==false){
                                        vistasVisited[3] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for the Law and Management Building.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 174:
                                    if (vistasVisited[4]==false){
                                        vistasVisited[4] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for the Catalyst.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 145:
                                    if (vistasVisited[5]==false){
                                        vistasVisited[5] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for Theatre Film and Television.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 158:
                                    if (vistasVisited[6]==false){
                                        vistasVisited[6] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for Computer Science.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 203:
                                    if (vistasVisited[7]==false){
                                        vistasVisited[7] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the vista for the Ron Cooke Hub.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                                case 58:
                                    if (vistasVisited[8]==false){
                                        vistasVisited[8] = true;
                                        game.pointsScore+=50;
                                        uiManager.addNotification("Found the final vista.");
                                        uiManager.addNotification("You gained 50 points.");
                                    }
                                    break;
                            }
                        }
                        break;

                    case FLIGHT:
                        int flightLocation = uiManager.updateFlightMenu();
                        if (flightLocation == 666) {
                            gameState = GameState.FREEROAM;
                        }else if((flightLocation != -1)){
                            level.stopInput = true;
                            switch (flightLocation){
                                case 0:
                                    if (flightSpotsVisited[0]) {
                                        level.player.setCurrentTile(new Vector2(179, 106));
                                        level.player.setTargetTile(new Vector2(179, 106));
                                        lastFlightSpot = new Vector2(179, 106);
                                        //level.player.oldPos = new Vector2(179, 106);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                                case 1:
                                    if (flightSpotsVisited[1]) {
                                        level.player.setCurrentTile(new Vector2(145, 111));
                                        level.player.setTargetTile(new Vector2(145, 111));
                                        lastFlightSpot = new Vector2(145, 111);
                                        //level.player.oldPos = new Vector2(145, 111);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                                case 2:
                                    if (flightSpotsVisited[2]) {
                                        level.player.setCurrentTile(new Vector2(102, 119));
                                        level.player.setTargetTile(new Vector2(102, 119));
                                        lastFlightSpot = new Vector2(102, 119);
                                        //level.player.oldPos = new Vector2(102, 119);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                                case 3:
                                    if (flightSpotsVisited[3]) {
                                        level.player.setCurrentTile(new Vector2(86, 93));
                                        level.player.setTargetTile(new Vector2(86, 93));
                                        lastFlightSpot = new Vector2(86, 93);
                                        //level.player.oldPos = new Vector2(86, 93);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                                case 4:
                                    if (flightSpotsVisited[4]) {
                                        level.player.setCurrentTile(new Vector2(71, 98));
                                        level.player.setTargetTile(new Vector2(71, 98));
                                        lastFlightSpot = new Vector2(71, 98);
                                        //level.player.oldPos = new Vector2(71, 98);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                                case 5:
                                    if (flightSpotsVisited[5]) {
                                        level.player.setCurrentTile(new Vector2(72, 73));
                                        level.player.setTargetTile(new Vector2(72, 73));
                                        lastFlightSpot = new Vector2(72, 73);
                                        //level.player.oldPos = new Vector2(72, 73);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                                case 6:
                                    if (flightSpotsVisited[6]) {
                                        level.player.setCurrentTile(new Vector2(89, 66));
                                        level.player.setTargetTile(new Vector2(89, 66));
                                        lastFlightSpot = new Vector2(89, 66);
                                        //level.player.oldPos = new Vector2(89, 66);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                                case 7:
                                    if (flightSpotsVisited[7]) {
                                        level.player.setCurrentTile(new Vector2(115, 85));
                                        level.player.setTargetTile(new Vector2(115, 85));
                                        lastFlightSpot = new Vector2(115, 85);
                                        //level.player.oldPos = new Vector2(115, 85);
                                        //level.player.setDirection(Character.Direction.DOWN);
                                    }else{
                                        uiManager.addNotification("Cannot fly to a location you have not already visited");
                                    }
                                    break;
                            }
                            level.player.updateFlight(delta);
                            gameState = GameState.FLYING;
                            flightTimer = 0;
                        }
                        break;
                }
                break;

            case BATTLE:
                if (game.wonBattle) {
                    uiManager.addNotification("You won the battle!");
                } else {
                    Game.party.setHealths(1);
                    level.player.setCurrentTile(new Vector2(lastFlightSpot.x, lastFlightSpot.y));
                    uiManager.addNotification("You lost the battle! You have been moved to the last flight spot you were at.");
                }
                gameState = GameState.FREEROAM;
                break;

            case FLYING:
                flightTimer += delta;
                level.player.setState(Character.CharacterState.TRANSITIONING);
                //level.player.updateFlight(delta);
                level.stopInput = true;
                if (flightTimer > 1){
                    level.player.updateMovement(Character.Direction.DOWN);
                    gameState = GameState.FREEROAM;
                }
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
