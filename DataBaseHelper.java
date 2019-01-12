package com.pockettrainer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;

import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "Historique.db";
    private static final String DB_TABLE = "Historique_Table";

    //Collones
    private static final String DATE = "DATE"; //0
    private static final String NOM = "NOM"; //1
    private static final String MUSCLE = "MUSCLE"; //2
    private static final String POIDS = "POIDS"; //3
    private static final String REP = "REP"; //4

    private static final String CREATE_TABLE = " CREATE TABLE " + DB_TABLE + " ( " +
            DATE + " TEXT, " +
            NOM + " TEXT, " +
            MUSCLE + " TEXT, " +
            POIDS + " TEXT, " +
            REP + " TEXT " +" )";


    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + DB_TABLE );

        onCreate(db);

    }


    public boolean insertData(String date, String nom, String muscle, int poids, int rep)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DATE, date);
        contentValues.put(NOM, nom);
        contentValues.put(MUSCLE, muscle);
        contentValues.put(POIDS, poids);
        contentValues.put(REP, rep);

        long result = db.insert(DB_TABLE,null,contentValues);

        return result != -1; //Si result = -1 ils vont pas être inséré
    }

    public Cursor viewData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = " SELECT * FROM " + DB_TABLE;
        Cursor cursor = db.rawQuery(query,null);

        return cursor;
    }

}

