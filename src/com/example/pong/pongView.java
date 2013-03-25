package com.example.pong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class pongView extends View{
	MultiTouchController mController;
	Paint p;
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
	    }
	    @Override
	    public void onDraw(Canvas canvas) {
	       canvas.save();
	       mController.show(canvas);
	       canvas.restore();
	       super.onDraw(canvas);
	    }
	 @Override
	 public boolean onTouchEvent(MotionEvent me) {
		int action= whichAction(me);
		    if (action==1) {
		        mController.touch(me, whichFinger(me)); //Register the touch event
		        System.out.println("Brian Says: Touch event");
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
