package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.mygdx.game.NPCs.MisunderstoodNPC;

/**
 * Assets is a static class containing all assets that are used in rendering the game.
 * This class handles loading and disposing of asset resources, done so by calling the
 * respective methods.
 */
public class Assets {
//  BATTLE ASSETS
    public static Texture playerTexture;
    public static Texture deadPlayerTexture;
    public static Texture[][] battleSprites;

    public static Texture battleTurnPointer;
    public static Texture targetingPointer;

//  MAP ASSETS
    public static Texture[] battleBGs = new Texture[4];

//  UI ASSETS
    public static BitmapFont consolas22;
    public static BitmapFont consolas16;
    public static NinePatch patch;
    public static TextureAtlas atlas;
    public static Texture dialoguePointer;

    private static final int PLAYER_WALKSHEET_COLS = 4;
    private static final int PLAYER_WALKSHEET_ROWS = 2;
    private static final int PLAYER_SWIMSHEET_COLS = 4;
    private static final int PLAYER_SWIMSHEET_ROWS = 2;
    private static final int NPC_WALKSHEET_COLS = 4;
    private static final int NPC_WALKSHEET_ROWS = 2;

    //  CHARACTER TEXTURE SHEETS
    public static Animation[] playerWalkAnimation;
    public static Texture playerWalkSheet;

    public static Animation[] playerSwimAnimation;
    public static Texture playerSwimSheet;

    public static Animation[] SallyNPCWalkAnimation;
    public static Texture SallyNPCWalkSheet;

    public static Animation[] RoboNPCWalkAnimation;
    public static Texture RoboNPCWalkSheet;

    public static Animation[] MallardNPCWalkAnimation;
    public static Texture MallardNPCWalkSheet;

    public static Animation[] BreadStealerNPCWalkAnimation;
    public static Texture BreadStealerNPCWalkSheet;

    public static Animation[] DannyNPCWalkAnimation;
    public static Texture DannyNPCWalkSheet;

    public static Animation[] GressinghamNPCWalkAnimation;
    public static Texture GressinghamNPCWalkSheet;

    public static Animation[] JulieNPCWalkAnimation;
    public static Texture JulieNPCWalkSheet;

    public static Animation[] MisunderstoodNPCWalkAnimation;
    public static Texture MisunderstoodNPCWalkSheet;

    public static Animation[] RandomNPCWalkAnimation;
    public static Texture RandomNPCWalkSheet;

    public static Animation[] TealNPCWalkAnimation;
    public static Texture TealNPCWalkSheet;

    public static Animation[] SammyNPCWalkAnimation;
    public static Texture SammyNPCWalkSheet;

    public static Animation[] SomeDucksNPCWalkAnimation;
    public static Texture SomeDucksNPCWalkSheet;

    public static Texture shadow;

    public static Texture title;


    //SOUNDS
    public static Sound sfx_menuMove;
    public static Sound sfx_menuSelect;
    public static Sound sfx_menuBack;
    public static Sound sfx_hitNoise;
    public static Sound sfx_healNoise;

    public static Sound sfx_battleStart;
    public static Sound sfx_battleWin;
    public static Sound sfx_battleLose;

    public static Music battleMusic;
    public static Music worldMusic;
    /**
     * Loads the assets from the asset folder and initialises animation frames.
     */
    public static void load() {

        title = new Texture("Start Screen.png");
        //  BATTLE ASSETS

        playerTexture = new Texture("CharacterSpriteSheets/Duck.png");
        deadPlayerTexture = new Texture("CharacterSpriteSheets/DuckDead.png");
        battleSprites = new Texture[13][2];
        battleSprites[0][0] = new Texture("CharacterSpriteSheets/Duck.png");
        battleSprites[0][1] = new Texture("CharacterSpriteSheets/DuckDead.png");
        battleSprites[1][0] = new Texture("CharacterSpriteSheets/Roboduck.png");
        battleSprites[1][1] = new Texture("CharacterSpriteSheets/RoboDuckDead.png");
        battleSprites[2][0] = new Texture("CharacterSpriteSheets/Ooze.png");
        battleSprites[2][1] = new Texture("CharacterSpriteSheets/OozeDead.png");
        battleSprites[3][0] = new Texture("CharacterSpriteSheets/RadiatedDuck.png");
        battleSprites[3][1] = new Texture("CharacterSpriteSheets/RadiatedDuckDead.png");
        battleSprites[4][0] = new Texture("CharacterSpriteSheets/ScarDuck.png");
        battleSprites[4][1] = new Texture("CharacterSpriteSheets/ScarDuckDead.png");
        battleSprites[5][0] = new Texture("CharacterSpriteSheets/UndeadDuck.png");
        battleSprites[5][1] = new Texture("CharacterSpriteSheets/UndeadDuckDead.png");
        battleSprites[6][0] = new Texture("CharacterSpriteSheets/Mallard.png");
        battleSprites[6][1] = new Texture("CharacterSpriteSheets/MallardDead.png");
        battleSprites[7][0] = new Texture("CharacterSpriteSheets/NPC2.png");
        battleSprites[7][1] = new Texture("CharacterSpriteSheets/NPC2dead.png");
        battleSprites[8][0] = new Texture("CharacterSpriteSheets/Gressingham.png");
        battleSprites[8][1] = new Texture("CharacterSpriteSheets/GressinghamDead.png");
        battleSprites[9][0] = new Texture("CharacterSpriteSheets/NPC3.png");
        battleSprites[9][1] = new Texture("CharacterSpriteSheets/NPC3dead.png");
        battleSprites[10][0] = new Texture("CharacterSpriteSheets/NPC1.png");
        battleSprites[10][1] = new Texture("CharacterSpriteSheets/NPC1dead.png");
        battleSprites[11][0] = new Texture("CharacterSpriteSheets/Teal.png");
        battleSprites[11][1] = new Texture("CharacterSpriteSheets/TealDead.png");
        battleSprites[12][0] = new Texture("CharacterSpriteSheets/NPC7.png");
        battleSprites[12][1] = new Texture("CharacterSpriteSheets/NPC7Dead.png");

        battleTurnPointer = new Texture("Pointers/turnPointer.png");
        targetingPointer = new Texture("Pointers/targetingPointer.png");

        //  MAP ASSETS
        battleBGs[0] = new Texture("backgrounds/CS_centrefixed.png");
        battleBGs[1] = new Texture("backgrounds/LM_path.png");
        battleBGs[2] = new Texture("backgrounds/RCH_lake.png");
        battleBGs[3] = new Texture("backgrounds/Background_1.png");


        //  UI ASSETS
        consolas22 = new BitmapFont(Gdx.files.internal("fonts/consolas22.fnt"));
        consolas16 = new BitmapFont(Gdx.files.internal("fonts/consolas16.fnt"));
        atlas = new TextureAtlas(Gdx.files.internal("packedimages/pack.atlas"));
        patch = atlas.createPatch("knob2");
        dialoguePointer = new Texture("Pointers/dialoguePointer.png");

        sfx_menuMove = Gdx.audio.newSound(Gdx.files.internal("sound_effects/MenuMove.wav"));
        sfx_menuSelect = Gdx.audio.newSound(Gdx.files.internal("sound_effects/MenuSelect.wav"));
        sfx_menuBack = Gdx.audio.newSound(Gdx.files.internal("sound_effects/MenuBack.wav"));
        sfx_hitNoise = Gdx.audio.newSound(Gdx.files.internal("sound_effects/Damage.wav"));
        sfx_healNoise = Gdx.audio.newSound(Gdx.files.internal("sound_effects/Heal.wav"));

        sfx_battleStart  = Gdx.audio.newSound(Gdx.files.internal("sound_effects/EnterBattle.wav"));
        sfx_battleWin  = Gdx.audio.newSound(Gdx.files.internal("sound_effects/WinBattle.wav"));
        sfx_battleLose  = Gdx.audio.newSound(Gdx.files.internal("sound_effects/LoseBattle.wav"));

        battleMusic = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/BattleTheme.ogg"));
        worldMusic = Gdx.audio.newMusic(Gdx.files.internal("sound_effects/WorldTheme.ogg"));

        battleMusic.setLooping(true);
        worldMusic.setLooping(true);



        //  CHARACTER TEXTURE SHEETS
        shadow = new Texture("CharacterSpriteSheets/shadow.png");

        playerWalkSheet = new Texture("CharacterSpriteSheets/DuckAnimationFrames.png");
        TextureRegion[][] tmp = TextureRegion.split(playerWalkSheet, playerWalkSheet.getWidth() / PLAYER_WALKSHEET_COLS, playerWalkSheet.getHeight() / PLAYER_WALKSHEET_ROWS);
        TextureRegion[][] walkFrameDirections = new TextureRegion[PLAYER_WALKSHEET_COLS][PLAYER_WALKSHEET_ROWS];
        int index = 0;
        for (int i = 0; i < PLAYER_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < PLAYER_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        playerWalkAnimation = new Animation[PLAYER_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            playerWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            playerWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        playerSwimSheet = new Texture("CharacterSpriteSheets/DuckSwimAnimationFrames.png");
        TextureRegion tmp2[][] = TextureRegion.split(playerSwimSheet, playerSwimSheet.getWidth() / PLAYER_SWIMSHEET_COLS, playerSwimSheet.getHeight() / PLAYER_SWIMSHEET_ROWS);
        TextureRegion[][] swimFrameDirections = new TextureRegion[PLAYER_SWIMSHEET_COLS][PLAYER_SWIMSHEET_ROWS];
        index = 0;
        for (int i = 0; i < PLAYER_SWIMSHEET_ROWS; i++) {
            for (int j = 0; j < PLAYER_SWIMSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    swimFrameDirections[index][j % 2] = tmp2[i][j];
                } else {
                    swimFrameDirections[index++][j % 2] = tmp2[i][j];
                }
            }
        }
        playerSwimAnimation = new Animation[PLAYER_SWIMSHEET_COLS];
        for (int x = 0; x < swimFrameDirections.length;x++) {
            playerSwimAnimation[x] = new Animation(0.175f, swimFrameDirections[x]);
            playerSwimAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        SallyNPCWalkSheet = new Texture("CharacterSpriteSheets/EvilDuckAnimationFrames.png");
        tmp = TextureRegion.split(SallyNPCWalkSheet, SallyNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, SallyNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        SallyNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            SallyNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            SallyNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        RoboNPCWalkSheet = new Texture("CharacterSpriteSheets/RoboDuckAnimationFrames.png");
        tmp = TextureRegion.split(RoboNPCWalkSheet, RoboNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, RoboNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        RoboNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            RoboNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            RoboNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        MallardNPCWalkSheet = new Texture("CharacterSpriteSheets/MallardAnimationFrames.png");
        tmp = TextureRegion.split(MallardNPCWalkSheet, MallardNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, MallardNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        MallardNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            MallardNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            MallardNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        BreadStealerNPCWalkSheet = new Texture("CharacterSpriteSheets/NPC2AnimationFrames.png");
        tmp = TextureRegion.split(BreadStealerNPCWalkSheet, BreadStealerNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, BreadStealerNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        BreadStealerNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            BreadStealerNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            BreadStealerNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        GressinghamNPCWalkSheet = new Texture("CharacterSpriteSheets/GressinghamAnimationFrames.png");
        tmp = TextureRegion.split(GressinghamNPCWalkSheet, GressinghamNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, GressinghamNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        GressinghamNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            GressinghamNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            GressinghamNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        DannyNPCWalkSheet = new Texture("CharacterSpriteSheets/NPC4AnimationFrames.png");
        tmp = TextureRegion.split(DannyNPCWalkSheet, DannyNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, DannyNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        DannyNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            DannyNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            DannyNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        JulieNPCWalkSheet = new Texture("CharacterSpriteSheets/NPC5AnimationFrames.png");
        tmp = TextureRegion.split(JulieNPCWalkSheet, JulieNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, JulieNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        JulieNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            JulieNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            JulieNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        MisunderstoodNPCWalkSheet = new Texture("CharacterSpriteSheets/NPC3AnimationFrames.png");
        tmp = TextureRegion.split(MisunderstoodNPCWalkSheet, MisunderstoodNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, MisunderstoodNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        MisunderstoodNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            MisunderstoodNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            MisunderstoodNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        RandomNPCWalkSheet = new Texture("CharacterSpriteSheets/NPC1AnimationFrames.png");
        tmp = TextureRegion.split(RandomNPCWalkSheet, RandomNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, RandomNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        RandomNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            RandomNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            RandomNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        TealNPCWalkSheet = new Texture("CharacterSpriteSheets/TealAnimationFrames.png");
        tmp = TextureRegion.split(TealNPCWalkSheet, TealNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, TealNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        TealNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            TealNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            TealNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        SammyNPCWalkSheet = new Texture("CharacterSpriteSheets/NPC6AnimationFrames.png");
        tmp = TextureRegion.split(SammyNPCWalkSheet, SammyNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, SammyNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        SammyNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            SammyNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            SammyNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

        SomeDucksNPCWalkSheet = new Texture("CharacterSpriteSheets/NPC7AnimationFrames.png");
        tmp = TextureRegion.split(SomeDucksNPCWalkSheet, SomeDucksNPCWalkSheet.getWidth() / NPC_WALKSHEET_COLS, SomeDucksNPCWalkSheet.getHeight() / NPC_WALKSHEET_ROWS);
        walkFrameDirections = new TextureRegion[NPC_WALKSHEET_COLS][NPC_WALKSHEET_ROWS];
        index = 0;
        for (int i = 0; i < NPC_WALKSHEET_ROWS; i++) {
            for (int j = 0; j < NPC_WALKSHEET_COLS; j++) {
                if (j % 2 == 0) {
                    walkFrameDirections[index][j % 2] = tmp[i][j];
                } else {
                    walkFrameDirections[index++][j % 2] = tmp[i][j];
                }
            }
        }
        SomeDucksNPCWalkAnimation = new Animation[NPC_WALKSHEET_COLS];
        for (int x = 0; x < walkFrameDirections.length;x++) {
            SomeDucksNPCWalkAnimation[x] = new Animation(0.175f, walkFrameDirections[x]);
            SomeDucksNPCWalkAnimation[x].setPlayMode(Animation.PlayMode.LOOP);
        }

    }

    /**
     * Disposes of assets when not in use.
     */
    public static void dispose() {
        playerTexture.dispose();
        playerTexture.dispose();
        battleTurnPointer.dispose();
        consolas22.dispose();
        atlas.dispose();

    }
}
