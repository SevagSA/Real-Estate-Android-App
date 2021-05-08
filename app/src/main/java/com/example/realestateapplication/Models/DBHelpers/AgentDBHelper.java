package com.example.realestateapplication.Models.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AgentDBHelper extends SQLiteOpenHelper {
    SQLiteDatabase db;
    public static final String DATABASE_NAME = "RedwoodRealty.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Agent";
    public static final String COL_ID = "ID";
    public static final String COL_FULL_NAME = "full_name";
    public static final String COL_COMPANY_NAME = "company_name";
    public static final String COL_EMAIL = "email";
    public static final String COL_PROFILE_IMG_ID = "profileImg_id";
    public static final String COL_NUM_OF_SOLD_LISTINGS = "sold_listings";
    public static final String COL_SERVICE_LOCATION = "service_location";
    public static final String COL_PHONE_NUMBER = "phone_number";


    public AgentDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Agent(" + COL_ID + " integer primary key autoincrement," +
                COL_FULL_NAME + " text, " +
                COL_COMPANY_NAME + " text, " +
                COL_EMAIL + " text, " +
                COL_PROFILE_IMG_ID + " integer, " +
                COL_NUM_OF_SOLD_LISTINGS + " integer, " +
                COL_SERVICE_LOCATION + " text, " +
                COL_PHONE_NUMBER + " text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }


    public Long addData(String fullName, String companyName, String email, String propertyId, int profileImgId,
                        int numOfSoldListings, String serviceLocation, String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_COMPANY_NAME, companyName);
        values.put(COL_EMAIL, email);
        values.put(COL_PROFILE_IMG_ID, propertyId);
        values.put(COL_NUM_OF_SOLD_LISTINGS, numOfSoldListings);
        values.put(COL_SERVICE_LOCATION, serviceLocation);
        values.put(COL_PHONE_NUMBER, phoneNumber);
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

    public int updateData(String id, String fullName, String companyName, String email, String propertyId, int profileImgId,
                              int numOfSoldListings, String serviceLocation, String phoneNumber) {
        ContentValues values = new ContentValues();
        values.put(COL_ID, id);
        values.put(COL_FULL_NAME, fullName);
        values.put(COL_COMPANY_NAME, companyName);
        values.put(COL_EMAIL, email);
        values.put(COL_PROFILE_IMG_ID, propertyId);
        values.put(COL_NUM_OF_SOLD_LISTINGS, numOfSoldListings);
        values.put(COL_SERVICE_LOCATION, serviceLocation);
        values.put(COL_PHONE_NUMBER, phoneNumber);
        return db.update(TABLE_NAME, values, "ID = ?", new String[]{id});
    }

    public Integer deleteValue(String id) {
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
