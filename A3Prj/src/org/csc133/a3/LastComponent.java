package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.geom.Dimension;

import java.io.IOException;

/**
 * Seven Segment Display of the current last SkyScraper reached of the Player Helicopter
 */
public class LastComponent extends Component {
    private GameWorld gw;

    private Image[] digitImages = new Image[10];
    private int ledColor;
    private static final int numDigitsShowing = 1;
    private Image[] lastDigits = new Image[numDigitsShowing];

    static final float scaleFactor = (float) 0.6;

    /**
     * Creates new Last Component object
     * @param g reference to the current GameWorld
     */
    public LastComponent(GameWorld g){
        gw = g;
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
        }
        catch (IOException e) {e.printStackTrace();}

        for(int i =0; i<numDigitsShowing; i++)
            lastDigits[i] = digitImages[0];
        ledColor = ColorUtil.GREEN;
    }

    public void setLedColor(int ledColor){
        this.ledColor = ledColor;
    }

    protected Dimension calcPreferredSize(){
        return new Dimension(digitImages[0].getWidth()*numDigitsShowing,digitImages[0].getHeight());
    }

    /**
     * Paints the Last component on screen
     * @param g component's graphics
     */
    public void paint(Graphics g){
        super.paint(g);
        final int COLOR_PAD = 1;

        int digitWidth = lastDigits[0].getWidth();
        int digitHeight = lastDigits[0].getHeight();

        int displayDigitWidth = (int)(scaleFactor*digitWidth);
        int displayDigitHeight = (int)(scaleFactor*digitHeight);
        int displayClockWidth = displayDigitWidth*numDigitsShowing;

        int displayX = getX() + (getWidth()-displayClockWidth)/2;
        int displayY = getY() + (getHeight()-displayDigitHeight)/2;

        g.setColor(ledColor);
        g.fillRect(displayX + COLOR_PAD, displayY + COLOR_PAD,
                displayClockWidth-COLOR_PAD*2, displayDigitHeight-COLOR_PAD*2);

        for (int digitIndex = 0; digitIndex < numDigitsShowing; digitIndex++)
            g.drawImage(lastDigits[digitIndex], displayX + digitIndex * displayDigitWidth,
                    displayY, displayDigitWidth, displayDigitHeight);
    }

    /**
     * Chooses the correct digit to display based on the last SkyScraper reached by the Player Helicopter
     */
    public void setLastSkyScraperReached(){
        int lastSkyScraperReached = gw.findPlayerHeli().getLastSkyScraperReached();

        lastDigits[0] = digitImages[lastSkyScraperReached];
    }
}
