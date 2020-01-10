package com.example.logotest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMenu extends AppCompatActivity {
    static Button button1;
    Button button2;
    static Button button3;
    Button button4;
    Button button5;
    TextView copyrightTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Typeface myfont = Typeface.createFromAsset(getAssets(), "fonts/big_noodle_titling_oblique.ttf");
        statusCheck();

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button1.setTypeface(myfont);
        button2.setTypeface(myfont);
        button3.setTypeface(myfont);
        button4.setTypeface(myfont);
        button5.setTypeface(myfont);
        copyrightTextView = findViewById(R.id.copyrightTextView);
        copyrightTextView.setTypeface(myfont);

        Cursor cursor = MainActivity.database.rawQuery("SELECT * FROM books", null);
        if(!cursor.moveToFirst()){
        button3.setEnabled(false);
        button1.setEnabled(false);
        }
        else{
            button3.setEnabled(true);
            button1.setEnabled(true);
        }

    }

    @Override
    public void onBackPressed(){
    }

    public void openMyBooks(){
        Intent intent = new Intent(this, MyBooksActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void myBooksOnClick(View view) {
        openMyBooks();
    }



    public void openAbout(){
        Intent intent = new Intent(this, AboutActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void aboutOnClick(View view) {
        openAbout();
    }

    public void openAddBook(){
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void addBookOnClick(View view) {
        openAddBook();
    }

    public void openEditBooks(){
        Intent intent = new Intent(this, EditBooksActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    public void editBooksOnClick(View view) {
        openEditBooks();
    }


    public void nearestLibrariesOnClick(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=library");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("In order to find nearest libraries in your area you should have your GPS turned on. Do you want to do it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


}
