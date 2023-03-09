package org.csc133.a3;

/**
 * Provides methods to handle collisions between objects
 */
public interface ICollider {
    /**
     * Confirms a collisions between two objects
     * @param otherObject object being checked against for collision
     * @return true or flase depending on if collision is happening
     */
    boolean collidesWith(GameObject otherObject);

    /**
     * Deals with collision when it happens
     * @param otherObject object colliding with object being dealt with
     */
    void handleCollision(GameObject otherObject);
}
