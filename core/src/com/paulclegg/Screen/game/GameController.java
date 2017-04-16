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
import com.paulclegg.entity.Obstacle;
import com.paulclegg.entity.Player;

/**
 * Created by cle99 on 05/04/2017.
 */

public class GameController {

    // CONSTANTS

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    // ATTRIBUTES
    private final float startPlayerX = ( GameConfig.WORLD_WIDTH - GameConfig.PLAYER_SIZE ) / 2f;
    private final float startPlayerY = 1 - GameConfig.PLAYER_SIZE / 2f;
    private Player player;
    private Array<Obstacle> obstacles = new Array<Obstacle>();
    private Background background;
    private float obstacleTimer;
    private float scoreTimer;
    private int score;
    private int displayScore;
    private int lives = GameConfig.START_LIVES;
    private Pool<Obstacle> obstaclePool;

    // CONSTRUCTOR

    public GameController() {
        init();
    }


    public void init() {

        // create player
        player = new Player();


        // calculate position

        player.setPosition( startPlayerX, startPlayerY );

        obstaclePool = Pools.get( Obstacle.class, 40 );

        background = new Background();
        background.setPosition( 0, 0 );
        background.setSize( GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT );
    }

    // public methods

    public void update( float delta ) {

        if ( isGameOver() ) {

            return;
        }

        updatePlayer();
        updateObstacles( delta );
        updateScore( delta );
        updateDisplayScore( delta );

        if ( isPlayerCollidingWithObstacles() ) {

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

    public Array<Obstacle> getObstacles() {
        return obstacles;
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
        obstaclePool.freeAll( obstacles );
        obstacles.clear();
        player.setPosition( startPlayerX, startPlayerY );

    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    private boolean isPlayerCollidingWithObstacles() {
        for ( Obstacle obstacle : obstacles ) {
            if ( !obstacle.isCollided() && obstacle.playerCollision( player ) ) {
                return true;
            }
        }
        return false;
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

    }

    private void updateObstacles( float delta ) {
        for ( Obstacle obstacle : obstacles ) {
            obstacle.update();
        }
        createNewObstacle( delta );
        removePassedObstacles();  // remove obstacles that have passed off screen
    }

    private void createNewObstacle( float delta ) {
        obstacleTimer += delta;

        if ( obstacleTimer >= GameConfig.OBSTACLE_SPAWN_TIME ) {
            float min = 0.0f;
            float max = GameConfig.WORLD_WIDTH - GameConfig.OBSTACLE_SIZE;

            float obstacleX = MathUtils.random( min, max );
            float obstacleY = GameConfig.WORLD_HEIGHT;

            Obstacle obstacle = obstaclePool.obtain();
            DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
            obstacle.setYSpeed( difficultyLevel.getObstacleSpeed() );
            obstacle.setPosition( obstacleX, obstacleY );

            obstacles.add( obstacle );
            obstacleTimer = 0.0f;
        }
    }


    private void removePassedObstacles() {
        if ( obstacles.size > 0 ) {
            Obstacle first = obstacles.first();

            float minObstacleY = -GameConfig.OBSTACLE_SIZE;

            if ( first.getY() < minObstacleY ) {
                obstacles.removeValue( first, true );
                obstaclePool.free( first );
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

//    private float getSpeedMultiplier(float delta, float x) {
//        gameTime += delta ;
//        float xRange = x / GameConfig.WORLD_WIDTH * 2 * (float) Math.PI;
//        float radian =  gameTime / (2f * (float) Math.PI) + xRange;
//
//        return (float) Math.sin(radian);
//    }

}
