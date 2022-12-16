package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.bd.MyBdMeneger;
import com.example.myapplication.bd.MyConstants;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;

import de.hdodenhof.circleimageview.CircleImageView;

public class FullDisc extends AppCompatActivity {
    private String url = "", name = "", author = "", disc = "", likes = "";
    private MyBdMeneger myBdMeneger;
    CircleImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_disc);
        ImageButton img_b = (ImageButton) findViewById(R.id.img_b);
        ImageButton save = (ImageButton) findViewById(R.id.save);
        ImageButton save_empty = (ImageButton) findViewById(R.id.save_empty);
        ScrollView scrollView = findViewById(R.id.scrollDisc);
        TextView disc = findViewById(R.id.fullDisk);
        TextView name_text = findViewById(R.id.Name);
        TextView author_text = findViewById(R.id.Author);
        picture = findViewById(R.id.profile_image);
        myBdMeneger = new MyBdMeneger(this);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            url = arguments.get("url").toString();
            name = arguments.get("name").toString();
            author = arguments.get("author").toString();
            this.disc = arguments.get("disc").toString();
            likes = arguments.get("likes").toString();
        }
        if(Integer.parseInt(likes) == 0) {
            save.setVisibility(View.INVISIBLE);
        }
        else {
            save.setVisibility(View.VISIBLE);
        }
        LoadPicPicasso(url);
        disc.setText(this.disc);
        name_text.setText(name);
        author_text.setText(author);
        setOnClick(img_b, url);
        save(save_empty, save);
        unsave(save, save_empty);
    }
    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();
    }
    private void setOnClick(final ImageButton btn, final String url_times){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFullPicActivity(url_times);
            }
        });
    }
    public void startFullPicActivity (String url) {
        Intent intent = new Intent(this, FullPicture.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }
    public void LoadPicPicasso(String url){
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(picture);
    }
    private void save(final ImageButton btn, final ImageButton btn2){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBdMeneger.update(url, name, author, disc, "1");
                btn.setVisibility(View.INVISIBLE); //To set visible
                btn2.setVisibility(View.VISIBLE); //To set visible
            }
        });
    }

    private void unsave(final ImageButton btn, final ImageButton btn2){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBdMeneger.update(url, name, author, disc, "0");
                btn.setVisibility(View.INVISIBLE); //To set visible
                btn2.setVisibility(View.VISIBLE); //To set visible
            }
        });
    }

}