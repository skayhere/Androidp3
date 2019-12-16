package com.example.program3db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
// import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static android.os.Build.ID;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "EmployeeRecords" ;
    public  static final String TABLE_ = "EMPLOYEE";
    public  static final String NAME = "name";
    public  static final String ID= "id";
    public  static final String SALARY = "salary";
    public  static String CREATE_TABLE1;
    public SQLiteDatabase database = null;
    private Cursor cursor;
    public ContentValues cValues;

    DbHelper(Context context){
        super(context,context.getExternalFilesDir(null).getAbsolutePath()+"/"+DATABASE_NAME,null,1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        CREATE_TABLE1="CREATE TABLE "+TABLE_+"("+ID+"INTEGER PRIMARY KEY,"+NAME+" TEXT, "+ SALARY +" TEXT)";
        db.execSQL(CREATE_TABLE1);
        System.out.println("Table Created..");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_);
        onCreate(db);
    }

    public void insertRecord(String name,String salary){
        database = getWritableDatabase();
        cValues = new ContentValues();
        cValues.put(NAME,name);
        cValues.put(SALARY,salary);
        database.insert(TABLE_,null,cValues);
        database.close();
    }
    public  boolean updateRecord(String name,String salary){
        database = getWritableDatabase();
        cValues = new ContentValues();
        cValues.put(NAME,name);
        cValues.put(SALARY,salary);
        database.update(TABLE_,cValues,null,null);
        database.close();
        return true;

    }
    public void deleteRecords(){
        database = getReadableDatabase();
        database.delete(TABLE_,null,null);
        database.close();

    }
    public Cursor selectRecords(){
        database = getReadableDatabase();
        cursor = database.rawQuery("SELECT * FROM "+TABLE_,null);
        return cursor;
    }
}
