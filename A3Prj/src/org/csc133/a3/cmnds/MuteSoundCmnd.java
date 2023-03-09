package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.BGSound;
import org.csc133.a3.GameWorld;

/**
 * Mutes the background sound of the game
 */
public class MuteSoundCmnd extends Command {
    private GameWorld gw;
    private BGSound bgSound;
    private Boolean mute = true;

    /**
     * Creates new Mute sound command object
     * @param g reference to GameWorld object
     * @param b reference to BGSound object
     */
    public MuteSoundCmnd(GameWorld g, BGSound b) {
        super("Mute Sound");
        gw=g;
        bgSound = b;
    }

    /**
     * Switches between muted and unmuted depending on last choice
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        if(mute) {
            bgSound.pause();
            mute = false;
        }
        else{
            bgSound.play();
            mute = true;
        }
    }
}
