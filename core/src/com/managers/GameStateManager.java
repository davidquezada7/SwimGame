package com.managers;

import com.gamestates.GameState;
import com.gamestates.MenuState;
import com.gamestates.PlayState;
//Admin que cambia de estado el juego
public class GameStateManager {
	private GameState gameState;//Clase abstracta que representará cualquier estado del juego
	
	public static final int MENU = 0;
	public static final int PLAY = 1;
	
	public GameStateManager(){
		setState(MENU);
	}
	
	public void setState(int state){
		switch(state){
			case MENU:
				gameState = new MenuState(this);
				break;
			case PLAY:
				gameState = new PlayState(this);
				break;
			default:
				if(gameState != null)gameState.dispose();
				break;
		}
	}
	
	public void uptdate(float dt){
		gameState.update(dt);
	}
	
	public void draw(){
		gameState.draw();
	}
}
