package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Second Segment Display of the elapsed time of the Game
 */
public class GameClockComponent extends Component {
    private long startTime = System.currentTimeMillis();
    private long currentTime;
    private long elapsedTime;
    private boolean paused = false;

    private Image[] digitImages = new Image[10];
    private Image[] dotDigitImages = new Image[10];
    private Image colonImage;

    private int tenMinLedColor;
    private int ledColor;
    private static int HM_COLON_IDX = 2;

    static final float scaleFactor = (float) 0.6;
    private static final int numDigitsShowing = 6;

    private Image[] clockDigits = new Image[numDigitsShowing];

    /**
     * Create new Clock Component object
     */
    public GameClockComponent(){
        try {
            digitImages[0] = Image.createImage("/LED_digit_0.png");
            digitImages[1] = Image.createImage("/LED_digit_1.png");
            digitImages[2] = Image.createImage("/LED_digit_2.png");
            digitImages[3] = Image.createImage("/LED_digit_3.png");
            digitImages[4] = Image.createImage("/LED_digit_4.png");
            digitImages[5] = Image.createImage("/LED_digit_5.png");
            digitImages[6] = Image.createImage("/LED_digit_6.png");
            digitImages[7] = Image.createImage("/LED_digit_7.png");
            digitImages[8] = Image.createImage("/LED_digit_8.png");
            digitImages[9] = Image.createImage("/LED_digit_9.png");

            dotDigitImages[0] = Image.createImage("/LED_digit_0_with_dot.png");
            dotDigitImages[1] = Image.createImage("/LED_digit_1_with_dot.png");
            dotDigitImages[2] = Image.createImage("/LED_digit_2_with_dot.png");
            dotDigitImages[3] = Image.createImage("/LED_digit_3_with_dot.png");
            dotDigitImages[4] = Image.createImage("/LED_digit_4_with_dot.png");
            dotDigitImages[5] = Image.createImage("/LED_digit_5_with_dot.png");
            dotDigitImages[6] = Image.createImage("/LED_digit_6_with_dot.png");
            dotDigitImages[7] = Image.createImage("/LED_digit_7_with_dot.png");
            dotDigitImages[8] = Image.createImage("/LED_digit_8_with_dot.png");
            dotDigitImages[9] = Image.createImage("/LED_digit_9_with_dot.png");

            colonImage = Image.createImage("/LED_colon.png");
        }
        catch (IOException e) {e.printStackTrace();}

        for(int i =0; i<numDigitsShowing; i++)
            clockDigits[i] = digitImages[0];

        clockDigits[HM_COLON_IDX] = colonImage;
        ledColor = ColorUtil.CYAN;
        tenMinLedColor = ColorUtil.rgb(255,0,0);
    }

    public void setLedColor(int ledColor){ this.ledColor = ledColor; }

    public void start(){ getComponentForm().registerAnimated(this); }

    public void stop(){ getComponentForm().deregisterAnimated(this); }

    public void laidOut(){ this.start(); }

    protected Dimension calcPreferredSize(){
        return new Dimension(colonImage.getWidth()*numDigitsShowing,colonImage.getHeight());
    }

    /**
     * Paints the Game Clock on the screen
     * @param g graphics of the component
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = clockDigits[0].getWidth();
        int digitHeight = clockDigits[0].getHeight();

        int displayDigitWidth = (int)(scaleFactor*digitWidth);
        int displayDigitHeight = (int)(scaleFactor*digitHeight);
        int displayClockWidth = displayDigitWidth*numDigitsShowing;

        int displayX = getX() + (getWidth()-displayClockWidth)/2;
        int displayY = getY() + (getHeight()-displayDigitHeight)/2;

        g.setColor(ledColor);
        g.fillRect(displayX + COLOR_PAD, displayY + COLOR_PAD,
                (displayClockWidth - displayDigitWidth)-COLOR_PAD*2, displayDigitHeight-COLOR_PAD*2);

        g.setColor(ColorUtil.BLUE);
        g.fillRect((displayX+5*displayDigitWidth) + COLOR_PAD, displayY + COLOR_PAD,
                displayDigitWidth-COLOR_PAD*2, displayDigitHeight-COLOR_PAD*2);

        for (int digitIndex = 0; digitIndex < numDigitsShowing; digitIndex++)
            g.drawImage(clockDigits[digitIndex], displayX + digitIndex * displayDigitWidth,
                    displayY, displayDigitWidth, displayDigitHeight);
    }

    /**
     * Chooses correct digits to display based on the elapsed time of the game
     */
    private void setTime(){
        int minute = (int) (TimeUnit.MILLISECONDS.toMinutes(elapsedTime)%60);
        int second = (int) (TimeUnit.MILLISECONDS.toSeconds(elapsedTime)%60);
        int millis = (int) (TimeUnit.MILLISECONDS.toMillis(elapsedTime)/100);

        // changes clock digits to red after ten minutes
        if((minute%10) > 9)
            setLedColor(tenMinLedColor);

        clockDigits[0] = digitImages[minute/10];
        clockDigits[1] = digitImages[minute%10];
        clockDigits[3] = digitImages[second/10];
        clockDigits[4] = dotDigitImages[second%10];
        clockDigits[5] = digitImages[millis%10];
    }

    public boolean animate(){
        elapsedTime = System.currentTimeMillis()-startTime;
        if(paused) {
            return false;
        }
        setTime();
        return true;
    }

    public void resetElapsedTime(){elapsedTime = 0;}

    public long getElapsedTime(){return elapsedTime;}

    public void startElapsedTime(){
        paused = false;
        elapsedTime = currentTime;
    }

    public void stopElapsedTime(){
        paused = true;
        currentTime = elapsedTime;
    }
}
