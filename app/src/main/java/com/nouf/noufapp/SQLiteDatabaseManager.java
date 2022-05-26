package com.nouf.noufapp;

import android.content.Context;

import java.util.ArrayList;

public class SQLiteDatabaseManager {

    private static SQLiteDatabaseManager instance;
    private static Database database;

    private SQLiteDatabaseManager(){

    }

    public static SQLiteDatabaseManager getInstance(Context context){
        database = new Database(context);
        if (instance == null){
            instance = new SQLiteDatabaseManager();
        }

        return instance;
    }


    public long addNewStudent(StudentObject studentObject){
        return database.addNewStudent(studentObject);
    }

    public ArrayList<StudentObject> getAllStudent(){
        return database.getAllStudent();
    }


    public long deleteStudent(String studentId){
        return database.deleteStudent(studentId);
    }


    public long updateStudent(StudentObject studentObject){
        return database.updateStudent(studentObject);
    }

    public ArrayList<StudentObject> getSpecificStudent(String studentId){
        return database.getSpecificStudent(studentId);
    }
}
