package com.caellian.bowEngine.physics.attributes;

/**
 * @author Caellian
 */
public interface ICollidable {
    void collide(ICollidable collidable);

    default float getCollisionRadius() {
        return 2;
    }

    default boolean collideSolids() {
        return true;
    }

    default boolean collideNonSolids() {
        return false;
    }
}
