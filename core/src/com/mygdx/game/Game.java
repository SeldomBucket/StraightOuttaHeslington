package com.mygdx.game;

import com.mygdx.game.battle.BattleParameters;
import com.mygdx.game.battle.BattleScreen;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * This class is used to switch between the StartScreen, WorldScreen and BattleScreen.
 * It also stores the instances for the friendly party and all items and skills.
 * Executable jar file can be found at https://teammuscovy.wordpress.com/straight-outta-heslington/
 */
public class Game extends com.badlogic.gdx.Game {

	public static PartyManager party;
	public static PartyManager enemies;
	public static ItemManager items;
	public static SkillManager skills;
	private static JsonLoader jsonLoader = new JsonLoader();
	public static int pointsScore=0;

	public static float masterVolume = 0.1f;

	private WorldScreen worldScreen;
	private BattleScreen battleScreen;

	public boolean wonBattle;


	@Override
	public void create() {
		loadFiles();
		Assets.load();
		wonBattle = false;
		setScreen(new StartScreen(this));
	}

	/**
	 * Loads json files for the party, items and skills.
	 */
	private void loadFiles(){
		try {
			skills = jsonLoader.parseSkillManager("JSONfiles/skills.json");
			items = jsonLoader.parseItemManager("JSONfiles/items.json");
			party = jsonLoader.parsePartyManager("JSONfiles/party.json");
			enemies = jsonLoader.parsePartyManager("JSONfiles/enemies.json");
		} catch (FileNotFoundException ex) {
			// Do something with 'ex'
		} catch (IOException ex2) {
			// Do something with 'ex2'
		}
	}

	/**
	 * Called when a battle has ended.
	 * @param won True if the player won the battle.
	 */
	public void returnToWorld(boolean won){
		wonBattle = won;
		setScreen(worldScreen);
	}

	/**
	 * Disposes of assets when game is no longer used.
	 */
	@Override
	public void dispose() {
		super.dispose();
		Assets.dispose();
	}

	/**
	 * Creates a new battle and sets the battleScreen as the current screen.
	 * @param battleParams The battle parameters of the battle to be created
	 */
	public void newBattle(BattleParameters battleParams) {
		battleScreen = new BattleScreen(this, battleParams);
		setScreen(battleScreen);
	}

	/**
	 * Used when switching to the worldScreen from the startScreen.
	 */
	public void newWorldScreen() {
		worldScreen = new WorldScreen(this);
		setScreen(worldScreen);
	}
}