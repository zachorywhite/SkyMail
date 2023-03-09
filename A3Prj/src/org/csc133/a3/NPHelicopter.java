package org.csc133.a3;

import com.codename1.charts.util.ColorUtil;
import java.util.ArrayList;

/**
 * NPHelicopter is a helicopter that moves according to its
 * current strategy
 */
public class NPHelicopter extends Helicopter{
    private IStrategy strat;
    private GameWorld gw;

    private ArrayList<GameObject> collisionObj = new ArrayList<>();

    /**
     * Creates a new NPHelicopter with set color, location, size, speed, and heading
     * @param width Width of the screen
     * @param height Height of the screen
     * @param locX Initial X coordinate of Helicopter
     * @param locY Initial Y coordinate of Helicopter
     */
    public NPHelicopter(GameWorld gameworld, int width, int height, double locX, double locY) {
        super(width, height, locX, locY);
        this.setColor(ColorUtil.rgb(255,0,0));
        this.setSpeed(1);
        gw = gameworld;
    }

    /**
     * Before move, invoke the current strategy to turn the NPH
     * to the correct direction
     * @param eTime amount of time between ticks
     */
    @Override
    public void move(double eTime){
        invokeStrategy();
        super.move(eTime);
    }

    public void setStrategy(IStrategy strategy){this.strat=strategy;}

    public String getStrat(){
       if(strat instanceof AdvanceStrategy )
           return "Advance Strategy";
       else
           return "Attack Strategy";
    }

    public IStrategy getStrategy(){
        return strat;
    }
    /**
     * Executes the current strategy of the NPHelicopter
     */
    public void invokeStrategy(){strat.execute();}

    /**
     * Finds the Location, Color, Heading, Speed, Size, Max Speed, Stick Angle, Fuel Level,
     * Damage Level, and current strategy of the NPHelicopter and outputs it as a string.
     * @return String representation of Helicopter
     */
    @Override
    public String toString(){
        String str = super.toString();
        str += " Strategy=" + getStrat();
        return str;
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
     * Deals with NPHelicopter collisions between
     * all other game objects
     * @param otherObject object being checked against for collisions
     */
    @Override
    public void handleCollision(GameObject otherObject) {
            if(otherObject instanceof SkyScraper && (this.getLastSkyScraperReached()+1) == ((SkyScraper) otherObject).getSequenceNumber())
                gw.npHSkyScraperCollision(this);

            if(otherObject instanceof RefuelingBlimp)
                gw.npHRefuelingBlimpCollision((RefuelingBlimp) otherObject, this);

            if(otherObject instanceof PlayerHelicopter || otherObject instanceof NPHelicopter)
                gw.npHHeliCollision(this);

            if(otherObject instanceof Bird)
                gw.npHBirdCollision((Bird) otherObject, this);
    }
}
