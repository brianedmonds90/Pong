package com.example.pong;

import java.util.ArrayList;

import org.jbox2d.common.Vec2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
class MultiTouch{
  Vec2 currentTouch, lastTouch, disk;
  boolean selected;
  int meIndex;
  Vec2 movement; 
  ArrayList <Vec2>history;
  MultiTouch(){
   currentTouch=new Vec2();
   lastTouch= new Vec2();
   disk=new Vec2();
   selected=false;
   meIndex=-1;
   history=new ArrayList<Vec2>();

  }
  MultiTouch(float x,float y){
   currentTouch=new Vec2();
   lastTouch= new Vec2();
   disk=new Vec2(x,y);
   selected=false;
   meIndex=-1;
   history=new ArrayList<Vec2>();

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
	  
	  for(Vec2 a: history){
		  c.drawCircle(a.x,a.y,15, p);
	  }
  }
  Vec2 getHistoryAt(int index){
	  return history.get(index);
  }
}