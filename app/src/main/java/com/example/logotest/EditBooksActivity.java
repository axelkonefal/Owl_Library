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
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class EditBooksActivity extends AppCompatActivity {

    String itemValue;
    EditText bookTitleEditText;
    EditText authorEditText;
    Cursor cursor;
    Spinner categorySpinner;
    ArrayAdapter<String> spinnerAdapter;
    EditText descriptionEditText;
    RatingBar ratingBar;
    Button submitButton;

    TextView idTextView;

    Button goBackButton;
    Button deleteButton;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_books);
        final Typeface myfont = Typeface.createFromAsset(getAssets(), "fonts/big_noodle_titling_oblique.ttf");
        TextView editTextView = findViewById(R.id.editTextView);
        editTextView.setTypeface(myfont);
        TextView chooseTextView = findViewById(R.id.chooseTextView);
        chooseTextView.setTypeface(myfont);

        bookTitleEditText = findViewById(R.id.bookTitleEditText);
        bookTitleEditText.setTypeface(myfont);
        authorEditText = findViewById(R.id.authorEditText);
        authorEditText.setTypeface(myfont);
        categorySpinner = findViewById(R.id.categorySpinner);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        descriptionEditText.setTypeface(myfont);
        ratingBar = findViewById(R.id.ratingBar);
        submitButton = findViewById(R.id.submitButton);
        submitButton.setTypeface(myfont);
        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setTypeface(myfont);
        idTextView = findViewById(R.id.idTextView);

        TextView bookTitleTextView = findViewById(R.id.bookTitleTextView);
        bookTitleTextView.setTypeface(myfont);

        TextView authorTextView = findViewById(R.id.authorTextView);
        authorTextView.setTypeface(myfont);

        TextView categoryTextView = findViewById(R.id.categoryTextView);
        categoryTextView.setTypeface(myfont);

        TextView descriptionTextView = findViewById(R.id.descriptionTextView);
        descriptionTextView.setTypeface(myfont);

        TextView ratingBarTextView = findViewById(R.id.ratingBarTextView);
        ratingBarTextView.setTypeface(myfont);

        goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setTypeface(myfont);


        spinnerAdapter = new ArrayAdapter<String>(this, R.layout.my_spinner, MainActivity.categoryList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemValue = parent.getItemAtPosition(position).toString();
                ((TextView) view).setTypeface(myfont);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        Spinner selectBookSpinner = findViewById(R.id.selectBookSpinner);
        ArrayList<String> bookList = new ArrayList<>();
        cursor = MainActivity.database.rawQuery("SELECT * FROM books", null);

        //bookTitle VARCHAR, author VARCHAR,category VARCHAR, description VARCHAR, rate INT

        final int bookIdIndex = cursor.getColumnIndex("book_id");
        final int bookTitleIndex = cursor.getColumnIndex("bookTitle");
        final int authorIndex = cursor.getColumnIndex("author");
        final int categoryIndex = cursor.getColumnIndex("category");
        final int descriptionIndex = cursor.getColumnIndex("description");
        final int rateIndex = cursor.getColumnIndex("rate");

        cursor.moveToFirst();
        do{
            bookList.add(cursor.getString(bookTitleIndex));
        }while(cursor.moveToNext());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_spinner, bookList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectBookSpinner.setAdapter(adapter);

        selectBookSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemValue = parent.getItemAtPosition(position).toString();
                ((TextView) view).setTypeface(myfont);
                cursor.moveToFirst();
                for(int i = 0 ; i < position ;i++){
                    cursor.moveToNext();
                }


                bookTitleEditText.setText(cursor.getString(bookTitleIndex));
                authorEditText.setText(cursor.getString(authorIndex));
                int temp = 0;
                for(int i = 0 ; i < MainActivity.categoryList.size() ; i++){
                    if(cursor.getString(categoryIndex).equals(MainActivity.categoryList.get(i))){
                        temp = i;
                    }
                }
                categorySpinner.setAdapter(spinnerAdapter);
                categorySpinner.setSelection(temp);
                descriptionEditText.setText(cursor.getString(descriptionIndex));
                ratingBar.setRating(cursor.getInt(rateIndex));
                idTextView.setText(cursor.getString(bookIdIndex));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public void submitOnClick(View view) {

        int bookId = Integer.parseInt(idTextView.getText().toString());
        String bookTitle = bookTitleEditText.getText().toString();
        String bookAuthor = authorEditText.getText().toString();
        String cat = categorySpinner.getSelectedItem().toString();
        String description = descriptionEditText.getText().toString();
        float floatValue = ratingBar.getRating();
        int value = (int) floatValue;

        if (!bookTitle.isEmpty() && !bookAuthor.isEmpty() && !itemValue.isEmpty() && !description.isEmpty() && value != 0) {
            Toast.makeText(getApplicationContext(),
                    "Book has been edited",
                    Toast.LENGTH_LONG).show();




            MainActivity.database.execSQL("UPDATE books SET bookTitle= '"+bookTitle+"', author= '"+bookAuthor+"',category= '"+cat+"', description='"+description+"' , rate= '"+value+"' WHERE book_id = '"+bookId+"'");

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

    public void goBackOnClick(View view) {
        Intent goBackIntent = new Intent(this, ActivityMenu.class);
        startActivity(goBackIntent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

    }

    public void deleteBookOnClick(View view) {
        int bookId = Integer.parseInt(idTextView.getText().toString());
        MainActivity.database.execSQL("DELETE FROM books WHERE book_id = '"+bookId+"'");

        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM books", null);
        if(!cursor.moveToFirst()){
            Intent goBackIntent = new Intent(this, ActivityMenu.class);
            startActivity(goBackIntent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            Toast.makeText(getApplicationContext(),
                    "Book has been deleted",
                    Toast.LENGTH_LONG).show();

        }
        else{

            finish();
            startActivity(getIntent());
            Toast.makeText(getApplicationContext(),
                    "Book has been deleted",
                    Toast.LENGTH_LONG).show();
        }
    }
}
