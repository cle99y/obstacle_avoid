package com.paulclegg.Screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
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

        TextureAtlas uiAtlas = assetManager.get( AssetDescriptors.UI );
        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );

        uiFont = assetManager.get( AssetDescriptors.UIFONT );

        TextureRegion backgroundRegion = gameplayAtlas.findRegion( RegionNames.BACKGROUND );
        TextureRegion panelRegion = uiAtlas.findRegion( RegionNames.PANEL );

        Label.LabelStyle labelStyle = new Label.LabelStyle( uiFont, Color.WHITE );

        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );

        // high score text
        Label highScoreText = new Label( "HIGH SCORE", labelStyle );

        // high score value
        Label highScoreValue = new Label( GameManager.INSTANCE.getHighScore(), labelStyle );

        // back button
        ImageButton backBtn = createButton( uiAtlas, RegionNames.BACK, RegionNames.BACK_PRESSED );
        backBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                goBack();
            }
        } );

        Table highScoreTable = new Table();
        highScoreTable.defaults().pad( 20 );
        highScoreTable.setBackground( new TextureRegionDrawable( panelRegion ) );

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
