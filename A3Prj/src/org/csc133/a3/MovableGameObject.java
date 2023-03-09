package org.csc133.a3;

/**
 * Represents Game Objects that are Movable
 */
 abstract public class MovableGameObject extends GameObject {
    private int heading;
    private int speed;

     /**
      * Creates new MovableGameObject
      */
    public MovableGameObject(){
    }

     /**
      * Moves a movable object according to its Heading and Speed
      * Updates object to new position
      * @param eTime elapsed time between ticks
      */
    public void move(double eTime){
        double deltaX = eTime * Math.cos(Math.toRadians(90-getHeading()))*getSpeed();
        double deltaY = eTime * Math.sin(Math.toRadians(90-getHeading()))*getSpeed();

        double newLocationX = getLocationX() + deltaX;
        double newLocationY = getLocationY() + deltaY;
        setLocationX(newLocationX);
        setLocationY(newLocationY);
    }

    public int getHeading() {return heading;}
    public int getSpeed() {return speed;}
    public void setHeading(int heading) {this.heading = heading;}
    public void setSpeed(int speed) {this.speed = speed;}
}
