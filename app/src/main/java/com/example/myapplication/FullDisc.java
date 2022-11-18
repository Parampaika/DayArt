package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class FullDisc extends AppCompatActivity {
    private String url = "", name = "", author = "", disc = "";
    ImageView picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_disc);

        ScrollView scrollView = findViewById(R.id.scrollDisc);
        TextView disc = findViewById(R.id.fullDisk);
        TextView name_text = findViewById(R.id.Name);
        TextView author_text = findViewById(R.id.Author);
        picture = findViewById(R.id.picture_pic);

        Bundle arguments = getIntent().getExtras();

        if(arguments!=null){
            url = arguments.get("url").toString();
            name = arguments.get("name").toString();
            author = arguments.get("author").toString();
            this.disc = arguments.get("disc").toString();
        }
        LoadPicPicasso(url);
        disc.setText(this.disc);
        name_text.setText(name);
        author_text.setText(author);
        disc.setTextSize(26);
    }

    public void LoadPicPicasso(String url){
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .into(picture);
    }

}