package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.GameWorld;

/**
 * Displays information about the current Game
 */
public class AboutCmnd extends Command {
    private GameWorld gw;

    /**
     * Creates new About command object
     * @param g reference to GameWorld object
     */
    public AboutCmnd(GameWorld g) {
        super("About");
        gw=g;
    }

    /**
     * Shows dialog box that contains the author's name, class, and project's version number
     * upon correct command call
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        Dialog.show("About", "Zach White\n CSC 133\n V0.03", "Ok", null);
    }
}
