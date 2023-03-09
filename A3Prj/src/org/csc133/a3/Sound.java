package org.csc133.a3;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.InputStream;

/**
 * Makes sounds to be usable for playing the game
 */
public class Sound {
    private Media m;

    /**
     * Creates new sound object
     * @param fileName name of sound file
     */
    public Sound(String fileName){
        try{
            InputStream is = Display.getInstance().getResourceAsStream(getClass(),"/"+fileName);

            m = MediaManager.createMedia(is,  "audio/wav");
            m.setVolume(20);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play() {
        m.setTime(0);
        m.play();
    }
}
