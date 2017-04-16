package com.paulclegg.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.paulclegg.Config.DifficultyLevel;
import com.paulclegg.ObstacleGame;

/**
 * Created by cle99 on 14/04/2017.
 */

public class GameManager {

    public static final GameManager INSTANCE = new GameManager();

    private static final String HIGH_SCORE_KEY = "highscore";
    private static final String DIFFICULTY_KEY = "difficulty";


    private int highScore;
    private Preferences PREFS;
    private DifficultyLevel difficultyLevel;

    private GameManager() {
        PREFS = Gdx.app.getPreferences( ObstacleGame.class.getSimpleName() );

        highScore = PREFS.getInteger( HIGH_SCORE_KEY, 0 );  // default value 0 if no high score
        String difficultyNName = PREFS.getString( DIFFICULTY_KEY, DifficultyLevel.MEDIUM.name() );
        difficultyLevel = DifficultyLevel.valueOf( difficultyNName );


    }

    public String getHighScore() {
        return String.valueOf( highScore );
    }

    public void updateHighScore( int score ) {
        if ( score < highScore ) {
            return;
        } else {
            PREFS.putInteger( HIGH_SCORE_KEY, score );
            PREFS.flush();
            highScore = score;
        }
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel( DifficultyLevel newDifficultyLevel ) {
        if ( getDifficultyLevel() == newDifficultyLevel ) {
            return;
        }

        difficultyLevel = newDifficultyLevel;
        PREFS.putString( DIFFICULTY_KEY, difficultyLevel.name() );
        PREFS.flush();
    }

}
