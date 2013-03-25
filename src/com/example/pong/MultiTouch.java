package com.example.pong;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
class MultiTouch{
  pt currentTouch, lastTouch, disk;
  boolean selected;
  int meIndex;
  pt movement; 
  ArrayList <pt>history;
  MultiTouch(){
   currentTouch=new pt();
   lastTouch= new pt();
   disk=new pt();
   selected=false;
   meIndex=-1;
   history=new ArrayList<pt>();

  }
  MultiTouch(float x,float y){
   currentTouch=new pt();
   lastTouch= new pt();
   disk=new pt(x,y);
   selected=false;
   meIndex=-1;
   history=new ArrayList<pt>();

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
  void drawHistory(Canvas c,Paint p){
	  
	  for(pt a: history){
		  c.drawCircle(a.x,a.y,15, p);
	  }
  }
}