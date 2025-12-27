package com.example.medireminder.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.medireminder.constants.AppConstants;

public class DBHelper extends SQLiteOpenHelper {
    
    // Users table
    private static final String CREATE_TABLE_USERS = 
            "CREATE TABLE " + AppConstants.TABLE_USERS + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "username TEXT NOT NULL UNIQUE, " +
            "email TEXT NOT NULL UNIQUE, " +
            "password TEXT NOT NULL" +
            ")";
    
    // Medicines table
    private static final String CREATE_TABLE_MEDICINES = 
            "CREATE TABLE " + AppConstants.TABLE_MEDICINES + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "name TEXT NOT NULL, " +
            "dosage TEXT NOT NULL, " +
            "frequency TEXT NOT NULL, " +
            "time TEXT NOT NULL, " +
            "startDate TEXT NOT NULL, " +
            "endDate TEXT, " +
            "isActive INTEGER DEFAULT 1, " +
            "userId INTEGER NOT NULL, " +
            "FOREIGN KEY(userId) REFERENCES " + AppConstants.TABLE_USERS + "(id)" +
            ")";
    
    // History table
    private static final String CREATE_TABLE_HISTORY = 
            "CREATE TABLE " + AppConstants.TABLE_HISTORY + " (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "medicineId INTEGER NOT NULL, " +
            "medicineName TEXT NOT NULL, " +
            "dosage TEXT NOT NULL, " +
            "takenTime TEXT NOT NULL, " +
            "takenDate TEXT NOT NULL, " +
            "FOREIGN KEY(medicineId) REFERENCES " + AppConstants.TABLE_MEDICINES + "(id)" +
            ")";

    public DBHelper(Context context) {
        super(context, AppConstants.DATABASE_NAME, null, AppConstants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_TABLE_MEDICINES);
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + AppConstants.TABLE_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + AppConstants.TABLE_MEDICINES);
        db.execSQL("DROP TABLE IF EXISTS " + AppConstants.TABLE_USERS);
        onCreate(db);
    }
}

