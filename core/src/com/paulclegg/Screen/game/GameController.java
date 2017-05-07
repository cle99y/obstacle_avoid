package com.paulclegg.Screen.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.Pool;
import com.badlogic.gdx.utils.Pools;
import com.paulclegg.Config.DifficultyLevel;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.common.EntityFactory;
import com.paulclegg.common.GameManager;
import com.paulclegg.entity.Background;
import com.paulclegg.entity.Collider;
import com.paulclegg.entity.Lanes;
import com.paulclegg.entity.PlayerSprite;

/**
 * Created by cle99 on 05/04/2017.
 */

public class GameController {

    // CONSTANTS

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );
    public static final String TOP = "top";
    public static final String BOTTOM = "bottom";

    // ATTRIBUTES
    private ObstacleGame game;
    private final AssetManager assetManager;
    private EntityFactory factory;

    private final float startPlayerX = ( GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE ) / 2f;
    private final float startPlayerY = 2 - GameConfig.PLAYER_SIZE / 2f;
    private PlayerSprite player;

    private Array<Collider> colliders = new Array<Collider>();
    private Array<Lanes> lanes = new Array<Lanes>();
    private Background background;

    private float laneSpeed, colliderSpeed;

    private float colliderTimer;
    private float scoreTimer;
    private float animatorTimer;
    private float gameTime;

    private float gameDistance;
    private float laneDistance;
    private float colliderDistance;

    private int score;
    private int displayScore;
    private int lives = GameConfig.START_LIVES;

    public boolean debug;
    public boolean turningLeft, turningRight;
    boolean isTopEdge;

    private Pool<Collider> colliderPool;
    private Pool<Lanes> lanesPool;

    // -- CONSTRUCTORS --

    public GameController( ObstacleGame game ) {
        this.game = game;
        assetManager = game.getAssetManager();
        factory = new EntityFactory( assetManager );
        init();
    }


    public void init() {

        // create player
        player = factory.createPlayer();
        debug = false;
        isTopEdge = false;

        background = new Background();
        background.setPosition( 0, 0 );
        background.setSize( GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT );

        colliderSpeed = GameConfig.SPEED;
        laneSpeed = GameConfig.LANE_LINE_Y_SPEED;


        // calculate position

        player.setPosition( startPlayerX, startPlayerY );

        colliderPool = Pools.get( Collider.class, 40 );
        lanesPool = Pools.get( Lanes.class, 10 );


        for ( int i = 0; i <= 5; i++ ) {
            // initial state of lanes on the road
            addNewLanes().setPosition( 0, i * GameConfig.LANES_HEIGHT );
        }

    }

    private Lanes addNewLanes() {
        Lanes lanesBG = lanesPool.obtain();
        lanes.add( lanesBG );
        return lanesBG;
    }

    private void addNewLanes( float distance ) {
        if ( laneDistance >= distance ) {
            laneDistance = 0f;
            addNewLanes().reset();
        }
    }

    // public methods

    public void update( float delta ) {

        if ( isGameOver() ) {

            return;
        }


        updateDebug();
        updatePlayer();
        updateBackground( delta );
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

    public PlayerSprite getPlayer() {
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
        lanesPool.freeAll( lanes );
        colliders.clear();
        lanes.clear();
        player.setPosition( startPlayerX, startPlayerY );

    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    private boolean isPlayerCollidingWithColliders() {
//        for ( Collider collider : colliders ) {
//            if ( !collider.isCollided() && collider.playerCollision( player ) ) {
//                return true;
//            }
//        }
        return false;
    }

    private void updateBackground( float delta ) {
        for ( Lanes lane : lanes ) {
            lane.update();
//            if ( lane.getY() < -lane.getHeight() ) {
//                lane.reset();
//            }
            lane.setPosition( lane.getX(), lane.getY() );
        }
        gameDistance += Lanes.getYSpeed();  // total game distance
        laneDistance += Lanes.getYSpeed();  // distance since last lane spawn

        removePassedLanes();
        addNewLanes( GameConfig.LANE_LINE_SPAWN_DISTANCE );
        adjustGameSpeed();
    }

    private void adjustGameSpeed() {

        /*  function to reduce or increase the game speed based on events
            initially just set to use keyboard keys for testing
        */

        if ( Gdx.input.isKeyPressed( Input.Keys.DOWN ) ) {
            if ( Lanes.getYSpeed() > 0f ) {
                Lanes.setYSpeed( Lanes.getYSpeed() - GameConfig.ACCELERATION );
            }
        }

        if ( Gdx.input.isKeyPressed( Input.Keys.UP ) ) {
            if ( Lanes.getYSpeed() < GameConfig.MAX_LANE_LINE_Y_SPEED ) {
                Lanes.setYSpeed( Lanes.getYSpeed() + GameConfig.ACCELERATION );
            }
        }
    }

    public void updateDebug() {
        if ( Gdx.input.isKeyJustPressed( Input.Keys.F12 ) ) {
            debug = !debug;
        }
    }


    private void updatePlayer() {

        float xSpeed = 0;
        float xSpeedAdjuster = Lanes.getYSpeed() / GameConfig.MAX_LANE_LINE_Y_SPEED;

        // prevent rotation when player speed is zero and to scale rotation according to speed
        // (smaller rotation at high speed)
        float rotation = ( Lanes.getYSpeed() <= 0f )
                ? 10f
                : GameConfig.PLAYER_ROTATION_POTENTIAL * Lanes.getYSpeed() / GameConfig.MAX_LANE_LINE_Y_SPEED;
        log.debug( "reoation " + rotation );

        if ( Gdx.input.isKeyPressed( Input.Keys.RIGHT ) ) {

            xSpeed = GameConfig.MAX_PLAYER_X_SPEED * xSpeedAdjuster;
            turningRight = true;
            player.setRotationAngle( 360f - GameConfig.PLAYER_MAX_ROTATION + rotation );
            player.setRotation( player.getRotationAngle() );

        } else if ( Gdx.input.isKeyPressed( Input.Keys.LEFT ) ) {
            xSpeed = -GameConfig.MAX_PLAYER_X_SPEED * xSpeedAdjuster;
            turningLeft = true;
            player.setRotationAngle( GameConfig.PLAYER_MAX_ROTATION - rotation );
            player.setRotation( player.getRotationAngle() );
        } else {
            turningRight = false;
            turningLeft = false;
            player.setRotationAngle( 0.0f );
            player.setRotation( player.getRotationAngle() );
        }

        player.setX( player.getX() + xSpeed );

        float playerX = MathUtils.clamp(
                player.getX(),                                  // value to be limited
                0f,                                             // min value
                GameConfig.WORLD_WIDTH - player.getWidth()      // max valuePlayer
        );

        player.setPosition( playerX, player.getY() );
        player.updateBounds();

        // set animation parameters
        float dT = Gdx.graphics.getDeltaTime();
        animatorTimer += dT;
        if ( animatorTimer >= GameConfig.POLICE_ANIMATION_SPEED ) {
            // update the animator index after speficied period elapsed
            //player.updateAnimator();
            animatorTimer = 0f;  //reset animator timer
        }


    }

    private void updateColliders( float delta ) {
        for ( Collider collider : colliders ) {
            float speed = Lanes.getYSpeed() - GameConfig.LANE_LINE_Y_SPEED;
            colliderSpeed = collider.getInitialSpeed() + speed;
            collider.setYSpeed( colliderSpeed );


            collider.update();

        }
        String edge = removePassedColliders();  // remove obstacles that have passed off screen
        createNewObject( delta, edge );

    }

    private void createNewObject( float delta, String edge ) {
        float speedModifier;
        colliderDistance += colliderSpeed;

        if ( Math.abs( colliderDistance ) >= GameConfig.OBSTACLE_SPAWN_DISTANCE ) {
            float min = 0.0f;
            float max = GameConfig.WORLD_WIDTH - GameConfig.COLLIDER_SIZE;

            float obstacleX = getObstacleX();
            float obstacleY = GameConfig.WORLD_HEIGHT;
            if ( edge.equals( TOP ) ) {
                obstacleY = -GameConfig.COLLIDER_SIZE;
            }

            Collider collider = colliderPool.obtain();
            DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
            speedModifier = getSpeedMultiplier( obstacleX, max - min );

            collider.setPosition( obstacleX, obstacleY );
            collider.setInitialSpeed( GameConfig.SPEED * speedModifier );
            adjustNewColliderSpeed( collider );

            //log.debug( "collider Y speed " + collider.getYSpeed() );

            colliders.add( collider );
            colliderDistance = 0.0f;
        }

    }

    private float getObstacleX() {
        float lane = MathUtils.random( 0, 5 );
        float laneWidth = GameConfig.WORLD_WIDTH / 6f;
        float margin = ( laneWidth - GameConfig.COLLIDER_SIZE ) / 2;
        return lane + margin;
    }

    private void adjustNewColliderSpeed( Collider newCollider ) {
        for ( Collider collider : colliders ) {
            if ( collider.getX() == newCollider.getX() ) {
                newCollider.setYSpeed( collider.getYSpeed() );
            }

        }
    }

    private String removePassedColliders() {

        if ( colliders.size > 0 ) {
            float minColliderY = -GameConfig.COLLIDER_SIZE;
            float maxColliderY = GameConfig.WORLD_HEIGHT;
            for ( Collider collider : colliders ) {

                if ( collider.getY() < minColliderY || collider.getY() > maxColliderY ) {
                    isTopEdge = collider.getY() > maxColliderY;
                    colliders.removeValue( collider, true );
                    colliderPool.free( collider );
                }
            }
        }
        if ( isTopEdge ) {
            return TOP;
        }
        return BOTTOM;
    }

    private void removePassedLanes() {
        if ( lanes.size > 0 ) {
            float minLanesY = -GameConfig.LANE_LINE_SPAWN_DISTANCE;
            for ( Lanes lane : lanes ) {

                if ( lane.getY() < minLanesY ) {
                    lanes.removeValue( lane, true );
                    lanesPool.free( lane );
                }
            }
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
