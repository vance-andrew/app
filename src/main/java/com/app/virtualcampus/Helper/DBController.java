package com.app.virtualcampus.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.virtualcampus.Model.Building;
import com.app.virtualcampus.Model.Faculty;
import com.app.virtualcampus.Model.mClass;

import java.util.ArrayList;

public class DBController extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "virtualcampus.db";
    private static final int DATABASE_VERSION = 1;

    public static final String DB_TABLE_BUILDING = "table_building";
    public static final String DB_TABLE_CLASS = "table_class";
    public static final String DB_TABLE_FACULTY = "table_faculty";

    public static final String KEY_ID = "id";
    public static final String BUILDING_ID = "build_id";
    public static final String NAME = "name";
    public static final String LAT = "lat";
    public static final String LNG = "lng";
    public static final String PIC = "pic";

    private static final String CREATE_TABLE_BUILDING = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_BUILDING + "("+
            KEY_ID + " integer PRIMARY KEY autoincrement," +
            BUILDING_ID + " TEXT," +
            NAME + " TEXT," +
            LAT + " TEXT," +
            LNG + " TEXT," +
            PIC + " TEXT);";

    public static final String CLASS_ID = "class_id";
    public static final String TIME = "time";
    public static final String ROOM = "room";
    public static final String FLOOR = "floor";

    private static final String CREATE_TABLE_CLASS = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_CLASS + "("+
            KEY_ID + " integer PRIMARY KEY autoincrement," +
            CLASS_ID + " TEXT," +
            NAME + " TEXT," +
            TIME + " TEXT," +
            ROOM + " TEXT," +
            FLOOR + " TEXT," +
            BUILDING_ID + " TEXT," +
            PIC + " TEXT);";

    public static final String PROF_ID = "prof_id";
    public static final String DESIGNATION = "designation";
    public static final String DEPT = "dept";
    public static final String CLASS_TAUGHT = "class_taught";

    private static final String CREATE_TABLE_FACULTY = "CREATE TABLE IF NOT EXISTS " + DB_TABLE_FACULTY + "("+
            KEY_ID + " integer PRIMARY KEY autoincrement," +
            PROF_ID + " TEXT," +
            NAME + " TEXT," +
            DESIGNATION + " TEXT," +
            DEPT + " TEXT," +
            CLASS_TAUGHT + " TEXT," +
            ROOM + " TEXT," +
            FLOOR + " TEXT," +
            BUILDING_ID + " TEXT," +
            PIC + " TEXT);";



    public DBController(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(CREATE_TABLE_BUILDING);
        db.execSQL(CREATE_TABLE_CLASS);
        db.execSQL(CREATE_TABLE_FACULTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_BUILDING);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE_FACULTY);
        onCreate(db);
    }

    public void addBuilding(Building building)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(BUILDING_ID , building.getID());
        cv.put(NAME , building.getName());
        cv.put(LAT , building.getLat());
        cv.put(LNG , building.getLng());
        cv.put(PIC , building.getPic());

        long res = database.insert( DB_TABLE_BUILDING, null, cv );
        Log.v(AppConstants.TAG , "RES FOR addBuilding ENTRY: " + res);
        database.close();
    }

    public void addClass(mClass mClass)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(CLASS_ID , mClass.getId());
        cv.put(NAME , mClass.getName());
        cv.put(TIME , mClass.getTime());
        cv.put(ROOM , mClass.getRoom());
        cv.put(FLOOR , mClass.getFloor());
        cv.put(BUILDING_ID , mClass.getBuilding());
        cv.put(PIC , mClass.getPic());

        long res = database.insert( DB_TABLE_CLASS, null, cv );
        Log.v(AppConstants.TAG , "RES FOR addClass ENTRY: " + res);
        database.close();
    }

    public void addFaculty(Faculty faculty)
    {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues cv = new  ContentValues();
        cv.put(PROF_ID , faculty.getId());
        cv.put(NAME , faculty.getName());
        cv.put(DESIGNATION , faculty.getDesignation());
        cv.put(DEPT , faculty.getDept());
        cv.put(CLASS_TAUGHT , faculty.getClassTaught());
        cv.put(ROOM , faculty.getRoom());
        cv.put(FLOOR , faculty.getFloor());
        cv.put(BUILDING_ID , faculty.getBuilding());
        cv.put(PIC , faculty.getPic());

        long res = database.insert( DB_TABLE_FACULTY, null, cv );
        Log.v(AppConstants.TAG , "RES FOR addFaculty ENTRY: " + res);
        database.close();
    }

    public Cursor getAllData(String tableName)
    {
        String selectQuery = "SELECT * FROM " + tableName;

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);

        //   database.close();
        return cursor;
    }

    public ArrayList<String> getclasses(String buildingID)
    {
        ArrayList<String> classes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + DB_TABLE_CLASS + " WHERE " + BUILDING_ID + "=" + "'" + buildingID + "'";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(selectQuery, null);
        if(cursor.getCount() > 0)
        {
            if(cursor.moveToFirst())
            do{
                String NAME = cursor.getString(cursor.getColumnIndex(DBController.NAME));
                classes.add(NAME);

            }while (cursor.moveToNext());
        }
        cursor.close();
        database.close();

        return classes;
    }

    public Cursor search(String searchWord)
    {
        String selectQuery = "SELECT * FROM " + DB_TABLE_CLASS + " WHERE " + NAME + "=" + "'" + searchWord + "'";
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor_class = database.rawQuery(selectQuery, null);
        Log.w(AppConstants.TAG , "search cursor_class: " + cursor_class.getCount());

        if(cursor_class.getCount() > 0)
        {
            return cursor_class;
        }

        return cursor_class;
    }
}
