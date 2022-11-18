package com.example.myapplication;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.bd.MyBdMeneger;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private MyBdMeneger myBdMeneger;
    ImageView imageView;
    TextView text_about_pic, name_text, author_text;
    int count = 0;
    int MyDBSize = 6;
    Button readFull;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myBdMeneger = new MyBdMeneger(this);
        imageView = (ImageView) findViewById(R.id.imageView2);
        name_text = findViewById(R.id.Name);
        author_text = findViewById(R.id.Author);
        text_about_pic = findViewById(R.id.textOfPic);
        readFull = findViewById(R.id.readFullDisc);
    }
    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();
        LoadDailyPic();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBdMeneger.CloseDB();
    }
    public void WorkWithBD(View v) {
        Intent intent = new Intent(this, WorkWithBD.class);
        startActivity(intent);
    }

    public void OpenArchive (View v){
        Intent intent = new Intent(this, Archive.class);
        startActivity(intent);
    }

    public void LoadDailyPic(){
        Timer myTimer;
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            public void run() {
                timerTick();
            }
        }, 0, 15000);
    }

    private void timerTick() {
        this.runOnUiThread(doTask);
    }

    private Runnable doTask = new Runnable() {
        public void run() {
            String url, name, author, disk;
            count++;
            if(count > MyDBSize){
                count = 1;
            }
            List<String> info = myBdMeneger.getOneStringFromDB(count);
            url = info.get(0);
            name = info.get(1);
            author = info.get(2);
            disk = info.get(3);
            LoadPicPicasso(url);
            setOnClick(readFull, url, name, author, disk);
            name_text.setText(name);
            author_text.setText(author);
            text_about_pic.setText(disk);
        }
    };

    private void setOnClick(final Button btn, final String url_times, final String name, final String author, final String disc){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFullDiscActivity(url_times, name, author, disc);
            }
        });
    }
    public void startFullDiscActivity (String url, String name, String author, String disc) {
        Intent intent = new Intent(this, FullDisc.class);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        intent.putExtra("author", author );
        intent.putExtra("disc", disc);
        startActivity(intent);
    }

    public void LoadPicPicasso(String url){
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}

