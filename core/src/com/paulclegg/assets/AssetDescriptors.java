package com.paulclegg.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by cle99 on 10/04/2017.
 */

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> UIFONT =
            new AssetDescriptor<BitmapFont>( AssetPaths.UI_FONT, BitmapFont.class );

    public static final AssetDescriptor<Texture> BACKGROUND =
            new AssetDescriptor<Texture>( AssetPaths.BACKGROUND_TEXTURE, Texture.class );

    public static final AssetDescriptor<Texture> OBSTACLE =
            new AssetDescriptor<Texture>( AssetPaths.OBSTACLE_TEXTURE, Texture.class );

    public static final AssetDescriptor<Texture> PLAYER =
            new AssetDescriptor<Texture>( AssetPaths.PLAYER_TEXTURE, Texture.class );

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>( AssetPaths.GAME_PLAY, TextureAtlas.class );

//    public static final AssetDescriptor<TextureAtlas> UI =
//            new AssetDescriptor<TextureAtlas>( AssetPaths.UI, TextureAtlas.class );

    public final static AssetDescriptor<Skin> UI_SKIN =
            new AssetDescriptor<Skin>( AssetPaths.UI_SKIN, Skin.class );


    public AssetDescriptors() {


    }
}
