package com.example.pong;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.collision.shapes.EdgeShape;
import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Rot;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.Fixture;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

public class pongView extends View{
	MultiTouchController mController;
	Paint p;
	PhysicsWorld pWorld;
	PhysicsWorld game;
	EdgeShape e,e1,e2,e3;
	Vec2 v,angular;
	Vec2 lP,rP;
	boolean moveL,moveR;
	boolean winner;
	private Drawable cloud;
	private Drawable apple;
	private Bitmap appleTest;
	Scoreboard scoreboard;
	Path gamePiece;
	public Pong pActivity;
	float screenWidth,screenHeight;//Height and width in pixels
	UIHelper ui;
	VelocityTracker vTracker;
	public boolean drawWinScreen;
	Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.green_grass);
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
	    //mController.init();
	    p=new Paint();
	    game=new PhysicsWorld();
	    scoreboard = new Scoreboard(new Vec2(20,20),0,0,10);
	    game.setScoreboard(scoreboard);
	    game.init();
	    ui=new UIHelper(game,this,mController,scoreboard);
	    ui.setContactListener();
	    lP=new Vec2();
	    drawWinScreen=false;
	    apple = (BitmapDrawable) context.getResources().getDrawable(
	                    R.drawable.apple);
	    cloud= context.getResources().getDrawable(
                R.drawable.cloud);
	    appleTest= BitmapFactory.decodeResource(getResources(), R.drawable.apple);
	    //apple= Bitmap.createBitmap(context.getResources().getDrawable(R.drawable.apple));
	    
	    }
		@Override
	    public void onDraw(Canvas canvas) {
	    	game.update();
	    	super.onDraw(canvas);
	    //	Draw the backround-below method is too slow
	    	
//	    	
	    	Rect dest = new Rect(0, 0, getWidth(), getHeight());
	    	Paint paint = new Paint();
	    	paint.setFilterBitmap(true);
	    	canvas.drawBitmap(background, null, dest, paint);
		    canvas.save();
		    scoreboard.draw(canvas,
		    		(float)getResources().getDimensionPixelSize(R.dimen.boardFontSize));
		    p.setColor(Color.BLACK);
		    setScreenWidth(canvas);
		    setScreenHeight(canvas);
		    drawGame(canvas, p);
		    //TODO these need to be refactored into a single draw
		    Body paddle=ui.getPaddle();
		    lP=ui.getleftCoord(paddle);
		    rP=ui.getRightCoord(paddle);
		    p.setColor(Color.RED);
		    ui.showPhysVec(lP, canvas, p);
		    p.setColor(Color.BLUE);
		    ui.showPhysVec(rP,canvas,p);
	        canvas.drawLine(toScreenX(rP.x),toScreenY(rP.y),toScreenX(lP.x),toScreenY(lP.y),p);

		    
		    try{
		    	p.setStrokeWidth(5);
		    	if(mController.getDiskAt(0).x<mController.getDiskAt(1).x){
		    		canvas.drawLine(toScreenX(lP.x),toScreenY(lP.y),
		    			mController.getDiskAt(0).x,mController.getDiskAt(0).y,p);
		    		try{
				    	canvas.drawLine(toScreenX(rP.x),toScreenY(rP.y),
				    			mController.getDiskAt(1).x,mController.getDiskAt(1).y,p);
				    }
				    catch(Exception e){
				    	e.printStackTrace();
				    }
		    	}
		    	else{
		    		canvas.drawLine(toScreenX(lP.x),toScreenY(lP.y),
			    			mController.getDiskAt(1).x,mController.getDiskAt(1).y,p);
			    		try{
					    	canvas.drawLine(toScreenX(rP.x),toScreenY(rP.y),
					    			mController.getDiskAt(0).x,mController.getDiskAt(0).y,p);
			    		}
			    		   catch(Exception e)
			   		    {
			   		    	e.printStackTrace();
			   		    }
		    	}
		    }
		    catch(Exception e)
		    {
		    	e.printStackTrace();
		    }
		    
		    try{
		    	mController.show(canvas);
		    }
		    catch(Exception e){
		    	e.printStackTrace();
		    }
		   
	       if(game.winner){
//	    	   p.setColor(Color.BLACK);
//	    	   canvas.drawCircle(200, 200, 100, p);
	    	   try{
	    	   Intent gameOverScreen = new Intent(pActivity, WinnerActivity.class);
	    	   pActivity.startActivity(gameOverScreen);
	    	   }
	    	   catch(Exception e){
	    		   
	    	   }
	       }
	       else{
	       canvas.restore();
	       invalidate();
	       }
	    	
	    }
	 @Override
	 public boolean onTouchEvent(MotionEvent me) {
		int action= whichAction(me);
		vTracker=VelocityTracker.obtain();
		if(mController.size()==1){
			 Body paddle=ui.getPaddle();
			 float distance=0;
			 try{
				 distance=ui.getleftCoord(paddle).disTo(mController.getDiskAt(0));
			 }
			 catch(Exception e){
				 e.printStackTrace();
			 }
	    	  if(distance<ui.getRightCoord(paddle).disTo(mController.getDiskAt(0)))
	    	  {
	    		  moveL=true;
	    		  moveR=false;
	    	  }
	    	  else{
	    		  moveR=true;
	    		  moveL=false;
	    	  }
		}
		if (action==1) {
		        mController.touch(me, whichFinger(me)); //Register the touch event
		     //   invalidate();
		    }
		    else if (action==0) {
		      mController.lift(whichFinger(me)); //Register the lift event
		     moveL=false;
		     moveR=false;
		 
		   //   invalidate();
		    }
		    else if (action==2) {
		      mController.motion(me);//Register the motion event
		      if(mController.size()==2){
		    	  ui.move(mController.getDiskAt(0),mController.getDiskAt(1));
		        }
		      else{
		    	  if(moveL)
		    	  {
		    		  ui.moveL(mController.getDiskAt(0));
		    	  }
		    	  else if(moveR){
		    		  ui.moveR(mController.getDiskAt(0));
		    	  }
		      }
		     // invalidate();
		    }
	    return true;
	 }
	Vec2 velocity(MotionEvent me){
		vTracker.obtain();
		vTracker.addMovement(me);
	    vTracker.computeCurrentVelocity(1000);
	    return new Vec2(vTracker.getXVelocity(),vTracker.getYVelocity());
	}
	Vec2 velocity(MotionEvent me, int index){
		vTracker.obtain();
		vTracker.addMovement(me);
	    vTracker.computeCurrentVelocity(1000);
	    return new Vec2(vTracker.getXVelocity(1),vTracker.getYVelocity(1));
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

		//End drawing edges
		
		Body list=game.getBodyList();
		
		p.setStyle(Paint.Style.FILL);
		while(list!=null){
		
		 if(list.m_userData=="circle"){//draw the circles

			 drawDrawable(list,canvas,apple);
	//		 drawRotatedApple(list,canvas,appleTest);
			 
//			 canvas.drawCircle(toScreenX(list.getPosition().x),toScreenY(list.getPosition().y),
//					 (int)(Math.sqrt((screenWidth*screenHeight)/800.0)), p);
		 } 
		 if(list.m_userData=="box"){//Draw the boxes
			 
			 
			// gamePiece=new Path();
			 drawPaddle(list,canvas,p);
			
		 }

		 if(list.m_userData=="goalBarrier"){
			 p.setColor(Color.YELLOW);
			 drawBlock(list,canvas,p);
		 }
		 if(list.m_userData=="goal"){
			 p.setColor(Color.MAGENTA);
			 drawBlock(list,canvas,p);
		 }
		 if(list.m_userData=="block"){
		//	 p.setColor(Color.GREEN);
			// drawBlock(list,canvas,p);
			 drawCloud(list,canvas);
		 }
		 list=list.getNext();
		}
	}
	private void drawDrawable(Body list, Canvas canvas,Drawable bitmap){
		 Fixture fixture = list.getFixtureList();
		 CircleShape polygon = (CircleShape)fixture.getShape();
		 float radius=polygon.m_radius;
		 float yTop=toScreenY(list.getPosition().y-radius);
		 float xLeft=toScreenX(list.getPosition().x-radius);
		 float xRight=toScreenX(list.getPosition().x+radius);
		 float yBottom=toScreenY(list.getPosition().y+radius);
		 bitmap.setBounds((int)xLeft+5, (int)yTop+5, (int)xRight+5,(int)yBottom+5);
		 bitmap.draw(canvas);
	}
	private void drawRotatedApple(Body list, Canvas canvas,Bitmap b){
		 Fixture fixture = list.getFixtureList();
		 CircleShape polygon = (CircleShape)fixture.getShape();
		 float radius=polygon.m_radius;
		 float yTop=toScreenY(list.getPosition().y-radius);
		 float xLeft=toScreenX(list.getPosition().x-radius);
		 float xRight=toScreenX(list.getPosition().x+radius);
		 float yBottom=toScreenY(list.getPosition().y+radius);
		 Matrix m=new Matrix();
		 m.postRotate(list.getAngle());
//		 appleTest= Bitmap.createBitmap(appleTest, (int)xLeft, (int)yTop, 
//				 100, 100, m, true);
		// canvas.drawBitmap(b,m,new Paint());

	}
	private void drawCloud(Body list, Canvas canvas) {
		// TODO Auto-generated method stub
		
		 Fixture fixture = list.getFixtureList();
		 PolygonShape polygon = (PolygonShape)fixture.getShape();
		 float yTop=toScreenY(list.getTransform().p.y+polygon.getTopBound());
		 float xLeft=toScreenX(list.getTransform().p.x+polygon.getLeftBound());
		 float xRight=toScreenX(list.getTransform().p.x+polygon.getRightBound());
		 float yBottom=toScreenY(list.getTransform().p.y+polygon.getBottomBound());
		 cloud.setBounds((int)xLeft-5, (int)yTop-5, (int)xRight+5,(int)yBottom+5);
		 cloud.draw(canvas);
		 
	}
	void drawBlock(Body block, Canvas c, Paint p){
	    Fixture fixture = block.getFixtureList();
	    PolygonShape polygon = (PolygonShape)fixture.getShape();
	    
	    int v_count = polygon.m_count;
	    //this holds the vertices in world space
	    Vec2[] vertices = new Vec2[v_count];
	    Transform t=block.getTransform();
		    Vec2 temp;
		    for(int i=0; i<v_count; i++){
		      vertices[i]=polygon.m_vertices[i].translate(t.p);
		      temp = vertices[i];
		      vertices[i]=rotate(temp,t.q,block);
		    }
		    drawFilledBlock(vertices,c,p);
	  }
	   public void drawFilledBlock(Vec2[] vertices, Canvas c, Paint p){
		     //vertex order for block
		     // 0 ---- 1
		     // 3 ---- 2
		     p.setStyle(Paint.Style.FILL);
		     c.drawRect(toScreenX(vertices[0].x),toScreenY(vertices[0].y)
		         ,toScreenX(vertices[2].x),toScreenY(vertices[2].y), p);
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
	   // drawFilledPaddle(vertices,vertexCount,c,p);
	    drawPolygon(vertices,vertexCount,paddle,c,p);
	}
	private void drawFilledPaddle(Vec2[] vertices, int vertexCount, Canvas c,
			Paint p) {
		//gamePiece.moveTo(vertices[0].x, vertices[0].y);
		for(int i=0;i<vertexCount;i++){ 
			gamePiece.lineTo(toScreenX(vertices[i].x), toScreenY(vertices[i].y));
		}
		p.setStyle(Paint.Style.FILL);
		c.drawPath(gamePiece,p);
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
	private float toScreen(float x){
		return (float) (x*(screenWidth*screenHeight)/800.0);
	}
	private void drawSegment(Vec2 vec2, Vec2 vec22,Body body, Canvas c,Paint p) {
		p.setColor(Color.BLACK);
		c.drawLine(toScreenX(vec2.x),toScreenY(vec2.y),
				toScreenX(vec22.x),toScreenY(vec22.y),p);
	}
	public Vec2 rotate(Vec2 v,Rot r,Body b){
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
	public void setUIActivity(Pong p){
		ui.setPActivity(p);
	}
}