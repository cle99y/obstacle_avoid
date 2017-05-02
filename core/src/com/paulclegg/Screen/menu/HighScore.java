package com.paulclegg.Screen.menu;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.assets.AssetDescriptors;
import com.paulclegg.assets.RegionNames;
import com.paulclegg.common.GameManager;

/**
 * Created by cle99 on 14/04/2017.
 */

public class HighScore extends MenuBase {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    private BitmapFont uiFont;


    public HighScore( ObstacleGame game ) {
        super( game );
    }

    @Override
    protected Actor createUI() {
        Table table = new Table();

        Skin uiSkin = assetManager.get( AssetDescriptors.UI_SKIN );
        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );

        TextureRegion backgroundRegion = gameplayAtlas.findRegion( RegionNames.BACKGROUND );


        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );

        // high score text
        Label highScoreText = new Label( "HIGH SCORE", uiSkin );

        // high score value
        Label highScoreValue = new Label( GameManager.INSTANCE.getHighScore(), uiSkin );

        // back button
        TextButton backBtn = new TextButton( "BACK", uiSkin );
        backBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                goBack();
            }
        } );

        Table highScoreTable = new Table( uiSkin );
        highScoreTable.defaults().pad( 20 );
        highScoreTable.setBackground( RegionNames.PANEL );

        highScoreTable.add( highScoreText ).row();
        highScoreTable.add( highScoreValue ).row();
        highScoreTable.add( backBtn );

        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );

        // set table

        table.add( highScoreTable );
        table.center();
        table.setFillParent( true );
        table.pack();

        return table;

    }

    private void goBack() {
        log.debug( "go back" );
        game.setScreen( new MenuScreen( game ) );
    }
}
