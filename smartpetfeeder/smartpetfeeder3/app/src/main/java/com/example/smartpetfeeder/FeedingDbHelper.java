package com.example.smartpetfeeder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
public class FeedingDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SmartPetFeeder.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FEEDING = "feeding_history";

    // Table columns
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_AMOUNT = "amount_grams";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_FEEDING + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TIMESTAMP + " INTEGER NOT NULL, " +
                    COLUMN_AMOUNT + " REAL NOT NULL);";

    public FeedingDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEEDING);
        onCreate(db);
    }

    // Insert new feeding record
    @SuppressWarnings("UnusedReturnValue") //added this annotation
    public long addFeeding(long timestamp, float amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TIMESTAMP, timestamp);
        values.put(COLUMN_AMOUNT, amount);
        long id = db.insert(TABLE_FEEDING, null, values);
        db.close();
        return id;
    }

    // Get feeding data for time range
    public Cursor getFeedingData(long startTime, long endTime) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_FEEDING +
                " WHERE " + COLUMN_TIMESTAMP + " BETWEEN ? AND ?" +
                " ORDER BY " + COLUMN_TIMESTAMP + " ASC";
        return db.rawQuery(query, new String[]{String.valueOf(startTime), String.valueOf(endTime)});
    }
}
