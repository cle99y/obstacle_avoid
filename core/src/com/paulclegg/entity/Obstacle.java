package com.paulclegg.entity;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Pool;
import com.paulclegg.Config.GameConfig;

/**
 * Created by cle99 on 03/04/2017.
 */

public class Obstacle extends GameObject implements Pool.Poolable {

    private float ySpeed = GameConfig.MEDIUM_OBSTACLE_SPEED;
    private boolean collided;

    public Obstacle() {
        super( GameConfig.OBSTACLE_BOUNDS_RADIUS );
        setSize( GameConfig.OBSTACLE_SIZE, GameConfig.OBSTACLE_SIZE );
    }

    public void update() {
        setY( getY() - ySpeed );
    }

    public boolean playerCollision( Player player ) {
        Circle playerBounds = player.getBounds();
        // check if playerBounds overlap obstacle bounds
        boolean overlaps = Intersector.overlaps( playerBounds, getBounds() );

//        if(overlaps) {
//            collided = true;
//        }

        // better way
        collided = overlaps;

        return overlaps;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setYSpeed( float ySpeed ) {
        this.ySpeed = ySpeed;
    }

    @Override
    public void reset() {
        collided = false;
    }
}
