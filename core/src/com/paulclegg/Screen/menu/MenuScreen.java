package com.paulclegg.Screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Screen.game.GameScreen;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.assets.AssetDescriptors;
import com.paulclegg.assets.RegionNames;

/**
 * Created by cle99 on 14/04/2017.
 */

public class MenuScreen extends MenuBase {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    public MenuScreen( ObstacleGame game ) {
        super( game );
    }

    // -- private methods --

    @Override
    protected Actor createUI() {
        Table table = new Table();

        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );
        TextureRegion backgroundRegion = gameplayAtlas.findRegion( RegionNames.UI_BACKGROUND );
        Skin uiSkin = assetManager.get( AssetDescriptors.UI_SKIN );

        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );

        // play button
        TextButton playBtn = new TextButton( "PLAY", uiSkin );
        playBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                play();
            }
        } );

        // high score button
        TextButton highScoreBtn = new TextButton( "HIGH SCORE", uiSkin );
        highScoreBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                highScore();
            }
        } );


        // options button
        TextButton optionsBtn = new TextButton( "OPTIONS", uiSkin );
        optionsBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                options();
            }
        } );

        // quit button
        TextButton quitBtn = new TextButton( "QUIT", uiSkin );
        quitBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                quit();
            }
        } );


        Table btnTable = new Table( uiSkin );
        btnTable.defaults().pad( 20 );
        btnTable.setBackground( RegionNames.PANEL );
        btnTable.add( playBtn ).row();
        btnTable.add( highScoreBtn ).row();
        btnTable.add( optionsBtn ).row();
        btnTable.add( quitBtn );
        btnTable.center();

        // set up table

        table.add( btnTable );
        table.center();
        table.setFillParent( true );
        table.pack();

        return table;

    }

    private void play() {
        log.debug( "play" );
        game.setScreen( new GameScreen( game ) );
    }

    private void highScore() {
        log.debug( "high score" );
        game.setScreen( new HighScore( game ) );
    }

    private void options() {
        log.debug( "options" );
        game.setScreen( new OptionsScreen( game ) );
    }

    private void quit() {
        log.debug( "exit game" );
        Gdx.app.exit();
    }


}
