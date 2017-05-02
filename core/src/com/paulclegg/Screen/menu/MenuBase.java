package com.paulclegg.Screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Util.GdxUtils;

/**
 * Created by cle99 on 14/04/2017.
 */

public abstract class MenuBase extends ScreenAdapter {

    protected final ObstacleGame game;
    protected final AssetManager assetManager;

    private Viewport viewport;
    private Stage stage;

    public MenuBase( ObstacleGame game ) {
        this.game = game;
        assetManager = game.getAssetManager();
    }


    @Override
    public void show() {

        viewport = new FitViewport( GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT );
        stage = new Stage( viewport, game.getSpriteBatch() );

        Gdx.input.setInputProcessor( stage );

        stage.addActor( createUI() );
    }

    @Override
    public void render( float delta ) {
        GdxUtils.clearScreen();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize( int width, int height ) {
        viewport.update( width, height, true );
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    protected abstract Actor createUI();

}
