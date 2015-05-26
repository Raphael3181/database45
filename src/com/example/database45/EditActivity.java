package com.example.database45;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class EditActivity extends Activity{
	Intent intent;
	EditText name, build, fate, disp, length, width, draft, crew, power, speed, dist, engine, art, antiAir, airGroup;
	Spinner country_id, class_id;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_four);
		intent = getIntent();
		initTextViews();
		//Если действие Редактирование загружаем информацию о корабле
		if(intent.getStringExtra("action").equals("edit")) loadInfo();
		loadSpinners();
	}
	/**
	 * Заносим в переменные ссылки на элементы разметки
	 */
	public void initTextViews() {
		name = (EditText)findViewById(R.id.textname);
		build = (EditText)findViewById(R.id.textbuild);
		fate = (EditText)findViewById(R.id.textfate);
		disp = (EditText)findViewById(R.id.textdisp);
		length = (EditText)findViewById(R.id.textlength);
		width = (EditText)findViewById(R.id.textwidth);
		draft = (EditText)findViewById(R.id.textdraft);
		crew= (EditText)findViewById(R.id.textcrew);
		power = (EditText)findViewById(R.id.textpower);
		speed = (EditText)findViewById(R.id.textspeed);
		dist = (EditText)findViewById(R.id.textdist);
		engine = (EditText)findViewById(R.id.textengine);
		art = (EditText)findViewById(R.id.textart);
		antiAir = (EditText)findViewById(R.id.textantaircr);
		airGroup = (EditText)findViewById(R.id.textairgr);
		country_id = (Spinner)findViewById(R.id.country_select);
		class_id = (Spinner)findViewById(R.id.class_select);
	}
	/**
	 * Загрузка информации из базы данных
	 */
	public void loadInfo() {
		SQLiteDatabase db = new DBHelper(this, "ships.sqlite").getWritableDatabase();
		Intent intent = getIntent();
    	String selection = "_id = '" + intent.getStringExtra("id") + "'";
		Cursor c = db.query("s_ships", null, selection, null, null, null, null);
		if(c.moveToFirst()){
			name.setText(c.getString(c.getColumnIndex("name")));
			build.setText(c.getString(c.getColumnIndex("building")));
			fate.setText(c.getString(c.getColumnIndex("fate")));
			disp.setText("" + c.getInt(c.getColumnIndex("displacement")));
			length.setText("" + c.getDouble(c.getColumnIndex("length")));
			width.setText("" + c.getDouble(c.getColumnIndex("width")));
			draft.setText("" + c.getDouble(c.getColumnIndex("draft")));
			crew.setText("" + c.getInt(c.getColumnIndex("crew")));
			power.setText("" + c.getString(c.getColumnIndex("power")));
			speed.setText("" + c.getDouble(c.getColumnIndex("speed")));
			dist.setText("" + c.getInt(c.getColumnIndex("distance")));
			engine.setText(c.getString(c.getColumnIndex("engine")));
			art.setText(c.getString(c.getColumnIndex("artillery")));
			antiAir.setText(c.getString(c.getColumnIndex("antiaircraft")));
			airGroup.setText(c.getString(c.getColumnIndex("air_group")));
		}
		c.close();
		db.close();	
	}
	/**
	 * Обработка нажатия кнопки сохранить
	 */
	public void addOrEditShip(View v) {
		SQLiteDatabase db = new DBHelper(this, "ships.sqlite").getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("name", name.getText().toString());
		cv.put("building", build.getText().toString());
		cv.put("fate", fate.getText().toString());
		cv.put("displacement", disp.getText().toString());
		cv.put("length", length.getText().toString());
		cv.put("width", width.getText().toString());
		cv.put("draft", draft.getText().toString());
		cv.put("engine", engine.getText().toString());
		cv.put("power", power.getText().toString());
		cv.put("speed", speed.getText().toString());
		cv.put("distance", dist.getText().toString());
		cv.put("crew", crew.getText().toString());
		cv.put("artillery", art.getText().toString());
		cv.put("antiaircraft", antiAir.getText().toString());
		cv.put("air_group", airGroup.getText().toString());
		cv.put("class_id", class_id.getSelectedItemPosition() + 1);
		cv.put("country_id", country_id.getSelectedItemPosition() + 1);
		if(intent.getStringExtra("action").equals("add")) db.insert("s_ships", null, cv);
		else db.update("s_ships", cv, "_id = ?", new String[] {intent.getStringExtra("id")}); 
	    super.onBackPressed();
	}
	
	public void loadSpinners () {
		SQLiteDatabase db = new DBHelper(this, "ships.sqlite").getWritableDatabase();
		Cursor c = db.query("s_class", null, null, null, null, null, null);
		String data[] = new String[c.getCount()];
		if(c.moveToFirst()){
			for(int i = 0; i < c.getCount(); i++) {
				data[i] = c.getString(c.getColumnIndex("class_name"));
				c.moveToNext();
			}
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	    class_id.setAdapter(adapter);
	    
	    c = db.query("s_country", null, null, null, null, null, null);
		data = new String[c.getCount()];
		if(c.moveToFirst()){
			for(int i = 0; i < c.getCount(); i++) {
				data[i] = c.getString(c.getColumnIndex("country_name"));
				c.moveToNext();
			}
		}
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	    country_id.setAdapter(adapter);
		c.close();
		db.close();
	}
}
