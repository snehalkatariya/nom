package com.nom;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.ParseException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		ActionBar actionBar = getActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.rgb(255, 102, 0)));
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		new NetworkConnection().execute("");	
		
		  Intent intent = getIntent();
		  
		  Log.v("Intent", "ff "+ intent.getAction());
		  
		  
		    if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
		      String query = intent.getStringExtra(SearchManager.QUERY);
		      Log.v("Search Query", query);
		      doMySearch(query);
		    }
		  
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
	  SearchManager searchManager =
	           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView =
	            (SearchView) menu.findItem(R.id.search).getActionView();
	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getComponentName()));

	
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items	
	    switch (item.getItemId()) {
	        case R.id.search:
	            openSearch(); 
	            return true;
	        case R.id.preference:
	            Log.d("Pref", "Pref");
	            return true;
	        case R.id.add:
	            Log.d("Add", "Add");
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	public void openSearch() {
	
	}
	
	public void doMySearch(String query) {
		
	}
	
	public class NetworkConnection extends AsyncTask<String, Integer, String> {

		protected String doInBackground(String... urls) {

			String url = "http://desireemelcer.com/nom/index.php";
			JSONArray jArray = null;
			String result = null;
			StringBuilder sb = null;
			InputStream is = null;

			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			try {
				HttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new HttpPost(url);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();

			} catch (Exception e) {
				Log.e("log_tag", "Error in http connection" + e.toString());
			}

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "iso-8859-1"), 8);
				sb = new StringBuilder();
				sb.append(reader.readLine() + "\n");

				String line = "0";
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				is.close();
				result = sb.toString();

				Log.v("String", result);
			} catch (Exception e) {
				Log.e("log_tag", "Error converting result " + e.toString());
			}
			
			
			String name = "";
			
			try{
			      jArray = new JSONArray(result);
			      JSONObject json_data=null;
			      for(int i=0;i<jArray.length();i++){
			             json_data = jArray.getJSONObject(i);
			             name = json_data.getString("NAME");//here "Name" is the column name in database
			         }
			      }
			      catch(JSONException e1){
			       //Toast.makeText(getBaseContext(), "No Data Found" ,Toast.LENGTH_LONG).show();
			      } catch (ParseException e1) {
			   e1.printStackTrace();
			 }

			return result;
		}

		protected void onProgressUpdate(Integer... progress) {
		}

		protected void onPostExecute(String result) {
			
			TextView tv1 = (TextView) findViewById(R.id.main_text);
			tv1.setText(result);
		}

	}

}
