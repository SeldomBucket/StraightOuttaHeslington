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
    public boolean updatePartyMenu() {
        return partyMenu.update();
    }

    /**
     * Creates a new dialogue from an array of messages.
     * @param messages The array of messages to be displayed. Each element is displayed in a new dialogue box
     */
    public void createDialogue(String[] messages) {
        dialogue = new UIDialogue(20, 20, Gdx.graphics.getWidth()/2-40, 0, messages);
    }

    /**
     * To be called if the player is in a dialogue.
     * @param delta time elapsed since last frame
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
     * @param image the image to be displayed
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
     * @param message The message to be displayed by the notification
     */
    public void addNotification(String message) {
        notifications.add(new UIMessageBox(message, Assets.consolas22, Color.WHITE, Align.center, 20, Gdx.graphics.getHeight()-120, Gdx.graphics.getWidth()/2, 0));
    }

    /**
     * To be called once per frame to update timing of notification components.
     * @param delta Time elapsed since last frame
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

    /**
     * Initialises the flight menu
     * @param locations the array of locations - false if it has not been visited before, and true if it has
     * @param locationNames the array of location names - maps directly to the boolean array
     */
    public void createFlightMenu(boolean[] locations, String[] locationNames){
        flightMenu = new UIFlightMenu(10,10,Gdx.graphics.getWidth()-20, Gdx.graphics.getHeight()-60,locations,locationNames);
    }

    /**
     * displays the flight menu
     * @param location The location which should be selected first
     */
    public void showFlightMenu(int location){
        flightMenu.show(location);
    }

    /**
     * updates flight menu
     * @return returns -1 if the dialogue box should continue to be displayed, 666 if no location was selected, and the location number if a location was selected
     */
    public int updateFlightMenu(){
        return flightMenu.update();

    }

    /**
     * Initialises the shop menu
     * @param itemNames The names of the items - should map to both the prices and the level requirements
     * @param prices The price in points of the items - should map to both the names and the level requirements
     * @param levelRequirements The level requirements of the items - should map to both the prices and the names
     */
    public void createShopMenu(String[] itemNames, int[] prices, int[]levelRequirements){
        shopMenu = new UIShopMenu(10,10,Gdx.graphics.getWidth()-20, Gdx.graphics.getHeight()-60,itemNames, prices, levelRequirements);
    }

    /**
     * Displays shop menu
     * @param highestLevel The level of the highest level member of the party (used for level requirements for items)
     */
    public void showShopMenu(int highestLevel){
        shopMenu.show(highestLevel);
    }

    /**
     * Updates the shop menu
     * @return Returns -1 if the menu is not to be closed, 974 if no item was selected, or the index of the item selected if one was selected
     */
    public int updateShopMenu(){
        return shopMenu.update();
    }

    /**
     * Adds a UIComponent to be rendered
     * @param c UIComponent to be added to the list of components to be rendered
     */
    public void addUIComponent(UIComponent c) {
        uiComponents.add(c);
    }

    /**
     * Removes a UIComponent so it is not rendered
     * @param c UIComponent to be removed from the list of components to be rendered
     */
    public void removeUIComponent(UIComponent c) {
        uiComponents.remove(c);
    }

    /**
     * Returns a specified UIComponent
     * @param i The index of the UIComponent to be returned
     * @return The UIComponent at i
     */
    public UIComponent getUIComponent(int i) {
        return uiComponents.get(i);
    }

    /**
     * Returns the list of all UIComponents
     * @return The list of all UIComponents
     */
    public List<UIComponent> getUIComponents() {
        return uiComponents;
    }
}
