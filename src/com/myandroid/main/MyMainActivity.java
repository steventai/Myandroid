package com.myandroid.main;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.myandroid.main.R;
import com.databasehelper.java.ListDatabaseHelper;
import com.jsonparsing.library.JSONParser;
import com.databasehelper.java.ListDatabaseHelper;

import android.database.Cursor;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.app.AlertDialog;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class MyMainActivity extends Activity {

	//URL to get JSON Array
	private static String url = "http://www.twnwcledu.com/?json=get_recent_posts";
	
	//JSON Node Names 
	private static final String TAG_POST = "posts";
	private static final String TAG_TITLE = "title";
	public JSONArray  posts = null;		
	
	
	private MyAdapter titltlistAdapter;
	public ListDatabaseHelper databaseHelper = null;
	AlertDialog ad;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		String title ="";	
		
        if(checkNetworkConnected())
        {
        
			JSONObject post ;
			
			JSONParser jParser = new JSONParser();
			databaseHelper = new ListDatabaseHelper(this);		
			// Getting JSON from URL
			JSONObject json = jParser.getJSONFromUrl(url);
			
			
			try {
				  //Getting JSON Array
				if(json != null)
				{
				  posts = json.getJSONArray(TAG_POST);
				  
	              for(int i=0;i<posts.length();i++)
	              {	  
					post = posts.getJSONObject(i);			
					title = post.getString(TAG_TITLE);	
				    databaseHelper.saveRecord(title, "");
	              }
				}
		
		    } catch (JSONException e1) {

			       e1.printStackTrace();
		    }
			
			
        }else{
        	
			Cursor cursor;        		

    	    cursor = databaseHelper.getAllTimeRecords();
	    	cursor.moveToFirst();
	    	while (cursor.isAfterLast() == false) {
	    	 
	       	    int totalColumn = cursor.getColumnCount();
	       	    JSONObject rowObject = new JSONObject();
	       	    
		   	    String aString = Integer.toString(totalColumn);
				ad.setMessage(aString);  
				ad.show(); 	 	    
	       	    
	       	    for( int i=0 ;  i< totalColumn ; i++ )
	       	    {

	       	    	
	       	    	if( cursor.getColumnName(i) != null ) 
	       	    	{
	       	    		try 
	       	    		{
		       	    		if( cursor.getString(i) != null )
		           	    	{
		           	    		rowObject.put(cursor.getColumnName(i) ,  cursor.getString(i) );
		           	    	}
		           	    	else
		           	    	{
		           	    		rowObject.put( cursor.getColumnName(i) ,  "" ); 
		           	    	}
	       	    		}
	       	    		catch( JSONException e2 )
	       	    		{
	       	    			//Log.d("TAG_NAME", e.getMessage()  );
	       	    		}
	       	    	}
	 
	       	    }
	 
	       	    posts.put(rowObject);
	       	    cursor.moveToNext();
	       }					
        }
        
        
        
        ListView listView = (ListView) findViewById(R.id.times_list);
        titltlistAdapter = new MyAdapter(posts);
        listView.setAdapter(titltlistAdapter);

		
        listView.setOnItemClickListener(new OnItemClickListener() {

        	@Override
        	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        		int i;
        		i=0;

        	}

        	});     
        
      }

    
    private boolean checkNetworkConnected() {
    	//boolean result = false;
        // get Connectivity Manager object to check connection
        ConnectivityManager connec =  (ConnectivityManager)getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
         
           // Check for network connections
            if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||
                 connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                
                // if connected with internet                 
                return true;
                 
            } else if ( 
              connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
              connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
               
                return false;
            }
          return false;
        }
    	   
}