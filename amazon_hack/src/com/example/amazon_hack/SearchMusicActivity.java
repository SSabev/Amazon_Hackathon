package com.example.amazon_hack;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class SearchMusicActivity extends Activity {
	private static final int VOICE_RECOGNITION_REQUEST_CODE = 1001;
	private ImageButton mbtSpeak;
	private Button submitButton;
	private EditText contentText;
	private String token = "";
	private String url;

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_music, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			token = extras.getString("MUSIC_TOKEN");
		}
		url = "http://54.244.123.24:8000/listen/" + token;
		Log.i("TOKEN BABY", token);
		setContentView(R.layout.activity_search_music);
		// metTextHint = (EditText) findViewById(R.id.etTextHint);
		// mlvTextMatches = (ListView) findViewById(R.id.lvTextMatches);
		// msTextMatches = (Spinner) findViewById(R.id.sNoOfMatches);
		mbtSpeak = (ImageButton) findViewById(R.id.btSpeak);
		submitButton = (Button) findViewById(R.id.submit_button);
		contentText = (EditText) findViewById(R.id.enter_content);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

		checkVoiceRecognition();
		
		submitButton.setOnClickListener(
		        new View.OnClickListener()
		        {
		            public void onClick(View view)
		            {
		            	String searchData = contentText.getText().toString();
		            	new SendLinkTask().execute(url, searchData);
		            }
		        });
	}

	public void checkVoiceRecognition() {
		// Check if voice recognition is present
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(
				RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		if (activities.size() == 0) {
			mbtSpeak.setEnabled(false);
			// mbtSpeak.setText("Voice recognizer not present");
			Toast.makeText(this, "Voice recognizer not present",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void speak(View view) {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

		// Specify the calling package to identify your application
		intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass()
				.getPackage().getName());

		// Display an hint to the user about what he should say.
		// intent.putExtra(RecognizerIntent.EXTRA_PROMPT, metTextHint.getText()
		// .toString());

		// Given an hint to the recognizer about what the user is going to say
		// There are two form of language model available
		// 1.LANGUAGE_MODEL_WEB_SEARCH : For short phrases
		// 2.LANGUAGE_MODEL_FREE_FORM : If not sure about the words or phrases
		// and its domain.
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
				RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);

		// If number of Matches is not selected then return show toast message
		// if (msTextMatches.getSelectedItemPosition() ==
		// AdapterView.INVALID_POSITION) {
		// Toast.makeText(this, "Please select No. of Matches from spinner",
		// Toast.LENGTH_SHORT).show();
		// return;
		// }

		int noOfMatches = 1;
		// Specify how many results you want to receive. The results will be
		// sorted where the first result is the one with higher confidence.
		intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, noOfMatches);
		// Start the Voice recognizer activity for the result.
		startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE)

			// If Voice recognition is successful then it returns RESULT_OK
			if (resultCode == RESULT_OK) {

				ArrayList<String> textMatchList = data
						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

				if (!textMatchList.isEmpty()) {
					// If first Match contains the 'search' word
					// Then start web search.
					if (textMatchList.get(0).contains("search")) {

						String searchQuery = textMatchList.get(0);
						searchQuery = searchQuery.replace("search", "");
						Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
						search.putExtra(SearchManager.QUERY, searchQuery);
						startActivity(search);
					} else {
						// populate the Matches
						// HttpClient httpclient = new DefaultHttpClient();
						// HttpPost httppost = new
						// HttpPost("http://54.244.123.24:8000/listen/" +
						// token);
						
						// Add your data
						List<NameValuePair> nvp = new ArrayList<NameValuePair>(
								1);
						nvp.add(new BasicNameValuePair("content", textMatchList
								.get(0)));
						String search_data = textMatchList.get(0);
						// httppost.setEntity(new UrlEncodedFormEntity(nvp));

						// Execute HTTP Post Request

						new SendLinkTask().execute(url, search_data);

						// Intent search = new Intent(Intent.ACTION_WEB_SEARCH);
						// search.putExtra(SearchManager.QUERY,
						// textMatchList.get(0));
						// startActivity(search);
						// mlvTextMatches
						// .setAdapter(new ArrayAdapter<String>(this,
						// android.R.layout.simple_list_item_1,
						// textMatchList));
					}

				}
				// Result code for various error.
			} else if (resultCode == RecognizerIntent.RESULT_AUDIO_ERROR) {
				showToastMessage("Audio Error");
			} else if (resultCode == RecognizerIntent.RESULT_CLIENT_ERROR) {
				showToastMessage("Client Error");
			} else if (resultCode == RecognizerIntent.RESULT_NETWORK_ERROR) {
				showToastMessage("Network Error");
			} else if (resultCode == RecognizerIntent.RESULT_NO_MATCH) {
				showToastMessage("No Match");
			} else if (resultCode == RecognizerIntent.RESULT_SERVER_ERROR) {
				showToastMessage("Server Error");
			}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * Helper method to show the toast message
	 **/
	void showToastMessage(String message) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
	}

	public void submitContent(View view) {
		
	}
}
