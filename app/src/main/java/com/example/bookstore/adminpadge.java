package com.example.bookstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class adminpadge extends AppCompatActivity implements View.OnClickListener {

    Button books, users, contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminpadge);

        books = (Button) findViewById(R.id.books);
        books.setOnClickListener(this);
        users = (Button) findViewById(R.id.users);
        users.setOnClickListener(this);
        contacts = (Button) findViewById(R.id.contacts);
        contacts.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.books:
                startActivity(new Intent(this, bookspageadmin.class));
                break;
            case R.id.users:
                startActivity(new Intent(this, userspage.class));
                break;
            case R.id.contacts:
                startActivity(new Intent(this, contactspageadmin.class));
                break;
        }
    }
}