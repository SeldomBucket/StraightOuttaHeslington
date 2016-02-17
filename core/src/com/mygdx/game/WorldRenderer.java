package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.NPCs.*;
import com.mygdx.game.UI.UIRenderer;

/**
 * This class renders a GameWorld that has been passed in it's constructor.
 * This also contains a mapRenderer that renders the map,
 * and the uiRenderer that renders UI.
 */
public class WorldRenderer {

    public static final float PLAYER_CAMERA_BOUND = 8f;

    public final float SCALE = 3f;

    private GameWorld world;

    private OrthographicCamera camera;
    private SpriteBatch batch;

    private OrthogonalTiledMapRenderer mapRenderer;
    private UIRenderer uiRenderer;

    private Vector2 textureOffset = new Vector2(-2, 0);

    private float stateTime = 0;

    /**
     * @param world required to access state of the game e.g.
     *              character positions and map.
     */
    public WorldRenderer(GameWorld world) {
        this.world = world;

        Assets.load();

        batch = new SpriteBatch();
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / SCALE, Gdx.graphics.getHeight() / SCALE);
        batch.setProjectionMatrix(camera.combined);

        mapRenderer = new OrthogonalTiledMapRenderer(world.level.map);
        mapRenderer.setView(camera);

        uiRenderer = new UIRenderer(world.uiManager);
    }

    /**
     * Renders the game world and should be called once per frame.
     */
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        updateCamera();

        mapRenderer.setView(camera);
        mapRenderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        renderPlayers();
        batch.end();
        uiRenderer.render();

    }

    /**
     * Iterates through each character in the level and renders the correct sprite based
     * on position, orientation, type etc.
     * NEW PARTS - Rendering swimming for player character, rendering a different sprite for each NPC
     */
    private void renderPlayers() {
        for (int i = 0; i < world.level.characters.size(); i++) {
            Character c = world.level.characters.get(i);
            if (c instanceof Player) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                if (!world.level.waterMap[(int)c.getCurrentTile().x][(int)c.getCurrentTile().y]) {
                    //if not swimming, draw the normal walking sprites
                    batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 9, c.getAbsPos().y - textureOffset.y - 4);
                    if (c.getDirection() == Player.Direction.DOWN) {
                        batch.draw(Assets.playerWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    } else if (c.getDirection() == Player.Direction.LEFT) {
                        batch.draw(Assets.playerWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    } else if (c.getDirection() == Player.Direction.RIGHT) {
                        batch.draw(Assets.playerWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    } else {
                        batch.draw(Assets.playerWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    }
                }else if (world.level.waterMap[(int)c.getCurrentTile().x][(int)c.getCurrentTile().y]) {
                    //if swimming, draw the swimming ones
                    if (c.getDirection() == Player.Direction.DOWN) {
                        batch.draw(Assets.playerSwimAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    } else if (c.getDirection() == Player.Direction.LEFT) {
                        batch.draw(Assets.playerSwimAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    } else if (c.getDirection() == Player.Direction.RIGHT) {
                        batch.draw(Assets.playerSwimAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    } else {
                        batch.draw(Assets.playerSwimAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                                c.getAbsPos().y - textureOffset.y);
                    }
                }

            } else if (c instanceof SallyNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.SallyNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.SallyNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.SallyNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.SallyNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            } else if (c instanceof RoboNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.RoboNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.RoboNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.RoboNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.RoboNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            } else if (c instanceof SammyNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.SammyNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.SammyNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.SammyNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.SammyNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            } else if (c instanceof BreadStealerNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.BreadStealerNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.BreadStealerNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.BreadStealerNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.BreadStealerNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof DannyNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.DannyNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.DannyNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.DannyNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.DannyNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if ((c instanceof GressinghamNPC) || (c instanceof BackUpNPC)) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.GressinghamNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.GressinghamNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.GressinghamNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.GressinghamNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof JulieNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.JulieNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.JulieNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.JulieNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.JulieNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof MallardsNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.MallardNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.MallardNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.MallardNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.MallardNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof MisunderstoodNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.MisunderstoodNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.MisunderstoodNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.MisunderstoodNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.MisunderstoodNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof RandomDuckNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.RandomNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.RandomNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.RandomNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.RandomNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof RoboFriendNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.RoboNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.RoboNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.RoboNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.RoboNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof TealNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.TealNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.TealNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.TealNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.TealNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }else if (c instanceof SomeDucksNPC) {
                if (c.getState() != Character.CharacterState.TRANSITIONING) {
                    c.setStateTime(0.174f);
                }else{
                    c.setStateTime(c.getStateTime() + Gdx.graphics.getDeltaTime());
                }
                batch.draw(Assets.shadow, c.getAbsPos().x - textureOffset.x - 10, c.getAbsPos().y - textureOffset.y - 4);
                if (c.getDirection() == Player.Direction.DOWN) {
                    batch.draw(Assets.SomeDucksNPCWalkAnimation[2].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.LEFT) {
                    batch.draw(Assets.SomeDucksNPCWalkAnimation[1].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else if (c.getDirection() == Player.Direction.RIGHT) {
                    batch.draw(Assets.SomeDucksNPCWalkAnimation[3].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                } else {
                    batch.draw(Assets.SomeDucksNPCWalkAnimation[0].getKeyFrame(c.getStateTime()), c.getAbsPos().x - textureOffset.x,
                            c.getAbsPos().y - textureOffset.y);
                }
            }
        }
    }

    /**
     * Updates the position of the camera to be constrained to the player and stay within the
     * bounds of the map.
     */
    private void updateCamera() {

//      Constrain camera to player
        if ((world.level.player.getAbsPos().x + Character.CHARACTER_SIZE.x > camera.position.x + camera.viewportWidth / PLAYER_CAMERA_BOUND))
            camera.position.x = (world.level.player.getAbsPos().x + Character.CHARACTER_SIZE.x) - (camera.viewportWidth / PLAYER_CAMERA_BOUND);
        if ((world.level.player.getAbsPos().x < camera.position.x - camera.viewportWidth / PLAYER_CAMERA_BOUND))
            camera.position.x = world.level.player.getAbsPos().x + (camera.viewportWidth / PLAYER_CAMERA_BOUND);
        if ((world.level.player.getAbsPos().y + Character.CHARACTER_SIZE.y > camera.position.y + camera.viewportHeight / PLAYER_CAMERA_BOUND))
            camera.position.y = (world.level.player.getAbsPos().y + Character.CHARACTER_SIZE.y) - (camera.viewportHeight / PLAYER_CAMERA_BOUND);
        if ((world.level.player.getAbsPos().y < camera.position.y - camera.viewportHeight / PLAYER_CAMERA_BOUND))
            camera.position.y = world.level.player.getAbsPos().y + (camera.viewportHeight / PLAYER_CAMERA_BOUND);

//      Constrain camera to map
        if (camera.position.x + camera.viewportWidth / 2f > world.level.mapBounds.x)
            camera.position.x = world.level.mapBounds.x - camera.viewportWidth / 2f;
        if (camera.position.x < camera.viewportWidth / 2f)
            camera.position.x = camera.viewportWidth / 2f;
        if (camera.position.y + camera.viewportHeight / 2f > world.level.mapBounds.y)
            camera.position.y = world.level.mapBounds.y - camera.viewportHeight / 2f;
        if (camera.position.y < camera.viewportHeight / 2f)
            camera.position.y = camera.viewportHeight / 2f;

        camera.update();
    }

    public void resize(int width, int height) {
        camera.viewportWidth = width / SCALE;
        camera.viewportHeight = height / SCALE;
        camera.update();
        uiRenderer.resize(width, height);
    }

    public void dispose() {
        batch.dispose();
        uiRenderer.dispose();
        mapRenderer.dispose();
    }
}
