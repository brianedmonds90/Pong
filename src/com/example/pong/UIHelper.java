package com.example.pong;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

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
				paddle.setTransform(toPhysicsCoords(m.disk.toVec2()), paddle.getAngle());
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
	public void initPaddle(MultiTouch m){
		Body paddle=game.getBodyList();
		a=new pt();
		while(paddle!=null){
			if(paddle.m_userData=="box"){
				a.setTo(a.toPt(toScreenCoords(paddle.getPosition())));
				m.disk.setTo(a);
				return;
			}
			paddle=paddle.getNext();
		}
	}
	public void userMove(vec v) {
		// TODO Auto-generated method stub
		Vec2 velocity=v.toVec2();
		Body paddle=game.getBodyList();
		while(paddle!=null){
			if(paddle.m_userData=="box"){
			   paddle.setLinearVelocity(toPhysicsCoords(velocity));
			   return;
			}
			paddle=paddle.getNext();
		}
	}

}
