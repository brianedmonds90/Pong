package com.example.pong;

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
  void show() {
	  //ellipse(x,y,3,3); return this;
	  }                 
    
  public String toString(){
   return x+", "+y+" "; 
  }
  } // end of pt class
