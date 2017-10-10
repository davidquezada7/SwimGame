package com.swimgame;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.managers.GameStateManager;

import sprites.Player;

public class MainGame extends ApplicationAdapter {
	private GameStateManager gsm; 

	
	@Override
	public void create () {
		//GSM
		gsm = new GameStateManager();
	
	}

	@Override
	public void render () {
		
	//RENDER GAME STATE
		gsm.uptdate(Gdx.graphics.getDeltaTime());
		gsm.draw();

	}
	
	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();
	}
}
