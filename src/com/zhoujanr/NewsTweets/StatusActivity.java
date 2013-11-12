package com.zhoujanr.NewsTweets;


import winterwell.jtwitter.Twitter;
import winterwell.jtwitter.TwitterException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class StatusActivity extends Activity implements OnClickListener, TextWatcher{
	
	private static final String TAG = "StatusActivity";
	EditText editText;
	Button updateButton;
	TextView textCount;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_status);
		
		
		// Find views
	    editText = (EditText) findViewById(R.id.editText); // 
	    
	    updateButton = (Button) findViewById(R.id.buttonUpdate);
	    updateButton.setOnClickListener(this); // 
	    
	    textCount = (TextView)findViewById(R.id.textCount);
	    textCount.setText(Integer.toString(140));
	    textCount.setTextColor(Color.GREEN);
	    editText.addTextChangedListener(this);

	}

	
	//Asynchronously post to twitter
	class PostToTwitter extends AsyncTask<String, Integer, String> {
		
		//Called to initiate the background activity
		@Override
		protected String doInBackground(String... statuses) {
			try{
				YamaApplication yama = (YamaApplication)getApplication();
				Twitter.Status status = yama.getTwitter().updateStatus(statuses[0]);
				return status.text;
			}catch (TwitterException e){
				Log.e(TAG, e.toString());
				e.printStackTrace();
				return "Failed to Post";
			}
		}
		 
		// Called when there's a status to be updated
	    @Override
	    protected void onProgressUpdate(Integer... values) { // 
	    	
	    	super.onProgressUpdate(values);
	      // Not used in this case
	    }

	    // Called once the background activity has completed
	    @Override
	    protected void onPostExecute(String result) { // 

	    	Toast.makeText(StatusActivity.this, result, Toast.LENGTH_LONG).show();

	    }

	}
	
	//TextWather methods
	public void afterTextChanged(Editable statusText){
		int count = 140 - statusText.length();
		textCount.setText(Integer.toString(count));
		textCount.setTextColor(Color.GREEN);
		if (count<10)
			textCount.setTextColor(Color.YELLOW);
		if (count<0)
			textCount.setTextColor(Color.RED);
	}
	
	public void beforeTextChanged(CharSequence s, int start, int count, int after){
		
	}
	
	public void onTextChanged(CharSequence s, int start, int count, int after){
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		String status = editText.getText().toString();
	    new PostToTwitter().execute(status);
	    editText.setText("");
	    Log.d(TAG, "onClicked");
	      
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
			case R.id.itemPrefs:
				startActivity(new Intent(this, PrefsActivity.class));
				break;
			case R.id.itemServiceStart:
				startService(new Intent(this, UpdateService.class));
				break;
			case R.id.itemServiceStop:
				stopService(new Intent(this, UpdateService.class));
				break;
		}
		
		return true;
	}

}
