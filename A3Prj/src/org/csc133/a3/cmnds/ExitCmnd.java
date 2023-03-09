package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

/**
 * Exits the current game and closes the program
 */
public class ExitCmnd extends Command {
    private GameWorld gw;

    /**
     * Creates new Exit command object
     * @param g reference to the GameWorld
     */
    public ExitCmnd(GameWorld g) {
        super("Exit");
        gw=g;
    }

    /**
     * Prompts user to confirm they want to quit
     * If okay is chosen, program and game close
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        Boolean bOk = Dialog.show("Confirm?", "Are you sure?", "Ok", "Cancel");
        if(bOk)
            Display.getInstance().exitApplication();
    }
}
