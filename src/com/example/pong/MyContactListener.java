package com.example.pong;

import org.jbox2d.callbacks.ContactImpulse;
import org.jbox2d.callbacks.ContactListener;
import org.jbox2d.collision.Manifold;
import org.jbox2d.dynamics.contacts.Contact;

public class MyContactListener implements ContactListener{

	public Scoreboard scoreboard;
	public UIHelper helper;
	MyContactListener(){
		
	}
	MyContactListener(Scoreboard score){
		scoreboard=score;
	}
	MyContactListener(Scoreboard score,UIHelper myHelper){
		scoreboard=score;
		helper=myHelper;
	}
	@Override
	public void beginContact(Contact contact) {
		Contact c_list = contact;
		//iterate through the LL
		while(c_list != null){
			String userDataA=(String) c_list.getFixtureA().m_body.m_userData;
			String userDataB=(String) c_list.getFixtureB().m_body.m_userData;
			//if the ball is colliding with a goal
			if(userDataA=="goal"&&userDataB=="circle"){
					//player 2 has scored
					scoreboard.incScore();
					
			}
			else if(userDataA=="goal 2"&&userDataB=="circle"){
				//ball has hit the ground
				scoreboard.decScore();
			}
			else if(userDataB=="goal 2"&&userDataA=="circle"){
				//ball 2
				scoreboard.decScore();
			}
			else if(userDataB=="goal"&&userDataA=="circle"){
					scoreboard.incScore();
			}
			c_list=c_list.m_next;
		}
	}
	@Override
	public void endContact(Contact contact) {
		Contact c_list = contact;
		//iterate through the LL
		while(c_list != null){
			String userDataA=(String) c_list.getFixtureA().m_body.m_userData;
			String userDataB=(String) c_list.getFixtureB().m_body.m_userData;
			//if the ball is colliding with a goal
			if(userDataA=="goal"&&userDataB=="circle"){
					//player 2 has scored
				if(scoreboard.isP2Win()){	
					helper.playerWon();
				}
			}
		  	if(userDataA == "block" && userDataB =="circle"){
			       c_list.getFixtureA().m_body.m_userData = PhysicsWorld.DESTROY;
		  		}
				      c_list = c_list.getNext();
			 }
			c_list=c_list.m_next;
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
