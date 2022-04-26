package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class usercreate extends AppCompatActivity implements View.OnClickListener {

    Button btncreate;
    EditText dbLogin, dbPassword;

    DB db;
    SQLiteDatabase database;
    ContentValues contentValues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.usercreate);

        btncreate = findViewById(R.id.btncreate);
        btncreate.setOnClickListener(this);
        dbLogin = findViewById(R.id.dbLogin);
        dbPassword = findViewById(R.id.dbPassword);
        db = new DB(this);
        database =  db.getWritableDatabase();
        dbLogin.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbLogin.setHint("");
            else
                dbLogin.setHint("Login");
        });
        dbPassword.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbPassword.setHint("");
            else
                dbPassword.setHint("Password");
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncreate:
                String login = dbLogin.getText().toString();
                String password = dbPassword.getText().toString();
                contentValues = new ContentValues();
                contentValues.put(DB.KEY_LOGIN, login);
                contentValues.put(DB.KEY_PASSWORD, password);

                database.insert(DB.TABLE_USERS, null, contentValues);
                dbLogin.setText("");
                dbPassword.setText("");
                startActivity(new Intent(this, userspage.class));
                break;
        }
    }
}