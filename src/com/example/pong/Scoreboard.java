package com.example.pong;

import org.jbox2d.common.Vec2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Scoreboard {

	private int p1Score;
	private int p2Score;
	public int maxScore;
	//center of the scoreboard
	private Vec2 loc;



	public Scoreboard(){
		this(new Vec2(0,0));
	}
	public Scoreboard(Vec2 loc){
		this(loc,0,0);
	}
	public Scoreboard(Vec2 loc, int p1Score, int p2Score){
		this.loc = loc;
		this.p1Score = p1Score;
		this.p2Score = p2Score;
	}
	public Scoreboard(Vec2 loc, int p1Score, int p2Score, int myMaxScore){
		this(loc,p1Score,p2Score);
		this.maxScore=myMaxScore;
	}
	

	/** Getters and Setters **/
	public int getP1Score(){
		return p1Score;
	}
	public int getP2Score(){
		return p2Score;
	}

	public void setP1Score(int score){
		p1Score = score;
	}
	public void setP2Score(int score){
		p2Score = score;
	}

	public Vec2 getLoc(){
		return loc;
	}

	public void setLoc(Vec2 newLoc){
		loc = newLoc;
	}

	/** Game Logic Methods **/
	public void incScore(){
		p1Score+=2;
		
	}
	public void decScore(){
		p1Score--;
	}
	public void incP2Score(){
		p2Score++;
	}

	public boolean isP1Win(){
		if(p1Score >= maxScore){
			return true;
		}
		else
			return false;
	}

	public boolean isP2Win(){
		if(p2Score >= maxScore){
			return true;
		}
		else
			return false;
	}
	
	/** String Manipulation Methods **/
	public String p1ToString(){
		return Integer.toString(p1Score);
	}
	public String p2ToString(){
		return Integer.toString(p2Score);
	}

	/** Draw Methods **/
	public void draw(Canvas c, float fontSize){

		float height = c.getHeight();
		float width = c.getWidth();
		//set up paint
		Paint p = new Paint();
		
		//draw dividing line
		
		p.setStrokeWidth(7.5f);
		p.setStyle(Paint.Style.FILL);
		p.setColor(Color.WHITE);
		c.drawRect(0, height/2f-10, 150, height/2f+60, p);
		
		//c.drawLine(10, height/2f , 150, height/2f, p);
		p.setStyle(Paint.Style.STROKE);
		p.setColor(Color.RED);
		p.setTextSize(fontSize);
		//draw the p1 score
		p.setStyle(Paint.Style.FILL);
		c.drawText(p1ToString(), 10, height/2f+40, p);



	

		//draw p2 score
//		p.setColor(Color.BLUE);
//		p.setStyle(Paint.Style.FILL);
//		c.drawText(p2ToString(), width - 60, height/2f + 60, p);
	}



}