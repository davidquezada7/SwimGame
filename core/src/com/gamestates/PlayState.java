package com.gamestates;

import static com.others.Vars.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.io.TCPClient;
import com.io.UDPListener;
import com.io.UDPSender;
import com.managers.GameStateManager;
import com.maps.ParseLayer;

import Scenes.Hud;
import sprites.Loser;
import sprites.NumberGo;
import sprites.NumberOne;
import sprites.NumberThree;
import sprites.NumberTwo;
import sprites.Player;
import sprites.Wait;
import sprites.Winner;

public class PlayState extends GameState {
	public boolean readyToStart = false;
	public boolean playMainSound = false;
	public boolean once = true;
	//WORLD
	public World world;
	public Box2DDebugRenderer boxRenderer;
	//SCREEN
	public static int WIDTH;
	public static int HEIGHT;
	
	//CAMERA
	public SpriteBatch batch;
	public OrthographicCamera camera;
	
	//CLIENT
	public TCPClient tcpClient;
	public Thread tcpClientThread;
	
	//SENDER
	public UDPSender udpSender;
	public Thread udpSenderThread;
	
	//LISTENER
	public UDPListener udpListener;
	public Thread udpListenerThread;
	
	//ATLAS
	public TextureAtlas atlas;
	public TextureAtlas numThreeAtlas;
	public TextureAtlas numTwoAtlas;
	public TextureAtlas numOneAtlas;
	public TextureAtlas goAtlas;
	public TextureAtlas waitAtlas;
	
	public TextureAtlas winnerAtlas;
	public TextureAtlas loserAtlas;
	
	//PLAYERS
	public Player player1;
	public Player player2;
	//NUMBERS
	public NumberOne numOne;
	public NumberTwo numTwo;
	public NumberThree numThree;
	public NumberGo go;
	public Wait waitSprite;
	
	public Winner winner;
	public Loser loser;
	
	public float timerAcumulator = 0f;
	//MAP
	public OrthogonalTiledMapRenderer tmr;
	public TiledMap map;
	
	public Texture img;
	public Sprite mapSprite;
	//
	public float stateTime;
	public TextureRegion currentFrame;
	//
	public float removeSprite = 1f;
	public boolean wait = true;
	
	public WorldContactListener wcl;
	//SOUNDS
	public Music level1;
	//HUD
	public Hud hud;
	
	public PlayState(GameStateManager gsm){
		super(gsm);
		//WOLRD
		world = new World(new Vector2(0,0),true);
		boxRenderer = new Box2DDebugRenderer();
				
		//SCREEN
		Gdx.graphics.setWindowedMode(736, 700);
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		batch = new SpriteBatch();
		
		//CAMERA
		camera = new OrthographicCamera();
		camera.setToOrtho(false,WIDTH/PPM, HEIGHT/PPM);
		
		//PLAYER IMAGES
		atlas = new TextureAtlas(Gdx.files.internal("players/cron/CronMove.atlas"));
		//NUMBERS
		numThreeAtlas = new TextureAtlas(Gdx.files.internal("numbers/tres/numtres.atlas"));
		numTwoAtlas = new TextureAtlas(Gdx.files.internal("numbers/dos/numdos.atlas"));
		numOneAtlas = new TextureAtlas(Gdx.files.internal("numbers/uno/numuno.atlas"));
		goAtlas = new TextureAtlas(Gdx.files.internal("numbers/go/numgo.atlas"));
		waitAtlas = new TextureAtlas(Gdx.files.internal("numbers/wait/numwait.atlas"));
		
		winnerAtlas = new TextureAtlas(Gdx.files.internal("winerloser/win/whowin.atlas"));
		loserAtlas = new TextureAtlas(Gdx.files.internal("winerloser/lose/wholose.atlas"));
		
		wcl = new WorldContactListener( );
		world.setContactListener(wcl);
		//SOUNDS
		level1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/level1.mp3"));
		level1.setVolume(0.5f);
		
		//HUD
		hud = new Hud(batch);
		
		init();
	}
	
	public TextureAtlas getWinner(){
		return winnerAtlas;
	}
	
	public TextureAtlas getLoser(){
		return loserAtlas;
	}
	
	public TextureAtlas getWait(){
		return waitAtlas;
	}
	
	public TextureAtlas getAtlas(){
		return atlas;
	}
	
	public TextureAtlas getnumTres(){
		return numThreeAtlas;
	}
	
	public TextureAtlas getnumDos(){
		return numTwoAtlas;
	}
	
	public TextureAtlas getnumUno(){
		return numOneAtlas;
	}
	
	public TextureAtlas getGo(){
		return goAtlas;
	}
	public void init(){
		//NUMBERS
		numOne = new NumberOne(world,270,300,this);
		numTwo = new NumberTwo(world,270,300,this);
		numThree = new NumberThree(world,270,300,this);
		go = new NumberGo(world,270,300,this);
		waitSprite = new Wait(world,200,300,this);
		
		winner = new Winner(world,100,7800,this);
		loser = new Loser(world,200,7800,this);
		
		//CREATE MAPS
		map = new TmxMapLoader().load("levels/level1/testLevel.tmx");
		tmr = new OrthogonalTiledMapRenderer(map,1/PPM);
		
		//ADD THE GROUND LAYER FOR COLLISION BODIES
		ParseLayer.parseGround(world,map);
		//world, map, layer, restitution
		ParseLayer.parseObstacles(world, map,5,0.5f);
		ParseLayer.parseObstacles(world, map,7,0.15f);
		ParseLayer.parseObstacles(world, map,9,0.9f);
		ParseLayer.parseObstacles(world, map,11,1.3f);
		ParseLayer.parseObstacles(world, map,13,1.6f);
		
		ParseLayer.parseMap(world, map,14,0.08f);
		
		//PLAYERS
		player1 = new Player(world,550,86,this,true);
		player2 = new Player(world,195,86,this,false);
		
		//CLIENT
		tcpClient = new TCPClient("192.168.1.14",9998);
		tcpClientThread = new Thread(tcpClient);
		tcpClientThread.start();
		
		//SENDER
		udpSender = new UDPSender(9102,"192.168.1.14");
		udpSenderThread = new Thread(udpSender);
		
		//LISTENER
		udpListener = new UDPListener(9107);
		udpListenerThread = new Thread(udpListener);
		udpListenerThread.start();
				
	}
	public void update(float dt){
		if(wcl.lock){
			
			readyToStart = false;
			
			udpSender.end = 1;
			udpSender.type = 3;
			udpSenderThread.run();
			udpSenderThread.run();
			udpSenderThread.run();
			udpSenderThread.run();
			udpSenderThread.run();
		}
		
		if(udpListener.refreshStart){
			if(udpListener.timetoStart == 0){
				readyToStart = true;
			}
			udpListener.refreshStart = false;
		}
		
		//UPDATE DE KEYBOARD INPUT
		if(readyToStart){
			
			
			handleInput();
			//VARIABLES PARA ACTIVAR EL SONIDO AL TERMINAR EL CONTEO 3,2,1 GO
			if(once){
				playMainSound = true;
				once = false;
			}
			//CONTAR HUD
			//HUD
			hud.update(dt);
		}
		//
		if(playMainSound){
			System.out.println("sonando");
			level1.play();
			playMainSound = false;
		}
		//EACH SECOND SEND THE POSITION
		if(timerAcumulator>1){
			udpSender.posX= player1.body.getPosition().x;
			udpSender.posY= player1.body.getPosition().y;
			udpSender.type = 2;
			udpSenderThread.run();
			timerAcumulator = 0f;
		}else{
			timerAcumulator = timerAcumulator + dt;
		}
		
		//SI HAY UN PAQUETE DE ENTRADA DE IMPULSE -> REFRESCAR
		if(udpListener.refreshImpulse){
			
			if(udpListener.impulseY>0)
				player2.impulseUp = udpListener.impulseY;
			else
				player2.impulseDown = udpListener.impulseY;
			
			if(udpListener.impulseX>0)
				player2.impulseRight = udpListener.impulseX;
			else
				player2.impulseLeft = udpListener.impulseX;
			
	
			udpListener.impulseX = 0f;
			udpListener.impulseY = 0f;
	
			udpListener.refreshImpulse=false;
		}
		
		//SI HAY UN PAQUETE DE ENTRADA DE POSITION -> REFRESCAR
		if(udpListener.refreshPosition){
			player2.body.setTransform(udpListener.posX, udpListener.posY,0);
			udpListener.posX = 0f;
			udpListener.posY = 0f;
			udpListener.refreshPosition=false;
		}		
		
		//UPDATE CAMERA THAT FOLLOWS PLAYER IF 3/4 HEIGHT
		if((player1.body.getPosition().y >= ((HEIGHT)/2)/PPM) ){
			camera.position.set(WIDTH/2/PPM,HEIGHT/2/PPM + player1.body.getPosition().y - ((HEIGHT)/2)/PPM ,0);
			camera.update();
		}
		
		
		
		//HOW MANY TIMES CALCULATE PER SECOND FOR PHYSIC WORLD
		world.step(dt, 6, 2);
		tmr.setView(camera);
		player1.update(dt);
		player2.update(dt);

	}
	
	public void draw(){
		
		//CLEAR SCREEN
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//DEBUG RENDERER
		boxRenderer.render(world,camera.combined);
		
		//WORLD RENDERER
		tmr.render();
		
		batch.setProjectionMatrix(camera.combined);
		
		batch.begin();
		if(udpListener.timetoStart == 3){
			numThree.draw(batch);
			wait = false;
		}else if(udpListener.timetoStart == 2)
			numTwo.draw(batch);
		else if(udpListener.timetoStart == 1)
			numOne.draw(batch);
		else if(udpListener.timetoStart ==0)
			go.draw(batch);
		else if(wait){
			waitSprite.draw(batch);
		}
		
		if(udpListener.win==1){
			System.out.println("usted Gano");
			winner.draw(batch);
		}else if(udpListener.win ==2){
			System.out.println("usted perdio");
			loser.setPosition(player1.body.getPosition().x - 400/PPM, player1.body.getPosition().y);
			loser.draw(batch);
		}
		
		player1.draw(batch);
		player2.draw(batch);
		batch.end();
			
		batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.stage.draw();
	}
	public void handleInput(){
			
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
			player1.setUp(true);	
			udpSender.impulseX= 0f;
			udpSender.impulseY= player1.impulseUp;
			udpSender.type = 1;
			udpSenderThread.run();
		}else{
			player1.setUp(false);
		}
	
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
			player1.setLeft(true);
			udpSender.impulseX= player1.impulseLeft;
			udpSender.impulseY= 0f;
			udpSender.type = 1;
			udpSenderThread.run();
		}else{
			player1.setLeft(false);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
			player1.setRight(true);
			udpSender.impulseX= player1.impulseRight;
			udpSender.impulseY= 0f;
			udpSender.type = 1;
			udpSenderThread.run();
		}else{
			player1.setRight(false);
		}
		
		if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)){
			player1.setDown(true);
			udpSender.impulseX= 0f;
			udpSender.impulseY= player1.impulseDown;
			udpSender.type = 1;
			udpSenderThread.run();
		}else{
			player1.setDown(false);
		}
		
	}
	public void dispose(){
		boxRenderer.dispose();
		tmr.dispose();
		map.dispose();
		
	}
	
}


