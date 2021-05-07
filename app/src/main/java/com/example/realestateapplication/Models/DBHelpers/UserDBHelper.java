package com.example.realestateapplication.Models.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UserDBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final String DATABASE_NAME = "RedwoodRealty.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "User";
    private static final String COL_ID = "ID";
    private static final String COL_EMAIL = "email";
    private static final String COL_FULL_NAME = "fullName";
//    TODO given that this is a school project, hashing the password
//     does not really matter. However, if you have time, make sure
//     to implemented encryption and decryption
    private static final String COL_PASSWORD = "password";


    public UserDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS User(" + COL_ID + "integer primary key autoincrement," +
                                COL_EMAIL + " text, " + COL_FULL_NAME + " text, " + COL_PASSWORD + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public Long addData(String email, String fullName, String password) {
        ContentValues values = new ContentValues();
        values.put(COL_EMAIL, email);
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_PASSWORD, password);
        long result = db.insert(TABLE_NAME, null, values);
        return result;
    }

    public Cursor runQuery(String query) {
        Cursor result = db.rawQuery(query, null);
        return result;
    }

    public Cursor runQuery(String query, String[] selectionArgs) {
        Cursor result = db.rawQuery(query, selectionArgs);
        return result;
    }

    public boolean updateData(String id, String email, String fullName, String password) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_EMAIL, email);
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_PASSWORD, password);
        db.update(TABLE_NAME, values, "ID = ?", new String[] {id});
        return false;
    }

    public Integer deleteValue(String id) {
        return db.delete(TABLE_NAME,"ID = ?", new String[] {id});
    }
}
