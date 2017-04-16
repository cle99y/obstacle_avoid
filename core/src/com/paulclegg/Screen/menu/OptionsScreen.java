package com.paulclegg.Screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Config.DifficultyLevel;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.ObstacleGame;
import com.paulclegg.Util.ViewportUtils;
import com.paulclegg.assets.AssetDescriptors;
import com.paulclegg.assets.RegionNames;
import com.paulclegg.common.GameManager;

/**
 * Created by cle99 on 14/04/2017.
 */

public class OptionsScreen extends MenuBase {

    private static final Logger log = new Logger( ViewportUtils.class.getName(), Logger.DEBUG );

    private Image checkMark;

    public OptionsScreen( ObstacleGame game ) {
        super( game );
    }

    @Override
    protected Actor createUI() {
        Table table = new Table();

        TextureAtlas uiAtlas = assetManager.get( AssetDescriptors.UI );
        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );

        TextureRegion backgroundRegion = gameplayAtlas.findRegion( RegionNames.BACKGROUND );
        TextureRegion panelRegion = uiAtlas.findRegion( RegionNames.PANEL );
        Image background = new Image( backgroundRegion );
        background.setSize( GameConfig.HUD_WIDTH, GameConfig.HUD_HEIGHT );

        BitmapFont uiFont = assetManager.get( AssetDescriptors.UIFONT );
        Label.LabelStyle labelStyle = new Label.LabelStyle( uiFont, Color.WHITE );

        Label label = new Label( "DIFFICULTY", labelStyle );
        label.setPosition( GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 + 180, Align.center );

        // EASY button
        final ImageButton easy = createButton( uiAtlas, RegionNames.EASY );
        easy.setPosition( GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 + 90, Align.center );
        easy.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                log.debug( "easy" );
                checkMark.setY( easy.getY() + 25 );
                GameManager.INSTANCE.setDifficultyLevel( DifficultyLevel.EASY );
            }
        } );

        // MEDIUM button
        final ImageButton medium = createButton( uiAtlas, RegionNames.MEDIUM );
        medium.setPosition( GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2, Align.center );
        medium.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                log.debug( "medium" );
                checkMark.setY( medium.getY() + 25 );
                GameManager.INSTANCE.setDifficultyLevel( DifficultyLevel.MEDIUM );

            }
        } );

        // HARD button
        final ImageButton hard = createButton( uiAtlas, RegionNames.HARD );
        hard.setPosition( GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 - 90, Align.center );
        hard.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                log.debug( "hard" );
                checkMark.setY( hard.getY() + 25 );
                GameManager.INSTANCE.setDifficultyLevel( DifficultyLevel.HARD );

            }
        } );

        final ImageButton back = createButton( uiAtlas, RegionNames.BACK, RegionNames.BACK_PRESSED );
        back.setPosition( GameConfig.HUD_WIDTH / 2, GameConfig.HUD_HEIGHT / 2 - 180, Align.center );
        back.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                game.setScreen( new MenuScreen( game ) );
            }
        } );

        TextureRegion checkMarkRegion = uiAtlas.findRegion( RegionNames.CHECK_MARK );
        checkMark = new Image( new TextureRegionDrawable( checkMarkRegion ) );
        checkMark.setPosition( medium.getX() + 50, medium.getY() + 40, Align.center );

        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        if ( difficultyLevel.isEasy() ) {
            checkMark.setPosition( easy.getX() + 50, easy.getY() + 40, Align.center );
        } else if ( difficultyLevel.isHard() ) {
            checkMark.setPosition( hard.getX() + 50, hard.getY() + 40, Align.center );
        }

        // add actors

        table.addActor( background );
        table.addActor( label );
        table.addActor( easy );
        table.addActor( medium );
        table.addActor( hard );
        table.addActor( checkMark );
        table.addActor( back );

        return table;

    }

}
