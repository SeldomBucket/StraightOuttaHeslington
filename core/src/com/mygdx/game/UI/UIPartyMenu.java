package com.mygdx.game.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.*;

import java.util.ArrayList;
import java.util.List;

/**
 * The party menu allows the user to see information about each party member.
 * It contains a party member's skills and statistics.
 */
public class UIPartyMenu extends UIComponent {

    private PartyManager party;
    private boolean show;
    private equipMode mode;

    private int playerSelected, menuSelected, equipmentSelected, inventorySelected;

    private List<UIPlayer> playerList;
    private List<UIEquipment> equipList;
    private UIEquipmentInventory inventory;

    public UIPartyMenu(float x, float y, float width, float height, PartyManager party) {
        super(x, y, width, height);
        this.party = party;
        show = false;
        mode = equipMode.NOT;
        playerSelected = 0;
        menuSelected = 1;
        equipmentSelected = -1;
        playerList = new ArrayList<UIPlayer>();
        equipList = new ArrayList<UIEquipment>();
        for (int i=0;i<party.size();i++) {
            playerList.add(new UIPlayer(x,(y + height - 82)-(122*i), width/2, 82, party.getMember(i)));
        }
    }

    /**
     * Called once per frame to render the party menu.
     */
    @Override
    public void render(SpriteBatch batch, NinePatch patch) {

        if (show) {
            new UIMessageBox("", Assets.consolas22, Color.WHITE, Align.center, x, y, width, height).render(batch, patch);
            //render the player list
            for (int i=0;i<playerList.size();i++) {
                playerList.get(i).render(batch, patch);
            }
            if (menuSelected == 0) {
                new UIMessageBox("STATS", Assets.consolas22, Color.WHITE, Align.center, x+width/2, (y + height + 4), width/6, 0, 10).render(batch, patch);
                new UIMessageBox("SKILLS", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2+width/6, (y + height +4), width/6, 0, 10).render(batch, patch);
                new UIMessageBox("EQUIPMENT", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2+width/3, (y + height+4), width/6, 0, 10).render(batch, patch);
                new UIStats(x + width/2, (y + height - 446), width/2, 410, party.getMember(playerSelected)).render(batch, patch);
            }
            if (menuSelected == 1) {
                new UIMessageBox("STATS", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2, (y + height + 4), width/6, 0, 10).render(batch, patch);
                new UIMessageBox("SKILLS", Assets.consolas22, Color.WHITE, Align.center, x+width/2+width/6, (y + height +4), width/6, 0, 10).render(batch, patch);
                new UIMessageBox("EQUIPMENT", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2+width/3, (y + height+4), width/6, 0, 10).render(batch, patch);
                //populate skill list with highlighted character's skills & render
                for (int i=0;i<party.getMember(playerSelected).getSkills().size();i++) {
                    new UISkill(x + width/2, (y + height - 86)-(90*i), width/2, 50,
                            Game.skills.getSkill(party.getMember(playerSelected).getSkills().get(i))).render(batch, patch);
                }
            }
            if (menuSelected == 2) {
                new UIMessageBox("STATS", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2, (y + height + 4), width/6, 0, 10).render(batch, patch);
                new UIMessageBox("SKILLS", Assets.consolas22, Color.LIGHT_GRAY, Align.center, x+width/2+width/6, (y + height +4), width/6, 0, 10).render(batch, patch);
                new UIMessageBox("EQUIPMENT", Assets.consolas22, Color.WHITE, Align.center, x+width/2+width/3, (y + height+4), width/6, 0, 10).render(batch, patch);
                //Populate equipment list with highlighted character's equipment & render
                equipList.clear();
                for (int i=0;i<5;i++) {
                    equipList.add (new UIEquipment(x + width/2, (y+ height - 86) - (90 * i), width/2, 50,
                            Game.items.getEquipable(party.getMember(playerSelected).getCurrentEquipment().getEquipment(i))));
                    if (i == equipmentSelected) {
                        equipList.get(i).selected = true;
                    }
                    equipList.get(i).render(batch, patch);
                }
                //overlay inventory UI elements over player list if user is in this part of the menu
                if (mode == equipMode.EQUIP) {
                    inventory = new UIEquipmentInventory(x, y, width/2, height, party.getEquipables());
                    inventory.selected = inventorySelected;
                    inventory.render(batch, patch);
                }
            }

        }
    }

    /**
     * Makes the UI component visible on screen.
     */
    public void show() {
        playerSelected = 0;
        menuSelected = 1;
        show = true;
    }

    /**
     * Called once per frame to handle input logic for selecting a player and exiting the menu.
     * @return returns true if the dialogue box should continue to be displayed.
     */
    public boolean update() {
        if ((InputHandler.isEscJustPressed()) && (mode == equipMode.NOT)) {
            show = false;
            return false;
        } else {
            playerList.get(playerSelected).selected = false;
            optionUpdate();
            playerList.get(playerSelected).selected = true;
            return true;
        }

    }

    private void optionUpdate() {
        //Player is not currently in equipment sub-menu
        if (mode == equipMode.NOT) {
            if (InputHandler.isUpJustPressed()) {
                playerSelected--;
            } else if (InputHandler.isDownJustPressed()) {
                playerSelected++;
            }
            if (InputHandler.isLeftJustPressed()) {
                menuSelected--;
            } else if (InputHandler.isRightJustPressed()) {
                menuSelected++;
            }
            if ((InputHandler.isActJustPressed()) && (menuSelected == 2)) {
                mode = equipMode.SELECT;
                equipmentSelected = 0;
            }
        }
        //player is selecting an equipment from the currently selected character
        else if (mode == equipMode.SELECT) {
            if (InputHandler.isUpJustPressed()) {
                equipmentSelected--;
            } else if (InputHandler.isDownJustPressed()) {
                equipmentSelected++;
            }
            if ((InputHandler.isActJustPressed()) && (menuSelected == 2)) {
                mode = equipMode.EQUIP;
            }
            if ((InputHandler.isEscJustPressed()) && (menuSelected == 2)) {
                mode = equipMode.NOT;
                equipmentSelected = -1;
            }
        }
        //player is choosing a replacement equipment for the currently selected equipment
        else if (mode == equipMode.EQUIP) {
            if (InputHandler.isUpJustPressed()) {
                inventorySelected--;
            } else if (InputHandler.isDownJustPressed()) {
                inventorySelected++;
            }
            if ((InputHandler.isActJustPressed())) {
                //Unequip item
                if (inventorySelected == party.getEquipables().size()) {
                    party.getMember(playerSelected).getCurrentEquipment().unequip(equipmentSelected);
                }
                //Equip item
                else {
                    party.getMember(playerSelected).getCurrentEquipment().equip(inventorySelected);
                }
            }
            if ((InputHandler.isEscJustPressed()) && (menuSelected == 2)) {
                mode = equipMode.SELECT;
            }
        }
        //Guards to stop menu pointers from running out of bounds
        if (menuSelected < 0) {
            menuSelected = 0;
        }
        if (menuSelected > 2) {
            menuSelected = 2;
        }
        if (playerSelected < 0) {
            playerSelected = 0;
        }
        if (playerSelected >= party.size()) {
            playerSelected = party.size() - 1;
        }
        if (equipmentSelected < 0 && mode != equipMode.NOT) {
            equipmentSelected = 0;
        }
        if (equipmentSelected > 4) {
            equipmentSelected = 4;
        }
        if (inventorySelected < 0) {
            inventorySelected = 0;
        }
        if (inventorySelected > party.getEquipables().size()) {
            inventorySelected = party.getEquipables().size();
        }
    }
    /**
     * NOT- player is not equipping
     * SELECT- player is selecting a currently equipped item
     * EQUIP- player is choosing a replacement item
     */
    public enum equipMode {
        NOT, SELECT, EQUIP
    }
}
