package com.example.pong;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;

import android.graphics.Canvas;
import android.graphics.Paint;



public class PhysicsWorld {
	private World m_world;
	float timeStep;
	int velocityIterations = 6;

	int positionIterations = 2;

	public PhysicsWorld(){
		
		 m_world = new World(new Vec2(0,0), false);
		 // getWorld().setGravity(new Vec2(0,0));
		 timeStep= 1.0f / 60.0f;
	}
	void init(){
		 for (int i = 0; i < 2; i++) {
			 PolygonShape polygonShape = new PolygonShape();
		     polygonShape.setAsBox(5,.5f);
		      
		     BodyDef bodyDef = new BodyDef();
		     bodyDef.type = BodyType.DYNAMIC;
		     bodyDef.position.set(5 * i, 0);
		     bodyDef.angle = (float) (Math.PI / 4 * i);
		     bodyDef.allowSleep = false;
		     bodyDef.userData="box";
		     Body body = getWorld().createBody(bodyDef);
		     body.createFixture(polygonShape, 5.0f);

		      //body.applyForce(new Vec2(-10000 * (i - 1), 0), new Vec2());
		    }
		    CircleShape circle = new CircleShape();
		    circle.m_p.set(2.0f, 3.0f);
		    circle.m_radius=1;
		    BodyDef circleDef = new BodyDef();
		    circleDef.type = BodyType.DYNAMIC;
		    circleDef.position.set(1, 1);
		    circleDef.angle = (float) (Math.PI / 4 );
		    circleDef.linearVelocity=new Vec2(1,1);
		    circleDef.allowSleep = false;
		    circleDef.userData="circle";
		    Body circleBody = getWorld().createBody(circleDef);
		    circleBody.createFixture(circle, 5.0f);
	}
	void draw(Canvas canvas,Paint p){
		Body list=getBodyList();
		while(list!=null){
		 if(list.m_userData=="circle"){
			 canvas.drawCircle((float) (list.getPosition().x*(200.0/5.0)), (float) (list.getPosition().y*(200/5.0)), 20, p);
		 }
//		 else if(list.m_userData=="box"){
//			 canvase((float) (list.getPosition().x*(200.0/5.0)), (float) (list.getPosition().y*(200/5.0)), 20, p))
//		 }
		 list=list.getNext();
		}
		//need to implement
	}
	  /**
	   * Gets the current world
	   * 
	   * @return
	   */
	  public World getWorld() {
	    return m_world;
	  }
	  public Body getBodyList(){
		  return m_world.getBodyList();
	  }
	  public void update(){
		  m_world.step(timeStep, velocityIterations, positionIterations);
	  }
	
}
