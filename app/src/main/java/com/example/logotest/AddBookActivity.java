package com.example.logotest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AddBookActivity extends AppCompatActivity {
    EditText bookTitleEditText;
    EditText authorEditText;
    String itemValue;
    EditText descriptionEditText;
    RatingBar ratingBar;
    Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        TextView addTextView = findViewById(R.id.editTextView);
        TextView bookTitleTextView = findViewById(R.id.bookTitleTextView);
        bookTitleEditText = findViewById(R.id.bookTitleEditText);
        TextView authorTextView = findViewById(R.id.authorTextView);
        authorEditText = findViewById(R.id.authorEditText);

        final Typeface myfont = Typeface.createFromAsset(getAssets(), "fonts/big_noodle_titling_oblique.ttf");
        addTextView.setTypeface(myfont);
        bookTitleTextView.setTypeface(myfont);
        bookTitleEditText.setTypeface(myfont);
        authorTextView.setTypeface(myfont);
        authorEditText.setTypeface(myfont);

        Button goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setTypeface(myfont);


        Button submitButton = findViewById(R.id.submitButton);
        submitButton.setTypeface(myfont);

        TextView categoryTextView = findViewById(R.id.categoryTextView);
        categoryTextView.setTypeface(myfont);


        categorySpinner = findViewById(R.id.categorySpinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner, MainActivity.categoryList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemValue = parent.getItemAtPosition(position).toString();
                ((TextView) view).setTypeface(myfont);
                Log.i("tablica kategorii: ", itemValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setTypeface(myfont);

        descriptionEditText = findViewById(R.id.descriptionEditText);
        descriptionEditText.setTypeface(myfont);

        TextView ratingBarTextView = findViewById(R.id.ratingBarTextView);
        ratingBarTextView.setTypeface(myfont);
        ratingBar = findViewById(R.id.ratingBar);

    }

    public void goBackOnClick(View view) {

        Intent goBackIntent = new Intent(this, ActivityMenu.class);
        startActivity(goBackIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public void submitOnClick(View view) {

        String bookTitle = bookTitleEditText.getText().toString();
        String bookAuthor = authorEditText.getText().toString();
        String cat = categorySpinner.getSelectedItem().toString();
        String description = descriptionEditText.getText().toString();
        float floatValue = ratingBar.getRating();
        int value = (int) floatValue;

        if (!bookTitle.isEmpty() && !bookAuthor.isEmpty() && !itemValue.isEmpty() && !description.isEmpty() && value != 0) {
            Toast.makeText(getApplicationContext(),
                    "Book has been added",
                    Toast.LENGTH_LONG).show();


            ActivityMenu.button3.setEnabled(true);
            ActivityMenu.button1.setEnabled(true);

            MainActivity.database.execSQL("INSERT into books (bookTitle , author ,category , description , rate ) VALUES ( '"+bookTitle+"','"+bookAuthor+"','"+cat+"','"+description+"','"+value+"' )");

            Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM books",null);
            int book_idIndex = cursor.getColumnIndex("book_id");
            int bookTitleIndex = cursor.getColumnIndex("bookTitle");
            int authorIndex = cursor.getColumnIndex("author");
            int categoryIndex = cursor.getColumnIndex("category");
            int descriptionIndex = cursor.getColumnIndex("description");
            int rateIndex = cursor.getColumnIndex("rate");

            cursor.moveToFirst();
            do {
                Log.i("book id " , cursor.getString(book_idIndex));
                Log.i("tytul " , cursor.getString(bookTitleIndex));
                Log.i("autor  " , cursor.getString(authorIndex));
                Log.i("kategoria  " , cursor.getString(categoryIndex));
                Log.i("opis  " , cursor.getString(descriptionIndex));
                Log.i("ocena  " , cursor.getString(rateIndex));

            }while(cursor.moveToNext());


            finish();
            startActivity(getIntent());


        } else {
            Toast.makeText(getApplicationContext(),
                    "Fill all fields",
                    Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);



    }
}
