package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

/**
 * Displays the keybindings of the Game that the player can press
 */
public class HelpCmnd extends Command {
    private GameWorld gw;

    /**
     * Creates new Help Command object
     * @param g reference to current GameWorld
     */
    public HelpCmnd(GameWorld g) {
        super("Help");
        gw=g;
    }

    /**
     * Shows dialog box containing a list of the buttons that are
     * available to press by the user
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        Dialog.show("Help", "Commands:\nAccelerate - a\n Decelerate - b\n" +
                "Turn Left - l\n Turn Right - r", "Ok", null);
    }
}
