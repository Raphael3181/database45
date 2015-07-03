package com.example.database45;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);
		act = this;
		setCapture();
	}
	@Override
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
    	//SQLiteDatabase db = new DBHelper(this, "ships.sqlite").getWritableDatabase();
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
				        @Override
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
    	//Формируем запрос о получении подходящий по параметру запроса кораблей 
    	/*String selection = "select s_class.class_name as class , s_ships.name as sname, s_ships._id from s_ships, "
    			+ "s_class where s_ships.class_id = s_class._id and country_id = " + intent.getIntExtra("Country" , 0);
        if (intent.getBooleanExtra("Линкор", false)||intent.getBooleanExtra("Крейсер", false)||intent.getBooleanExtra("Эсминец", false)||
        intent.getBooleanExtra("Авианосец", false)) {
        	selection += " and (";
        	if (intent.getBooleanExtra("Линкор", false)) selection += "class_id = 1";
        	if (intent.getBooleanExtra("Крейсер", false)) selection +=(selection.endsWith("1")? " or ": "") + "class_id = 2";
        	if (intent.getBooleanExtra("Эсминец", false)) selection +=(selection.endsWith("1")|| selection.endsWith("2")? " or ": "") +  "class_id = 3";
        	if (intent.getBooleanExtra("Авианосец", false)) selection +=(selection.endsWith("1")|| selection.endsWith("2")||
        	selection.endsWith("3")? " or ": "") +  "class_id = 4";	
        	selection += ")";
    	}	
    	Cursor c = db.rawQuery(selection, null);*/
    	//Получаем данные из результатов запроса
		/*ArrayList<Map<String, Object>> dataA = new ArrayList<Map<String, Object>>();
	    Map<String, Object> m;
		if(c.moveToFirst()){
			for(int i = 0; i < c.getCount(); i++) {
				m = new HashMap<String, Object>();
				m.put("name", c.getString(c.getColumnIndex("sname")));
				m.put("class", c.getString(c.getColumnIndex("class")));
				m.put("id", c.getString(c.getColumnIndex("s_ships._id")));
				dataA.add(m);
				c.moveToNext();
			}
		}
		c.close();
		db.close();
	    String[] from = {"name","class","id"};
	    int[] to = { R.id.namebase , R.id.classbase, R.id.idbase };
	    SimpleAdapter sAdapter = new SimpleAdapter(this, dataA, R.layout.list_view_ships, from, to);
	    ListView lv = (ListView) findViewById(R.id.list_view_ships);
	    lv.setAdapter(sAdapter);
	    //Обработка нажатия на элемент списка
	    lv.setOnItemClickListener(new OnItemClickListener() {
	        @Override
			public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
				Intent intent;
			    intent = new Intent(act, DetailsActivity.class);
			    intent.putExtra("id", ((TextView)view.findViewById(R.id.idbase)).getText().toString());
			    startActivity(intent);
		    }
	   });*/
    }
} 
