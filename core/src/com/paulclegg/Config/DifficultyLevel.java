package com.paulclegg.Config;

/**
 * Created by cle99 on 05/04/2017.
 */

public enum DifficultyLevel {

    EASY( GameConfig.SPEED ),
    MEDIUM( GameConfig.SPEED ),
    HARD( GameConfig.SPEED );

    private final float obstacleSpeed;

    DifficultyLevel( float obstacleSpeed ) {

        this.obstacleSpeed = obstacleSpeed;
    }

    public float getObstacleSpeed() {

        return obstacleSpeed;
    }

    public boolean isEasy() {
        return this == EASY;
    }

    public boolean isMedium() {
        return this == MEDIUM;
    }

    public boolean isHard() {
        return this == HARD;
    }
}
