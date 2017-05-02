package com.paulclegg.Screen.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Logger;
import com.paulclegg.Config.DifficultyLevel;
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

    private ButtonGroup<CheckBox> checkBoxGroup;
    private CheckBox easy;
    private CheckBox medium;
    private CheckBox hard;

    public OptionsScreen( ObstacleGame game ) {
        super( game );
    }

    private static CheckBox checkBox( String text, Skin skin ) {
        CheckBox checkBox = new CheckBox( text, skin );
        checkBox.left().pad( 10 );

        checkBox.getLabelCell().pad( 10 );

        return checkBox;

    }

    @Override
    protected Actor createUI() {

        Table table = new Table();

        Skin uiSkin = assetManager.get( AssetDescriptors.UI_SKIN );
        TextureAtlas gameplayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );

        TextureRegion backgroundRegion = gameplayAtlas.findRegion( RegionNames.BACKGROUND );
        table.setBackground( new TextureRegionDrawable( backgroundRegion ) );

        Label label = new Label( "DIFFICULTY", uiSkin );

        easy = checkBox( DifficultyLevel.EASY.name(), uiSkin );
        medium = checkBox( DifficultyLevel.MEDIUM.name(), uiSkin );
        hard = checkBox( DifficultyLevel.HARD.name(), uiSkin );

        checkBoxGroup = new ButtonGroup<CheckBox>( easy, medium, hard );

        // set default or current difficulty
        DifficultyLevel difficultyLevel = GameManager.INSTANCE.getDifficultyLevel();
        checkBoxGroup.setChecked( difficultyLevel.name() );

        // back button
        TextButton backBtn = new TextButton( "BACK", uiSkin );
        backBtn.addListener( new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                back();
            }
        } );

        // listener for checkboxes
        ChangeListener difficultyListener = new ChangeListener() {
            @Override
            public void changed( ChangeEvent event, Actor actor ) {
                difficultyChanged();
            }
        };

        // add listener to each checkbox instance
        easy.addListener( difficultyListener );
        medium.addListener( difficultyListener );
        hard.addListener( difficultyListener );

        // set up table
        Table highScoreTable = new Table( uiSkin );
        highScoreTable.defaults().pad( 10 );
        highScoreTable.setBackground( RegionNames.PANEL );

        highScoreTable.add( label ).row();
        highScoreTable.add( easy ).row();
        highScoreTable.add( medium ).row();
        highScoreTable.add( hard ).row();
        highScoreTable.add( backBtn );

        table.add( highScoreTable );
        table.center();
        table.setFillParent( true );
        table.pack();


        return table;

    }

    private void back() {
        log.debug( "back" );
        game.setScreen( new MenuScreen( game ) );
    }

    private void difficultyChanged() {
        log.debug( "difficulty changed" );

        CheckBox checked = checkBoxGroup.getChecked();

        if ( checked == easy ) {
            GameManager.INSTANCE.setDifficultyLevel( DifficultyLevel.EASY );
        } else if ( checked == medium ) {
            GameManager.INSTANCE.setDifficultyLevel( DifficultyLevel.MEDIUM );
        } else {
            GameManager.INSTANCE.setDifficultyLevel( DifficultyLevel.HARD );
        }
    }

}
