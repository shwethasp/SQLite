package com.shwethasp.mysqldb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by shwethap on 05-07-2016.
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "db_save";
    public static final String TABLE_NAME = "details";
    public static final String NAME_COLUMN_ID = "id";
    public static final String NAME_COLUMN_UNIQUEID = "uid";
    public static final String NAME_COLUMN_NAME = "name";
    public static final String NAME_COLUMN_ADDRESS = "address";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
//create table for setting name and address
        String CREATE_SAVE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + NAME_COLUMN_ID + " INTEGER PRIMARY KEY,"
                + NAME_COLUMN_NAME + " TEXT," + NAME_COLUMN_ADDRESS + " TEXT)";
        db.execSQL(CREATE_SAVE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS time");
        // Create tables again
        onCreate(db);
    }

    // code to add the new details
    public String insertDetails(String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN_NAME, name);
        contentValues.put(NAME_COLUMN_ADDRESS, address);
        // Inserting Row
        long i = db.insert(TABLE_NAME, null, contentValues);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
        return null;
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        return res;
    }


    //code to update the single detail
    public int updateDetails(String id, String name, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME_COLUMN_NAME, name);
        contentValues.put(NAME_COLUMN_ADDRESS, address);

        // updating row
        int i = db.update(TABLE_NAME, contentValues, NAME_COLUMN_ID + " = ?",
                new String[]{id});
        return i;

    }

    public ArrayList<DetailsModel> getAllDetails() {

        ArrayList<DetailsModel> listData = new ArrayList<DetailsModel>();


        // Select All Query
        String selectQuery = "SELECT " + NAME_COLUMN_ID + "," + NAME_COLUMN_NAME + "," + NAME_COLUMN_ADDRESS + " FROM " + TABLE_NAME;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                DetailsModel details = new DetailsModel();
                details.setId(c.getString(c.getColumnIndex(NAME_COLUMN_ID)));
                details.setName(c.getString(c.getColumnIndex(NAME_COLUMN_NAME)));
                details.setAddress(c.getString(c.getColumnIndex(NAME_COLUMN_ADDRESS)));

                listData.add(details);
            } while (c.moveToNext());
        }

        // closing connection
        c.close();
        db.close();

        return listData;
    }

    public void deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete(TABLE_NAME, NAME_COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + NAME_COLUMN_ID + " = '" + id + "'");
        Log.e("Delete data", String.valueOf((id)));
    }
}
