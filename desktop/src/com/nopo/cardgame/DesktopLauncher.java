package com.nopo.cardgame;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.nopo.cardgame.screens.Game;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setTitle("nopo is cool");
		config.setWindowedMode(960, 800);
		config.useVsync(true);
		config.setForegroundFPS(60);
		new Lwjgl3Application(new Game(), config);
	}
}
