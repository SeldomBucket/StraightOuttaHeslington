package com.mygdx.game.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Game;
import com.mygdx.game.Player;

/**
 * Component for rendering the X and Y coordinates of the player (not used in production version, only for debugging)
 * NEW CLASS
 */
public class UIDebugCoords extends UIComponent {

    UIMessageBox messageBox;
    String messagePrepend="Coordinates: ";
    Player player;

    public UIDebugCoords(Player inputPlayer){
        super(0,0,0,0);
        this.width=300;
        this.height=20;
        this.x= 0;
        this.y= Gdx.graphics.getHeight()/2;
        this.player = inputPlayer;
        this.messageBox = new UIMessageBox(messagePrepend+Float.toString(player.getCurrentTile().x)+", "+Float.toString(player.getCurrentTile().y),this.x,this.y,this.width,this.height,10,10);
    }

    /**
     * Called once per frame to render the score.
     */
    public void render(SpriteBatch batch, NinePatch patch){
        messageBox.setMessage(messagePrepend+Float.toString(player.getCurrentTile().x)+", "+Float.toString(player.getCurrentTile().y));
        messageBox.render(batch,patch);
    }
}
