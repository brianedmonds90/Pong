package com.example.pong;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.Fixture;
import org.jbox2d.dynamics.contacts.Contact;

public class MyContactListener implements ContactListener{

	public Scoreboard scoreboard;
	
	MyContactListener(){
		
	}
	MyContactListener(Scoreboard score){
		scoreboard=score;
	}
	@Override
	public void beginContact(Contact contact) {
		Contact c_list = contact;
		//iterate through the LL
		while(c_list != null){
			
			String userDataA=(String) c_list.getFixtureA().m_body.m_userData;
			String userDataB=(String) c_list.getFixtureB().m_body.m_userData;
			//if the ball is colliding with a goal
			if(userDataA=="goal 1"&&userDataB=="circle"){
					//player 2 has scored
					scoreboard.incP2Score();
				
			}
			else if(userDataA=="goal 2"&&userDataB=="circle"){
				
				//player 2 has scored
				scoreboard.incP1Score();
			
			}

			c_list=c_list.m_next;
		}
	}
	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
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
