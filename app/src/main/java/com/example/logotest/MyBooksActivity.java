package com.example.logotest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MyBooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        final Typeface myfont = Typeface.createFromAsset(getAssets(), "fonts/big_noodle_titling_oblique.ttf");
        Button button = findViewById(R.id.button);
        button.setTypeface(myfont);

        ListView booksListView = findViewById(R.id.booksListView);
        ArrayList<String> arrayList = new ArrayList<String>();
        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM books",null);
        int bookTitleIndex = cursor.getColumnIndex("bookTitle");


        cursor.moveToFirst();
        do{
            arrayList.add(cursor.getString(bookTitleIndex));
        }while(cursor.moveToNext());

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.listview_my_layout,arrayList);

        booksListView.setAdapter(arrayAdapter);
    }




    public void goBackOnClick(View view) {

        Intent goBackIntent = new Intent(this, ActivityMenu.class);
        startActivity(goBackIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
