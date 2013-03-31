package com.example.pong;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.World;



public class PhysicsWorld {
	private World m_world;
	float timeStep;
	int velocityIterations = 6;
	
	//Maintain a list of edges as they wont move
	private ArrayList<EdgeShape> edges = new ArrayList<EdgeShape>();
	int positionIterations = 2;
	
	public static final float OFFSET = 0.15f;
	public static final float WIDTH = 20.0f;//Dont change these, as they have to match the physics test bed
	public static final float HEIGHT = 40.0f;
	
	public PhysicsWorld(){
		
		 m_world = new World(new Vec2(0,0));
		 timeStep= 1.0f / 60.0f;
	}
	void init(){
	    for (int i = 1; i <3; i++) {
		      PolygonShape polygonShape = new PolygonShape();
		      polygonShape.setAsBox(5,.5f);
		      
		      BodyDef bodyDef = new BodyDef();
		      bodyDef.type = BodyType.DYNAMIC;
		      bodyDef.position.set(i+10, 10);
		      bodyDef.angle = (float) (Math.PI / 4 * i);
		      bodyDef.allowSleep = false;
		      bodyDef.userData="box";
		      Body body = getWorld().createBody(bodyDef);
		      body.createFixture(polygonShape, 5.0f);

		      //body.applyForce(new Vec2(-10000 * (i - 1), 0), new Vec2());
		}
			getWorld().setGravity(new Vec2(0,0));
		    CircleShape circle = new CircleShape();
		    circle.m_radius=1;
		    BodyDef circleDef = new BodyDef();
		    circleDef.type = BodyType.DYNAMIC;
		    circleDef.position.set(10, 10);
		    circleDef.linearVelocity=new Vec2(0,10);
		    circleDef.allowSleep = false;
		    circleDef.userData="circle";
		    Body circleBody = getWorld().createBody(circleDef);
		    circleBody.createFixture(circle, 5.0f);
		    
		    
		    //GAME SURFACE EDGES
			BodyDef edgeDef=new BodyDef();
			edgeDef.type=BodyType.STATIC;
			EdgeShape edge=new EdgeShape();
			edge.set(new Vec2(0,0),new Vec2(WIDTH,0));
			edges.add(edge);
			
			Body edgeBody=getWorld().createBody(edgeDef);
			edgeBody.createFixture(edge, 0).m_restitution=1;
			
			BodyDef edgeDef1=new BodyDef();
			edgeDef1.type=BodyType.STATIC;
			EdgeShape edge1=new EdgeShape();
			edge1.set(new Vec2(0,0),new Vec2(0,HEIGHT));
			edges.add(edge1);
			
			Body edgeBody1=getWorld().createBody(edgeDef1);
			edgeBody1.createFixture(edge1, 0).m_restitution=1;
			
			BodyDef edgeDef2=new BodyDef();
			edgeDef2.type=BodyType.STATIC;
			EdgeShape edge2=new EdgeShape();
			edge2.set(new Vec2(0, HEIGHT),new Vec2(WIDTH,HEIGHT));
			edges.add(edge2);
			
			Body edgeBody2=getWorld().createBody(edgeDef2);
			edgeBody2.createFixture(edge2, 0).m_restitution=1;
			
			BodyDef edgeDef3=new BodyDef();
			edgeDef3.type=BodyType.STATIC;
			EdgeShape edge3=new EdgeShape();
		
			edge3.set(new Vec2(WIDTH, HEIGHT),new Vec2(WIDTH,0));
			edges.add(edge3);
			
			Body edgeBody3=getWorld().createBody(edgeDef3);
			
			edgeBody3.createFixture(edge3, 0).m_restitution=1;
			//END GAME SURFACE EDGES
	}
	
	public ArrayList<EdgeShape> getEdges(){
		return this.edges;
	}
	
	public EdgeShape getEdge(int i){
		return edges.get(i);
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
		  //m_world.setContinuousPhysics(true);
		  m_world.step(timeStep, velocityIterations, positionIterations);
	  } 
	
}
