package org.csc133.a3;

import com.codename1.util.MathUtil;

/**
 * Strategy taken by Non-player Helicopters to win the game by
 * advancing through SkyScrapers
 */
public class AdvanceStrategy implements IStrategy {
    private NPHelicopter npHelicopter;
    private GameObjectCollection objects;
    private SkyScraper target;

    /**
     * Creates new Advance Strategy
     * @param h NonPlayer Helicopter that currently changing strategy
     * @param obj target object for NonPlayer Helicopter
     */
    public AdvanceStrategy(NPHelicopter h, GameObjectCollection obj){
        npHelicopter=h;
        objects=obj;
    }

    /**
     * Executes current strategy
     * Non-player helicopters update their direction based on their
     * last SkyScraper reached
     */
    @Override
    public void execute() {
        // finds correct skyscraper to target
        for(int j=0; j<objects.getSkyScrapers().size(); j++){
            SkyScraper s = (SkyScraper) objects.getSkyScrapers().get(j);
            if((npHelicopter.getLastSkyScraperReached() + 1) == s.getSequenceNumber())
                target = s;
        }

        double x = target.getLocationX() - npHelicopter.getLocationX();
        double y = target.getLocationY() - npHelicopter.getLocationY();

        double idealHeading = Math.toDegrees(MathUtil.atan2(x, y));

        npHelicopter.setHeading((int)idealHeading);
    }
}
