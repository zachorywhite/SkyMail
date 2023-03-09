package org.csc133.a3;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.InputStream;

/**
 * Plays background sound for game on a loop
 */
public class BGSound implements Runnable{
    private Media m;

    /**
     * creates new background sound object
     * @param fileName name of the sound file
     */
    public BGSound(String fileName){
        try{
            InputStream is = Display.getInstance().getResourceAsStream(getClass(),"/"+fileName);
            m = MediaManager.createMedia(is, "audio/wav", this);
            m.setVolume(20);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void play() {m.play();}
    public void pause() {m.pause();}

    @Override
    public void run() {
        m.setTime(0);
        m.play();
    }
}
