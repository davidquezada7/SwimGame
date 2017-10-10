package sprites;
import static com.others.Vars.PPM;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.gamestates.PlayState;

public class Player extends Sprite{
	//HUD THINGS
	public static final int V_WIDTH = 500;
	public static final int V_HEIGHT = 208;
	
	//
	public boolean mainPlayer;
	//WORLD
	public World world;
	//BODY & SPRITES
	public enum State {FORWARD, BACKWARD, STANDING, LEFT, RIGHT};
	public State currentState;
	public State previousState;
	//ANIMATIONS
	public Animation playerForward;
	public Animation playerBackward;
	public Animation playerRight;
	public Animation playerLeft;

	public float stateTimer;
	//BODY	
	public Body body;
	private TextureRegion playerStand;
	//IMPULSE
	public float impulseUp = 1.4f;
	public float impulseDown = -2f;
	public float impulseLeft = -2.4f;
	public float impulseRight = 2.4f;
	///INIT POSITION
	public float xInit = 0;
	public float yInit = 0;
	
	//SENDER BOOLEAN
	public boolean sendPosition = true;
	
	//KEYBOARD
	private boolean up = false;
	private boolean right = false;
	private boolean left = false;
	private boolean down = false;

	public Player(World world, int xInit,int yInit,PlayState playerState,boolean mainPlayer){
		super(playerState.getAtlas().findRegion("cron"));
		this.world = world;		
		this.xInit = xInit;
		this.yInit = yInit;
		this.mainPlayer = mainPlayer;
		//INIT
		currentState = State.STANDING;
		previousState = State.STANDING;
		stateTimer = 0;
		//IF MAINPLAYER
		if(!mainPlayer){
			impulseUp = 0f;
			impulseDown = 0f;
			impulseRight = 0f;
			impulseLeft = 0f;
		}
		
		//ANIMATIONS INIT
			
			//FORWARD ANIMATION
		Array<TextureRegion> frames = new Array<TextureRegion>();
		for(int i =0;i<3;i++)
			frames.add(new TextureRegion(getTexture(),i*32,96,32,32));
		playerForward = new Animation (0.1f,frames);
		frames.clear();
		
			//BACKWARD ANIMATION
		for(int i =0;i<3;i++)
			frames.add(new TextureRegion(getTexture(),i*32,0,32,32));
		playerBackward = new Animation (0.1f,frames);
		frames.clear();
			
			//RIGHT ANIMATION
		for(int i =0;i<3;i++)
			frames.add(new TextureRegion(getTexture(),i*32,64,32,32));
		playerRight = new Animation (0.1f,frames);
		frames.clear();
			
			//LEFT ANIMATION
		for(int i =0;i<3;i++)
			frames.add(new TextureRegion(getTexture(),i*32,33,32,32));
		playerLeft = new Animation (0.1f,frames);
		frames.clear();
		
		playerStand = new TextureRegion(getTexture(),32,96,32,32);
		
		setBounds(0,0,32/PPM,32/PPM);
		setRegion(playerStand);
		definePlayer();
	}
	
	public void definePlayer(){
		BodyDef bodyDef = new BodyDef();
		bodyDef.position.set(xInit/PPM,yInit/PPM);
		bodyDef.type = BodyType.DynamicBody;
		
		body = world.createBody(bodyDef);
		body.setGravityScale(8.0f);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(11/PPM,12/PPM);
		
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		body.createFixture(fixtureDef);
		body.setLinearDamping(2f);
	
		EdgeShape head = new EdgeShape();
		head.set(new Vector2(-2/PPM,17/PPM),new Vector2(2/PPM,17/PPM));
		fixtureDef.shape = head;
		fixtureDef.isSensor = true;
		
		body.createFixture(fixtureDef).setUserData("head");
	}
	
	public void setUp(boolean b){up = b;}
	public void setLeft(boolean b){left = b;}
	public void setRight(boolean b){right = b;}
	public void setDown(boolean b){down = b;}
	
	public TextureRegion getFrame(float dt){
		currentState = getState();
		TextureRegion region;
		
		switch(currentState){
			case FORWARD:	
				region = playerForward.getKeyFrame(stateTimer,true);
				break;
				
			case BACKWARD:
				region = playerBackward.getKeyFrame(stateTimer,true);
				break;
				
			case RIGHT:	
				region = playerRight.getKeyFrame(stateTimer, true);
				break;
				
			case LEFT:
				region = playerLeft.getKeyFrame(stateTimer, true);
				break;
				
			default:
				region = playerStand;
		}
		
		stateTimer = currentState == previousState ? stateTimer + dt: 0;
		previousState = currentState;
		return region;
	}
	
	public State getState(){
		
		if(body.getLinearVelocity().y>0 && previousState ==State.FORWARD )
			return State.FORWARD;
		
		else if(body.getLinearVelocity().y<0 && previousState == State.BACKWARD)
			return State.BACKWARD;
		
		else if(body.getLinearVelocity().x<0 && previousState == State.LEFT)
			return State.LEFT;
		
		else if(body.getLinearVelocity().x>0 && previousState == State.RIGHT)
			return State.RIGHT;
		
		else 
			return State.STANDING;
	}
	
	public void update(float dt){
		setPosition(body.getPosition().x - getWidth()/2,body.getPosition().y - getHeight()/2);
		setRegion(getFrame(dt));
		if(mainPlayer){
			boolean reSetSprite = true;
			
			if(up){
				
				body.applyLinearImpulse(0f, impulseUp, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				
				//SETEA EL UPPER SPRITE SOLO SI EL PLAYER VA PARA ARRIBA, SI TIENE VELOCIDAD HACIA ABAJO NO LO SETEA
				if(body.getLinearVelocity().y>0)
					previousState = State.FORWARD;
			}
			
			if(down){
				body.applyLinearImpulse(0f, impulseDown, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				if(body.getLinearVelocity().y<0)
				previousState = State.BACKWARD;
			}
			
			if(right){
				body.applyLinearImpulse(impulseRight, 0f, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				previousState = State.RIGHT;
			}
			
			if(left){
				body.applyLinearImpulse(impulseLeft, 0f, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				previousState = State.LEFT;
			}
			
		}else{
			if(impulseUp>0){
				body.applyLinearImpulse(0f, impulseUp, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				//SETEA EL UPPER SPRITE SOLO SI EL PLAYER VA PARA ARRIBA, SI TIENE VELOCIDAD HACIA ABAJO NO LO SETEA
				if(body.getLinearVelocity().y>0)
					previousState = State.FORWARD;
				impulseUp = 0;
			}
			
			if(impulseDown<0){
				body.applyLinearImpulse(0f, impulseDown, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				if(body.getLinearVelocity().y<0)
				previousState = State.BACKWARD;
				impulseDown = 0;
			}
			
			if(impulseRight>0){
				body.applyLinearImpulse(impulseRight, 0f, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				previousState = State.RIGHT;
				impulseRight = 0;
			}
			
			if(impulseLeft<0){
				body.applyLinearImpulse(impulseLeft, 0f, 0f, 0f, true);
				body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
				previousState = State.LEFT;
				impulseLeft = 0;
			}
			
		}
		
	}
	
}



