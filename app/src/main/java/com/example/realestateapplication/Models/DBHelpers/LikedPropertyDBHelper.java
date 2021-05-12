package com.example.realestateapplication.Models.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LikedPropertyDBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final String DATABASE_NAME = "RedwoodRealty.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "LikedProperty";
    public static final String COL_ID = "ID";
    public static final String COL_USER_ID = "user_id";
    public static final String COL_PROPERTY_ID = "property_id";

    public LikedPropertyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS LikedProperty(" + COL_ID + " integer primary key autoincrement," +
                COL_USER_ID + " integer," +
                "" + COL_PROPERTY_ID + " integer, " +
                "FOREIGN KEY( " + COL_USER_ID + ") REFERENCES User(id), " +
                "FOREIGN KEY(" + COL_PROPERTY_ID + ") REFERENCES Property(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public Long addData(String userId, String propertyId) {
        ContentValues values = new ContentValues();
        values.put(COL_USER_ID, userId);
        values.put(COL_PROPERTY_ID, propertyId);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }

    public Cursor runQuery(String query, String[] selectionArgs) {
        Cursor result = db.rawQuery(query, selectionArgs);
        return result;
    }

    public Integer deleteValue(String id) {
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
