package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.myapplication.bd.MyBdMeneger;
import com.example.myapplication.bd.MyConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

//Потом перепишу с использованием глайда,
// этот вариант временный, чтобы проверить работоспособность
public class favorites extends ListActivity {
    private MyBdMeneger myBdMeneger;
    private favorites.PictureAdapter mAdapter;
    private String[] url = {}, name = {}, author = {}, disc = {}, like = {};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myBdMeneger = new MyBdMeneger(this);
        mAdapter = new PictureAdapter(this);
        setListAdapter((ListAdapter) mAdapter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();
        List<String> urls = myBdMeneger.getFavorites(MyConstants.URL);
        url = urls.toArray(new String[0]);
        List<String> names = myBdMeneger.getFavorites(MyConstants.NAME);
        name = names.toArray(new String[0]);
        List<String> authors = myBdMeneger.getFavorites(MyConstants.AUTHOR);
        author = authors.toArray(new String[0]);
        List<String> discs = myBdMeneger.getFavorites(MyConstants.DISC);
        disc = discs.toArray(new String[0]);
        List<String> likes = myBdMeneger.getFavorites(MyConstants.LIKE);
        like = likes.toArray(new String[0]);
    }
    private class PictureAdapter extends BaseAdapter {
        private LayoutInflater mLayoutInflater;

        PictureAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return url.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = mLayoutInflater.inflate(R.layout.activity_favorites, null);

            Log.d("TEST", "OK");
            ImageButton image = (ImageButton) convertView.findViewById(R.id.image_view_icon);
            Button openInfo = (Button) convertView.findViewById(R.id.openInfo);
            Picasso.with(favorites.this)
                    .load(url[position])
                    .placeholder(R.drawable.loading)
                    .into(image);


            TextView signTextView = (TextView) convertView.findViewById(R.id.name);
            signTextView.setText(name[position]);

            TextView dateTextView = (TextView) convertView.findViewById(R.id.author);
            dateTextView.setText(author[position]);

            setOnClick_new(openInfo, url[position], name[position], author[position], disc[position], like[position]);
            setOnClick_new_image(image, url[position], name[position], author[position], disc[position], like[position]);

            return convertView;
        }


    }

    private void setOnClick_new(final Button btn, final String url_times, final String name, final String author, final String disc, final String like){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo(url_times, name, author, disc, like);
            }
        });
    }
    private void setOnClick_new_image(final ImageButton btn, final String url_times, final String name, final String author, final String disc, final String like){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfo(url_times, name, author, disc, like);
            }
        });
    }
    public void openInfo(final String url, final String name, final String author, final String disc, final String like){
        Intent intent = new Intent(this, FullDisc.class);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        intent.putExtra("author", author );
        intent.putExtra("disc", disc);
        intent.putExtra("likes", like);
        startActivity(intent);
    }
    public void goHome (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void WorkWithBD(View v) {
        Intent intent = new Intent(this, WorkWithBD.class);
        startActivity(intent);
    }

}