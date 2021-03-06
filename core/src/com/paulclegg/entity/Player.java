package com.paulclegg.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.assets.RegionNames;

/**
 * Created by cle99 on 03/04/2017.
 */


public class Player {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    private float x;
    private float y;
    private float width;
    private float height;

    private int animator;  // integer value between 0 and 2 representing an animated state

    private Rectangle bounds;

    public Player() {
        bounds = new Rectangle( x, y, GameConfig.PLAYER_WIDTH, GameConfig.PLAYER_LENGTH );
        width = GameConfig.PLAYER_WIDTH;
        height = GameConfig.PLAYER_LENGTH;
    }

    public void setPosition( float x, float y ) {
        this.x = x;
        this.y = y;
        updateBounds();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public int getAnimator() {
        return animator;
    }

    public void updateAnimator() {
        animator++;
        if (animator > RegionNames.PLAYER.length - 1) {
            animator = 0;
        }
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setX( float x ) {
        this.x = x;
        updateBounds();
    }

    public void setY( float y ) {
        this.y = y;
    }

    public void setSize( float width, float height ) {
        this.width = width;
        this.height = height;
        updateBounds();
    }

    public void updateBounds() {
        bounds.setPosition( x, y );
    }

    public void drawDebug( ShapeRenderer renderer ) {
        renderer.rect( bounds.x, bounds.y, bounds.getWidth(), bounds.getHeight() );
    }
}
