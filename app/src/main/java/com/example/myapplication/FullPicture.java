package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FullPicture extends AppCompatActivity {
    private String url = "", name = "", author = "", disc = "";
    ImageView picture;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_full_picture);

        picture = findViewById(R.id.fullPicture);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            url = arguments.get("url").toString();
        }
        LoadPicPicasso(url);
    }


    public void LoadPicPicasso(String url){
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(picture);
    }
}