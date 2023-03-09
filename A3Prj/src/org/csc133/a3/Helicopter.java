package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/**
 * Helicopters are movable, steerable objects manipulated in the Game World
 */
public class Helicopter extends MovableGameObject implements ISteerable {
    private int mapWidth, mapHeight;

    private int stickAngle = 0;
    private int fuelLevel = 2000;
    private int fuelConsumptionRate = 1;
    private int damageLevel = 0;
    private int lastSkyScraperReached = 1;


    // Max/Min values for the speed, stick angle, damage level, and fuel level of Player Helicopter
    private int initialMaximumSpeed = getRandomInt(20,31);
    private int maxStickAngle = 40;
    private int lowStickAngle = -40;
    private int maxDamageLevel = 100;
    private int maximumSpeed = initialMaximumSpeed;
    private int maxFuelLevel = 2000;


    /**
     * Creates a new Helicopter with set color, location, size, speed, and heading
     * @param width Width of the screen
     * @param height Height of the screen
     * @param locX Initial X coordinate of Helicopter
     * @param locY Initial Y coordinate of Helicopter
     */
    public Helicopter(int width, int height, double locX, double locY){
        mapHeight = height;
        mapWidth = width;
        this.setColor(ColorUtil.rgb(0,50,255));
        this.setLocationX(locX);
        this.setLocationY(locY);
        super.setSize(50);
        this.setSpeed(0);
        this.setHeading(0);
    }

    public void turn(){
        if (getStickAngle() > 0) {               // right turn
            if(getHeading() + 5 > 360)
                setHeading(getHeading()+5-360);
            else
                setHeading(getHeading() + 5);
            setStickAngle(getStickAngle() - 5);
        } else if (getStickAngle() < 0) {        // left turn
            if(getHeading()-5 < 0)
                setHeading(getHeading()-5+360);
            else
                setHeading(getHeading() - 5);
            setStickAngle(getStickAngle() + 5);
        }
    }

    /**
     * Changes the Stick Angle of Helicopter left or right depending on input
     * @param nStickAngle The direction needed to change to
     */
    public void changeStickDirection(int nStickAngle){
        if(nStickAngle + stickAngle > maxStickAngle)
            stickAngle = maxStickAngle;
        else if(nStickAngle + stickAngle < lowStickAngle)
            stickAngle = lowStickAngle;
        else
            stickAngle += nStickAngle;
    }

    /**
     * Helicopter takes appropriate amount of damage
     * Adds damage to damage level
     * Updates max speed of Helicopter according to damage level
     * Fades the color of the Helicopter by amount of damage taken
     * @param dmgAmount Specific amount of damgage that the Helicopter will take
     */
    public void takeDamage(int dmgAmount){
        if((damageLevel + dmgAmount) < maxDamageLevel){
            damageLevel += dmgAmount;
        }
        else if((damageLevel + dmgAmount) >= maxDamageLevel){
            damageLevel = maxDamageLevel;
        }
        updateMaxSpeed();

        // fade color of player Helicopter depending on damage amount taken
        int r = ColorUtil.red(getColor());
        int b = ColorUtil.blue(getColor());
        if(b > 0)
            setColor(ColorUtil.rgb(0, 0, ColorUtil.blue(getColor())- 20));
        if(r > 0)
            setColor(ColorUtil.rgb(ColorUtil.red(getColor())- 20, 0, 0));
    }

    /**
     * Helicopter loses fuel of amount based on the fuel consumption rate
     */
    public void loseFuel(){
        if((fuelLevel - fuelConsumptionRate) >= 0)
            fuelLevel -= fuelConsumptionRate;
        else if((fuelLevel - fuelConsumptionRate) < 0)
            fuelLevel = 0;

        if(fuelLevel == 0)
            setSpeed(0);
    }

    /**
     * Fuel is added to Helicopter
     * @param fuelAmount Amount of fuel to gain
     */
    public void gainFuel(int fuelAmount){
        if((fuelLevel + fuelAmount) > maxFuelLevel)
            fuelLevel = maxFuelLevel;
        else if((fuelLevel + fuelAmount) <= maxFuelLevel)
            fuelLevel += fuelAmount;
    }

    /**
     * Limits the max speed by a percentage of the damage taken
     * As player takes damage, the max speed of Helicopter is lowered
     */
    public void updateMaxSpeed(){
        double percentDamage = 1 - ((double) damageLevel / maxDamageLevel);
        maximumSpeed = (int) (initialMaximumSpeed * percentDamage);
    }

    /**
     * As Helicopter reaches next SkyScraper in the sequence, increment
     * the Helicopters lastSkyScraperReached value
     */
    public void incLastSkyScraperReached(){
        this.lastSkyScraperReached++;
    }

    /**
     * {@inheritDoc}
     *
     * Before Helicopter moves, check if it is allowed to move
     * @param eTime amount of time between ticks
     */
    @Override
    public void move(double eTime){
        if(fuelNotEmpty()){

            if(getLocationX() < -20){
                setLocationX(getRandomDouble(0,mapWidth));
                setLocationY(getRandomDouble(0,mapHeight));}
            else if(getLocationX() > mapWidth + 20){
                setLocationX(getRandomDouble(0,mapWidth));
                setLocationY(getRandomDouble(0,mapHeight));}

            if(getLocationY() < -20){
                setLocationX(getRandomDouble(0,mapWidth));
                setLocationY(getRandomDouble(0,mapHeight));}
            else if(getLocationY() > mapHeight + 20){
                setLocationX(getRandomDouble(0,mapWidth));
                setLocationY(getRandomDouble(0,mapHeight));}

            super.move(eTime);}
    }

    /**
     * Checks if Helicopter has fuel left
     * @return True/False Helicopter can move
     */
    public boolean fuelNotEmpty(){
        if(fuelLevel > 0)
            return true;
        return false;
    }

    /**
     * Accelerates Helicopter by some speed
     * Makes sure speed never exceeds the max speed of the Helicopter
     */
    public void accelerate(){
        if(getSpeed() + 1 > getMaximumSpeed())
            setSpeed(getMaximumSpeed());
        else
            setSpeed(getSpeed()+1);
    }

    /**
     * Decelerates Helicopter by some speed
     * Makes sure speed can never be a negative value
     */
    public void decelerate(){
        if(getSpeed() - 1 <= 0)
            setSpeed(0);
        else if(getSpeed() - 1 > 0)
            setSpeed(getSpeed()-1);
    }

    /**
     * Finds the Location, Color, Heading, Speed, Size, Max Speed, Stick Angle, Fuel Level,
     * and Damage Level of Helicopter and outputs it as a string.
     * @return String representation of Helicopter
     */
    public String toString(){
        String myDesc = "Helicopter: loc=" + Math.round(getLocationX()*10)/10.0 + ", " + Math.round(getLocationY()*10)/10.0 + " color=[" + ColorUtil.red(getColor())
                + ", " + ColorUtil.green(getColor()) + ", " + ColorUtil.blue(getColor()) + "] heading=" + getHeading()
                + " speed=" + getSpeed() + " size=" + getSize() + " maxSpeed=" + getMaximumSpeed()
                + " stickAngle=" + getStickAngle() + " fuelLevel=" + getFuelLevel() + " damageLevel="
                + getDamageLevel();
        return myDesc;
    }

    /**
     * Size of Helicopter cannot change.
     * Override GameObject setSize function to empty function
     * so it does nothing to the Helicopter
     */
    @Override
    public void setSize(int size) { }

    public void setDamageLevel(int damageLevel) { this.damageLevel = damageLevel; }
    public void setFuelLevel(int fuelAmount){fuelLevel=fuelAmount;}
    public void setStickAngle(int stickAngle) { this.stickAngle = stickAngle; }
    public int getStickAngle() { return stickAngle; }
    public int getLastSkyScraperReached() { return lastSkyScraperReached; }
    public int getFuelLevel() {return fuelLevel;}
    public int getDamageLevel() { return damageLevel; }
    public int getMaximumSpeed() { return maximumSpeed; }

    /**
     * Draws Helicopter onto the screen with correction position and size
     * Has line pointing in the direction of its heading
     * @param g Graphics object
     * @param containerOrigin Origin of the container the Helicopter is in
     */
    @Override
    public void draw(Graphics g, Point containerOrigin) {
        g.setColor(getColor());
        g.fillArc(containerOrigin.getX() + (int)getLocationX() - getSize()/2,
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
    public void handleCollision(GameObject otherObject) {

    }
}
