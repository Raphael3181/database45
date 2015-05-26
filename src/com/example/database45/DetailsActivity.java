package com.example.database45;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DetailsActivity extends Activity{
	Intent intent;
	DetailsActivity act;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		act = this;
		setContentView(R.layout.activity_three);
		loadInfo();
	}
	@Override
	protected void onResume() {
		super.onResume();
		loadInfo();
	}
	/**
	 * Загрузка подробной информации о выбранном корабле
	 */
	public void loadInfo() {
    	SQLiteDatabase db = new DBHelper(this, "ships.sqlite").getWritableDatabase();
    	intent = getIntent();
    	String selection = "_id = '" + intent.getStringExtra("id") + "'";
		Cursor c = db.query("s_ships", null, selection, null, null, null, null);
		if(c.moveToFirst()){
			Cursor d = db.query("s_class", null, "_id = '" + c.getInt(c.getColumnIndex("class_id")) + "'", null, null, null, null);
			d.moveToFirst();
			((TextView)findViewById(R.id.textname)).setText(d.getString(d.getColumnIndex("class_name"))+ " "+ c.getString(c.getColumnIndex("name")));
			d.close();
			((TextView)findViewById(R.id.textbuild)).setText(c.getString(c.getColumnIndex("building")));
			((TextView)findViewById(R.id.textfate)).setText(c.getString(c.getColumnIndex("fate")));
			((TextView)findViewById(R.id.textdisp)).setText(getString(R.string.act3_vodoizm) + "\n" +c.getInt(c.getColumnIndex("displacement")));
			((TextView)findViewById(R.id.textlength)).setText(getString(R.string.act3_length) + "\n" +c.getDouble(c.getColumnIndex("length")));
			((TextView)findViewById(R.id.textwidth)).setText(getString(R.string.act3_width) + "\n" +c.getDouble(c.getColumnIndex("width")));
			((TextView)findViewById(R.id.textdraft)).setText(getString(R.string.act3_draft) + "\n" +c.getDouble(c.getColumnIndex("draft")));
			((TextView)findViewById(R.id.textcrew)).setText(getString(R.string.act3_crew) + "\n" +c.getInt(c.getColumnIndex("crew")));
			((TextView)findViewById(R.id.textpower)).setText(getString(R.string.act3_power) + "\n" +c.getString(c.getColumnIndex("power")));
			((TextView)findViewById(R.id.textspeed)).setText(getString(R.string.act3_speed) + "\n" +c.getDouble(c.getColumnIndex("speed")));
			((TextView)findViewById(R.id.textdist)).setText(getString(R.string.act3_dal) + "\n" +c.getInt(c.getColumnIndex("distance")));
			((TextView)findViewById(R.id.textengine)).setText(getString(R.string.act3_engine) + "\n" +c.getString(c.getColumnIndex("engine")));
			((TextView)findViewById(R.id.textart)).setText(getString(R.string.act3_art) + "\n" +c.getString(c.getColumnIndex("artillery")));
			((TextView)findViewById(R.id.textantaircr)).setText(getString(R.string.act3_zen) + "\n" +c.getString(c.getColumnIndex("antiaircraft")));
			((TextView)findViewById(R.id.textairgr)).setText(getString(R.string.act3_avia) + "\n" +c.getString(c.getColumnIndex("air_group")));
		}
		c.close();
		db.close();	
	}
	/**
	 * Обработка нажатия на кнопку Редактировать
	 */
	public void editShip(View v) {
		Intent i = new Intent(this, EditActivity.class);
		i.putExtra("action", "edit");
		i.putExtra("id", intent.getStringExtra("id"));
		startActivity(i);
	}
	/**
	 * Обработка нажатия на кнопку Удалить
	 */
	public void delShip(View v) {
		new AlertDialog.Builder(this).setTitle(R.string.del_ship)
		.setPositiveButton(R.string.dialog_yes, new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
			SQLiteDatabase db = new DBHelper(act, "ships.sqlite").getWritableDatabase();
			db.delete("s_ships", "_id = " + intent.getStringExtra("id"), null);
			finish();
		} })
		.setNegativeButton(R.string.dialog_no, null)
		.setCancelable(false).create().show();
	}
}
