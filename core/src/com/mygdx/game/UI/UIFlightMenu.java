package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.InputHandler;

/**
 * The party menu allows the user to select a location to fly to
 * NEW CLASS
 */

public class UIFlightMenu extends UIComponent {

    private boolean show;

    private boolean[] locations;
    private String[] locationNames;

    private int locationSelected;

    public UIFlightMenu(float x, float y, float width, float height, boolean[] locations, String[] locationNames) {
        super(x, y, width, height);
        show = false;
        locationSelected = 0;
        this.locations = locations;
        this.locationNames = locationNames;
    }

    /**
     * Called once per frame to render the party menu.
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {
        if (show) {
           for (int i = 0;i<4;i++){
                if (locations[i]){
                    if (i == locationSelected){
                        new UIMessageBox("\n\n"+locationNames[i], Assets.consolas22, Color.GOLD, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }else{
                        new UIMessageBox("\n\n"+locationNames[i], Assets.consolas22, Color.WHITE, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }
                }else{
                    if (i == locationSelected){
                        new UIMessageBox("\n\nnot unlocked yet", Assets.consolas22, Color.GOLD, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }else{
                        new UIMessageBox("\n\nnot unlocked yet", Assets.consolas22, Color.WHITE, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }
                }
            }
            for (int i = 0;i<4;i++){
                if (locations[i+4]) {
                    if (i + 4 == locationSelected) {
                        new UIMessageBox("\n\n"+locationNames[i + 4], Assets.consolas22, Color.GOLD, Align.center, x + (width / 2), y + ((height / 4) * i), width / 2, height / 6).render(batch, patch);
                    }else {
                        new UIMessageBox("\n\n"+locationNames[i + 4], Assets.consolas22, Color.WHITE, Align.center, x + (width / 2), y + ((height / 4) * i), width / 2, height / 6).render(batch, patch);
                    }
                }else{
                    if (i + 4 == locationSelected) {
                        new UIMessageBox("\n\nnot unlocked yet", Assets.consolas22, Color.GOLD, Align.center, x+(width/2), y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }else {
                        new UIMessageBox("\n\nnot unlocked yet", Assets.consolas22, Color.WHITE, Align.center, x+(width/2), y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }
                }
            }
        }
    }

    /**
     * Makes the UI component visible on screen.
     * @param location The location which should be selected first
     */
    public void show(int location) {
        locationSelected = location;
        show = true;
    }

    /**
     * Called once per frame to handle input logic for selecting a player and exiting the menu.
     * @return returns -1 if the dialogue box should continue to be displayed, 666 if no location was selected, and the location number if a location was selected
     */
    public int update() {
        if (InputHandler.isActJustPressed()) {
            show = false;
            return locationSelected;
        } else if (InputHandler.isEscJustPressed()) {
            show = false;
            return 666;
        }else{
            optionUpdate();
            return -1;
        }
    }

    private void optionUpdate() {
        if (InputHandler.isUpJustPressed()) {
            if (locationSelected!=3){
                locationSelected++;
            }
        } else if (InputHandler.isDownJustPressed()) {
            if (locationSelected!=4){
                locationSelected--;
            }
        } else if (InputHandler.isLeftJustPressed()) {
            if (locationSelected>3){
                locationSelected-=4;
            }
        } else if (InputHandler.isRightJustPressed()) {
            if (locationSelected<4){
                locationSelected+=4;
            }
        }
        if (locationSelected < 0) {
            locationSelected = 0;
        }
        if (locationSelected > 7) {
            locationSelected = 7;
        }
        if (locationSelected < 0) {
            locationSelected = 0;
        }
    }

}
