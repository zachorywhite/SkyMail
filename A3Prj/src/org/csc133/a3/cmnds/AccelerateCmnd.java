package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

/**
 * Accelerates the Player Helicopter in the current Game
 */
public class AccelerateCmnd extends Command {

    private GameWorld gw;

    /**
     * Creates an Accelerate Command object
     * @param g reference to GameWorld object
     */
    public AccelerateCmnd(GameWorld g) {
        super("Accelerate");
        gw=g;
    }

    /**
     * Increases the speed of the player's helicopter every command call
     * by certain amount
     * @param evt Action Event Object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        gw.playerHeliAccelerate();
    }
}
