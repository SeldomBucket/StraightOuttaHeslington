package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.Assets;
import com.mygdx.game.InputHandler;
import com.mygdx.game.PartyManager;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is responsible for the creation of UI elements on the screen.
 * The ui renderer uses this class to call the render function of each component.
 */
public class UIManager {
    public UIPartyMenu partyMenu;
    public UIShopMenu shopMenu;
    public List<UIMessageBox> notifications;

    private List<UIComponent> uiComponents;

    public UIDialogue dialogue;
    private float notificationTimer;
    public UIFlightMenu flightMenu;
    public UIVista vista;

    public UIManager(PartyManager party) {
        notifications = new ArrayList<UIMessageBox>();
        notificationTimer = 0;
        uiComponents = new ArrayList<UIComponent>();
        partyMenu = new UIPartyMenu(40, 150, Gdx.graphics.getWidth()-80, Gdx.graphics.getHeight()-272, party);
    }

    /**
     * Opens the party menu.
     */
    public void openPartyMenu() {
        partyMenu.show();
    }

    /**
     * Called once per frame to update the party menu.
     * @return false if the party menu is closed.
     */
    public boolean updatePartyMenu(float delta) {
        return partyMenu.update(delta);
    }

    /**
     * Creates a new dialogue from an array of messages.
     */
    public void createDialogue(String[] messages) {
        dialogue = new UIDialogue(20, 20, Gdx.graphics.getWidth()/2-40, 0, messages);
    }

    /**
     * To be called if the player is in a dialogue.
     * @return false when dialogue has finished.
     */
    public boolean updateDialogue(float delta) {
        if (!dialogue.update(delta)) {
            dialogue = null;
            return false;
        }
        return true;
    }

    /**
     * Creates a new dialogue from an array of messages.
     */
    public void addVista(Texture image) {
        vista = new UIVista(image);
    }

    /**
     * To be called if the player is in a dialogue.
     * @return false when dialogue has finished.
     */
    public boolean updateVista() {
        if (!vista.update()) {
            vista = null;
            return false;
        }else{
            return true;
        }
    }

    /**
     * Adds a notification to the current list of notifications waiting to be displayed.
     */
    public void addNotification(String message) {
        notifications.add(new UIMessageBox(message, Assets.consolas22, Color.WHITE, Align.center, 20, Gdx.graphics.getHeight()-120, Gdx.graphics.getWidth()/2, 0));
    }

    /**
     * To be called once per frame to update timing of notification components.
     */
    public void updateNotification(float delta) {
        if (notifications.isEmpty()) {
            notificationTimer = 0;
        } else {
            notificationTimer += delta;
            if (notificationTimer > 4f) {
                notificationTimer = 0;
                notifications.remove(0);
            }
        }

    }

    public void createFlightMenu(boolean[] locations, String[] locationNames){
        flightMenu = new UIFlightMenu(10,10,Gdx.graphics.getWidth()-20, Gdx.graphics.getHeight()-60,locations,locationNames);
    }
    public void showFlightMenu(int location){
        flightMenu.show(location);
    }

    public int updateFlightMenu(){
        return flightMenu.update();

    }
    public void createShopMenu(boolean[] itemsToDisplay, String[] itemNames, int[] prices, int[]levelRequirements){
        shopMenu = new UIShopMenu(10,10,Gdx.graphics.getWidth()-20, Gdx.graphics.getHeight()-60,itemsToDisplay,itemNames, prices, levelRequirements);
    }
    public void showShopMenu(int highestLevel){
        shopMenu.show(highestLevel);
    }
    public int updateShopMenu(float delta){
        return shopMenu.update(delta);
    }

    public void addUIComponent(UIComponent c) {
        uiComponents.add(c);
    }

    public void removeUIComponent(UIComponent c) {
        uiComponents.remove(c);
    }

    public UIComponent getUIComponent(int i) {
        return uiComponents.get(i);
    }

    public List<UIComponent> getUIComponents() {
        return uiComponents;
    }
}
