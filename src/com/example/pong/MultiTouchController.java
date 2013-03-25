package com.example.pong;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.view.MotionEvent;

class MultiTouchController{//Used to process the android API touch events for easy use by applications
   ArrayList <MultiTouch> mTContainer;//Container for MultiTouch objects
   MultiTouchController(int num){
      mTContainer=new ArrayList<MultiTouch>(num);
      for(int i=0;i<num;i++){
        mTContainer.add(new MultiTouch()); 
      }
   }
     MultiTouchController(){
      mTContainer=new ArrayList<MultiTouch>();
   }
   public void init(){//Puts disk objects on the screen to be moved around
    
      mTContainer.add(new MultiTouch(300,100));
      mTContainer.add(new MultiTouch(300,400));
      mTContainer.add(new MultiTouch(600,100));
      mTContainer.add(new MultiTouch(600,400));

   }
  public void touch(MotionEvent ev, int pointerId){//Method used when a touch event happens
    pt cTouch= new pt(ev.getX(pointerId),ev.getY(pointerId));
    MultiTouch finger;
   if(mTContainer.size()<4){
      finger=new MultiTouch(cTouch.x,cTouch.y);
      finger.selected=true;
      finger.meIndex=pointerId;
      finger.lastTouch=cTouch;
      //finger.history.add(finger.lastTouch);
      mTContainer.add(finger);
    }
    else{
     MultiTouch temp =findClosest(cTouch);
      if(temp!=null){
        temp.selected=true;
        temp.meIndex=pointerId;
        temp.lastTouch=cTouch; //Keep track of the touch location for movement
        //temp.history.add(temp.disk);
        System.out.println("Selection touch event");
     }
    }
    
  }
  public void lift(int pointerId){//Used when a finger is lifted
    MultiTouch temp=null;
    for(int i=0;i<mTContainer.size();i++){//iterate through the multiTouch Container object
      temp=mTContainer.get(i); 
      if(temp.meIndex==pointerId){
          temp.selected=false;
          temp.meIndex=-1;
         // temp.history.clear();
      }
    }
  // smoothing=true; sfairInit(); fstp=0; 
  }
  public MultiTouch findClosest(pt aa){//Returns the index of the closest disk of the container to the 
    float minDistance= Float.MAX_VALUE;
    MultiTouch closest=null;
    for(MultiTouch mt: mTContainer){
      float d= d(aa,mt.disk);
      if(d<minDistance&&!mt.selected){
        minDistance=d;
        closest=mt; 
      }
    }
    return closest; 
  }
  public void motion(MotionEvent me){//Used when a finger moves on the screen
    MultiTouch temp=null;
    //println("Inside of motion");
    for(int i=0;i<me.getPointerCount();i++){
      int j=me.getPointerId(i);
      int index=indexOf(j);
        if(index!=-1 && mTContainer.get(index).selected){
          temp=mTContainer.get(index);
          //log the current position of the users fingers
          temp.currentTouch= new pt(me.getX(i),me.getY(i));
          //calculate the distance moved from the previous frame and move the point
          temp.disk.move(temp.currentTouch.subtract(temp.lastTouch));
          temp.lastTouch.set(temp.currentTouch);
        }
    }
  } 
  void show(Canvas canvas){//shows the disks  
    int num=0;
    for(int i=0;i<mTContainer.size();i++){
      mTContainer.get(i).show(canvas); 
    }
  }
  public String toString(){
    String ret="";
    for(int i=0;i<mTContainer.size();i++){ 
      ret+="Multitouch: "+mTContainer.get(i);
      ret+="\n";
    }
    return ret;
  }
  int indexOf(int pointerId){
    for(int i=0;i<mTContainer.size();i++){
      if(mTContainer.get(i).meIndex==pointerId&&mTContainer.get(i).selected){
        return i; 
      }  
    }
    return -1;
  } 
  pt firstPt(){//Returns the first point of the MultiTouchController
    return mTContainer.get(0).disk; 
  }
  int size(){
     return this.mTContainer.size(); 
  }
  pt getDiskAt(int index){
    return this.mTContainer.get(index).disk;
  }
  MultiTouch getAt(int index){
   return mTContainer.get(index); 
  }

 MultiTouch getMultiTouchAt(int i){
  return mTContainer.get(i); 
 }
 float d(pt P, pt Q) {return (float) Math.sqrt(d2(P,Q));  };                                                       // ||AB|| (Distance)
 float d2(pt P, pt Q) {
	 return (float) (Math.pow((Q.x-P.x),2)+Math.pow((Q.y-P.y),2)); };                                             // AB*AB (Distance squared)
}

