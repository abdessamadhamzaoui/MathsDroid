package com.example.mathsdroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;


public class DataBaseConnection  extends SQLiteOpenHelper {
    public static final String DbName="Mathsdroid.db";
    public static final int Version =1;
    public DataBaseConnection(@Nullable Context context) {
        super(context, DbName, null, Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table IF NOT EXISTS primeNumber (id INTEGER PRIMARY KEY AUTOINCREMENT,number INTEGER UNIQUE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS primeNumber");
        onCreate(db);

    }
    public boolean insertData (long number){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);

        long result = db.insertWithOnConflict("primeNumber", null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
        if ( result== -1)
            return false;
        return true;
    }
}

