package com.paulclegg.common;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.paulclegg.assets.AssetDescriptors;
import com.paulclegg.assets.RegionNames;
import com.paulclegg.entity.PlayerSprite;

/**
 * Created by cle99 on 05/05/2017.
 */

public class EntityFactory {

    // -- attributes --
    private AssetManager assetManager;
    private TextureAtlas gamePlayAtlas;

    // -- constructors --
    public EntityFactory( AssetManager assetManager ) {
        this.assetManager = assetManager;
        init();
    }

    private void init() {
        gamePlayAtlas = assetManager.get( AssetDescriptors.GAME_PLAY );
    }

    public PlayerSprite createPlayer() {
        TextureRegion playerRegion = gamePlayAtlas.findRegion( RegionNames.PLAYER[1] );
        return new PlayerSprite( playerRegion );
    }


}
