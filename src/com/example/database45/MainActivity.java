package com.example.database45;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;


public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
	}
	/**
	 * Оработка нажатия на кнопки нации
	 */
	public void nationOnClick(View v) {
		Intent intent = new Intent(this, ListActivity.class);  
		switch (v.getId()){ //Добавляем в intent информацию о выбранноый стране
			case R.id.butt_nation1:intent.putExtra("Country", 1);break;
			case R.id.butt_nation2:intent.putExtra("Country", 2);break;
			case R.id.butt_nation3:intent.putExtra("Country", 3);break;
			case R.id.butt_nation4:intent.putExtra("Country", 4);break;	
		}//добавляем информацию о выбранных классах
		if (((CheckBox)findViewById(R.id.activity_first_cb1)).isChecked())intent.putExtra("Линкор", true);
		if (((CheckBox)findViewById(R.id.activity_first_cb2)).isChecked())intent.putExtra("Крейсер", true);
		if (((CheckBox)findViewById(R.id.activity_first_cb3)).isChecked())intent.putExtra("Эсминец", true);
		if (((CheckBox)findViewById(R.id.activity_first_cb4)).isChecked())intent.putExtra("Авианосец", true);
		startActivity(intent);
	}
	/**
	 * Нажатие кнопки добавить корабль
	 */
	public void addShip(View v) {
		Intent intent = new Intent(this, EditActivity.class);
		intent.putExtra("action", "add"); //Выбранно действие добавления
		startActivity(intent);
	}
}
