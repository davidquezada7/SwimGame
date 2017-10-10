package sprites;

import static com.others.Vars.PPM;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.gamestates.PlayState;

public class Wait extends Sprite{
	private TextureRegion playerStand;
	public Wait(World world, int xInit,int yInit,PlayState playerState){
		super(playerState.getWait().findRegion("wait"));
		
		playerStand = new TextureRegion(getTexture(),0,0,500,200);
		setBounds(0,0,325/PPM,200/PPM);
		setRegion(playerStand);
		setPosition(xInit/PPM,yInit/PPM);
	}

}