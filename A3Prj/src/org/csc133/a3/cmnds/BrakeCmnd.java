package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

/**
 * Decelerates the Player Helicopter in the current game
 */
public class BrakeCmnd extends Command {
    private GameWorld gw;

    /**
     * Creates new Brake Command object
     * @param g reference to current GameWorld
     */
    public BrakeCmnd(GameWorld g) {
        super("Decelerate");
        gw=g;
    }

    /**
     * Decreases speed of the player helicopter
     * by certain amounts
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        gw.playerHeliBrake();
    }
}
