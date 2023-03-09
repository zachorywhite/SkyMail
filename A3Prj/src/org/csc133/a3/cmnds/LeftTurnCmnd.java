package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

/**
 * Turns the Player Helicopter to the left
 */
public class LeftTurnCmnd extends Command {
    private GameWorld gw;

    /**
     * Creates new Left Turn command object
     * @param g reference to the GameWorld
     */
    public LeftTurnCmnd(GameWorld g) {
        super("Turn Left");
        gw=g;
    }

    /**
     * Turns current player helicopter in the game world
     * to the left small amount upon command call
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        gw.turnPlayerHeliLeft();
    }
}
