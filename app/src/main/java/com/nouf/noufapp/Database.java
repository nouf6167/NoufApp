package com.nouf.noufapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "nouf.db";

    private static final String TABLE_STUDENTS = "students";

    private static final String KEY_ID = "id";
    private static final String KEY_FIREBASE_ID = "firebase_id";
    private static final String KEY_STUDENT_ID = "student_id";
    private static final String KEY_STUDENT_NAME = "student_name";
    private static final String KEY_STUDENT_SURNAME = "student_surname";
    private static final String KEY_STUDENT_FATHER_NAME = "student_father_name";
    private static final String KEY_STUDENT_NATIONAL_ID = "student_national_id";
    private static final String KEY_STUDENT_DATE_OF_BIRTH = "student_date_of_birth";
    private static final String KEY_STUDENT_GENDER = "student_gender";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_STUDENTS_TABLE = "CREATE TABLE " + TABLE_STUDENTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIREBASE_ID
                + " TEXT," + KEY_STUDENT_ID + " TEXT, " + KEY_STUDENT_NAME + " TEXT, " + KEY_STUDENT_SURNAME
                + " TEXT, " + KEY_STUDENT_FATHER_NAME + " TEXT, " + KEY_STUDENT_NATIONAL_ID
                + " TEXT, " + KEY_STUDENT_DATE_OF_BIRTH + " TEXT, " + KEY_STUDENT_GENDER
                + " TEXT )";

        db.execSQL(CREATE_STUDENTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        // Create tables again
        onCreate(db);
    }

    public long addNewStudent(StudentObject studentObject){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c=db.rawQuery("SELECT * FROM "+ TABLE_STUDENTS + " WHERE "+KEY_STUDENT_ID+"="+studentObject.getStudentId(), null);
        if(!c.moveToFirst())
        {
            ContentValues values = new ContentValues();
            values.put(KEY_FIREBASE_ID, studentObject.getId());
            values.put(KEY_STUDENT_ID, studentObject.getStudentId());
            values.put(KEY_STUDENT_NAME, studentObject.getStudentName());
            values.put(KEY_STUDENT_SURNAME, studentObject.getStudentSurName());
            values.put(KEY_STUDENT_FATHER_NAME, studentObject.getStudentFatherName());
            values.put(KEY_STUDENT_NATIONAL_ID, studentObject.getStudentNationalId());
            values.put(KEY_STUDENT_DATE_OF_BIRTH, studentObject.getStudentDateOfBirth());
            values.put(KEY_STUDENT_GENDER, studentObject.getStudentGender());
            return db.insert(TABLE_STUDENTS, null, values);
        }
        return 0;
    }

    public ArrayList<StudentObject> getAllStudent(){
        ArrayList<StudentObject> studentObjects = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StudentObject studentObject = new StudentObject();
                studentObject.setId(cursor.getString(1));
                studentObject.setStudentId(cursor.getString(2));
                studentObject.setStudentName(cursor.getString(3));
                studentObject.setStudentSurName(cursor.getString(4));
                studentObject.setStudentFatherName(cursor.getString(5));
                studentObject.setStudentNationalId(cursor.getString(6));
                studentObject.setStudentDateOfBirth(cursor.getString(7));
                studentObject.setStudentGender(cursor.getString(8));

                // Adding contact to list
                studentObjects.add(studentObject);
            } while (cursor.moveToNext());
        }

        return studentObjects;
    }

    public long deleteStudent(String studentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_STUDENTS, KEY_STUDENT_ID + " = ?",
                new String[] { String.valueOf(studentId) });
    }

    public long updateStudent(StudentObject studentObject){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FIREBASE_ID, studentObject.getId());
        values.put(KEY_STUDENT_ID, studentObject.getStudentId());
        values.put(KEY_STUDENT_NAME, studentObject.getStudentName());
        values.put(KEY_STUDENT_SURNAME, studentObject.getStudentSurName());
        values.put(KEY_STUDENT_FATHER_NAME, studentObject.getStudentFatherName());
        values.put(KEY_STUDENT_NATIONAL_ID, studentObject.getStudentNationalId());
        values.put(KEY_STUDENT_DATE_OF_BIRTH, studentObject.getStudentDateOfBirth());
        values.put(KEY_STUDENT_GENDER, studentObject.getStudentGender());

        return db.update(TABLE_STUDENTS, values, KEY_STUDENT_ID + " = ?",
                new String[] { String.valueOf(studentObject.getStudentId()) });

    }

    public ArrayList<StudentObject> getSpecificStudent(String studentId){
        ArrayList<StudentObject> studentObjects = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_STUDENTS + " WHERE " +KEY_STUDENT_ID+"="+studentId;
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                StudentObject studentObject = new StudentObject();
                studentObject.setId(cursor.getString(1));
                studentObject.setStudentId(cursor.getString(2));
                studentObject.setStudentName(cursor.getString(3));
                studentObject.setStudentSurName(cursor.getString(4));
                studentObject.setStudentFatherName(cursor.getString(5));
                studentObject.setStudentNationalId(cursor.getString(6));
                studentObject.setStudentDateOfBirth(cursor.getString(7));
                studentObject.setStudentGender(cursor.getString(8));

                // Adding contact to list
                studentObjects.add(studentObject);
            } while (cursor.moveToNext());
        }

        return studentObjects;
    }
}
