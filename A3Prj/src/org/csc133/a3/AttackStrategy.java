package org.csc133.a3;

import com.codename1.util.MathUtil;

/**
 * Strategy taken by Non-player Helicopters to win the game by
 * attacking Player's Helicopter
 */
public class AttackStrategy implements IStrategy {
    private NPHelicopter npHelicopter;
    private PlayerHelicopter player;

    /**
     * Creates new Attack Strategy
     * @param h Non-player Helicopter currently changing strategy
     * @param p player helicopter target for NPH
     */
    public AttackStrategy(NPHelicopter h, PlayerHelicopter p){
        npHelicopter=h;
        player=p;
    }

    /**
     * Executes current strategy
     * Non-player helicopters update their direction based on position
     * of the Player Helicopter
     */
    @Override
    public void execute() {
        double x = player.getLocationX() - npHelicopter.getLocationX();
        double y = player.getLocationY() - npHelicopter.getLocationY();

        double idealHeading = Math.toDegrees(MathUtil.atan2(x, y));

        npHelicopter.setHeading((int)idealHeading);
    }
}
