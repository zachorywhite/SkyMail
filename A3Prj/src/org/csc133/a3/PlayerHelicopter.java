package org.csc133.a3;

import java.util.ArrayList;

/**
 * PlayerHelicopter is a singleton Helicopter
 */
public class PlayerHelicopter extends Helicopter{
    private static PlayerHelicopter player = null;
    private GameWorld gw;

    private ArrayList<GameObject>  collisionObj = new ArrayList<>();

    private PlayerHelicopter(GameWorld gameworld, int width, int height, double locX, double locY){
        super(width, height,locX,locY);
        gw = gameworld;
    }

    public static PlayerHelicopter getInstance(GameWorld gameworld, int width, int height, double locX, double locY){
        if(player == null)
            return new PlayerHelicopter(gameworld, width, height, locX, locY);
        else
            return player;
    }

    public static PlayerHelicopter getInstance(){
        return player;
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
     * Deals with PlayerHelicopter collisions between
     * all other game objects
     * @param otherObject object being checked against for collisions
     */
    @Override
    public void handleCollision(GameObject otherObject) {
            if(otherObject instanceof SkyScraper
                    && (this.getLastSkyScraperReached()+1) == ((SkyScraper) otherObject).getSequenceNumber())
                gw.playerSkyScraperCollision();

            if(otherObject instanceof RefuelingBlimp)
                gw.playerRefuelingBlimpCollision((RefuelingBlimp) otherObject);

            if(otherObject instanceof NPHelicopter)
                gw.playerHeliCollision();

            if(otherObject instanceof Bird)
                gw.playerBirdCollision((Bird) otherObject);
    }
}
