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
			if(userDataA=="goal 1"&&userDataB=="circle"){
					//player 2 has scored
				scoreboard.incP2Score();
				if(scoreboard.isP2Win()){
					
					//helper.playerWon();
					
					//helper.serveBall(2);
					//do something
				}
			}
			else if(userDataA=="goal 2"&&userDataB=="circle"){
				//player 2 has scored
				scoreboard.incP1Score();
				if(scoreboard.isP1Win()){
					//do something
				}
			}
			else if(userDataB=="goal 1"&&userDataA=="circle"){
				scoreboard.incP2Score();
			}
			else if(userDataB=="goal 2"&&userDataA=="circle"){
				scoreboard.incP1Score();
			}
			c_list=c_list.m_next;
		}
	}
	@Override
	public void endContact(Contact contact) {
		Contact c_list = contact;
		
		while(c_list != null){
			String userDataA=(String) c_list.getFixtureA().m_body.m_userData;
			String userDataB=(String) c_list.getFixtureB().m_body.m_userData;
			//if the block and ball are colliding
			if(userDataA == "block" && userDataB =="circle"){
				c_list.getFixtureA().m_body.m_userData = PhysicsWorld.DESTROY;
			}
			c_list = c_list.getNext();
		}
		
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
