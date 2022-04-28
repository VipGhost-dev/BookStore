package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class bookcreate extends AppCompatActivity implements View.OnClickListener {

    Button btncreate;
    EditText dbName, dbAuthor, dbPrice;

    DB db;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookcreate);

        btncreate = findViewById(R.id.btncreate);
        btncreate.setOnClickListener(this);

        dbName = findViewById(R.id.dbName);
        dbAuthor = findViewById(R.id.dbPhone);
        dbPrice = findViewById(R.id.dbTime);

        db = new DB(this);
        database =  db.getWritableDatabase();
        dbName.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbName.setHint("");
            else
                dbName.setHint("Name");
        });
        dbAuthor.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbAuthor.setHint("");
            else
                dbAuthor.setHint("Author");
        });
        dbPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus)
                dbPrice.setHint("");
            else
                dbPrice.setHint("Price");
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btncreate:
                String name = dbName.getText().toString();
                String author = dbAuthor.getText().toString();
                String price = dbPrice.getText().toString();
                contentValues = new ContentValues();
                contentValues.put(DB.KEY_NAME, name);
                contentValues.put(DB.KEY_AUTHOR, author);
                contentValues.put(DB.KEY_PRICE, price);

                database.insert(DB.TABLE_BOOKS, null, contentValues);
                dbName.setText("");
                dbAuthor.setText("");
                dbPrice.setText("");
                startActivity(new Intent(this, bookspage.class));
                break;
        }
    }
}