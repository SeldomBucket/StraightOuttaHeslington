package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.InputHandler;

/**
 * The party menu allows the user to see information about each party member.
 * It contains a party member's skills and statistics.
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
                        new UIMessageBox(locationNames[i], Assets.consolas22, Color.GOLD, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }else{
                        new UIMessageBox(locationNames[i], Assets.consolas22, Color.WHITE, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }
                }else{
                    if (i == locationSelected){
                        new UIMessageBox("not unlocked yet", Assets.consolas22, Color.GOLD, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }else{
                        new UIMessageBox("not unlocked yet", Assets.consolas22, Color.WHITE, Align.center, x, y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }
                }
            }
            for (int i = 0;i<4;i++){
                if (locations[i+4]) {
                    if (i + 4 == locationSelected) {
                        new UIMessageBox(locationNames[i + 4], Assets.consolas22, Color.GOLD, Align.center, x + (width / 2), y + ((height / 4) * i), width / 2, height / 6).render(batch, patch);
                    }else {
                        new UIMessageBox(locationNames[i + 4], Assets.consolas22, Color.WHITE, Align.center, x + (width / 2), y + ((height / 4) * i), width / 2, height / 6).render(batch, patch);
                    }
                }else{
                    if (i + 4 == locationSelected) {
                        new UIMessageBox("not unlocked yet", Assets.consolas22, Color.GOLD, Align.center, x+(width/2), y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }else {
                        new UIMessageBox("not unlocked yet", Assets.consolas22, Color.WHITE, Align.center, x+(width/2), y+((height/4)*i), width/2, height/6).render(batch, patch);
                    }
                }
            }

            /*new UIMessageBox("STATS", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2, (y + height + 4), width/6, 0, 10).render(batch, patch);
            new UIMessageBox("SKILLS", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2+width/6, (y + height +4), width/6, 0, 10).render(batch, patch);
            new UIMessageBox("EQUIPMENT", Assets.consolas22, Color.WHITE, Align.center, x+width/2+width/3, (y + height+4), width/6, 0, 10).render(batch, patch);*/
        }
    }

    /**
     * Makes the UI component visible on screen.
     */
    public void show(int location) {
        locationSelected = location;
        show = true;
    }

    /**
     * Called once per frame to handle input logic for selecting a player and exiting the menu.
     * @return returns true if the dialogue box should continue to be displayed.
     */
    public int update(float delta) {
        if (InputHandler.isActJustPressed()) {
            show = false;
            return locationSelected;
        } else {
            optionUpdate();
            return -1;
        }

    }

    private void optionUpdate() {
        if (InputHandler.isUpJustPressed()) {
            locationSelected++;
        } else if (InputHandler.isDownJustPressed()) {
            locationSelected--;
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
