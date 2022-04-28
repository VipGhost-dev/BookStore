package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class UserpageActivity extends AppCompatActivity implements View.OnClickListener {

    Button books, contacts, cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userpage);

        books = findViewById(R.id.books);
        books.setOnClickListener(this);
        contacts = findViewById(R.id.contacts);
        contacts.setOnClickListener(this);
        cp = findViewById(R.id.changepassword);
        cp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.books:
                startActivity(new Intent(this, bookspage.class));
                break;
            case R.id.contacts:
                startActivity(new Intent(this, contactspage.class));
                break;
            case R.id.changepassword:
                startActivity(new Intent(this, Changepage.class));
                break;
        }
    }
}