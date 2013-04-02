package com.example.pong;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;


public class StartMenu extends View{

	public StartMenu(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
	} 
	 public StartMenu(Context context, AttributeSet attrs) {
	        this(context, attrs, 0);
	    }
	 public StartMenu(Context context, AttributeSet attrs, int defStyle) {
	        super(context, attrs, defStyle); 
	 }
	 public void startGame(){
		 System.out.println("Button Pressed");
	 }

}
