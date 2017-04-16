package com.paulclegg.Screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

        TextureAtlas uiAtlas = assetManager.get( AssetDescriptors.UI );
        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );

        TextureRegion backgroundRegion = gameplayAtlas.findRegion( RegionNames.BACKGROUND );
        TextureRegion panelRegion = uiAtlas.findRegion( RegionNames.PANEL );

        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );

        // play button
        ImageButton playBtn = createButton( uiAtlas, RegionNames.PLAY, RegionNames.PLAY_PRESSED );
        playBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                play();
            }
        } );

        // high score button

        ImageButton highScoreBtn = createButton( uiAtlas, RegionNames.HIGH_SCORE, RegionNames.HIGH_SCORE_PRESSED );
        highScoreBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                highScore();
            }
        } );


        // options button

        ImageButton optionsBtn = createButton( uiAtlas, RegionNames.OPTIONS, RegionNames.OPTIONS_PRESSED );
        optionsBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                options();
            }
        } );

        // quit button


        Table btnTable = new Table();
        btnTable.defaults().pad( 20 );
        btnTable.setBackground( new TextureRegionDrawable( panelRegion ) );

        btnTable.add( playBtn ).row();
        btnTable.add( highScoreBtn ).row();
        btnTable.add( optionsBtn ).row();
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


}
