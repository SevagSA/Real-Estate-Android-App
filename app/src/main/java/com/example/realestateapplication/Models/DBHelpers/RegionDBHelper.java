package com.example.realestateapplication.Models.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class RegionDBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final String DATABASE_NAME = "RedwoodRealty.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Region";
    public static final String COL_ID = "ID";
    public static final String COL_IMG_URL = "imgURL";
    public static final String COL_LOCATION_TITLE = "locationTitle";

    public RegionDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Region(" + COL_ID + " integer primary key autoincrement," +
                COL_IMG_URL + " text, " + COL_LOCATION_TITLE + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public Long addData(String imgUrl, String locationTitle) {
        ContentValues values = new ContentValues();
        values.put(COL_IMG_URL, imgUrl);
        values.put(COL_LOCATION_TITLE, locationTitle);
        return db.insert(TABLE_NAME, null, values);
    }

    public Cursor runQuery(String query) {
        return db.rawQuery(query, null);
    }

    public Cursor runQuery(String query, String[] selectionArgs) {
        return db.rawQuery(query, selectionArgs);
    }

    public int updateData(String id, String imgUrl, String locationTitle) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_IMG_URL, imgUrl);
        values.put(COL_LOCATION_TITLE, locationTitle);
        return db.update(TABLE_NAME, values, "ID = ?", new String[]{id});
    }

    public Integer deleteValue(String id) {
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
