package com.example.amazon_hack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class HomescreenActivity extends Activity {
	public final static String EXTRA_MESSAGE = "com.example.amazon_hack.MESSAGE";
	private Button go_button;
	private EditText code_field;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homescreen);
		go_button = (Button)findViewById(R.id.ok_button);
		code_field   = (EditText)findViewById(R.id.enter_code);
		
		go_button.setOnClickListener(
		        new View.OnClickListener()
		        {
		            public void onClick(View view)
		            {
		            	sendCode(code_field.getText().toString());
		            }
		        });
	}

	public void sendCode(String code) {
		// DO smth
		Intent intent = new Intent(this, SearchMusicActivity.class);
		intent.putExtra("MUSIC_TOKEN", code);
		startActivity(intent);
	}
	
	public void scanQR(View view) {
		IntentIntegrator intIntegrator = new IntentIntegrator(
				HomescreenActivity.this);
		intIntegrator.initiateScan();
	}
	
	public void submitCode(View view) {
		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		IntentResult scanResult = IntentIntegrator.parseActivityResult(
				requestCode, resultCode, intent);
		String token = scanResult.getContents().substring(scanResult.getContents().lastIndexOf("/")+1);
		sendCode(token);
//		Intent sendMusic = new Intent(this, SearchMusicActivity.class);
//		sendMusic.putExtra("MUSIC_TOKEN", token);
//		startActivity(sendMusic);
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