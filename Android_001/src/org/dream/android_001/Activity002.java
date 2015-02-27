package org.dream.android_001;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Activity002 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_activity002);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity002, menu);
		return true;
	}

}
