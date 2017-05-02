package com.paulclegg.Screen.loading;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Screen.menu.MenuScreen;
import com.paulclegg.Util.GdxUtils;
import com.paulclegg.assets.AssetDescriptors;

/**
 * Created by cle99 on 11/04/2017.
 */

public class LoadingScreen extends ScreenAdapter {

    // == constants ==
    private static final Logger log = new Logger( LoadingScreen.class.getName(), Logger.DEBUG );

    private static final float PROGRESS_BAR_WIDTH = GameConfig.HUD_WIDTH / 2f; // world units
    private static final float PROGRESS_BAR_HEIGHT = 60; // world units
    private final ObstacleGame game;
    private final AssetManager assetManager;
    // == attributes ==
    private OrthographicCamera camera;
    private Viewport viewport;
    private ShapeRenderer renderer;
    private float progress;
    private float waitTime = 0.75f;
    private boolean changeScreen;


    // == constructors ==
    public LoadingScreen( ObstacleGame game ) {
        this.game = game;
        assetManager = game.getAssetManager();
    }

    // == public methods ==
    @Override
    public void show() {
        log.debug( "show" );
        camera = new OrthographicCamera();
        viewport = new FitViewport( GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, camera );
        renderer = new ShapeRenderer();

        assetManager.load( AssetDescriptors.UIFONT );
        assetManager.load( AssetDescriptors.GAME_PLAY );
        //assetManager.load( AssetDescriptors.UI );
        assetManager.load( AssetDescriptors.UI_SKIN );
    }

    @Override
    public void render( float delta ) {
        update( delta );

        GdxUtils.clearScreen();
        viewport.apply();
        renderer.setProjectionMatrix( camera.combined );
        renderer.begin( ShapeRenderer.ShapeType.Filled );

        draw();

        renderer.end();

        if ( changeScreen ) {
            game.setScreen( new MenuScreen( game ) );
        }
    }

    @Override
    public void resize( int width, int height ) {
        viewport.update( width, height, true );
    }

    @Override
    public void hide() {
        log.debug( "hide" );
        // NOTE: screens don't dispose automatically
        dispose();
    }

    @Override
    public void dispose() {
        log.debug( "dispose" );
        renderer.dispose();
        renderer = null;
    }

    // == private methods ==
    private void update( float delta ) {
        // progress is between 0 and 1
        progress = assetManager.getProgress();

        // update returns true when all assets are loaded
        if ( assetManager.update() ) {
            waitTime -= delta;

            if ( waitTime <= 0 ) {
                changeScreen = true;
            }
        }
    }

    private void draw() {
        float progressBarX = ( GameConfig.HUD_WIDTH - PROGRESS_BAR_WIDTH ) / 2f;
        float progressBarY = ( GameConfig.HUD_HEIGHT - PROGRESS_BAR_HEIGHT ) / 2f;

        renderer.rect( progressBarX, progressBarY,
                progress * PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT
        );
    }

}

