package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnEnter, btnSign;
    EditText login, password;
    DB db;
    SQLiteDatabase database;
    String adminUser = "admin";
    String adminPassword = "admin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnEnter = (Button) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(this);
        btnSign = (Button) findViewById(R.id.btnSign);
        btnSign.setOnClickListener(this);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        db = new DB(this);
        database = db.getWritableDatabase();

        login.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                login.setHint("");
            else
                login.setHint("Username");
        });
        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                password.setHint("");
            else
                password.setHint("Password");
        });

        ContentValues contentValues = new ContentValues();
        contentValues.put(DB.KEY_LOGIN, adminUser);
        contentValues.put(DB.KEY_PASSWORD, adminPassword);
        database.insert(DB.TABLE_USERS, null, contentValues);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnEnter:
                Cursor loginCursor = database.query(DB.TABLE_USERS, null, null, null, null, null, null);
                boolean logged = false;
                if (loginCursor.moveToFirst()) {
                    int usernameIndex = loginCursor.getColumnIndex(DB.KEY_LOGIN);
                    int passwordIndex = loginCursor.getColumnIndex(DB.KEY_PASSWORD);
                    do {
                        if(login.getText().toString().equals(adminUser) && password.getText().toString().equals(adminPassword)) {
                            startActivity(new Intent(this, adminpadge.class));
                            logged = true;
                            break;
                        }
                        if(login.getText().toString().equals(loginCursor.getString(usernameIndex)) && password.getText().toString().equals(loginCursor.getString(passwordIndex))){
                          startActivity(new Intent(this, UserpageActivity.class));
                            logged = true;
                            break;
                        }
                    } while (loginCursor.moveToNext());
                }
                loginCursor.close();
                if (!logged)
                    Toast.makeText(this, "Введенная комбинация логина и пароля не была найдена", Toast.LENGTH_LONG).show();
                break;
            case R.id.btnSign:
                Cursor signCursor = database.query(DB.TABLE_USERS, null, null, null, null, null, null);
                boolean funded = false;
                if (signCursor.moveToFirst()) {
                    int usernameIndex = signCursor.getColumnIndex(DB.KEY_LOGIN);
                    do {
                        if (login.getText().toString().equals(signCursor.getString(usernameIndex))) {
                            Toast.makeText(this, "Введеный вами логин уже зарегистрирован", Toast.LENGTH_LONG).show();
                            funded = true;
                            break;
                        }
                    } while (signCursor.moveToNext());
                }
                if (!funded) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DB.KEY_LOGIN, login.getText().toString());
                    contentValues.put(DB.KEY_PASSWORD, password.getText().toString());
                    database.insert(DB.TABLE_USERS, null, contentValues);
                    Toast.makeText(this, "Вы успешно зарегистрировались!", Toast.LENGTH_LONG).show();
                }
                signCursor.close();
                break;
        }
    }
}