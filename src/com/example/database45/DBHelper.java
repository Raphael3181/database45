package com.example.database45;

import java.io.*;

import android.content.Context;
import android.database.sqlite.*;

public class DBHelper extends SQLiteOpenHelper {

	private String dbname;

	public void onCreate(SQLiteDatabase db) {
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	public DBHelper(Context context, String dbName) {
		super(context, dbName, null, 1);
		dbname = dbName;
		if (!context.getDatabasePath(dbname).exists())
			copyDBfromAssets(context);
	}

	private void copyDBfromAssets(Context context) {
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new BufferedInputStream(context.getAssets().open(dbname), 8192);
			File path = context.getDatabasePath(dbname);
			if (!path.exists())
				path.getParentFile().mkdir();
			out = new BufferedOutputStream(new FileOutputStream(
					context.getDatabasePath(dbname)), 8192);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = in.read(buffer)) > 0)
				out.write(buffer, 0, length);
			out.flush();
		} catch (IOException e) {
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
