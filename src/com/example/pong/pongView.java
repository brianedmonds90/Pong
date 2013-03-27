package com.example.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
///Users/Brian/Downloads/android-ndk-r8e/ndk-build

public class pongView extends View{
	MultiTouchController mController;
	Paint p;
	Paddle a,b;
	pt p1L,p1R,p2L,p2R;
	PhysicsWorld pWorld;
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
	        a=new Paddle();
	        b=new Paddle();
	        p1L=new pt();p2L=new pt();p1R=new pt();p2R=new pt();
	    }
	    @Override
	    public void onDraw(Canvas canvas) {
	       canvas.save();
	       pWorld.draw(canvas,p);
	       pWorld.update();
	      // mController.show(canvas);
//	       a.setToFingers(mController.getMultiTouchAt(0), mController.getMultiTouchAt(1));
//	       b.setToFingers(mController.getMultiTouchAt(2), mController.getMultiTouchAt(3));
//	       try{
//	    	   System.out.println("paddle a: "+a.p1.toString());
//	    	   System.out.println("paddle b: "+b.p2.toString());
//	       }
//	       catch(Exception e){
//	    	   e.printStackTrace();
//	       }
//	       p.setColor(Color.RED);
//	       a.showAll(canvas,p);
//	       p.setColor(Color.BLUE);
//	       b.showAll(canvas,p);
	       canvas.restore();
	       super.onDraw(canvas);
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
//		      if(record)
//		        updateHistory();
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
}
