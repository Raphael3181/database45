package com.example.database45;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ListActivity extends Activity {
	TextView capture; 
	ListActivity act;
	ArrayList<Ship> ships;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		act = this;
		setCapture();
	}
	
	protected void onStart() {
		super.onStart();
		nameFromBase();
	}
	
	/**
	 *Устанавливаем заголовок в соответсвии с выбранной страной
	 */
	public void setCapture() {
		capture = (TextView)findViewById(R.id.textnation);
		int country = getIntent().getIntExtra("Country" , 0);
		if (country == 1) capture.setText("СССР");
		if (country == 3) capture.setText("Япония");
		if (country == 2) capture.setText("США");
		if (country == 4) capture.setText("Германия");
	}
	
    /**
     *Загружаем информацию о типе и названии кораблей из базы данных
     */
    public void nameFromBase() {
    	final Intent intent = getIntent();
    	findViewById(R.id.progress).setVisibility(View.VISIBLE);
    	new AsyncTask<Void, Void, ArrayList<Ship>>() {
			protected ArrayList<Ship> doInBackground(Void... params) {
				boolean[] classes = new boolean[4];
		    	classes[0] = intent.getBooleanExtra("Авианосец", false);
		    	classes[1] = intent.getBooleanExtra("Линкор", false);
		    	classes[2] = intent.getBooleanExtra("Крейсер", false);
		    	classes[3] = intent.getBooleanExtra("Эсминец", false);
				try {
					if(intent.getIntExtra("Country", 0) == 0) return null;
					else return ServerAPI.getShips(intent.getIntExtra("Country", 0), classes);
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return null;
				} catch (JSONException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
			protected void onPostExecute(ArrayList<Ship> result) {
				ships = result;
				if(ships != null) {
					ArrayList<Map<String, Object>> dataA = new ArrayList<Map<String, Object>>();
				    Map<String, Object> m;
				    for(Ship ship: ships) {
				    	m = new HashMap<String, Object>();
						m.put("name", ship.name);
						m.put("class", ship.category);
						dataA.add(m);
				    }
				    String[] from = {"name","class","id"};
				    int[] to = { R.id.namebase , R.id.classbase };
				    SimpleAdapter sAdapter = new SimpleAdapter(act, dataA, R.layout.list_view_ships, from, to);
				    ListView lv = (ListView) findViewById(R.id.list_view_ships);
				    lv.setAdapter(sAdapter);
				    //Обработка нажатия на элемент списка
				    lv.setOnItemClickListener(new OnItemClickListener() {
						public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
							Intent intent;
						    intent = new Intent(act, DetailsActivity.class);
						    intent.putExtra("ship", ships.get(position));
						    startActivity(intent);
					    }
				   });
				}
				findViewById(R.id.progress).setVisibility(View.GONE);
			}
		}.execute();
    }
} 
