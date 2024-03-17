package edu.jsu.mcis.cs408.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final int CSV_RECORD_LENGTH = 1;
    private static final String DATABASE_NAME = "mydatabase.db";
    private static final String TABLE_MEMOS = "memos";

    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";

    private static final String QUERY_CREATE_MEMOS_TABLE = "CREATE TABLE " + TABLE_MEMOS + " (" + COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " text)";
    private static final String QUERY_DELETE_MEMOS_TABLE = "DROP TABLE IF EXISTS " + TABLE_MEMOS;

    private static final String QUERY_GET_ALL_MEMOS = "SELECT * FROM " + TABLE_MEMOS;
    private static final String QUERY_GET_MEMO = "SELECT * FROM " + TABLE_MEMOS + " WHERE " + COLUMN_ID + " = ?";



    public DatabaseHandler(Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_CREATE_MEMOS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(QUERY_DELETE_MEMOS_TABLE);
        onCreate(db);
    }

    public void addMemo(Memo m) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, m.getName());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }

    public void deleteMemo(Memo m) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMOS, "_id=?", new String[] {String.valueOf(m.getID())});
    }

    public void deleteAllMemos() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_MEMOS, null, null);
    }

    public Memo getMemo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(QUERY_GET_MEMO, new String[]{String.valueOf(id)});
        Memo m = null;

        if (cursor.moveToFirst()) {
            int newID = cursor.getInt(0);
            String newName = cursor.getString(1);
            m = new Memo(newID, newName);
            cursor.close();
        }

        db.close();

        return m;
    }

    public ArrayList<Memo> getAllMemosAsList() {
        ArrayList<Memo> allMemos = new ArrayList<>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(QUERY_GET_ALL_MEMOS, null);

        if (cursor.moveToFirst()) {
            cursor.moveToFirst();

            do {
                int id = cursor.getInt(0);
                allMemos.add(getMemo(id));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return allMemos;
    }


}


