package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.util.ArrayList;

/**
 * Represents a SkyScraper as a fixed object manipulated in the Game World
 */
public class SkyScraper extends FixedGameObject {
    private int sequenceNumber;
    private int mapWidth, mapHeight;
    private int stringColor = ColorUtil.BLACK;

    private ArrayList<GameObject> collisionObj = new ArrayList<>();

    /**
     * Creates a SkyScraper with set location, color, size, and sequence number
     * @param w Width of the screen
     * @param h Height of the screen
     * @param sequenceNumber Sequence number of SkyScraper
     */
    public SkyScraper(int w, int h, int sequenceNumber){
        mapWidth=w;
        mapHeight=h;

        super.setLocationX(getRandomDouble(0, mapWidth));
        super.setLocationY(getRandomDouble(0, mapHeight));
        super.setColor(ColorUtil.rgb(0,204,0));
        super.setSize(75);
        this.sequenceNumber = sequenceNumber;
    }

    /**
     * Finds the Location, color, size, and sequence number of SkyScraper
     * and outputs it as a string.
     * @return String representation of SkyScraper
     */
    public String toString(){
        String myDesc = "SkyScraper: loc=" + Math.round(getLocationX()*10)/10.0 + ", " + Math.round(getLocationY()*10)/10.0 + " color=[" + ColorUtil.red(getColor())
                + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] size=" + getSize()
                + " seqNum=" + getSequenceNumber();
        return myDesc;
    }

    /**
     * Color of SkyScraper cannot change.
     * Override GameObject setColor function to empty function
     * so it does nothing to the SkyScraper
     */
    @Override
    public void setColor(int color) { }

    /**
     * Size of SkyScraper cannot change.
     * Override GameObject setSize function to empty function
     * so it does nothing to the SkyScraper
     */
    @Override
    public void setSize(int size) { }

    /**
     * X-coordinate location of SkyScraper cannot change.
     * Override GameObject setLocationX function to empty function
     * so it does nothing to the SkyScraper
     */
    @Override
    public void setLocationX(double locationX) { }

    /**
     * Y-coordinate location SkyScraper cannot change.
     * Override GameObject setLocationY function to empty function
     * so it does nothing to the SkyScraper
     */
    @Override
    public void setLocationY(double locationY) { }

    public int getSequenceNumber() {return sequenceNumber;}

    public void skyScraperReached(){
        stringColor = ColorUtil.BLUE;
    }

    /**
     * Draws SkyScraper onto the screen with correction position and size
     * Has line pointing in the direction of its heading
     * @param g Graphics object
     * @param containerOrigin Origin of the container the SkyScraper is in
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.fillRect(containerOrigin.getX() + (int)getLocationX() - getSize()/2,
                containerOrigin.getY() + (int)getLocationY() - getSize()/2,
                getSize(), getSize());

        g.setColor(stringColor);

        g.drawString(String.valueOf(getSequenceNumber()),
                containerOrigin.getX() + (int)getLocationX() - getSize()/4,
                containerOrigin.getY() + (int)getLocationY() - getSize()/2);
    }

    public void changeColor(int sequenceNum){
        if(sequenceNum + 1 == getSequenceNumber())
            stringColor = ColorUtil.BLUE;
    }

    /**
     * Detects collisions between two objects
     * Uses bounding circles to determine collision
     * Checks if the two objects are already colliding
     * @param otherObject object being checked against for collision
     * @return true or false if collision has happened
     */
    @Override
    public boolean collidesWith(GameObject otherObject) {
        double thisCenterX = this.getLocationX();
        double thisCenterY = this.getLocationY();

        double otherCenterX = (otherObject.getLocationX());
        double otherCenterY = (otherObject.getLocationY());

        double dx = thisCenterX - otherCenterX;
        double dy = thisCenterY - otherCenterY;

        double distBetweenCentersSqr = (dx * dx + dy * dy);

        int thisRadius= this.getSize() / 2;
        int otherRadius= otherObject.getSize() / 2;

        int radiiSqr= (thisRadius * thisRadius + 2 * thisRadius * otherRadius + otherRadius * otherRadius);

        if (distBetweenCentersSqr <= radiiSqr && collisionObj.isEmpty()) {
            collisionObj.add(otherObject);
            return true; }
        else if(distBetweenCentersSqr <= radiiSqr && collisionObj.contains(otherObject)){
            return false;
        }
        else {
            collisionObj.remove(otherObject);
            return false;
        }
    }

    /**
     * Deals with SkyScraper collisions between
     * all other game objects
     * @param otherObject object being checked against for collisions
     */
    @Override
    public void handleCollision(GameObject otherObject) {
        if(otherObject instanceof NPHelicopter || otherObject instanceof PlayerHelicopter){
            changeColor(((Helicopter) otherObject).getLastSkyScraperReached());
        }
    }
}
