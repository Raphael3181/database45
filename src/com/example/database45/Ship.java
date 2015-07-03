package com.example.database45;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Parcel;
import android.os.Parcelable;

public class Ship implements Parcelable {
	
	public String category; // Класс судна
	public String country; // Страна службы
	public String name; // Название
	public String summary; // Описание
	public String build; // Введен в эксплуатацию
	public int displacement; // Стандартное водоизмещение в т
	public double length; // Длина в м
	public double width; // Ширина в м
	public double draft; // Осадка в м
	public int power; // Мощность в л. с.
	public int speed; // Скорость в узлах
	public int crew; // Экипаж
	public String arming; // Вооружение
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("d MMMM y г.", new Locale("ru", "RU"));
	
	/**
	 * Обработка данных из json
	 */
	public Ship(JSONObject json) {
		try {
			category = json.getJSONObject("category").getString("name");
			country = json.getJSONObject("country").getString("name");
			name = json.getString("name");
			summary = json.getString("summary");
			build = sdf.format(new Date(json.getLong("launch_date")));
			displacement = json.getInt("displacement");
			length = json.getDouble("length");
			width = json.getDouble("width");
			draft = json.getDouble("draft");
			power = json.getInt("power");
			speed = json.getInt("speed");
			crew = json.getInt("crew");
			arming = json.getString("arming");
		} catch (JSONException e) {}
	}
	
	/**
	 * Распаковка из Parcel
	 */
	public Ship(Parcel in) {
		category = in.readString();
		country = in.readString();
		name = in.readString();
		summary = in.readString();
		build = in.readString();
		displacement = in.readInt();
		length = in.readDouble();
		width = in.readDouble();
		draft = in.readDouble();
		power = in.readInt();
		speed = in.readInt();
		crew = in.readInt();
		arming = in.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	/**
	 * запаковка в Parcel
	 */
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(category);
		dest.writeString(country);
		dest.writeString(name);
		dest.writeString(summary);
		dest.writeString(build);
		dest.writeInt(displacement);
		dest.writeDouble(length);
		dest.writeDouble(width);
		dest.writeDouble(draft);
		dest.writeInt(power);
		dest.writeInt(speed);
		dest.writeInt(crew);
		dest.writeString(arming);
	}
	
	public static final Creator<Ship> CREATOR = new Creator<Ship>() {
		public Ship createFromParcel(Parcel source) { return new Ship(source); }
		public Ship[] newArray(int size) { return new Ship[size]; }
	};
}
