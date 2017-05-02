package com.paulclegg.entity;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.assets.RegionNames;

/**
 * Created by cle99 on 03/04/2017.
 */

public class Collider implements Pool.Poolable {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    private float x;
    private float y;
    private float width;
    private float height;

    private int animator;  // integer value between 0 and 2 representing an animated state

    private Rectangle bounds;

    private float ySpeed = GameConfig.EASY_SPEED;
    private boolean collided;
    private int colliderType;

    public Collider() {
        bounds = new Rectangle( x, y, GameConfig.COLLIDER_WIDTH, GameConfig.COLLIDER_LENGTH );
        width = GameConfig.COLLIDER_WIDTH;
        height = GameConfig.COLLIDER_LENGTH;

        //  the following assigns a collider type at random
        colliderType = MathUtils.random( 0, RegionNames.COLLIDER.length - 1 );
        log.debug( "collider type: " + colliderType );
    }

    public void update() {
        setY( getY() - ySpeed );
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

    public int getColliderType() {
        return colliderType;
    }

    public int getAnimator() {
        return animator;
    }

    public void updateAnimator() {
        animator++;
        if ( animator > RegionNames.PLAYER.length - 1 ) {
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
        updateBounds();
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

    public boolean playerCollision( Player player ) {
        Rectangle playerBounds = player.getBounds();
        // check if playerBounds overlap obstacle bounds
        boolean overlaps = playerBounds.overlaps( getBounds() );

//        if(overlaps) {
//            collided = true;
//        }

        // better way
        collided = overlaps;

        return collided;
    }

    public boolean isCollided() {
        return collided;
    }

    public void setYSpeed( float ySpeed ) {
        this.ySpeed = ySpeed;
    }

    public void reset() {
        collided = false;
        colliderType = MathUtils.random( 0, RegionNames.COLLIDER.length - 1 );
    }
}
