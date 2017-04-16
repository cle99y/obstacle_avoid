package com.paulclegg.entity;

import com.paulclegg.Config.GameConfig;

/**
 * Created by cle99 on 03/04/2017.
 */

public class Player extends GameObject {


    public Player() {
        super( GameConfig.PLAYER_BOUNDS_RADIUS );
        setSize( GameConfig.PLAYER_SIZE, GameConfig.PLAYER_SIZE );

    }

}
