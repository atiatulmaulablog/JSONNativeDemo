package id.atiatulmaula.jsonnativedemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

/**
 * 
 * @author atiatulmaula.wordpress.com
 *
 */
public class MainActivity extends Activity{

	private TextView jsontext;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_layout);
		jsontext = (TextView) findViewById(R.id.jsonText);
		new GetDataJSON().execute();
	}
	
	private class GetDataJSON extends AsyncTask<Void, Integer, String> {

		@Override
		protected String doInBackground(Void... param) {
			StringBuilder stringBuilder = new StringBuilder();
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(
					"http://headers.jsontest.com/");
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				StatusLine statusLine = httpResponse.getStatusLine();
				int statusCode = statusLine.getStatusCode();
				
				if (statusCode == 200) {
					HttpEntity httpEntity = httpResponse.getEntity();
					InputStream inputStream = httpEntity.getContent();
					BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
					String line;
					while ((line = bufferedReader.readLine()) != null) {
						stringBuilder.append(line);
					}
				}else{
					Log.e(MainActivity.class.toString(), "Failed to download file");
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e){
				e.printStackTrace();
			}
			return stringBuilder.toString();
		}
	     
		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			try {
				JSONObject jsonObject = new JSONObject(result);
				jsontext.setText(jsonObject.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	 }
}
