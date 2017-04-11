package com.paulclegg.Screen.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Util.ViewportUtils;

/**
 * Created by cle99 on 05/04/2017.
 */

public class GameScreen implements Screen {

    // CONSTANTS

    private static final Logger log = new Logger(ViewportUtils.class.getName(), Logger.DEBUG);

    // attributes

    private final ObstacleGame game;
    private final AssetManager assetManager;

    GameController controller;
    GameRenderer gameRenderer;

    public GameScreen(ObstacleGame game) {

        this.game = game;
        assetManager = game.getAssetManager();

    }

    // CONSTRUCTOR


    @Override
    public void show() {

        controller = new GameController();
        gameRenderer = new GameRenderer(assetManager, controller);
    }

    @Override
    public void render(float delta) {

        controller.update(delta);
        gameRenderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {

        gameRenderer.resize(width, height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

        gameRenderer.dispose();
        assetManager.dispose();
    }
}
