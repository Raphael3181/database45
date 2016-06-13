package com.example.database45;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;


public class MainActivity extends Activity {
	CheckBox cb1; 
	CheckBox cb2;
	CheckBox cb3;
	CheckBox cb4;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_first);
		cb1=((CheckBox)findViewById(R.id.activity_first_cb1));
		cb2=((CheckBox)findViewById(R.id.activity_first_cb2));
		cb3=((CheckBox)findViewById(R.id.activity_first_cb3));
		cb4=((CheckBox)findViewById(R.id.activity_first_cb4));
	}
	
	public void onCheckClick (View v){
		if (cb1.isChecked()||cb2.isChecked()||cb3.isChecked()||cb4.isChecked()){
			findViewById(R.id.butt_nation1).setEnabled(true);
			findViewById(R.id.butt_nation2).setEnabled(true);
			findViewById(R.id.butt_nation3).setEnabled(true);
			findViewById(R.id.butt_nation4).setEnabled(true);
		}
		else {
			findViewById(R.id.butt_nation1).setEnabled(false);
			findViewById(R.id.butt_nation2).setEnabled(false);
			findViewById(R.id.butt_nation3).setEnabled(false);
			findViewById(R.id.butt_nation4).setEnabled(false);
		}
	}
	
	/**
	 * Обработка нажатия на кнопки нации
	 */
	public void nationOnClick(View v) {
		Intent intent = new Intent(this, ListActivity.class);  
		switch (v.getId()){ //Добавляем в intent информацию о выбранноый стране
			case R.id.butt_nation1:intent.putExtra("Country", 1);break;
			case R.id.butt_nation2:intent.putExtra("Country", 2);break;
			case R.id.butt_nation3:intent.putExtra("Country", 3);break;
			case R.id.butt_nation4:intent.putExtra("Country", 4);break;	
		}//добавляем информацию о выбранных классах
		if (cb1.isChecked())intent.putExtra("Линкор", true);
		if (cb2.isChecked())intent.putExtra("Крейсер", true);
		if (cb3.isChecked())intent.putExtra("Эсминец", true);
		if (cb4.isChecked())intent.putExtra("Авианосец", true);
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
