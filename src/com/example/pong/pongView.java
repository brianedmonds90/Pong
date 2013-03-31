package com.example.pong;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class pongView extends View{
	MultiTouchController mController;
	Paint p;
	Paddle a,b;
	pt p1L,p1R,p2L,p2R;
	PhysicsWorld pWorld;
	PhysicsWorld game;
	EdgeShape e,e1,e2,e3;
	vec v;
	float screenWidth,screenHeight;//Height and width in pixels
	UIHelper ui;
	VelocityTracker vTracker;
	//MultiTouch m;
	public pongView(Context context, PhysicsWorld pWorld){
		this(context);
		this.pWorld = pWorld;
	}
	
	 public pongView(Context context) {
	    	this(context, null, 0);	
	    }  
	    public pongView(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }
	    public pongView(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle); 
	        mController=new MultiTouchController();
	        mController.init();
	        p=new Paint();
	        game=new PhysicsWorld();
	        game.init();
	        ui=new UIHelper(game,this,mController);
	        
	        //ui.initPaddle(mController.getMultiTouchAt(0));
	        
	    }
	    @Override
	    public void onDraw(Canvas canvas) {
	    	game.update();
	    	
	    	super.onDraw(canvas);
		    canvas.save();
		    p.setColor(Color.BLACK);
		    setScreenWidth(canvas);
		    setScreenHeight(canvas);
		    drawGame(canvas, p);
		    try{
		    	v.show(mController.getDiskAt(0), canvas, p);
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		    mController.getMultiTouchAt(0).show(canvas);
		    p.setColor(Color.RED);
		  
		    canvas.restore();
	       
	       invalidate();
	    }
	 @Override
	 public boolean onTouchEvent(MotionEvent me) {
		int action= whichAction(me);
		vTracker=VelocityTracker.obtain();
		    if (action==1) {
		        mController.touch(me, whichFinger(me)); //Register the touch event
		        ui.userMove(mController.getMultiTouchAt(0));
		        v=velocity(me);
		        invalidate();
	          
		    }
		    else if (action==0) {
		      mController.lift(whichFinger(me)); //Register the lift event
		      v=velocity(me);
		      invalidate();
		    }
		    else if (action==2) {
		      mController.motion(me);//Register the motion event
		      v=velocity(me);
		      
		      //ui.userMove(mController.getMultiTouchAt(0));
		      ui.userMove(v);
		      
		      invalidate();
		    }
	    return true;
	 }
	
	vec velocity(MotionEvent me){
		vTracker.obtain();
		vTracker.addMovement(me);
	    vTracker.computeCurrentVelocity(1000);
	    return new vec(vTracker.getXVelocity(),vTracker.getYVelocity());
	}
	int whichAction(MotionEvent me) { // 1=press, 0=release, 2=drag
		  int action = me.getAction(); 
		  int aaction = action & MotionEvent.ACTION_MASK;
		  int what=0;
		  if (aaction==MotionEvent.ACTION_POINTER_UP || aaction==MotionEvent.ACTION_UP) what=0;
		  if (aaction==MotionEvent.ACTION_DOWN || aaction==MotionEvent.ACTION_POINTER_DOWN) what=1;
		  if (aaction==MotionEvent.ACTION_MOVE) what=2;
		  return what;
	}  
	int whichFinger(MotionEvent me) {
	  int pointerIndex = (me.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)>> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
	  int pointerId = me.getPointerId(pointerIndex);
	  return pointerId;
	}
	public void runGame(){

	}
	public void pauseGame(){
		
	}
	
	//***Drawing Functions
	void drawGame(Canvas canvas,Paint p){
//		
//		Vec2 v=new Vec2(200,300);
//		Vec2 v1=new Vec2(300,300);
//		Vec2 v2=v.rotate(v,30.0f,v1);
//		p.setColor(Color.BLACK);
//		
//		showVec2(v,canvas,p);
//		p.setColor(Color.BLUE);
//		showVec2(v1,canvas,p);
//		p.setColor(Color.RED);
//		showVec2(v2,canvas,p);
		
		//Draw the edges of the game surface
//		ArrayList<EdgeShape> edges = game.getEdges();
//		p.setColor(Color.BLUE);
//		p.setStrokeWidth(17.5f);
//		p.setStyle(Paint.Style.STROKE);
		
//		for(EdgeShape edge : game.getEdges()){
//			Vec2 v1 = edge.m_vertex1;
//			Vec2 v2 = edge.m_vertex2;
//			canvas.drawLine(toScreenX(v1.x), toScreenY(v1.y), toScreenX(v2.x), toScreenY(v2.y), p);
//		}
		//End drawing edges
		
		Body list=game.getBodyList();
		
		p.setStyle(Paint.Style.FILL);
		while(list!=null){

		 if(list.m_userData=="circle"){//draw the circles
			 canvas.drawCircle(toScreenX(list.getPosition().x),toScreenY(list.getPosition().y),
					 (int)(Math.sqrt((screenWidth*screenHeight)/800.0)), p);
		 } 
		 if(list.m_userData=="box"){//Draw the boxes
			 p.setColor(Color.RED);
			 drawPaddle(list,canvas,p);
			 
		 }
		 list=list.getNext();
		}
	}
	private float toScreenX(float x){	  
	  return (float) (x*(screenWidth/20.0));
	}
	private float toScreenY(float y) {
		return (float) (y*(screenHeight/40.0));
	}
	float getScreenWidth(Canvas c){
		return c.getWidth();
	}
	float getScreenHeight(Canvas c){
		return c.getHeight();
	}
	void setScreenWidth(Canvas c){
		screenWidth= c.getWidth();
	}
	void setScreenHeight(Canvas c){
		screenHeight=c.getHeight();
	}
	
	void drawPaddle(Body paddle,Canvas c,Paint p){
		Fixture fixture=paddle.getFixtureList();
	    PolygonShape poly = (PolygonShape) fixture.getShape();
	    int vertexCount = poly.m_count;
	    Vec2[] vertices = new Vec2[vertexCount];
	    p.setColor(Color.RED);
	    Transform t=paddle.getTransform();
	    Vec2 vTemp;
	    for (int i = 0; i < vertexCount; ++i) {
	      vertices[i]=poly.m_vertices[i].translate(t.p);
	      vTemp=vertices[i];
	      vertices[i]=rotate(vTemp, t.q, paddle);
	    }
	    drawPolygon(vertices,vertexCount,paddle,c,p);
	}
	public void drawPolygon(Vec2[] vertices, int vertexCount,Body b, Canvas c, Paint p){
		
		if(vertexCount == 1){
			drawSegment(vertices[0], vertices[0], b,c,p);
			return;
		}
		
		for(int i=0; i<vertexCount-1; i+=1){
			drawSegment(vertices[i], vertices[i+1], b,c,p);
		}
		
		if(vertexCount > 2){
			drawSegment(vertices[vertexCount-1], vertices[0], b,c,p);
		}
	}
	
	//deprecated
	public void drawBox(Body b,Canvas c,Paint p){
		pt a=new pt(b.getPosition().x,b.getPosition().y);
		Fixture fixture=b.getFixtureList();
		
		PolygonShape shape=(PolygonShape) fixture.getShape();
		Transform t=b.getTransform();
		p.setStrokeWidth(5);
		p.setColor(Color.GREEN);
		c.drawLine(toScreenX(a.x),toScreenY(a.y),toScreenX(t.p.x)+100, toScreenY(t.p.y)+100,p);
		
	}
	
	private float toScreen(float x){
		return (float) (x*(screenWidth*screenHeight)/800.0);
	}
	private void drawSegment(Vec2 vec2, Vec2 vec22,Body body, Canvas c,Paint p) {
		Transform t=body.getTransform();
		c.drawLine(toScreenX(vec2.x),toScreenY(vec2.y),
				toScreenX(vec22.x),toScreenY(vec22.y),p);
	}
	private Vec2 rotate(Vec2 v,Rot r,Body b){
		Vec2 vec=new Vec2();
		vec=vec.rotate(v, r.getAngle(), b.getPosition());
		return vec;
	}
	private Vec2 rotate(Vec2 v,Rot r,Vec2 b){
		Vec2 vec=new Vec2();
		vec=vec.rotate(v, r.getAngle(), b);
		return vec;
	}
	public void showVec2Screen(Vec2 v,Canvas c, Paint p){
		c.drawCircle(toScreenX(v.x), toScreenY(v.y),10, p);
	}
	public void showVec2(Vec2 v,Canvas c, Paint p){
		c.drawCircle(v.x, v.y,10, p);
	}
	
}
