package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * Represents a Bird as a Movable Game Object manipulated in the Game World
 */
public class Bird extends MovableGameObject {
    private int damageToHeli = 10;
    private int mapWidth, mapHeight;

    /**
     * Creates a new Bird object with set color, random size,
     * random speed, random location, and random heading
     * @param w Width of the container the bird is in
     * @param h Height of the container the bird is in
     */
    public Bird(int w, int h){
        mapWidth=w;
        mapHeight=h;

        super.setColor(ColorUtil.rgb(0,0,0));
        super.setSize(getRandomInt(25,35));
        super.setSpeed(getRandomInt(2,4));

        this.setLocationX(getRandomDouble(0,mapWidth));
        this.setLocationY(getRandomDouble(0,mapHeight));
        this.setHeading(getRandomInt(0,360));
    }

    /**
     * {@inheritDoc}
     *
     * Before Bird moves, determine if bird is offscreen or not and
     * update direction of Bird to the opposite direction
     * @param eTime elapsed time
     */
    @Override
    public void move(double eTime){
        // check if Bird has moved off screen left/right, reverse direction if yes
        if(getLocationX() <= 0){
            setLocationX(0);
            setHeading(360 - getHeading());
        }
        else if(getLocationX() >= mapWidth)
        {
            setLocationX(mapWidth);
            setHeading(360-getHeading());
        }

        // check if Bird has moved off screen up/down, reverse direction if yes
        if(getLocationY() <= 0){
            setLocationY(0);
            setHeading(180-getHeading());
        }
        else if(getLocationY() >= mapHeight)
        {
            setLocationY(mapHeight);
            setHeading(180-getHeading());
        }
        super.move(eTime);
    }

    /**
     * Makes changes in the Bird's direction in small increments
     * left or right randomly as the bird moves
     */
    public void turnBird()
    {
        setHeading(getHeading()+getRandomInt(-11,11));
    }

    /**
     * Finds the Location, Color, Heading, Speed, and Size of Bird
     * and outputs it as a string.
     * @return String representation of Bird
     */
    public String toString(){
        String myDesc = "Bird: loc=" + Math.round(getLocationX()*10)/10.0 + ", " + Math.round(getLocationY()*10)/10.0 + " color=[" + ColorUtil.red(getColor())
                + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] heading=" + getHeading()
                + " speed=" + getSpeed() + " size=" + getSize();
        return myDesc;
    }

    /**
     * Speed of Bird cannot change.
     * Override MovableGameObject setSpeed function to empty function
     * so it does nothing to the Bird
     */
    @Override
    public void setSpeed(int speed) { }

    /**
     * Color of Bird cannot change.
     * Override GameObject setColor function to empty function
     * so it does nothing to the Bird
     */
    @Override
    public void setColor(int color) { }

    /**
     * Size of Bird cannot change.
     * Override GameObject setSize function to empty function
     * so it does nothing to the Bird
     */
    @Override
    public void setSize(int size) { }

    public int getDamageToHeli() { return damageToHeli; }

    /**
     * Draws Bird onto the screen with correction position and size
     * Has line pointing in the direction of its heading
     * @param g Graphics object
     * @param containerOrigin Origin of the container the bird is in
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.drawArc(containerOrigin.getX() + (int)getLocationX() - getSize()/2,
                containerOrigin.getY() + (int)getLocationY() - getSize()/2,
                getSize(), getSize(), 0,360);

        g.setColor(ColorUtil.BLACK);
        g.drawLine(containerOrigin.getX() + (int)getLocationX(),
                containerOrigin.getY() + (int)getLocationY(),
                containerOrigin.getX() + (int)getLocationX() + (int) (Math.cos(Math.toRadians(90-getHeading())) * getSize()/2),
                containerOrigin.getY() + (int)getLocationY() + (int) (Math.sin(Math.toRadians(90-getHeading())) * getSize()/2));
    }

    @Override
    public boolean collidesWith(GameObject otherObject) {
        return false;
    }

    @Override
    public void handleCollision(GameObject otherObject) { }
}
