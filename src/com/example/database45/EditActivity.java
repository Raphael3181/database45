package com.example.database45;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class EditActivity extends Activity {
	
	Intent intent;
	EditText name, build, fate, disp, length, width, draft, crew, power, speed, dist, engine, art, antiAir, airGroup;
	Spinner country_id, class_id;
	Ship ship;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_four);
		intent = getIntent();
		ship = intent.getParcelableExtra("ship");
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
		name.setText(ship.name);
		build.setText(ship.build);
		fate.setText(ship.summary);
		disp.setText(String.valueOf(ship.displacement));
		length.setText(String.valueOf(ship.length));
		width.setText(String.valueOf(ship.width));
		draft.setText(String.valueOf(ship.draft));
		crew.setText(String.valueOf(ship.crew));
		power.setText(String.valueOf(ship.power));
		speed.setText(String.valueOf(ship.speed));
		dist.setText(String.valueOf(ship.distance));
		engine.setText(ship.engine);
		art.setText(ship.arming);
		antiAir.setText(ship.antiAir);
		airGroup.setText(ship.airGroup);		
	}
	
	private int getCountry(String id) {
		if (id.equals("СССР")) return 1;
		if (id.equals("Япония")) return 2;
		if (id.equals("США")) return 3;
		if (id.equals("Германия")) return 4;
		return 0;	
	}
	
	private int getClass(String id) {
		if (id.equals("Крейсер")) return 1;
		if (id.equals("Эсминец")) return 2;
		if (id.equals("Авианосец")) return 3;
		if (id.equals("Линкор")) return 4;
		return 0;
	}
	
	/**
	 * Обработка нажатия кнопки сохранить
	 */
	public void addOrEditShip(View v) {
		ship.name = name.getText().toString();
		ship.build = build.getText().toString();
		ship.summary = fate.getText().toString();
		ship.displacement = Integer.parseInt(disp.getText().toString());
		ship.length = Double.parseDouble(length.getText().toString());
		ship.width = Double.parseDouble(width.getText().toString());
		ship.draft = Double.parseDouble(draft.getText().toString());
		ship.engine = engine.getText().toString();
		ship.power = Integer.parseInt(power.getText().toString());
		ship.speed = Integer.parseInt(speed.getText().toString());
		ship.distance = Integer.parseInt(dist.getText().toString());
		ship.crew = Integer.parseInt(crew.getText().toString());
		ship.artillery = art.getText().toString();
		ship.antiAir = antiAir.getText().toString();
		ship.airGroup = airGroup.getText().toString();
		ship.category = class_id.getSelectedItem().toString();
		ship.country = country_id.getSelectedItem().toString();		
		new AsyncTask<Void, Void, Boolean>() {
			protected Boolean doInBackground(Void... params) {
				try {
					return ServerAPI.sendShip(ship.getJSON());
				} catch (MalformedURLException e) {
					e.printStackTrace();
					return false;
				} catch (JSONException e) {
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			protected void onPostExecute(Boolean result) {
				if(result) finish(); 
				else Toast.makeText(EditActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();	
			}
		}.execute();
	}
	
	public void loadSpinners () {
		String data[] = new String[] { "Крейсер", "Эсминец", "Авианосец", "Линкор" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	    class_id.setAdapter(adapter);
	    data = new String[] { "СССР", "Япония", "США", "Германия" };
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
	    country_id.setAdapter(adapter);		
		country_id.setSelection(getCountry(ship.country)-1);
		class_id.setSelection(getClass(ship.category)-1);
	}
}
