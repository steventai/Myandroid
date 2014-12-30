package com.databasehelper.java;

import com.myandroid.main.MyAdapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ListDatabaseHelper {

	private static final int DATABASE_VERSION = 4;
	private static final String DATABASE_NAME = "MyBlogList.db";
	private static final String TABLE_NAME = "blogrecords";

	private static final String TIMETRACKER_COLUMN_ID = "_id";
	private static final String TIMETRACKER_COLUMN_TITLE = "title";
	private static final String TIMETRACKER_COLUMN_NOTES = "notes";

	private ListTrackerOpenHelper openHelper;
	private SQLiteDatabase database;


	// constructor
	//steven+2014 1230
	//steven+2014 1230...02
	
	public ListDatabaseHelper(Context myAdapter) {
		openHelper = new ListTrackerOpenHelper(myAdapter);
		database = openHelper.getWritableDatabase();
	}
	
	public void saveRecord(String title, String notes) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(TIMETRACKER_COLUMN_TITLE, title);
		contentValues.put(TIMETRACKER_COLUMN_NOTES, notes);
		database.insert(TABLE_NAME, null, contentValues);
	}
	
	public Cursor getAllTimeRecords() {
		return database.rawQuery(
				"SELECT * FROM " + TABLE_NAME, 
				null);
	}

	private class ListTrackerOpenHelper extends SQLiteOpenHelper {

		public ListTrackerOpenHelper(Context myAdapter) {
			super(myAdapter, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase database) {
			database.execSQL("CREATE TABLE " + TABLE_NAME + "("
					+ TIMETRACKER_COLUMN_TITLE + " TEXT, "
					+ TIMETRACKER_COLUMN_NOTES + " TEXT)");

		}

		@Override
		public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + "");
			onCreate(database);

		}

	}

}
