package com.example.myroutines.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myroutines.app.Constant;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class RoutineHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "myRoutine";
    private static final String TABLE_ROUTINES = "routines";
    private static final String KEY_ID = "id";
    private static final String KEY_STATUS = "status";
    private static final String KEY_COND = "cond";
    private static final String KEY_TIME = "time";
    private static final String KEY_ACTION = "action";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TEXT = "text";
    private static final String KEY_WIFI_STATE = "state";


    public RoutineHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ROUTINE_TABLE = "CREATE TABLE " + TABLE_ROUTINES + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_STATUS + " INTEGER,"
                + KEY_COND + " INTEGER,"
                + KEY_TIME + " TEXT,"
                + KEY_ACTION + " INTEGER,"
                + KEY_TITLE + " TEXT,"
                + KEY_TEXT + " TEXT,"
                + KEY_WIFI_STATE + " INTEGER"
                + ")";
        db.execSQL(CREATE_ROUTINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROUTINES);
        onCreate(db);
    }

    public void addRoutine (Routine routine) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO " + TABLE_ROUTINES + " VALUES (" +
                routine.getId() + ", " +
                routine.getStatus() + ", " +
                routine.getCond().getId() + ", \"" +
                routine.getCond().getLdt().toString() + "\", " +
                routine.getAction().getId() + ", " +
                routine.getAction().getTitle() + ", " +
                routine.getAction().getText() + ", " +
                routine.getAction().getWifiState() + ")"
        );
        db.close();
    }
    public List<Routine> getAllActiveRoutineCondition(int id) {
        List<Routine> routineList = new ArrayList<Routine>();
        String selectQuery = "SELECT  * FROM " + TABLE_ROUTINES + " WHERE " + KEY_STATUS + " = " + Constant.ROUTINE_ACTIVE
                + " AND " + KEY_COND + " = " + id;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Condition cond = new Condition(
                        Integer.parseInt(cursor.getString(2)),
                        LocalDateTime.parse(cursor.getString(3))
                );
                Action action = new Action(
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5),
                        cursor.getString(6),
                        Integer.parseInt(cursor.getString(7))
                );
                Routine routine = new Routine(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cond, action
                );
                routineList.add(routine);
            } while (cursor.moveToNext());
        }
        return routineList;
    }

    public List<Routine> getAllActiveRoutine() {
        List<Routine> routineList = new ArrayList<Routine>();
        String selectQuery = "SELECT  * FROM " + TABLE_ROUTINES + " WHERE " + KEY_STATUS + " = " + Constant.ROUTINE_ACTIVE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Condition cond = new Condition(
                        Integer.parseInt(cursor.getString(2)),
                        LocalDateTime.parse(cursor.getString(3))
                );
                Action action = new Action(
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5),
                        cursor.getString(6),
                        Integer.parseInt(cursor.getString(7))
                );
                Routine routine = new Routine(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cond, action
                );
                routineList.add(routine);
            } while (cursor.moveToNext());
        }
        return routineList;
    }

    public List<Routine> getAllInactiveRoutine() {
        List<Routine> routineList = new ArrayList<Routine>();
        String selectQuery = "SELECT  * FROM " + TABLE_ROUTINES + " WHERE " + KEY_STATUS + " = " + Constant.ROUTINE_INACTIVE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Condition cond = new Condition(
                        Integer.parseInt(cursor.getString(2)),
                        LocalDateTime.parse(cursor.getString(3))
                );
                Action action = new Action(
                        Integer.parseInt(cursor.getString(4)),
                        cursor.getString(5),
                        cursor.getString(6),
                        Integer.parseInt(cursor.getString(7))
                );
                Routine routine = new Routine(
                        Integer.parseInt(cursor.getString(0)),
                        Integer.parseInt(cursor.getString(1)),
                        cond, action
                );
                routineList.add(routine);
            } while (cursor.moveToNext());
        }
        return routineList;
    }

    public void changeState(int id, int state) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_ROUTINES + " SET " + KEY_STATUS + " = " + state + " WHERE " + KEY_ID + " = " + id);
        db.close();
    }

    public void deleteRoutine(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_ROUTINES + " WHERE " + KEY_ID + " = " + id);
        db.close();
    }
}

