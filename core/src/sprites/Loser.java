package sprites;

import static com.others.Vars.PPM;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;
import com.gamestates.PlayState;

public class Loser extends Sprite{
	private TextureRegion playerStand;
	public Loser(World world, int xInit,int yInit,PlayState playerState){
		super(playerState.getLoser().findRegion("lose"));
		
		playerStand = new TextureRegion(getTexture(),0,0,600,200);
		setBounds(0,0,600/PPM,200/PPM);
		setRegion(playerStand);
		setPosition(xInit/PPM,yInit/PPM);
	}

}