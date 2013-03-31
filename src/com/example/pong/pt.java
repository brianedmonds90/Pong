package com.example.pong;

import org.jbox2d.common.MathUtils;
import org.jbox2d.common.Transform;
import org.jbox2d.common.Vec2;

import android.graphics.Canvas;
import android.graphics.Paint;

class pt{
float x=0,y=0; 
  pt (float px, float py) {x = px; y = py;};
  pt(){
    
  }
  public pt subtract(pt a){
    return new pt(this.x-a.x,this.y-a.y);
  }
   public void move(pt delta){
    this.x+=delta.x;
    this.y+=delta.y;
  }
   void set(pt p){
	   this.x=p.x;
	   this.y=p.y; 
  }
  void draw(){
   //fill(255,0,00);
    //ellipse(this.x,this.y,5,5);
  }
  void show(Canvas canvas,Paint p){
      canvas.drawCircle(this.x,this.y,15, p);
  }
  pt (pt P) {x = P.x; y = P.y;};
  pt setTo(float px, float py) {x = px; y = py; return this;};  
  pt setTo(pt P) {x = P.x; y = P.y; return this;}; 
 // pt moveWithMouse() { x += mouseX-pmouseX; y += mouseY-pmouseY;  return this;}; 
  pt translateTowards(float s, pt P) {x+=s*(P.x-x);  y+=s*(P.y-y);  return this;}   
  pt add(float u, float v) {x += u; y += v; return this;}                       
  pt add(pt P) {x += P.x; y += P.y; return this;};        
  pt add(float s, pt P)   {x += s*P.x; y += s*P.y; return this;};   
//  pt add(vec V) {x += V.x; y += V.y; return this;}                              
//  pt add(float s, vec V) {x += s*V.x; y += s*V.y; return this;}                 
                
  pt R(pt Q, float a) {
	  	float dx=Q.x, dy=Q.y,
		  c=MathUtils.cos(a), s=MathUtils.sin(a); 
  		return new pt(c*dx+s*dy,-s*dx+c*dy); };  // Q rotated by angle a around the origin
  pt R(pt Q, float a, pt C) {
	  	float dx=Q.x-C.x, dy=Q.y-C.y,
		  c=MathUtils.cos(a), s=MathUtils.sin(a); 
  		return P(C.x+c*dx-s*dy, C.y+s*dx+c*dy); };  // Q rotated by angle a around point P
  public String toString(){
   return x+", "+y+" "; 
  }
  float d(pt Q) {return (float) Math.sqrt(d2(Q));  };                                                       // ||AB|| (Distance)
	float d2(pt Q) {
	return (float) (Math.pow((Q.x-this.x),2)+Math.pow((Q.y-this.y),2)); };                                             // AB*AB (Distance squared)
	pt P(pt P, vec V) {
		return P(P.x + V.x, P.y + V.y); 
	}                                                 //  P+V (P transalted by vector V)
	pt P(float x, float y) {return new pt(x,y); }; // make point (x,y)
	pt toPt(Vec2 v){
		return new pt(v.x,v.y);
		
	}
	Vec2 toVec2(){
		return new Vec2(this.x,this.y);
	}
	pt addTransform(Transform t){
		return new pt(x+t.p.x,y+t.p.y);
	}
} // end of pt class

	