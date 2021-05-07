package com.example.realestateapplication.Models.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PropertyDBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    public static final String DATABASE_NAME = "RedwoodRealty.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Property";
    public static final String COL_ID = "id";
    public static final String COL_MAIN_IMG = "main_img";
    public static final String COL_SECOND_IMG = "second_img";
    public static final String COL_THIRD_IMG = "third_img";
    public static final String COL_FOURTH_IMG = "fourth_img";
    public static final String COL_FIFTH_IMG = "fifth_img";
    public static final String COL_SIXTH_IMG = "sixth_img";
    public static final String COL_AGENT_ID = "agent_id";
    public static final String COL_PROPERTY_TYPE = "property_type";
    public static final String COL_PROPERTY_PRICE = "property_price";
    public static final String COL_PROPERTY_ADDRESS = "property_address";
    public static final String COL_NUM_OF_BED = "num_of_bed";
    public static final String COL_NUM_OF_BATH = "num_of_bath";
    public static final String COL_SQFT = "sqft";
    public static final String COL_DATE_ADDED = "date_added";


    public PropertyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Property(" + COL_ID + " integer primary key autoincrement," +
                COL_MAIN_IMG + " text, " +
                COL_SECOND_IMG + " text, " +
                COL_THIRD_IMG + " text, " +
                COL_FOURTH_IMG + " text, " +
                COL_FIFTH_IMG + " text, " +
                COL_SIXTH_IMG + " text, " +
                COL_AGENT_ID + " integer, " +
                COL_PROPERTY_TYPE + " text, " +
                COL_PROPERTY_PRICE + " text, " +
                COL_PROPERTY_ADDRESS + " text, " +
                COL_NUM_OF_BED + " integer, " +
                COL_NUM_OF_BATH + " integer, " +
                COL_SQFT + " integer, "+
                COL_DATE_ADDED + " date, " +
                "FOREIGN KEY(" + COL_AGENT_ID + ") REFERENCES Agent(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("Drop table if exists " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public Long addData(String mainImg, String secondImg, String thirdImg, String fourthImg,
                        String fifthImg, String sixthImg, int agentId, String propertyType,
                        String propertyPrice, String propertyAddress, int numOfBed, int numOfBath,
                        int sqft, String date_added) {
        ContentValues values = new ContentValues();
        values.put(COL_MAIN_IMG, mainImg);
        values.put(COL_SECOND_IMG, secondImg);
        values.put(COL_THIRD_IMG, thirdImg);
        values.put(COL_FOURTH_IMG, fourthImg);
        values.put(COL_FIFTH_IMG, fifthImg);
        values.put(COL_SIXTH_IMG, sixthImg);
        values.put(COL_AGENT_ID, agentId);
        values.put(COL_PROPERTY_TYPE, propertyType);
        values.put(COL_PROPERTY_PRICE, propertyPrice);
        values.put(COL_PROPERTY_ADDRESS, propertyAddress);
        values.put(COL_NUM_OF_BED, numOfBed);
        values.put(COL_NUM_OF_BATH, numOfBath);
        values.put(COL_SQFT, sqft);
        values.put(COL_DATE_ADDED, date_added);


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

    public long updateData(String id, String mainImg, String secondImg, String thirdImg, String fourthImg,
                           String fifthImg, String sixthImg, int agentId, String propertyType,
                           String propertyPrice, String propertyAddress, int numOfBed, int numOfBath,
                           String sqft, String date_added) {
        ContentValues values = new ContentValues();
        values.put(COL_MAIN_IMG, mainImg);
        values.put(COL_SECOND_IMG, secondImg);
        values.put(COL_THIRD_IMG, thirdImg);
        values.put(COL_FOURTH_IMG, fourthImg);
        values.put(COL_FIFTH_IMG, fifthImg);
        values.put(COL_SIXTH_IMG, sixthImg);
        values.put(COL_AGENT_ID, agentId);
        values.put(COL_PROPERTY_TYPE, propertyType);
        values.put(COL_PROPERTY_PRICE, propertyPrice);
        values.put(COL_PROPERTY_ADDRESS, propertyAddress);
        values.put(COL_NUM_OF_BED, numOfBed);
        values.put(COL_NUM_OF_BATH, numOfBath);
        values.put(COL_SQFT, sqft);
        values.put(COL_DATE_ADDED, date_added);
        return db.update(TABLE_NAME, values, "ID = ?", new String[]{id});
    }

    public Integer deleteValue(String id) {
        return db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}
