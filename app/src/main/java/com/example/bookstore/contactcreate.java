package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class contactcreate extends AppCompatActivity implements View.OnClickListener {

    Button btncreate;
    EditText dbAdress, dbPhone, dbTime;

    DB db;
    SQLiteDatabase database;
    ContentValues contentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contactcreate);

        btncreate = findViewById(R.id.btncreate);
        btncreate.setOnClickListener(this);

        dbAdress = findViewById(R.id.dbAdress);
        dbPhone = findViewById(R.id.dbPhone);
        dbTime = findViewById(R.id.dbTime);

        db = new DB(this);
        database =  db.getWritableDatabase();
        dbAdress.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbAdress.setHint("");
            else
                dbAdress.setHint("Adress");
        });
        dbPhone.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbPhone.setHint("");
            else
                dbPhone.setHint("Phone");
        });
        dbTime.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbTime.setHint("");
            else
                dbTime.setHint("Time");
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncreate:
                String adress = dbAdress.getText().toString();
                String phone = dbPhone.getText().toString();
                String time = dbTime.getText().toString();
                contentValues = new ContentValues();
                contentValues.put(DB.KEY_ADRESS, adress);
                contentValues.put(DB.KEY_PHONE, phone);
                contentValues.put(DB.KEY_TIME, time);

                database.insert(DB.TABLE_CONTACTS, null, contentValues);
                dbAdress.setText("");
                dbPhone.setText("");
                dbTime.setText("");
                startActivity(new Intent(this, contactspageadmin.class));
                break;
        }
    }
}