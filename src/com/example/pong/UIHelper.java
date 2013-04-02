package com.example.pong;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;

public class UIHelper {
	private PhysicsWorld game;
	private pongView pView;
	private MultiTouchController mC;
	public pt a;
	public Vec2 userVelocity;
	private Pong pActivity;
	Vec2 forceLeft,forceRight;
	public Scoreboard scoreboard;
	public float ks;
	public UIHelper(PhysicsWorld myGame, pongView myView, MultiTouchController myController){
		this.game=myGame;
		this.pView=myView;
		this.mC=myController;
		userVelocity=new Vec2(0,0);
		this.ks=20f;
	}
	public UIHelper(PhysicsWorld game2, pongView pongView,
			MultiTouchController mController, Scoreboard scoreboard2) {
			this(game2,pongView,mController);
			scoreboard=scoreboard2;
	}

	public void userMove(MultiTouch m){
		Body paddle=game.getBodyList();
		while(paddle!=null){
			if(paddle.m_userData=="box"){
				paddle.setTransform(toPhysicsCoords(m.disk), paddle.getAngle());
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
	public void userForce(Vec2 v) {
		// TODO Auto-generated method stub
	
		Body paddle=game.getBodyList();
	
		while(paddle!=null){
			if(paddle.m_userData=="box"){
				//paddle.applyForce(force, point);
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

	
	public void setContactListener(){
		game.getWorld().setContactListener(new MyContactListener(scoreboard));
	}



	public Vec2 getleftCoord(Body paddle){//Gets the left coordinate of the paddle to apply a force to
		Fixture fixture=paddle.getFixtureList();
		
	    PolygonShape poly = (PolygonShape) fixture.getShape();
	    int vertexCount = poly.m_count;
	    Vec2[] vertices = new Vec2[vertexCount];
		Transform t=paddle.getTransform();
	    for (int i = 0; i < vertexCount; ++i) {
		      vertices[i]=poly.m_vertices[i].translate(t.p);
		      Vec2 vTemp=vertices[i];
		      vertices[i]=pView.rotate(vTemp, t.q, paddle);
		    }
	    Vec2 a=vertices[0];
	    Vec2 b=vertices[3];
	    Vec2 ret=a.mid(b);
		return ret;
	}
	public Vec2 getRightCoord(Body paddle){//Gets the left coordinate of the paddle to apply a force to
		Fixture fixture=paddle.getFixtureList();
		
	    PolygonShape poly = (PolygonShape) fixture.getShape();
	    int vertexCount = poly.m_count;
	    Vec2[] vertices = new Vec2[vertexCount];
		Transform t=paddle.getTransform();
	    for (int i = 0; i < vertexCount; ++i) {
		      vertices[i]=poly.m_vertices[i].translate(t.p);
		      Vec2 vTemp=vertices[i];
		      vertices[i]=pView.rotate(vTemp, t.q, paddle);
		    }
	    Vec2 a=vertices[1];
	    Vec2 b=vertices[2];
	    Vec2 ret=a.mid(b);
		return ret;
	}
	public Body getPaddle(){
		Body paddle=game.getBodyList();
		while(paddle!=null){
			if(paddle.m_userData=="box"){
				return paddle;
			}
			paddle=paddle.getNext();
		}
		return null;
	}
	public void showPhysVec(Vec2 v, Canvas c, Paint p){//Converts a physics world vec2 to screen coords and draws it
		Vec2 d= toScreenCoords(v);
		c.drawCircle(d.x, d.y, 5, p);
	}
	public Vec2 toCOM(Vec2 v,Body b){//Calculates the distance from the center of mass to a vec2
		return v.sub(b.getPosition());
		 
	}
	public void move(Vec2 f) {
		Body b=getPaddle();
		Vec2 force;
		Vec2 finger=toPhysicsCoords(f);
		Vec2 lPaddle= getleftCoord(b);
		Vec2 rPaddle= getRightCoord(b);
		float distance=finger.disTo(lPaddle);
		if(finger.disTo(rPaddle)<distance){
			Vec2 dR=finger.sub(rPaddle);
			b.applyLinearImpulse(dR.mul(ks),rPaddle);
		}
		else{
			Vec2 dL=finger.sub(lPaddle);
			b.applyLinearImpulse(dL.mul(ks), lPaddle);
		}
		b.m_linearDamping=3;
		b.m_angularDamping=3;	
	}
	public void moveL(Vec2 f) {
		Body b=getPaddle();
		Vec2 finger=toPhysicsCoords(f);
		Vec2 lPaddle= getleftCoord(b);
		Vec2 dL=finger.sub(lPaddle);
		forceLeft=dL.mul(ks);
		forceRight=null;
		b.applyLinearImpulse(dL.mul(ks), lPaddle);
		b.m_linearDamping=3;
		b.m_angularDamping=3;	
	}
	public void moveR(Vec2 f) {
		Body b=getPaddle();
		Vec2 finger=toPhysicsCoords(f);
		Vec2 rPaddle= getRightCoord(b);
		Vec2 dR=finger.sub(rPaddle);	
		forceRight=dR.mul(ks);
		forceLeft=null;
		b.applyLinearImpulse(dR.mul(ks),rPaddle);
		b.m_linearDamping=3;
		b.m_angularDamping=3;	
	}
	public void move(Vec2 l,Vec2 r) {
		Vec2 lFinger,rFinger;
		if(l.x<r.x){
			lFinger=toPhysicsCoords(l);
			rFinger=toPhysicsCoords(r);
		}
		else{
			lFinger=toPhysicsCoords(r);
			rFinger=toPhysicsCoords(l);
		}
		Body b=getPaddle();
		Vec2 lPaddle= getleftCoord(b);
		Vec2 rPaddle= getRightCoord(b);
		Vec2 dL=lFinger.sub(lPaddle);
		Vec2 dR=rFinger.sub(rPaddle);
		forceLeft=dL.mul(ks);
		forceRight=dR.mul(ks);
		b.applyLinearImpulse(dL.mul(ks), lPaddle);
		b.applyLinearImpulse(dR.mul(ks),rPaddle);
		b.m_linearDamping=3;
		b.m_angularDamping=3;
	}
	public void setPActivity(Pong p){
		pActivity=p;
	}
	
}
