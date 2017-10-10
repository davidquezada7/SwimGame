package com.swimgame.desktop;

import java.net.*;
import java.util.*;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.swimgame.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Swim Game";
		config.width = 1440; //1440
		config.height = 700;
		config.resizable = true;
		new LwjglApplication(new MainGame(), config);
	}
} //////////////////////////////////////////////////////////