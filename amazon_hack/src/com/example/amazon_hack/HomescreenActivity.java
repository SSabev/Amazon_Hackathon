package com.example.amazon_hack;

import android.app.Activity;
import android.app.SearchManager;
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
	
	public void scanQR(View view) {
		IntentIntegrator intIntegrator = new IntentIntegrator(
				HomescreenActivity.this);
		intIntegrator.initiateScan();

	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		String token = scanResult.getContents().substring(scanResult.getContents().lastIndexOf("/")+1);
		Intent sendMusic = new Intent(this, SearchMusicActivity.class);
		sendMusic.putExtra("MUSIC_TOKEN", token);
		startActivity(sendMusic);
//
//		if (scanResult != null) {
//			Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
//			search.putExtra(scanResult.getContents(),
//					true);
//			startActivity(search);
//		}
		// else continue with any other code you need in the method
		// ...
	}
}