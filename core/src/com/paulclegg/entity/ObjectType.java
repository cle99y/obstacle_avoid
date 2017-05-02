package com.paulclegg.entity;

/**
 * Created by cle99 on 28/04/2017.
 */

public enum ObjectType {

    OBSTACLE,
    HEALTH,
    POINTS;

//    private final float spawnTime;
//
//    ObjectType( float spawnTime ) {
//        this.spawnTime = spawnTime;
//    }

    public boolean isObstacle() {
        return this == OBSTACLE;
    }

    public boolean isHealth() {
        return this == HEALTH;
    }

    public boolean isPoints() {
        return this == POINTS;
    }

    public ObjectType getType() {

        if ( this.isHealth() ) {
            return HEALTH;
        } else if ( isPoints() ) {
            return POINTS;
        } else {
            return OBSTACLE;
        }
    }

}

