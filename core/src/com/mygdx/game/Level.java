package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

/**
 * The level class contains the map used by the game and stores all characters.
 */
public class Level {

    public static final int TILE_SIZE = 16;

    private GameWorld gameWorld;
    public TiledMap map;
    public boolean[][] collisionMap, waterMap, roadMap;
    public Interaction[][] interactionMap;
    public Player player;
    public ArrayList<Character> characters;
    public boolean stopInput;

    public Map<Integer,String[]> textSigns;
    public Map<Integer,Texture> vistaSigns;

    public int mapWidth;
    public int mapHeight;
    public int tileWidth;
    public int tileHeight;
    public Vector2 mapBounds;

    /**
     * The constructor loads the map and creates a new player in the appropriate position.
     */
    public Level(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        map = new TmxMapLoader().load("hesEastMap.tmx");

        MapProperties prop = map.getProperties();
        mapWidth = prop.get("width", Integer.class);
        mapHeight = prop.get("height", Integer.class);
        tileWidth = prop.get("tilewidth", Integer.class);
        tileHeight = prop.get("tileheight", Integer.class);
        mapBounds = new Vector2(mapWidth * tileWidth, mapHeight * tileHeight);

        collisionMap = new boolean[mapWidth][mapHeight];
        waterMap = new boolean[mapWidth][mapHeight];
        roadMap = new boolean[mapWidth][mapHeight];
        interactionMap = new Interaction[mapWidth][mapHeight];
        textSigns = new HashMap<Integer, String[]>();
        vistaSigns = new HashMap<Integer, Texture>();

        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                collisionMap[x][y] = layer.getCell(x, y).getTile().getProperties().containsKey("blocked");
            }
        }
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                waterMap[x][y] = layer.getCell(x, y).getTile().getProperties().containsKey("water");
            }
        }
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                roadMap[x][y] = layer.getCell(x, y).getTile().getProperties().containsKey("road");
            }
        }
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                if(layer.getCell(x, y).getTile().getProperties().containsKey("textSign")){
                    interactionMap[x][y] = Interaction.TEXT_SIGN;
                    switch (x+y){
                        case 223:
                            textSigns.put(x+y, new String[]{"Hi there, I'm a helpful sign!","You've already guessed to use Z to interact","(you can interact with signs and other ducks)","and use arrow keys to move.","But did you know, you can open your party menu with M", "and use X to exit out of it."});
                            break;
                        case 224:
                            textSigns.put(x+y, new String[]{"Be careful!","If you're not walking on a road, a random battle may occur."});
                            break;
                        case 225:
                            textSigns.put(x+y, new String[]{"You can swim in light blue water","but darker water is too dangerous to swim in."});
                            break;
                        case 226:
                            textSigns.put(x+y, new String[]{"Go north to Constantine College now!","There will be more signs to guide you."});
                            break;
                        case 264:
                            textSigns.put(x+y, new String[]{"To get to Constantine college, go North,","go left at the fork,", "then take the first right."});
                            break;
                        case 283:
                            textSigns.put(x+y, new String[]{"This is a Flight Spot.","Stand on it and press Z to activate it.","When a Flight Spot is activated, you can use it","to fly to any other activated Flight Spot on campus.","You get 50 points for each new flight spot you find!"});
                            break;
                        case 286:
                            textSigns.put(x+y, new String[]{"Welcome to Constantine College,","We're the newest buildings on campus!"});
                            break;
                        case 289:
                            textSigns.put(x+y, new String[]{"This sign to the left is a vista point,","Interact with it to see the campus.","You get 50 points for each new vista point you find.","There are 9 in all, try to find them all!"});
                            break;
                        case 260:
                            textSigns.put(x+y, new String[]{"Welcome to Langwith College,","We've got the pub!"});
                            break;
                        case 222:
                            textSigns.put(x+y, new String[]{"Welcome to Goodricke College,","We're really handy for all the departments!"});
                            break;
                        case 180:
                            textSigns.put(x+y, new String[]{"Welcome to Law and Management,","Phoenix Wright has nothing on these guys!"});
                            break;
                        case 170:
                            textSigns.put(x+y, new String[]{"Welcome to the Catalyst,","Can you hear the business happening?"});
                            break;
                        case 144:
                            textSigns.put(x+y, new String[]{"Welcome to Theatre, Film, and Television,","Shh! They're rehearsing!"});
                            break;
                        case 156:
                            textSigns.put(x+y, new String[]{"Welcome to Computer Science,","The best department!"});
                            break;
                        case 205:
                            textSigns.put(x+y, new String[]{"Welcome to the Ron Cooke Hub,","You can grab a pizza here before lectures!"});
                            break;
                        case 59:
                            textSigns.put(x+y, new String[]{"Well done, you've found the last vista!","Don't you feel accomplished now?","Enjoy the swim back!"});
                            break;
                    }
                }else if (layer.getCell(x, y).getTile().getProperties().containsKey("vistaSign")){
                    interactionMap[x][y] = Interaction.VISTA_SIGN;
                    switch (x+y){
                        case 288:
                            vistaSigns.put(x+y, new Texture("constantine.png"));
                            break;
                        case 258:
                            vistaSigns.put(x+y, new Texture("langwith.png"));
                            break;
                        case 223:
                            vistaSigns.put(x+y, new Texture("goodricke.png"));
                            break;
                        case 181:
                            vistaSigns.put(x+y, new Texture("LMB.png"));
                            break;
                        case 174:
                            vistaSigns.put(x+y, new Texture("catalyst.png"));
                            break;
                        case 145:
                            vistaSigns.put(x+y, new Texture("TFTV.png"));
                            break;
                        case 158:
                            vistaSigns.put(x+y, new Texture("CS.png"));
                            break;
                        case 203:
                            vistaSigns.put(x+y, new Texture("RCH.png"));
                            break;
                        case 58:
                            vistaSigns.put(x+y, new Texture("hesEast.png"));
                            break;
                    }
                }else if(layer.getCell(x, y).getTile().getProperties().containsKey("flightSpot")){
                    interactionMap[x][y] = Interaction.FLIGHT;
                }else{
                    interactionMap[x][y] = Interaction.NONE;
                }
            }
        }

        player = new Player(this, new Vector2(197, 23));
        player.setDirection(Character.Direction.DOWN);
        characters = new ArrayList<Character>();
        characters.add(player);
        stopInput = false;
    }

    /**
     * This method is called once per frame and updates each character in the level.
     */
    public void update(float delta) {
        characters.sort(new Character.CharacterComparator());
        updateCollisionMap();
        for (int i = 0; i < characters.size(); i++) {
            characters.get(i).update(delta);
        }
    }

    /**
     * The CollisionMap allows characters to know if their path is blocked by a player or a blocked tile.
     */
    private void updateCollisionMap() {
        for (int i = 0; i < collisionMap.length; i++) {
            for (int j = 0; j < collisionMap[i].length; j++) {
                collisionMap[i][j] = false;
            }
        }
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(0);
        for (int x = 0; x < mapWidth; x++) {
            for (int y = mapHeight - 1; y >= 0; y--) {
                collisionMap[x][y] = layer.getCell(x, y).getTile().getProperties().containsKey("blocked");
            }
        }
        for (int i = 0; i < characters.size(); i++) {
            collisionMap[(int) characters.get(i).getCurrentTile().x][(int) characters.get(i).getCurrentTile().y] = true;
            collisionMap[(int) characters.get(i).targetTile.x][(int) characters.get(i).targetTile.y] = true;
        }
        collisionMap[(int) player.targetTile.x][(int) player.targetTile.y] = true;
        collisionMap[(int) player.getCurrentTile().x][(int) player.getCurrentTile().y] = true;
    }

    /**
     * @return Returns null if no character exists at x, y.
     */
    public Character getCharacterAt(float tileX, float tileY){
        if (characters != null) {
            for (Character c : characters) {
                if (c.getCurrentTile().equals(new Vector2(tileX, tileY)))
                    return c;
            }
        }
        return null;
    }


}
