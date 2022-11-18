package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.myapplication.bd.MyBdMeneger;
import com.example.myapplication.bd.MyConstants;

public class WorkWithBD extends AppCompatActivity {
    private MyBdMeneger myBdMeneger;
    private EditText edUrl, edName, edAuthor, edDisc;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_url);
        myBdMeneger = new MyBdMeneger(this);
        edUrl = findViewById(R.id.edUrl);
        edAuthor = findViewById(R.id.edAuthor);
        edName = findViewById(R.id.edName);
        edDisc = findViewById(R.id.edDisc);
        textView = findViewById(R.id.result);
        textView.setText("");
    }

    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();
        for (String url : myBdMeneger.getALLURLFromDB(MyConstants.URL)) {
            textView.append(url + "\n");
        }
    }

    public void SaveDataToDB (View view) {
        textView.setText("");
        myBdMeneger.Insert_toDb(edUrl.getText().toString(), edName.getText().toString(), edAuthor.getText().toString(), edDisc.getText().toString());
        for (String url : myBdMeneger.getALLURLFromDB(MyConstants.URL)) {
            textView.append(url + "\n");
        }
    }

    public void deleteDataFromDb (View view) {
        textView.setText("");
        String url_delete = edUrl.getText().toString();
        myBdMeneger.DeleteStringFromDb(url_delete);
        for (String url : myBdMeneger.getALLURLFromDB(MyConstants.URL)) {
            textView.append(url + "\n");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBdMeneger.CloseDB();
    }
    public void goHome (View v) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void OpenArchive (View v){
        Intent intent = new Intent(this, Archive.class);
        startActivity(intent);
    }
}