package com.paulclegg.Util.debug;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Util.ViewportUtils;


/**
 * Created by cle99 on 03/04/2017.
 */

public class DebugCameraController {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    DebugCameraConfig config;

    // -- Attributes --
    private Vector2 position = new Vector2();
    private Vector2 startPosition = new Vector2();
    private float zoom = 1.0f;

    // -- Constructor --
    public DebugCameraController() {

        config = new DebugCameraConfig();
        log.info( "cameraConfig = " + config );

    }

    public void setStartPosition( float x, float y ) {
        startPosition.set( x, y );
        position.set( x, y );
    }

    public void applyTo( OrthographicCamera camera ) {

        camera.position.set( position, 0 );
        camera.zoom = zoom;
        camera.update();

    }

    public void handleDebugInput( float delta ) {

        if ( Gdx.app.getType() != Application.ApplicationType.Desktop ) {
            return;
        }

        float moveSpeed = config.getMoveSpeed() * delta;

        float zoomSpeed = config.getZoomSpeed() * delta;

        // move controls
        if ( config.isLeftPressed() ) {
            moveLeft( moveSpeed );

        } else if ( config.isRightPressed() ) {
            moveRight( moveSpeed );

        } else if ( config.isDownPressed() ) {
            moveDown( moveSpeed );

        } else if ( config.isUpPressed() ) {
            moveUp( moveSpeed );
        }

        // --- zoom controls ---

        if ( config.isZoomInPressed() ) {
            zoomCamera( zoomSpeed );

        } else if ( config.isZoomOutPressed() ) {
            zoomCamera( -zoomSpeed );
        }

        // --- reset controls ---

        if ( config.isResetPressed() ) {
            resetCamera();
        }

        // --- Log Debug controls ---

        if ( config.isLogPressed() ) {
            logDebug();
        }

    }

    // -- Private methods

    private void setZoom( float value ) {
        // MathUtils method clamp sets parameter within bounds - cool!!
        zoom = MathUtils.clamp( value, config.getMaxZoomIn(), config.getMaxZoomOut() );
    }

    private void zoomCamera( float zoomSpeed ) {
        setZoom( zoom + zoomSpeed );
    }

    private void setPosition( float x, float y ) {
        position.set( x, y );
    }

    private void moveCamera( float xSpeed, float ySpeed ) {
        setPosition( position.x + xSpeed, position.y + ySpeed );
    }

    private void moveLeft( float speed ) {
        moveCamera( -speed, 0 );
    }

    private void moveRight( float speed ) {
        moveCamera( speed, 0 );
    }

    private void moveDown( float speed ) {
        moveCamera( 0, -speed );
    }

    private void moveUp( float speed ) {
        moveCamera( 0, speed );
    }

    private void resetCamera() {
        setZoom( 1.0f );
        position.set( startPosition );
    }

    private void logDebug() {
        log.debug( "Position = " + position + " zoom level = " + zoom );
    }
}
