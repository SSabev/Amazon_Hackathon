package com.example.amazon_hack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

public class HomescreenActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.amazon_hack.MESSAGE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
		// metTextHint = (EditText) findViewById(R.id.etTextHint);
		// mlvTextMatches = (ListView) findViewById(R.id.lvTextMatches);
		// msTextMatches = (Spinner) findViewById(R.id.sNoOfMatches);
	}

	public void sendCode(View view) {
		// DO smth
		Intent intent = new Intent(this, SearchMusicActivity.class);
		EditText editText = (EditText) findViewById(R.id.enter_code);
		String message = editText.getText().toString();
		intent.putExtra(EXTRA_MESSAGE, message);
		startActivity(intent);
	}
}