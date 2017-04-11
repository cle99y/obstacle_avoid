package com.paulclegg.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.paulclegg.Config.GameConfig;
import com.paulclegg.ObstacleGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new ObstacleGame(), config);

		config.width = (int) GameConfig.WIDTH;
		config.height = (int) GameConfig.HEIGHT;

	}
}
