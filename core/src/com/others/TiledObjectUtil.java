package com.others;
import static com.others.Vars.PPM;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class TiledObjectUtil {
	public static void parseTiledObjectLayer(World world, MapObjects objects){
		Shape shape;
		for(MapObject object: objects){
			if(object instanceof PolylineMapObject){
				shape = createPolyline((PolylineMapObject)object);
			}else{
				continue;
			}
			
			BodyDef bdef = new BodyDef();
			bdef.type = BodyDef.BodyType.StaticBody;
			
			Body body = world.createBody(bdef);
			FixtureDef fdef = new FixtureDef();
			fdef.shape = shape;
			fdef.restitution = 0.5f;
			body.createFixture(fdef);
			shape.dispose();
		}
	}
	
	public static ChainShape createPolyline(PolylineMapObject polyline){
		float[] vertices  = polyline.getPolyline().getTransformedVertices();
		Vector2[] cornerVertices = new Vector2[vertices.length/2];
		for(int i = 0;i<cornerVertices.length;i++){
			cornerVertices[i] = new Vector2(vertices[i*2]/PPM,vertices[i*2+1]/PPM);
		}
		
		ChainShape cs = new ChainShape();
		cs.createChain(cornerVertices);
		
		return cs;
	}
	
}












