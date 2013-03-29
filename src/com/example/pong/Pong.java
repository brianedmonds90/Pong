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
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_pong, menu);
		return true;
	}
 


}
