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

    public static final float OBSTACLE_SPAWN_TIME = 0.5f; // spawn obstacle every interval
    public static final float LANE_LINE_SPAWN_TIME = 0.5f; // spawn lane delimiting lines
    public static final float LANE_LINE_Y_SPEED = 0.18f;
    public static final float MAX_PLAYER_X_SPEED = 0.08f; // player speed
    public static final float MAX_SCORE_TIME = 1.25f; // update score every interval

    public static final int START_LIVES = 3; // lives on start

    public static final float EASY_SPEED = 0.1f;
    public static final float MEDIUM_SPEED = 0.13f;
    public static final float HARD_SPEED = 0.15f;

    public static final float POLICE_ANIMATION_SPEED = 0.3f;  // interval for changing texture

    public static final float COLLIDER_BOUNDS_RADIUS = 0.3f;
    public static final float COLLIDER_SIZE = 2 * COLLIDER_BOUNDS_RADIUS;

    public static final float COLLIDER_WIDTH = COLLIDER_SIZE;
    public static final float COLLIDER_LENGTH = COLLIDER_SIZE / 185 * 350;

    public static final float HEALTH_TOKEN_BOUNDS_RADIUS = 0.25f;
    public static final float HEALTH_TOKEN_SIZE = 2 * HEALTH_TOKEN_BOUNDS_RADIUS;

    public static final float POINTS_TOKEN_BOUNDS_RADIUS = 0.2f;
    public static final float POINTS_TOKEN_SIZE = 2 * POINTS_TOKEN_BOUNDS_RADIUS;

    public static final float PLAYER_BOUNDS_RADIUS = 0.3f;
    public static final float PLAYER_SIZE = 2 * PLAYER_BOUNDS_RADIUS;

    public static final float PLAYER_WIDTH = PLAYER_SIZE;
    public static final float PLAYER_LENGTH = PLAYER_SIZE / 185 * 350;


    private GameConfig() {
    }  // not to create an instance of class


}
