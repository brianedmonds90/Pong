package com.example.pong;



import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class Pong extends Activity {
	pongView pView;
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_pong);
		
		pView=new pongView(getBaseContext());
		setContentView(pView); 
//		 setContentView(R.layout.activity_pong);
//		mHandler = new Handler();
//		mHandler.post(update);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pong, menu);
		return true;
	}
//	private Runnable update = new Runnable(){
//		public void run(){
//			game.update();
//			pView.postInvalidate();
//			//update again after the time step
//			//System.out.println("game loop...");
//			mHandler.postDelayed(update, (long)(game.timeStep*1000));
//		}
//	};
 


}
