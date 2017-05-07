package com.paulclegg.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.paulclegg.Config.GameConfig;

/**
 * Created by cle99 on 05/05/2017.
 */

public class PlayerSprite extends Sprite {


    // -- attributes --
    private float x, y;
    private float playerWidth = GameConfig.PLAYER_WIDTH;
    private float playerLength = GameConfig.PLAYER_LENGTH;
    private Rectangle bounds;
    private float rotationAngle;

    // -- constructors --
    public PlayerSprite( TextureRegion region ) {
        super( region );
        bounds = new Rectangle( getX(), getY(), playerWidth, playerLength );
        setSize( playerWidth, playerLength );
        setOrigin( playerWidth / 2, 0 );
    }



    public void updateBounds() {
        bounds.setPosition( getX(), getY() );

    }

    public void drawDebug( ShapeRenderer renderer ) {
        //renderer.rect( bounds.x, bounds.y, bounds.getWidth(), bounds.getHeight() );


        renderer.rect(
                getX(),
                getY(),
                getOriginX(),
                getOriginY(),
                bounds.width,
                bounds.height,
                1f, 1f,
                getRotationAngle() );
    }

    public float getRotationAngle() {
        return rotationAngle;
    }

    public void setRotationAngle( float rotationAngle ) {
        this.rotationAngle = rotationAngle;
    }
}
