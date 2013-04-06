package com.example.amazon_hack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.AsyncTask;
import android.util.Log;

public class SendLinkTask extends AsyncTask<String, Void, String> {

	protected void onPostExecute(String url) {
		//

	}

	protected String doInBackground(String... urls) {
		try {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(urls[0]);
		List<NameValuePair> nvp = new ArrayList<NameValuePair>(
				1);
		nvp.add(new BasicNameValuePair("content", urls[1]));
		httppost.setEntity(new UrlEncodedFormEntity(nvp));
		HttpResponse response = httpclient.execute(httppost);
		Log.i("ti jiweesh samo wednyj", urls[1]);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}
		
		return "";
	}
}