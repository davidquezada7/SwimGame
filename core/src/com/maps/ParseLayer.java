package com.maps;

import static com.others.Vars.PPM;

import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class ParseLayer {
	public static Fixture fixture;
	
	public static void parseGround(World world,Map map){
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		for(MapObject object: map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject)object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/PPM+ rect.getWidth()/2/PPM, rect.getY()/PPM + rect.getHeight()/2/PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth()/2/PPM, rect.getHeight()/2/PPM);
			fdef.shape = shape;
			fdef.restitution = 0.1f;
			body.createFixture(fdef);
		}
	}
	
	public static void parseObstacles(World world,Map map, int layer, float restitution){
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		for(MapObject object: map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject)object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/PPM+ rect.getWidth()/2/PPM, rect.getY()/PPM + rect.getHeight()/2/PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth()/2/PPM, rect.getHeight()/2/PPM);
			fdef.shape = shape;
			fdef.restitution = restitution;
			body.createFixture(fdef);
		}
	}
	
	public static void parseMap(World world,Map map, int layer, float restitution){
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		
		for(MapObject object: map.getLayers().get(layer).getObjects().getByType(RectangleMapObject.class)){
			Rectangle rect = ((RectangleMapObject)object).getRectangle();
			bdef.type = BodyDef.BodyType.StaticBody;
			bdef.position.set(rect.getX()/PPM+ rect.getWidth()/2/PPM, rect.getY()/PPM + rect.getHeight()/2/PPM);
			
			body = world.createBody(bdef);
			shape.setAsBox(rect.getWidth()/2/PPM, rect.getHeight()/2/PPM);
			fdef.shape = shape;
			fdef.restitution = restitution;
			body.createFixture(fdef).setUserData("meta");
			fixture = body.createFixture(fdef);
			
		}
	}
}
