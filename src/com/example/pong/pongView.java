package com.example.pong;

import java.util.ArrayList;

import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class pongView extends View{
	MultiTouchController mController;
	Paint p;
	Paddle a,b;
	pt p1L,p1R,p2L,p2R;
	PhysicsWorld pWorld;
	PhysicsWorld game;
	EdgeShape e,e1,e2,e3;
	float screenWidth,screenHeight;//Height and width in pixels
	
	Scoreboard scoreboard;
	
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
        scoreboard = new Scoreboard(new pt(20,20));
        game.setScoreboard(scoreboard);
        
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
	    
	    
	    
	    scoreboard.draw(canvas,(float)getResources().getDimensionPixelSize(R.dimen.boardFontSize));
	    canvas.restore();       
	    invalidate();
    }
	 @Override
	 public boolean onTouchEvent(MotionEvent me) {
		int action= whichAction(me);
		    if (action==1) {
		        mController.touch(me, whichFinger(me)); //Register the touch event
		        invalidate();
	          
		    }
		    else if (action==0) {
		      mController.lift(whichFinger(me)); //Register the lift event
		      invalidate();
		    }
		    else if (action==2) {
		      mController.motion(me);//Register the motion event
		      
		      invalidate();
		    }
		   
	    return true;
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
		
		/**
		//Draw the edges of the game surface
		ArrayList<EdgeShape> edges = game.getEdges();
		p.setColor(Color.BLUE);
		p.setStrokeWidth(17.5f);
		p.setStyle(Paint.Style.STROKE);
		
		for(EdgeShape edge : game.getEdges()){
			Vec2 v1 = edge.m_vertex1;
			Vec2 v2 = edge.m_vertex2;
			canvas.drawLine(toScreenX(v1.x), toScreenY(v1.y), toScreenX(v2.x), toScreenY(v2.y), p);
		}
		//End drawing edges
		**/
		Body list=game.getBodyList();
		
		p.setStyle(Paint.Style.FILL);
		while(list!=null){
			if(list.m_userData=="circle"){
				p.setColor(Color.BLACK);
				canvas.drawCircle(toScreenX(list.getPosition().x),toScreenY(list.getPosition().y), 20, p);
			}
			if(list.m_userData=="box"){
				p.setColor(Color.RED);
				//canvas.drawRect(10, 10, 15, 200, p);
				canvas.drawCircle(toScreenX(list.getPosition().x),toScreenY(list.getPosition().y),15, p);
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
		c.drawCircle(toScreenX(paddle.getPosition().x),toScreenY(paddle.getPosition().y),10,p);
	}
}
