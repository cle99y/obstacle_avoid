package com.paulclegg.Config;

/**
 * Created by cle99 on 03/04/2017.
 */

public class GameConfig {

    public static final float WIDTH = 720; // pixels
    public static final float HEIGHT = 1280; // pixels

    public static final float HUD_WIDTH = 720; // pixels
    public static final float HUD_HEIGHT = 1280; // pixels

    public static final float WORLD_WIDTH = 6.0f; // World Units
    public static final float WORLD_HEIGHT = 10.0f; // World Units

    public static final float WORLD_CENTER_X = WORLD_WIDTH / 2f; // World Units
    public static final float WORLD_CENTER_Y = WORLD_HEIGHT / 2f; // World Units

    public static final float OBSTACLE_SPAWN_TIME = 0.25f; // player speed
    public static final float MAX_PLAYER_X_SPEED = 0.15f; // spawn obstacle every interval
    public static final float MAX_SCORE_TIME = 1.25f; // update score every interval

    public static final int START_LIVES = 3; // lives on start

    public static final float EASY_OBSTACLE_SPEED = 0.1f;
    public static final float MEDIUM_OBSTACLE_SPEED = 0.13f;
    public static final float HARD_OBSTACLE_SPEED = 0.15f;

    public static final float OBSTACLE_BOUNDS_RADIUS = 0.3f;
    public static final float OBSTACLE_SIZE = 2 * OBSTACLE_BOUNDS_RADIUS;
    public static final float PLAYER_BOUNDS_RADIUS = 0.4f;
    public static final float PLAYER_SIZE = 2 * PLAYER_BOUNDS_RADIUS;


    private GameConfig() {
    }  // not to create an instance of class


}
