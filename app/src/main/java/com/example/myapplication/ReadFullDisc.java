package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.bd.MyBdMeneger;
import com.example.myapplication.databinding.ActivityReadFullDiscBinding;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.example.myapplication.databinding.ActivityReadFullDiscBinding;
import com.squareup.picasso.Picasso;

public class ReadFullDisc extends AppCompatActivity {

    private ActivityReadFullDiscBinding binding;
    private String url = "", name = "", author = "", disc = "", likes = "";
    private Boolean choose;
    private MyBdMeneger myBdMeneger;
    ImageButton picture, save, save_empty;
    TextView discriptoin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityReadFullDiscBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;

        save = (ImageButton) findViewById(R.id.save_disk);
        save_empty = (ImageButton) findViewById(R.id.save_empty_disk);
        picture = (ImageButton) findViewById(R.id.picture);
        discriptoin = findViewById(R.id.textAboutPic);
        myBdMeneger = new MyBdMeneger(this);
        Bundle arguments = getIntent().getExtras();

        if(arguments!=null) {
            url = arguments.get("url").toString();
            name = arguments.get("name").toString();
            author = arguments.get("author").toString();
            disc = arguments.get("disc").toString();
            likes = arguments.get("likes").toString();
            choose = (Boolean) arguments.get("pic_or_auth");
        }
        if (choose){
            if(Integer.parseInt(likes) == 0) {
                save.setVisibility(View.INVISIBLE);
            }
            else {
                save.setVisibility(View.VISIBLE);
            }
        }
        else{
            save.setVisibility(View.INVISIBLE);
            save_empty.setVisibility(View.INVISIBLE);
        }
        discriptoin.setText(disc);
        LoadPicPicasso(url);
        setOnClick(picture, url);
        toolBarLayout.setTitle(name);
        save(save_empty, save);
        unsave(save, save_empty);

    }
    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_info_menu, menu);
        getSupportActionBar().setTitle(name);
        return true;
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
                Log.d("TEST", "OK");
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
                Log.d("TEST", "OK");
                myBdMeneger.update(url, name, author, disc, "0");
                btn.setVisibility(View.INVISIBLE); //To set visible
                btn2.setVisibility(View.VISIBLE); //To set visible
            }
        });
    }
}