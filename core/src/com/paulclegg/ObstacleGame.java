package com.paulclegg;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Screen.loading.LoadingScreen;

public class ObstacleGame extends Game {

    private AssetManager assetManager;
    private SpriteBatch sb;

    @Override
    public void create() {

        Gdx.app.setLogLevel( Application.LOG_DEBUG );

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel( Logger.DEBUG );

        sb = new SpriteBatch();

        setScreen( new LoadingScreen( this ) );
    }

    @Override
    public void dispose() {
        assetManager.dispose();
        sb.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getSpriteBatch() {
        return sb;
    }
}
