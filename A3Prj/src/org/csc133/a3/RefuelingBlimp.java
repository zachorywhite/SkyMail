package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

import java.util.ArrayList;

/**
 * Represents a RefuelingBlimp as a fixed object manipulated in the Game World
 */
public class RefuelingBlimp extends FixedGameObject{
    private int capacity;
    private int mapWidth, mapHeight;

    private ArrayList<GameObject> collisionObj = new ArrayList<>();

    /**
     * Creates a RefuelingBlimp with a random location, set size, set color, and proportional capacity
     * @param w Width of the screen
     * @param h Height of the screen
     */
    public RefuelingBlimp(int w, int h){
        mapWidth=w;
        mapHeight=h;

        super.setLocationX(getRandomDouble(0,mapWidth));
        super.setLocationY(getRandomDouble(0,mapHeight));
        super.setSize(getRandomInt(50, 70));

        this.setColor(ColorUtil.rgb(255,255,0));
        this.capacity = getSize()*6;
    }

    /**
     * Upon collision with refueling blimp,
     * set its capacity to empty and fade the color of the blimp
     */
    public void emptyBlimpFuel(){
        setCapacity(0);
        setColor(ColorUtil.rgb(255, 255, 153));
    }

    /**
     * Finds the Location, color, size, and capacity of RefuelingBlimp
     * and outputs it as a string.
     * @return String representation of RefuelingBlimp
     */
    public String toString(){
        String myDesc = "RefuelingBlimp: loc=" + Math.round(getLocationX()*10)/10.0 + ", " + Math.round(getLocationY()*10)/10.0 + " color=[" + ColorUtil.red(getColor())
                + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] size=" + getSize()
                + " capacity=" + getCapacity();
        return myDesc;
    }

    /**
     * Size of RefuelingBlimp cannot change.
     * Override GameObject setSize function to empty function
     * so it does nothing to the RefuelingBlimp
     */
    @Override
    public void setSize(int size) { }

    /**
     * X-coordinate location of RefuelingBlimp cannot change.
     * Override GameObject setLocationX function to empty function
     * so it does nothing to the RefuelingBlimp
     */
    @Override
    public void setLocationX(double locationX) { }

    /**
     * Y-coordinate location of RefuelingBlimp cannot change.
     * Override GameObject setLocationY function to empty function
     * so it does nothing to the RefuelingBlimp
     */
    @Override
    public void setLocationY(double locationY) { }

    public void setCapacity(int capacity) { this.capacity = capacity; }
    public int getCapacity() {return capacity;}

    /**
     * Draws RefuelingBlimp onto the screen with correction position and size
     * @param g Graphics object
     * @param containerOrigin Origin of the container the RefuelingBlimp is in
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.fillArc(containerOrigin.getX() + (int)getLocationX() - getSize()/2,
                containerOrigin.getY() + (int)getLocationY() - getSize()/2,
                2*getSize(), getSize(), 0,360);

        g.setColor(ColorUtil.BLACK);
        g.drawString(String.valueOf(getCapacity()),
                containerOrigin.getX() + (int)getLocationX() - getSize()/4,
                containerOrigin.getY() + (int)getLocationY() - getSize()/2);
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
     * Deals with RefuelingBlimp collisions between
     * all other game objects
     * @param otherObject object being checked against for collisions
     */
    @Override
    public void handleCollision(GameObject otherObject) {
        if(otherObject instanceof PlayerHelicopter || otherObject instanceof NPHelicopter)
            this.emptyBlimpFuel();
    }
}
