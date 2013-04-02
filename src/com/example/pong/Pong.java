package com.example.pong;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;

public class Pong extends Activity {
	pongView pView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		pView=new pongView(getBaseContext());
		pView.pActivity=this;
		setContentView(pView); 
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pong, menu);
		return true;
	}
	public void onPause(){
		super.onPause();
	}
	public void onResume(){
		super.onResume();
		PhysicsWorld.winner=false;
	}
}
