package com.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
	public boolean lock = false;
	public Sound boing;
	
	public WorldContactListener(){
		boing = Gdx.audio.newSound(Gdx.files.internal("sounds/boing1.mp3"));
	}	

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub
		
		//System.out.println("Hubo contacto");
		boing.play(1.0f);
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();
		
		if(fixtureA.getUserData()!= null && fixtureB.getUserData() != null){
			
			if(fixtureA.getUserData().equals("meta") || fixtureB.getUserData().equals("meta")){
				lock = true;
			}
		}
		
		
		//Gdx.app.log("Begin contact: ","");
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		//Gdx.app.log("End contact", "");
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
}
