package com.paulclegg.Screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.paulclegg.Config.DifficultyLevel;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.common.GameManager;
import com.paulclegg.entity.Background;
import com.paulclegg.entity.Collider;
import com.paulclegg.entity.Lanes;
import com.paulclegg.entity.Player;

/**
 * Created by cle99 on 05/04/2017.
 */

public class GameController {

    // CONSTANTS

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    // ATTRIBUTES
    private final float startPlayerX = ( GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE ) / 2f;
    private final float startPlayerY = 2 - GameConfig.PLAYER_SIZE / 2f;
    private Player player;

    private Array<Collider> colliders = new Array<Collider>();
    private Array<Lanes> lanes = new Array<Lanes>();
    private Background background;

    private float colliderTimer;
    private float scoreTimer;
    private float animatorTimer;
    private float gameTime;
    private int score;
    private int displayScore;
    private int lives = GameConfig.START_LIVES;

    private Pool<Collider> colliderPool;

    // CONSTRUCTOR

    public GameController() {

        init();
    }


    public void init() {

        // create player
        player = new Player();

        background = new Background();
        background.setPosition( 0, 0 );
        background.setSize( GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT );


        // calculate position

        player.setPosition( startPlayerX, startPlayerY );

        colliderPool = Pools.get( Collider.class, 40 );

        for ( int i = 0; i <= 7; i++ ) {
            lanes.add( new Lanes() );
            lanes.get( i ).setPosition( 0, i * GameConfig.WORLD_HEIGHT / 6f );


        }

    }

    // public methods

    public void update( float delta ) {

        if ( isGameOver() ) {

            return;
        }

        updatePlayer();
        updateBackground(delta);
        updateColliders( delta );
        updateScore( delta );
        updateDisplayScore( delta );

        if ( isPlayerCollidingWithColliders() ) {

            lives--;

            if ( isGameOver() ) {
                // update high scores
                GameManager.INSTANCE.updateHighScore( score );
            } else {
                restart();
            }
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Array<Collider> getObstacles() {
        return colliders;
    }

    public Array<Lanes> getLanes() {
        return lanes;
    }

    public Background getBackground() {
        return background;
    }

    public int getDisplayScore() {
        return displayScore;
    }

    public int getLives() {
        return lives;
    }

    // private methods

    private void restart() {
        colliderPool.freeAll( colliders );
        colliders.clear();
        player.setPosition( startPlayerX, startPlayerY );

    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    private boolean isPlayerCollidingWithColliders() {
        for ( Collider collider : colliders ) {
            if ( !collider.isCollided() && collider.playerCollision( player ) ) {
                return true;
            }
        }
        return false;
    }

    private void updateBackground( float delta ) {
        for ( Lanes lane : lanes ) {
            lane.update();
            if ( lane.getY() < -lane.getHeight() ) {
                lane.reset();
            }
            lane.setPosition( lane.getX(), lane.getY() );

        }
    }


    private void updatePlayer() {

        float xSpeed = 0;

        if ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ) {
            xSpeed = GameConfig.MAX_PLAYER_X_SPEED;

        } else if ( Gdx.input.isKeyPressed( Input.Keys.LEFT ) ) {
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED;
        }

        player.setX( player.getX() + xSpeed );

        float playerX = MathUtils.clamp(
                player.getX(),                                  // value to be limited
                0f,                                             // min value
                GameConfig.WORLD_WIDTH - player.getWidth()      // max valuePlayer
        );

        player.setPosition( playerX, player.getY() );

        // set animation parameters
        float dT = Gdx.graphics.getDeltaTime();
        animatorTimer += dT;
        if ( animatorTimer >= GameConfig.POLICE_ANIMATION_SPEED ) {
            // update the animator index after speficied period elapsed
            player.updateAnimator();
            animatorTimer = 0f;  //reset animator timer
        }

    }

    private void updateColliders( float delta ) {
        for ( Collider collider : colliders ) {
            collider.update();
        }
        createNewObject( delta );
        removePassedObjects();  // remove obstacles that have passed off screen
    }

    private void createNewObject( float delta ) {
        float speedModifier;
        colliderTimer += delta;

        if ( colliderTimer >= GameConfig.OBSTACLE_SPAWN_TIME ) {
            float min = 0.0f;
            float max = GameConfig.WORLD_WIDTH - GameConfig.COLLIDER_SIZE;

            float obstacleX = getObstacleX();
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Collider collider = colliderPool.obtain();
            DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
            speedModifier = getSpeedMultiplier( obstacleX, max - min );

            collider.setPosition( obstacleX, obstacleY );
            collider.setYSpeed( difficultyLevel.getObstacleSpeed() * speedModifier );

            colliders.add( collider );
            colliderTimer = 0.0f;
        }
    }

    private float getObstacleX() {
        float lane = MathUtils.random( 0, 5 );
        float laneWidth = GameConfig.WORLD_WIDTH / 6f;
        float margin = ( laneWidth - GameConfig.COLLIDER_SIZE ) / 2;
        return lane + margin;
    }


    private void removePassedObjects() {
        if ( colliders.size > 0 ) {
            float minObstacleY = -GameConfig.COLLIDER_SIZE;
            for ( Collider collider : colliders ) {

                if ( collider.getY() < minObstacleY ) {
                    colliders.removeValue( collider, true );
                    colliderPool.free( collider );
                }

            }
            // TODO delete code below when modified code above tested
//            Collider first = colliders.first();
//            float minObstacleY = -GameConfig.OBSTACLE_SIZE;
//            if ( first.getY() < minObstacleY ) {
//                colliders.removeValue( first, true );
//                colliderPool.free( first );
//            }
        }
    }


    private void updateScore( float delta ) {

        scoreTimer += delta;

        if ( scoreTimer >= GameConfig.MAX_SCORE_TIME ) {
            score += MathUtils.random( 1, 5 );

            scoreTimer = 0f;
        }
    }

    private void updateDisplayScore( float delta ) {

        if ( displayScore < score ) {
            displayScore = Math.min(
                    score,
                    displayScore + ( int ) ( 60 * delta )
            );
        }

    }

    private float getSpeedMultiplier( float x, float availableWidth ) {
        final float TWO_PI = 2f * ( float ) Math.PI;
        float dT = Gdx.graphics.getDeltaTime() * 5;
        gameTime = gameTime + dT;

        // convert position x to radians (determined as position / screenwidth * 2* Pi
        float xRange = x / availableWidth * TWO_PI;

        float radian = gameTime + xRange;

        // return float between 0.0 and 1.0
        float sineFunction = ( 1 + ( float ) Math.sin( radian ) ) / 2;
        return 1 + sineFunction / 3;
    }

}
