package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class contactspage extends AppCompatActivity implements View.OnClickListener {

    Button btnback;

    DB db;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactspage);

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(this);

        db = new DB(this);
        database =  db.getWritableDatabase();
        UpdateTable();
    }

    private void UpdateTable() {
        Cursor cursor = database.query(DB.TABLE_CONTACTS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DB.KEY_ID);
            int adreesIndex = cursor.getColumnIndex(DB.KEY_ADRESS);
            int phoneIndex = cursor.getColumnIndex(DB.KEY_PHONE);
            int timeIndex = cursor.getColumnIndex(DB.KEY_TIME);
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

                TextView outputAdress = new TextView(this);
                params.weight= 3.0f;
                outputAdress.setLayoutParams(params);
                outputAdress.setText(cursor.getString(adreesIndex));
                dbOutputRow.addView(outputAdress);

                TextView outputPhone= new TextView(this);
                params.weight= 3.0f;
                outputPhone.setLayoutParams(params);
                outputPhone.setText(cursor.getString(phoneIndex));
                dbOutputRow.addView(outputPhone);

                TextView outputTime= new TextView(this);
                params.weight= 3.0f;
                outputTime.setLayoutParams(params);
                outputTime.setText(cursor.getString(timeIndex));
                dbOutputRow.addView(outputTime);

                dbOutput.addView(dbOutputRow);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnback:
                startActivity(new Intent(this, UserpageActivity.class));
                break;
            default:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDb = (ViewGroup)  outputDBRow.getParent();
                outputDb.removeView(outputDBRow);
                outputDb.invalidate();

                database.delete(DB.TABLE_CONTACTS, DB.KEY_ID +" = ?", new String[]{String.valueOf(v.getId())});
                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DB.TABLE_CONTACTS, null, null, null, null, null, null);
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DB.KEY_ID);
                    int adressIndex = cursorUpdater.getColumnIndex(DB.KEY_ADRESS);
                    int phoneIndex = cursorUpdater.getColumnIndex(DB.KEY_PHONE);
                    int timeIndex = cursorUpdater.getColumnIndex(DB.KEY_TIME);
                    int realID=1;
                    do {
                        if (cursorUpdater.getInt(idIndex)>realID)
                        {
                            contentValues.put(DB.KEY_ID, realID);
                            contentValues.put(DB.KEY_ADRESS, cursorUpdater.getString(adressIndex));
                            contentValues.put(DB.KEY_PHONE, cursorUpdater.getString(phoneIndex));
                            contentValues.put(DB.KEY_TIME, cursorUpdater.getString(timeIndex));
                            database.replace(DB.TABLE_CONTACTS, null, contentValues);
                        }
                        realID++;
                    } while (cursorUpdater.moveToNext());
                    if (cursorUpdater.moveToLast()){
                        database.delete(DB.TABLE_CONTACTS,DB.KEY_ID + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();
                }
                cursorUpdater.close();
                break;
        }
    }
}