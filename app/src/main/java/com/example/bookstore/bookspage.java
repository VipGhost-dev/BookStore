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

public class bookspage extends AppCompatActivity implements View.OnClickListener {

    Button btnback, dbAdd;

    DB db;
    SQLiteDatabase database;
    ContentValues contentValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bookspage);

        dbAdd = findViewById(R.id.btnadd);
        dbAdd.setOnClickListener(this);
        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(this);

        db = new DB(this);
        database =  db.getWritableDatabase();
        UpdateTable();
    }

    private void UpdateTable() {
        Cursor cursor = database.query(DB.TABLE_BOOKS, null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DB.KEY_ID);
            int nameIndex = cursor.getColumnIndex(DB.KEY_NAME);
            int authorIndex = cursor.getColumnIndex(DB.KEY_AUTHOR);
            int priceIndex = cursor.getColumnIndex(DB.KEY_PRICE);
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

                TextView outputName = new TextView(this);
                params.weight= 3.0f;
                outputName.setLayoutParams(params);
                outputName.setText(cursor.getString(nameIndex));
                dbOutputRow.addView(outputName);

                TextView outputAuthor= new TextView(this);
                params.weight= 3.0f;
                outputAuthor.setLayoutParams(params);
                outputAuthor.setText(cursor.getString(authorIndex));
                dbOutputRow.addView(outputAuthor);

                TextView outputPrice= new TextView(this);
                params.weight= 3.0f;
                outputPrice.setLayoutParams(params);
                outputPrice.setText(cursor.getString(priceIndex));
                dbOutputRow.addView(outputPrice);

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
                startActivity(new Intent(this, bookcreate.class));
                break;

            case R.id.btnback:
                startActivity(new Intent(this, UserpageActivity.class));
                break;
            default:
                View outputDBRow = (View) v.getParent();
                ViewGroup outputDb = (ViewGroup)  outputDBRow.getParent();
                outputDb.removeView(outputDBRow);
                outputDb.invalidate();

                database.delete(DB.TABLE_BOOKS, DB.KEY_ID +" = ?", new String[]{String.valueOf(v.getId())});
                contentValues = new ContentValues();
                Cursor cursorUpdater = database.query(DB.TABLE_BOOKS, null, null, null, null, null, null);
                if (cursorUpdater.moveToFirst()) {
                    int idIndex = cursorUpdater.getColumnIndex(DB.KEY_ID);
                    int nameIndex = cursorUpdater.getColumnIndex(DB.KEY_NAME);
                    int authorIndex = cursorUpdater.getColumnIndex(DB.KEY_AUTHOR);
                    int priceIndex = cursorUpdater.getColumnIndex(DB.KEY_PRICE);
                    int realID=1;
                    do {
                        if (cursorUpdater.getInt(idIndex)>realID)
                        {
                            contentValues.put(DB.KEY_ID, realID);
                            contentValues.put(DB.KEY_NAME, cursorUpdater.getString(nameIndex));
                            contentValues.put(DB.KEY_AUTHOR, cursorUpdater.getString(authorIndex));
                            contentValues.put(DB.KEY_PRICE, cursorUpdater.getString(priceIndex));
                            database.replace(DB.TABLE_BOOKS, null, contentValues);
                        }
                        realID++;
                    } while (cursorUpdater.moveToNext());
                    if (cursorUpdater.moveToLast()){
                        database.delete(DB.TABLE_BOOKS,DB.KEY_ID + " = ?", new String[]{cursorUpdater.getString(idIndex)});
                    }
                    UpdateTable();
                }
                cursorUpdater.close();
                break;
        }
    }
}