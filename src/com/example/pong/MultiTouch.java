package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
class MultiTouch{
  pt currentTouch, lastTouch, disk;
  boolean selected;
  int meIndex;
  pt movement; 

  MultiTouch(){
   currentTouch=new pt();
   lastTouch= new pt();
   disk=new pt();
   selected=false;
   meIndex=-1;


  }
  MultiTouch(float x,float y){
   currentTouch=new pt();
   lastTouch= new pt();
   disk=new pt(x,y);
   selected=false;
   meIndex=-1;

  }
  void lift(){
   //this.meIndex=-1;
   this.selected=false;

  }


  void show(Canvas canvas){
	 Paint p=new Paint();
     if(this.selected){
       p.setColor(Color.RED);
       canvas.drawCircle(this.disk.x,this.disk.y,15, p);
       
       System.out.println("Brian: Anything Selected");
    	// fill(0,255,0);
       //ellipse(this.disk.x,this.disk.y,15,15);
     }
     else{
    	 p.setColor(Color.BLACK);
         canvas.drawCircle(this.disk.x,this.disk.y,15, p);
     }
  }
  public String toString(){
    String ret="";
    ret+= "disk: "+disk;
    ret+= " currentTouch: "+currentTouch+" lastTouch: "+lastTouch+" meIndex: "+meIndex+ "Selected: "+selected;
   return ret; 
  }

}