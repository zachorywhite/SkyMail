package org.csc133.a3;

import java.util.Random;

/**
 * Represents Game Objects that are manipulated in the Game World
 */
abstract public class GameObject implements IDrawable, ICollider {
    private int size;
    private double locationX;
    private double locationY;
    private int color;

    /**
     * Creates new GameObject
     */
    public GameObject(){
    }

    public int getSize(){return size;}
    public double getLocationX(){return locationX;}
    public double getLocationY(){return locationY;}
    public int getColor(){return color;}

    public void setSize(int size) { this.size = size; }
    public void setLocationX(double locationX) {this.locationX = locationX;}
    public void setLocationY(double locationY) {this.locationY = locationY;}
    public void setColor(int color) {this.color = color;}

    /**
     * Finds a random integer between two numbers
     * @param low Bottom end of the range
     * @param high Top end of the range
     * @return Random number between low and high
     */
    public int getRandomInt(int low, int high){
        Random randomNum = new Random();
        int i = randomNum.nextInt(high-low + 1) + low;
        return i;
    }

    /**
     * Finds a random double between two numbers
     * @param low Bottom end of the range
     * @param high Top end of the range
     * @return Random double between low and high
     */
    public double getRandomDouble(int low, int high){
        Random randomNum = new Random();
        double i = randomNum.nextDouble() * (high-low) + low;
        return i;
    }
}
