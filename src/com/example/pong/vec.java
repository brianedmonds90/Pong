package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

//vectors
class vec { 
	float x=0,y=0; 
	vec (float d, float e) {
		x = d; 
		y = e;
	}
	vec V(vec V) {return new vec(V.x,V.y); };                                                             // make copy of vector V
	vec V(vec U,vec V) {return new vec(U.x+V.x,U.y+V.y); };                                                             // make copy of vector V
	vec V(float x, float y) {return new vec(x,y); };                                                      // make vector (x,y)
	vec V(pt P, pt Q) {return new vec(Q.x-P.x,Q.y-P.y);};                                                 // PQ (make vector Q-P from P to Q
	vec U(vec V) {float n = n(V); if (n==0) return new vec(0,0); else return new vec(V.x/n,V.y/n);};      // V/||V|| (Unit vector : normalized version of V)
	vec V(float s,vec V) {return new vec(s*V.x,s*V.y);};                                                  // sV
	float n(vec V) {return (float) Math.sqrt(Math.pow((V.x),2)+Math.pow((V.y),2));};                                                       // n(V): ||V|| (norm: length of V)
	void show(pt a,Canvas c,Paint p){
		p.setColor(Color.BLUE);
		c.drawLine(a.x, a.y, this.x, this.y, p);
	}
} // end vec class