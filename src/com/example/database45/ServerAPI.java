package com.example.database45;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ServerAPI {
	/**
	 * Получить корабли с сервера по запросу
	 */
	public static ArrayList<Ship> getShips(int country, boolean[] classes) throws JSONException, MalformedURLException, IOException {
		ArrayList<Ship> result = new ArrayList<Ship>();
		JSONObject json = new JSONObject();
		json.put("country", country);
		json.put("aircraft", classes[0]);
		json.put("battleship", classes[1]);
		json.put("cruiser", classes[2]);
		json.put("destroyer", classes[3]);
		json = getShips(json);
		JSONArray ships = json.getJSONArray("ships");
		for(int i=0; i < ships.length(); i++) result.add(new Ship(ships.getJSONObject(i)));
		return result;
	}
	/**
	 * Запрос к серверу и получение ответа
	 */
	private static JSONObject getShips(JSONObject json) throws JSONException, MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL("https://warships-db.herokuapp.com/api/ships").openConnection();
		connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
		connection.setRequestProperty("Accept", "application/json;charset=UTF-8");
		connection.setRequestMethod("POST");
		OutputStream os = connection.getOutputStream();
		os.write(json.toString().getBytes("UTF-8"));
		os.close();
		InputStream is;
		if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) is = connection.getInputStream();
		else is = connection.getErrorStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		String jsonstr = "", s;
		while((s = reader.readLine()) != null) jsonstr += s + "\n";
		reader.close();
		connection.disconnect();
		return new JSONObject(jsonstr);
	}
}
