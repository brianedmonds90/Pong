package com.example.pong;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

public class UIHelper {
	private PhysicsWorld game;
	private pongView pView;
	private MultiTouchController mC;
	public pt a;
	public Vec2 userVelocity;
	public UIHelper(PhysicsWorld myGame, pongView myView, MultiTouchController myController){
		this.game=myGame;
		this.pView=myView;
		this.mC=myController;
		userVelocity=new Vec2(0,0);
	}
	public void userMove(MultiTouch m){
		Body paddle=game.getBodyList();
		while(paddle!=null){
			if(paddle.m_userData=="box"){
				paddle.setTransform(toPhysicsCoords(m.disk), paddle.getAngle());
			//	paddle.setLinearVelocity(new Vec2(10,10));
				return;
			}
			paddle=paddle.getNext();
		}
	}
	public  Vec2 toScreenCoords(Vec2 v){
		return new Vec2(toScreenX(v.x),toScreenY(v.y));
	}
	private float toScreenX(float x){
		return (float) (x*(pView.screenWidth/20.0));
	}
	private  float toScreenY(float y){
		return (float) (y*(pView.screenHeight/40.0));
	}
	private Vec2 toPhysicsCoords(Vec2 v){
		return new Vec2(toPhysicsX(v.x),toPhysicsY(v.y));
	}
	private float toPhysicsX(float x){
		return (float)((20.0*x)/pView.screenWidth);
	}
	private float toPhysicsY(float y){
		return (float)((40.0*y)/pView.screenWidth);
	}
	public void userMove(Vec2 v) {
		// TODO Auto-generated method stub
	
		Body paddle=game.getBodyList();
		while(paddle!=null){
			if(paddle.m_userData=="box"){
			   paddle.setLinearVelocity(toPhysicsCoords(v));
			   //paddle.setAngularVelocity(0);
			   return;
			}
			paddle=paddle.getNext();
		}
	}
	public void userMove(Vec2 linear, Vec2 angular){
		Body paddle=game.getBodyList();
		while(paddle!=null){
			if(paddle.m_userData=="box"){
			   paddle.setLinearVelocity(toPhysicsCoords(linear));
			   float angle=computeAngle(angular);
			   paddle.setAngularVelocity(angle);
			   return;
			}
			paddle=paddle.getNext();
		}
	}
	private float computeAngle(Vec2 angular) {
		// TODO Make this user interaction better
		float angle;
		if(angular.magnitude()<10)
			angle=0;
		else{
			if(angular.y<0)
				angle=15;
			else 
				angle=-15;
		}
		return angle;
	}
	

}
