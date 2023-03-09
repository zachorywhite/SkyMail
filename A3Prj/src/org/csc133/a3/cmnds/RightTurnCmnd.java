package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

/**
 * Turns the Player Helicopter to the right
 */
public class RightTurnCmnd extends Command {
    private GameWorld gw;

    /**
     * Creates new Right Turn Command object
     * @param g reference to the GameWorld
     */
    public RightTurnCmnd(GameWorld g) {
        super("Turn Right");
        gw=g;
    }

    /**
     * Turns current player helicopter in the game world
     * to the right small amount upon command call
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        gw.turnPlayerHeliRight();
    }
}
