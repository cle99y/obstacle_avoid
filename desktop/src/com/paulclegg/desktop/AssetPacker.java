package com.paulclegg.desktop;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

/**
 * Created by cle99 on 10/04/2017.
 */

public class AssetPacker {

    public static final boolean DRAW_DEBUG_OUTLINE = false;

    public static final String ASSET_RAW_PATH = "desktop/assets-raw";
    public static final String ASSET_PATH = "android/assets";

    public static void main( String[] args ) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.debug = DRAW_DEBUG_OUTLINE;

        settings.maxWidth = 2048;
        settings.maxHeight = 2048;

        // pack gameplay textures
        TexturePacker.process( settings,
                ASSET_RAW_PATH + "/Gameplay",
                ASSET_PATH + "/Gameplay",
                "gameplay"
        );

        // pack ui textures
        TexturePacker.process( settings,
                ASSET_RAW_PATH + "/ui",
                ASSET_PATH + "/ui",
                "ui"
        );

        TexturePacker.process( settings,
                ASSET_RAW_PATH + "/skin",
                ASSET_PATH + "/ui/skin",
                "uiskin"
        );

    }

}
