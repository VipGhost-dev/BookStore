package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class userspage extends AppCompatActivity implements View.OnClickListener {

    Button dbAdd,  dbClear , btnback;

    DB db;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userspage);

        dbAdd = findViewById(R.id.btnadd);
        dbAdd.setOnClickListener(this);
        dbClear = findViewById(R.id.btnclear);
        dbClear.setOnClickListener(this);
        btnback =findViewById(R.id.btnback);
        btnback.setOnClickListener(this);

        db = new DB(this);
        database =  db.getWritableDatabase();
        UpdateTable();
    }

    private void UpdateTable() {
        Cursor cursor = database.query(DB.TABLE_USERS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DB.KEY_ID1);
            int loginIndex = cursor.getColumnIndex(DB.KEY_LOGIN);
            int passwordIndex = cursor.getColumnIndex(DB.KEY_PASSWORD);
            TableLayout dbOutput = findViewById(R.id.dbOutput);
            dbOutput.removeAllViews();
            do {
                TableRow dbOutputRow = new TableRow(this);
                dbOutputRow.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);

                TextView outputID = new TextView(this);
                params.weight= 1.0f;
                outputID.setLayoutParams(params);
                outputID.setText(cursor.getString(idIndex));
                dbOutputRow.addView(outputID);

                TextView outputLogin = new TextView(this);
                params.weight= 3.0f;
                outputLogin.setLayoutParams(params);
                outputLogin.setText(cursor.getString(loginIndex));
                dbOutputRow.addView(outputLogin);

                TextView outputPassword= new TextView(this);
                params.weight= 3.0f;
                outputPassword.setLayoutParams(params);
                outputPassword.setText(cursor.getString(passwordIndex));
                dbOutputRow.addView(outputPassword);

                Button deleteBtn = new Button(this);
                deleteBtn.setOnClickListener(this);
                params.weight= 1.0f;
                deleteBtn.setLayoutParams(params);
                deleteBtn.setText("Удалить запись");
                deleteBtn.setId(cursor.getInt(idIndex));
                dbOutputRow.addView(deleteBtn);

                dbOutput.addView(dbOutputRow);

            } while (cursor.moveToNext());

        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnadd:
                startActivity(new Intent(this, usercreate.class));
                break;

            case R.id.btnclear:
                database.delete(DB.TABLE_USERS, null, null);
                TableLayout dbOutput = findViewById(R.id.dbOutput);
                dbOutput.removeAllViews();
                UpdateTable();
                break;
            case R.id.btnback:
                    startActivity(new Intent(this, adminpadge.class));
                    break;
            default:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDb = (ViewGroup)  outputDBRow.getParent();
                outputDb.removeView(outputDBRow);
                outputDb.invalidate();

                database.delete(DB.TABLE_USERS, DB.KEY_ID1 +" = ?", new String[]{String.valueOf(v.getId())});
                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DB.TABLE_USERS, null, null, null, null, null, null);
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DB.KEY_ID1);
                    int loginIndex = cursorUpdater.getColumnIndex(DB.KEY_LOGIN);
                    int passwordIndex = cursorUpdater.getColumnIndex(DB.KEY_PASSWORD);
                    int realID=1;
                    do {
                        if (cursorUpdater.getInt(idIndex)>realID)
                        {
                            contentValues.put(DB.KEY_ID1, realID);
                            contentValues.put(DB.KEY_LOGIN, cursorUpdater.getString(loginIndex));
                            contentValues.put(DB.KEY_PASSWORD, cursorUpdater.getString(passwordIndex));
                            database.replace(DB.TABLE_USERS, null, contentValues);
                        }
                        realID++;
                    } while (cursorUpdater.moveToNext());
                    if (cursorUpdater.moveToLast()){
                        database.delete(DB.TABLE_USERS,DB.KEY_ID1 + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();
                }
                cursorUpdater.close();
                break;
        }
    }
}