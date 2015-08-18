package com.caellian.bowEngine.physics.collision;

import com.caellian.bowEngine.core.Transform;
import com.caellian.bowEngine.physics.attributes.ICollidable;

import java.util.HashMap;

/**
 * Collision Map handles collision between {@link ICollidable} objects.
 *
 * @author Caellian
 */
public class CollisionMap extends HashMap<Transform, ICollidable> {

    public CollisionMap() {
        super(0);
    }

    public void trigger() {
        this.entrySet().forEach(entry ->
                        this.entrySet().forEach(compared -> {
                            if (entry.getValue() != compared.getValue()) {
                                float maxCollisionRadius = Math.max(entry.getValue().getCollisionRadius(), compared.getValue().getCollisionRadius());
                                float xDist = Math.abs(Math.max(entry.getKey().getPos().getX(), compared.getKey().getPos().getX()) - Math.min(entry.getKey().getPos().getX(), compared.getKey().getPos().getX()));
                                if (Math.abs(xDist) <= maxCollisionRadius) {
                                    float zDist = Math.abs(Math.max(entry.getKey().getPos().getZ(), compared.getKey().getPos().getZ()) - Math.min(entry.getKey().getPos().getZ(), compared.getKey().getPos().getZ()));
                                    if (Math.abs(zDist) <= maxCollisionRadius) {
                                        float yDist = Math.abs(Math.max(entry.getKey().getPos().getY(), compared.getKey().getPos().getY()) - Math.min(entry.getKey().getPos().getY(), compared.getKey().getPos().getY()));
                                        if (yDist <= maxCollisionRadius) {
                                            entry.getValue().collide(compared.getValue());
                                        }
                                    }
                                }
                            }
                        })
        );
    }
}
