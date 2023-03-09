package org.csc133.a3.cmnds;

import com.codename1.ui.Command;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import org.csc133.a3.AdvanceStrategy;
import org.csc133.a3.AttackStrategy;
import org.csc133.a3.GameWorld;
import org.csc133.a3.NPHelicopter;

/**
 * Allow player to choose the current strategy of the Non-player helicopters
 */
public class ChangeStratCmnd extends Command {
    private GameWorld gw;
    private NPHelicopter npHeli;

    /**
     * Creates new Change Strategy Command object
     * @param g reference to the GameWorld
     */
    public ChangeStratCmnd(GameWorld g) {
        super("Change Strategy");
        gw=g;
    }

    /**
     * Creates dialog box with two options that will change
     * the NPH strategy to chosen strategy
     * @param evt Action Event object
     */
    @Override
    public void actionPerformed(ActionEvent evt){
        for(int i=0; i<gw.getCollection().size(); i++){
            // Roll through array until find NPH and change to correct strategy
            if(gw.getCollection().get(i) instanceof NPHelicopter){
                npHeli = (NPHelicopter) gw.getCollection().get(i);

                // switch from advance to attack
                if(npHeli.getStrategy() instanceof AdvanceStrategy)
                    npHeli.setStrategy(new AttackStrategy(npHeli,gw.findPlayerHeli()));
                // switch from attack to advance
                else if(npHeli.getStrategy() instanceof AttackStrategy){
                    npHeli.setStrategy(new AdvanceStrategy(npHeli,gw.getCollection()));
                }
                npHeli.invokeStrategy();
            }
        }
    }
}
