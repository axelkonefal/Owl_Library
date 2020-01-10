package com.example.logotest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);



        Typeface myfont = Typeface.createFromAsset(getAssets(),"fonts/big_noodle_titling_oblique.ttf");
        TextView aboutTextView = findViewById(R.id.aboutTextView);
        aboutTextView.setTypeface(myfont);

        Button goBackButton = findViewById(R.id.goBackButton);
        goBackButton.setTypeface(myfont);





    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    public void goBackOnClick(View view) {
        Intent goBackIntent = new Intent(this,ActivityMenu.class);
        startActivity(goBackIntent);
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

    }
}
