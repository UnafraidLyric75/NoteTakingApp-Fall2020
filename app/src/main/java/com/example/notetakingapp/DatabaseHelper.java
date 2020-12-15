package com.example.notetakingapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String NOTES_TABLE = "NOTES_TABLE";
    public static final String COLUMN_NOTE_TITLE = "NOTE_TITLE";
    public static final String COLUMN_NOTE_BODY = "NOTE_BODY";
    public static final String COLUMN_ID = "ID";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    // this is called the first time a database is accessed. Code in here creates a new database
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + NOTES_TABLE + " ( " + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NOTE_TITLE + " TEXT, " + COLUMN_NOTE_BODY + " TEXT)";

        db.execSQL(createTableStatement);
    }

    // this is called if the database version number changes. It prevents previus version from breaking
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addOne(NotesModel notesModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NOTE_TITLE , notesModel.getNoteTitle());
        cv.put(COLUMN_NOTE_BODY, notesModel.getNoteBody());

        long insert = db.insert(NOTES_TABLE, null, cv);
        if (insert == -1){
            return false;
        }
        return true;
    }

    public void deleteOne(int noteId){
        SQLiteDatabase db = this.getWritableDatabase();
        String querryString = "DELETE FROM " + NOTES_TABLE + " WHERE " + COLUMN_ID + " = " + noteId;

        db.execSQL(querryString);
        db.close();
    }

    public List<NotesModel> getAllNotes(){
        List<NotesModel> returnList = new ArrayList<>();

        // get data from the database

        String queryString = "SELECT * FROM " + NOTES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                int noteID = cursor.getInt(0);
                String noteTitle = cursor.getString(1);
                String noteBody = cursor.getString(2);

                NotesModel newNotesModel = new NotesModel(noteID, noteTitle, noteBody);
                returnList.add(newNotesModel);

            } while (cursor.moveToNext());
        } else{
            // failure do not add anything to list
        }
        cursor.close();
        db.close();
        return returnList;
    }

    public String getNoteTitle(int noteID) {
        String result = "";

        // get data from the database

        String queryString = "SELECT * FROM " + NOTES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        cursor.moveToPosition(noteID);
        result = cursor.getString(1);
        cursor.close();
        db.close();
        return result;
    }

    public String getNoteBody(int noteID) {
        String result = "";

        // get data from the database

        String queryString = "SELECT * FROM " + NOTES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        cursor.moveToPosition(noteID);
        result = cursor.getString(2);
        cursor.close();
        db.close();
        return result;
    }

    public Integer getId(String title){
        Integer result = null;
        String sResult = null;

        // get data from the database

        String queryString = "SELECT * FROM " + NOTES_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()){
            do{
                result = cursor.getInt(0);
                sResult = cursor.getString(1);
            } while (cursor.moveToNext() && sResult != title);
        }
        cursor.close();
        db.close();
        return result;
    }
}
