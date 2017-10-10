package sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public class InteractiveTileObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Body body;
	protected Rectangle bounds;
	
	public InteractiveTileObject(World world, TiledMap map, Rectangle bounds){
		this.world = world;
		this.map = map;
		this.bounds = bounds;
	}
}
