package com.example.logotest;







import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.TransitionDrawable;
import android.location.LocationManager;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.VisibilityPropagation;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.Manifest;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    ImageView sowaBezLogo;
    TextView textView;
    ImageView img;
    ConstraintLayout constraintLayout;
    Button firstMenuBtn;
    static SQLiteDatabase database;
    static List<String> categoryList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = this.openOrCreateDatabase("books",MODE_PRIVATE,null);

       database.execSQL("CREATE TABLE IF NOT EXISTS books(book_id INTEGER PRIMARY KEY NOT NULL, bookTitle VARCHAR, author VARCHAR,category VARCHAR, description VARCHAR, rate INT)");
        Typeface myfont = Typeface.createFromAsset(getAssets(),"fonts/big_noodle_titling_oblique.ttf");

        img = findViewById(R.id.logo);
        sowaBezLogo = findViewById(R.id.imageView4);
        constraintLayout = findViewById(R.id.btnConstraintLayout);
        firstMenuBtn = findViewById(R.id.firstMenuBtn);
        textView = findViewById(R.id.textView);
        firstMenuBtn.setTypeface(myfont);
        constraintLayout.setVisibility(View.INVISIBLE);

        final Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        final Animation aniFade2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
        img.startAnimation(aniFade);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                img.startAnimation(aniFade2);
            }
        },5000); //5000

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                constraintLayout.startAnimation(aniFade);
                constraintLayout.setVisibility(View.VISIBLE);
            }
        },8000); //8000

        firstMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openActivityMenu();
            }
        });


        categoryList.add("Action and Adventure");
        categoryList.add("Anthology");
        categoryList.add("Classic");
        categoryList.add("Comic and Graphic Novel");
        categoryList.add("Crime and Detective");
        categoryList.add("Drama");
        categoryList.add("Fable");
        categoryList.add("Fairy Tale");
        categoryList.add("Fan-Fiction");
        categoryList.add("Fantasy");
        categoryList.add("Historical Fiction");
        categoryList.add("Horror");
        categoryList.add("Humor");
        categoryList.add("Legend");
        categoryList.add("Magical Realism");
        categoryList.add("Mystery");
        categoryList.add("Mythology");
        categoryList.add("Realistic Fiction");
        categoryList.add("Romance");
        categoryList.add("Satire");
        categoryList.add("Science Fiction (Sci-Fi)");
        categoryList.add("Short Story");
        categoryList.add("Suspense/Thriller");






    }

    public void  openActivityMenu(){
        Intent intent = new Intent(this, ActivityMenu.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


    }

}




