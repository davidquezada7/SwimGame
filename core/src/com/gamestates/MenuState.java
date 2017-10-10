package com.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.managers.GameStateManager;

public class MenuState extends GameState{
	//SCREEN
	public static int WIDTH;
	public static int HEIGHT;
	public Sprite background;
	public Texture textureBackground;
	public SpriteBatch batch;
	//OTROS
	public int currentItem = 1;
	public int top = 1;
	public int bottom = 2;
	//MENU ITEMS
	public Sprite newGame;
	public Sprite onNewGame;
	public Sprite join;
	public Sprite onJoin;
	public Sprite highscore;
	public Sprite onHighscore;
	public Sprite exit;
	public Sprite onExit;
	
	public MenuState(GameStateManager gsm){
		super(gsm);							//Guarda referencia al Game State Manager que lo creo
		init();
	}
	
	public void init(){
		//SCREEN
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		textureBackground = new Texture("menu/background.jpg");
		
		background = new Sprite(textureBackground);
		background.setPosition(0,-15);
		//MENU ITEMS
		
		//PLAY
		newGame = new Sprite(new Texture("menu/newGame.png"));
		newGame.setPosition(400,150);
		onNewGame = new Sprite(new Texture("menu/onNewGame.png"));
		onNewGame.setPosition(400,150);
		//EXIT
		
		exit = new Sprite (new Texture("menu/exit.png"));
		exit.setPosition(680, 160);
		onExit = new Sprite(new Texture("menu/onExit.png"));
		onExit.setPosition(680, 150);
	}
	public void update(float dt){
		handleInput();
	}
	public void draw(){
		
		batch.begin();
		
		batch.draw(background, background.getX(), background.getY());
		if(currentItem==1){
			batch.draw(onNewGame, onNewGame.getX(), onNewGame.getY());
			
			
			batch.draw(exit,exit.getX(),exit.getY());
		}else if(currentItem ==2){
			batch.draw(newGame, newGame.getX(), newGame.getY());
			
			batch.draw(onExit,onExit.getX(),onExit.getY());
		}
		
		batch.end();
	}

	public void handleInput(){
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			if(currentItem>top){
				currentItem--;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			if(currentItem<bottom){
				currentItem++;
			}
		}
		if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER)){
			select();
		}
		
	}
	public void select(){
		if(currentItem==1){
			gsm.setState(GameStateManager.PLAY);
		}else if(currentItem==100){
			//gsm.setState(GameStateManager.PLAY);
		}else if(currentItem==2){
			Gdx.app.exit();
		}
	}
	public void dispose(){}
}
