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
	public String engine; //Движитель
	public int power; // Мощность в л. с.
	public int speed; // Скорость в узлах
	public int distance;//Дальность хода
	public int crew; // Экипаж
	public String artillery; // Артиллерия
	public String arming; // Вооружение
	public String antiAir; //ПВО
	public String airGroup; //Авиагруппа
	
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
			engine = json.getString("engine");
			power = json.getInt("power");
			speed = json.getInt("speed");
			distance = json.getInt("distance");
			crew = json.getInt("crew");
			artillery = json.getString("artillery");
			arming = json.getString("arming");
			antiAir = json.getString("antiAir");
			airGroup = json.getString("airGroup"); 
		} catch (JSONException e) {}
	}
	
	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		try {
			category = json.getJSONObject("category").getString("name");
			country = json.getJSONObject("country").getString("name");
			json.put("name", name);
			json.put("summary", summary);
			json.put("build", build);
			json.put("displacement", displacement);
			json.put("length", length);
			json.put("width", width);
			json.put("draft", draft);
			json.put("engine", engine);
			json.put("power", power);
			json.put("speed", speed);
			json.put("distance", distance);
			json.put("crew", crew);
			json.put("artillery", artillery);
			json.put("arming", arming);
			json.put("antiAir", antiAir);
			json.put("airGroup", airGroup);
			return json;
		} catch (JSONException e) {return null;}
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
		engine = in.readString();
		power = in.readInt();
		speed = in.readInt();
		crew = in.readInt();
		arming = in.readString();
		artillery = in.readString();
		antiAir = in.readString();
		airGroup = in.readString();
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
		dest.writeString(engine);
		dest.writeInt(power);
		dest.writeInt(speed);
		dest.writeInt(crew);
		dest.writeString(arming);
		dest.writeString(artillery);
		dest.writeString(antiAir);
		dest.writeString(airGroup);
	}
	
	public static final Creator<Ship> CREATOR = new Creator<Ship>() {
		public Ship createFromParcel(Parcel source) { return new Ship(source); }
		public Ship[] newArray(int size) { return new Ship[size]; }
	};
}
