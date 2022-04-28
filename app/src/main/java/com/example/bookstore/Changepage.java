package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Changepage extends AppCompatActivity implements View.OnClickListener {

    Button btnchange;
    EditText dbLogin, dbPassword;

    DB db;
    SQLiteDatabase database;
    ContentValues contentValues;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepage);

        btnchange = findViewById(R.id.btnchange);
        btnchange.setOnClickListener(this);

        dbLogin = findViewById(R.id.dbLogin);
        dbPassword = findViewById(R.id.dbPassword);

        db = new DB(this);
        database = db.getWritableDatabase();

        Bundle arguments = getIntent().getExtras();

        Update();

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

    private void Update() {
        Cursor cursor = database.rawQuery("SELECT * FROM " + DB.TABLE_USERS + " WHERE " + DB.KEY_LOGIN + " = '" + MainActivity.user + "'", null);

        int loginIndex = cursor.getColumnIndex(DB.KEY_LOGIN);
        int passwordIndex = cursor.getColumnIndex(DB.KEY_PASSWORD);

        if (cursor.moveToFirst()) {
            dbLogin.setText(cursor.getString(loginIndex));
            dbPassword.setText(cursor.getString(passwordIndex));
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnchange:
                String login = dbLogin.getText().toString();
                String password = dbPassword.getText().toString();

                contentValues = new ContentValues();

                contentValues.put(DB.KEY_LOGIN, login);
                contentValues.put(DB.KEY_PASSWORD, password);
                database.update(DB.TABLE_USERS, contentValues,DB.KEY_LOGIN + " = '" + MainActivity.user + "'", null);
                startActivity(new Intent(this, MainActivity.class));
                finish();
        }
    }
}