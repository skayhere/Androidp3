package com.example.program3db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
// import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name,salary;
    Button insert,delete,select,update,sav,ret,del;
    DbHelper mDbHelper;
    Cursor mCursor;

    //SharedPreference
    public static final String preferences = "pref";
    public static final String saveit = "savekey";
    SharedPreferences sf;

    TextView mTextView;
    public static final String KEY_FIRSTNAME = "firstname_key";
    Button submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        salary = findViewById(R.id.salary);
        mDbHelper = new DbHelper(getApplicationContext());
        insert = findViewById(R.id.insert);
        delete = findViewById(R.id.delete);
        update = findViewById(R.id.update);
        select = findViewById(R.id.select);
        select.setOnClickListener(this);
        delete.setOnClickListener(this);
        update.setOnClickListener(this);
        insert.setOnClickListener(this);

        //Shared Preference
        sf = getSharedPreferences(preferences, Context.MODE_PRIVATE);
        sav = findViewById(R.id.sav);
        ret = findViewById(R.id.ret);
        del = findViewById(R.id.del);

        del.setOnClickListener(this);
        ret.setOnClickListener(this);
        sav.setOnClickListener(this);

        //Instance
        mTextView = findViewById(R.id.textView);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
        if(savedInstanceState!=null){
            String savedname = savedInstanceState.getString(KEY_FIRSTNAME);
            mTextView.setText(savedname);
        }
        else
            Toast.makeText(this,"new Entry",Toast.LENGTH_SHORT).show();

    }
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(KEY_FIRSTNAME,mTextView.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.insert:
                String sname = name.getText().toString();
                String ssal = salary.getText().toString();
                mDbHelper.insertRecord(sname,ssal);
                Toast.makeText(this,"Inserted successfully\nName "+ sname + "\nSalary "+ssal,Toast.LENGTH_LONG).show();
                break;

            case R.id.delete:
                mDbHelper.deleteRecords();
                Toast.makeText(this,"Deleted",Toast.LENGTH_LONG).show();
                break;

            case R.id.select:
                mCursor = mDbHelper.selectRecords();
                if (mCursor.moveToFirst())
                    do {
                        String ssname = mCursor.getString(mCursor.getColumnIndex(mDbHelper.NAME));
                        String sssal = mCursor.getString(mCursor.getColumnIndex(mDbHelper.SALARY));
                        Toast.makeText(this,"Values are \nName "+ ssname + "\nSalary "+sssal,Toast.LENGTH_LONG).show();
                    }while (mCursor.moveToNext());
                mCursor.close();
                break;

            case R.id.update:
                sname = name.getText().toString();
                ssal = salary.getText().toString();
                boolean upd = mDbHelper.updateRecord(sname,ssal);
                if(upd){
                    Toast.makeText(this,"Updated successfully",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this,"Update Not successful",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.sav:
                String str = name.getText().toString();
                SharedPreferences.Editor ed = sf.edit();
                ed.putString(saveit,str);
                ed.commit();
                break;
            case R.id.ret:
                sf = getSharedPreferences(preferences,Context.MODE_PRIVATE);
                if(sf.contains(saveit))
                {
                    name.setText(sf.getString(saveit,""));
                }
                break;
            case R.id.del:
                name.setText("");
                break;
            case R.id.submit:
                mTextView.setText(name.getText().toString());

        }
    }
}