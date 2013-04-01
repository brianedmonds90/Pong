package com.example.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Paddle {
	pt p1,p2;
	vec direction;
	float length;
	public Paddle(){
		this.p1=new pt();
		this.p2=new pt();
		length=200;
		direction= new vec(0,0);
	}
	public Paddle(pt aa,pt bb){
		this.p1=aa;
		this.p2=bb;
		direction= new vec(0,0);
		length=200;
	}
	void show(Canvas canvas,Paint p){
		p.setColor(Color.BLACK);
		p.setStrokeWidth(5);
		canvas.drawLine(p1.x, p1.y, p2.x, p2.y, p);
	}
	void showAll(Canvas c,Paint p){
		this.p1.show(c,p);
		this.p2.show(c,p);
		show(c,p);

		
	}
	void testShow(Canvas canvas,Paint p){
		p.setColor(Color.BLACK);
		p.setStrokeWidth(10);
		canvas.drawLine(p1.x, p1.y, p1.x+length, p1.y+length, p);
	}
//	void setToFingers(MultiTouch m,MultiTouch n){
//		p1.set(m.disk);
//		p2.set(n.disk);
//		//direction=paddleDirection();
//		ensureSize();
//	}
	private void ensureSize(){//keeps the size of the paddle constant
		float d=p1.d(p2);
		vec t1,t2;
		if(d>length){
			t1=direction.V(p2,p1);
			t1=direction.U(t1);
			t1=direction.V((d-length)/2, t1);
			p2=p2.P(p2,t1);
			
			t2=direction.V(p1,p2);
			t2=direction.U(t2);
			t2=direction.V((d-length)/2, t2);
			p1=p1.P(p1,t2);
		}
//		else if(d<length){
//			t1=direction.V(b,a);
//			t1=direction.U(t1);
//			t1=direction.V((d+length)/2, t1);
//			b=b.P(b,t1);
//			
//			t2=direction.V(a,b);
//			t2=direction.U(t2);
//			t2=direction.V((d+length)/2, t2);
//			a=a.P(a,t2);
//		}
	}
	private vec paddleDirection(){
		
		return direction.V(p1,p2);
		
	}
}
