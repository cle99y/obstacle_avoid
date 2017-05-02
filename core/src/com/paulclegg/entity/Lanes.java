package com.paulclegg.entity;

import com.paulclegg.Config.GameConfig;

/**
 * Created by cle99 on 01/05/2017.
 */

public class Lanes {

    private float x;
    private float y;
    private float width;
    private float height;
    private float ySpeed;

    public Lanes() {
        width = GameConfig.WORLD_WIDTH;
        height = GameConfig.WORLD_HEIGHT / 6;
        ySpeed = GameConfig.LANE_LINE_Y_SPEED;
    }

    public float getX() {
        return x;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getY() {
        return y;
    }

    public void setPosition( float x, float y ) {
        this.x = x;
        this.y = y;
    }

    public void update() {
        y -= ySpeed;
    }

    public void reset() {
        setPosition( 0, GameConfig.WORLD_HEIGHT );
    }
}
