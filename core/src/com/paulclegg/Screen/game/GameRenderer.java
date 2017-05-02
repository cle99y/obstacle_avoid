package com.paulclegg.Screen.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.Util.GdxUtils;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.Util.debug.DebugCameraController;
import com.paulclegg.assets.AssetDescriptors;
import com.paulclegg.assets.RegionNames;
import com.paulclegg.entity.Background;
import com.paulclegg.entity.Collider;
import com.paulclegg.entity.Lanes;
import com.paulclegg.entity.Player;


/**
 * Created by cle99 on 05/04/2017.
 */

public class GameRenderer implements Disposable {

    // attributes

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );
    private final GlyphLayout layout = new GlyphLayout();
    private final SpriteBatch sb;
    private OrthographicCamera camera;
    private OrthographicCamera hudCamera;
    private ShapeRenderer renderer;
    private Viewport viewport;
    private Viewport hudViewport;
    private BitmapFont hudFont;
    private DebugCameraController debugCameraController;
    private GameController controller;
    private AssetManager assetManager;

    // Textures
    private TextureRegion[] playerRegion = new TextureRegion[RegionNames.PLAYER.length];
    private TextureRegion[] colliderRegion = new TextureRegion[RegionNames.COLLIDER.length];
    private TextureRegion backgroundRegion;
    private TextureRegion lanesRegion;


    public GameRenderer( SpriteBatch sb, AssetManager am, GameController controller ) {

        this.controller = controller;
        this.assetManager = am;
        this.sb = sb;

        init();
    }

    // init method
    private void init() {

        String[] police = RegionNames.PLAYER;

        camera = new OrthographicCamera();
        viewport = new FitViewport( GameConfig.WORLD_WIDTH, GameConfig.WORLD_HEIGHT, camera );
        renderer = new ShapeRenderer();

        hudCamera = new OrthographicCamera();
        hudViewport = new FitViewport( GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT, hudCamera );

        hudFont = assetManager.get( AssetDescriptors.UIFONT );

        debugCameraController = new DebugCameraController();
        debugCameraController.setStartPosition( GameConfig.WORLD_CENTER_X, GameConfig.WORLD_CENTER_Y );

        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );


        for ( int i = 0; i < RegionNames.PLAYER.length; i++ ) {
            playerRegion[i] = gameplayAtlas.findRegion( police[i] );
        }

        for ( int i = 0; i < RegionNames.COLLIDER.length; i++ ) {
            colliderRegion[i] = gameplayAtlas.findRegion( RegionNames.COLLIDER[i] );
        }

        backgroundRegion = gameplayAtlas.findRegion( RegionNames.BACKGROUND );
        lanesRegion = gameplayAtlas.findRegion( RegionNames.LANES );


    }
    // public methods

    public void render( float delta ) {

        debugCameraController.handleDebugInput( delta );
        debugCameraController.applyTo( camera );

        // clear screen
        GdxUtils.clearScreen();

        // render gameplay assets
        renderGamePlay();

        // render HUD
        renderUI();

        // render graphics
        renderDebug();

    }

    public void resize( int width, int height ) {

        viewport.update( width, height, true );
        hudViewport.update( width, height, true );
        ViewportUtils.debugPixelPerUnit( viewport );

    }

    public void dispose() {
        renderer.dispose();
    }

    // private methods

    private void renderGamePlay() {

        viewport.apply();
        sb.setProjectionMatrix( camera.combined );
        sb.begin();

        drawGamePlay();

        sb.end();
    }

    private void renderUI() {

        hudViewport.apply();
        sb.setProjectionMatrix( hudCamera.combined );
        sb.begin();

        drawUI();

        sb.end();
    }


    private void renderDebug() {

        viewport.apply();
        renderer.setProjectionMatrix( camera.combined );
        renderer.begin( ShapeRenderer.ShapeType.Line );

        drawDebug();

        renderer.end();

        ViewportUtils.drawGrid( viewport, renderer );
    }

    private void drawGamePlay() {

        //draw background
        Background background = controller.getBackground();
        Array<Lanes> lanes = controller.getLanes();


            sb.draw( backgroundRegion,
                    background.getX(),
                    background.getY(),
                    background.getWidth(),
                    background.getHeight()
            );

        for ( Lanes lane: lanes) {
            sb.draw (lanesRegion,
                    lane.getX(),
                    lane.getY(),
                    lane.getWidth(),
                    lane.getHeight()
            );
        }




        // draw obstacles
        for ( Collider collider : controller.getObstacles() ) {
            sb.draw( colliderRegion[collider.getColliderType()],
                    collider.getX(),
                    collider.getY(),
                    collider.getWidth(),
                    collider.getHeight()
            );
        }

        // draw player

        Player player = controller.getPlayer();
        sb.draw( playerRegion[player.getAnimator()],
                player.getX(),
                player.getY(),
                player.getWidth(),
                player.getHeight()
        );

    }

    private void drawUI() {

        String livesText = "LIVES: " + controller.getLives();
        String scoreText = "SCORE: " + controller.getDisplayScore();

        layout.setText( hudFont, livesText );
        hudFont.draw( sb, livesText,
                20,
                GameConfig.HUD_HEIGHT - layout.height
        );

        layout.setText( hudFont, scoreText );
        hudFont.draw( sb, scoreText,
                GameConfig.HUD_WIDTH - 20 - layout.width,
                GameConfig.HUD_HEIGHT - layout.height
        );

    }

    private void drawDebug() {

        controller.getPlayer().drawDebug( renderer );

        for ( Collider collider : controller.getObstacles() ) {
            collider.drawDebug( renderer );
        }

    }
}
