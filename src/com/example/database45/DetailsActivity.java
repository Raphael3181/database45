package com.example.database45;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends Activity {
	
	Ship ship;
	Intent intent;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_three);
		intent = getIntent();
		ship = intent.getParcelableExtra("ship");
	}
	
	protected void onResume() {
		super.onResume();
		loadInfo();
	}
	
	/**
	 * Загрузка информации о корабле из Intent
	 */
	public void loadInfo() {
		((TextView)findViewById(R.id.textname)).setText(ship.category + " " + ship.name);
		((TextView)findViewById(R.id.textbuild)).setText(ship.build);
		((TextView)findViewById(R.id.textfate)).setText(ship.summary);
		((TextView)findViewById(R.id.textdisp)).setText(getString(R.string.act3_vodoizm) + "\n" + ship.displacement);
		((TextView)findViewById(R.id.textlength)).setText(getString(R.string.act3_length) + "\n" + ship.length);
		((TextView)findViewById(R.id.textwidth)).setText(getString(R.string.act3_width) + "\n" + ship.width);
		((TextView)findViewById(R.id.textdraft)).setText(getString(R.string.act3_draft) + "\n" + ship.draft);
		((TextView)findViewById(R.id.textcrew)).setText(getString(R.string.act3_crew) + "\n" + ship.crew);
		((TextView)findViewById(R.id.textpower)).setText(getString(R.string.act3_power) + "\n" + ship.power);
		((TextView)findViewById(R.id.textspeed)).setText(getString(R.string.act3_speed) + "\n" + ship.speed);
		((TextView)findViewById(R.id.textart)).setText(getString(R.string.act3_art) + "\n" + ship.arming);
	}
	
	/**
	 *  Обработка нажатия кнопки редактировать
	 */
	public void editShip(View v) {
		Intent i = new Intent(this, EditActivity.class);
		i.putExtra("action", "edit");
		i.putExtra("ship", ship);
		startActivity(i);
	}
	
	/**
	 * Обработка нажатия кнопки удалить
	 */
	public void delShip(View v) {
		new AlertDialog.Builder(this).setTitle(R.string.del_ship)
		.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				new AsyncTask<Void, Void, Boolean>() {
					protected Boolean doInBackground(Void... params) {
						try {
							return ServerAPI.delShip(ship.id);
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
						else Toast.makeText(DetailsActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();	
					}
				}.execute();
			}
		}).setNegativeButton(R.string.dialog_no, null)
		.setCancelable(false).create().show();
	}
}
