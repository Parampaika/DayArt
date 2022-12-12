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
    private EditText edUrl, edName, edAuthor, edDisc, edYears, edName_Auth, edPhoto, edBiog;
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

        edName_Auth = findViewById(R.id.edAuthorName);
        edYears = findViewById(R.id.edYearsOfLife);
        edPhoto = findViewById(R.id.PhotoOfAuthor);
        edBiog = findViewById(R.id.biogr);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();
        //for (String name : myBdMeneger.getALLURLFromDB(MyConstants.NAME)) {
           // textView.append(name + "\n");
        //}
    }

    public void SaveDataToDB (View view) {
        myBdMeneger.Insert_toDb(edUrl.getText().toString(), edName.getText().toString(), edAuthor.getText().toString(), edDisc.getText().toString(), "0");
    }

    public void deleteDataFromDb (View view) {
        String name_delete = edName.getText().toString();
        myBdMeneger.DeleteStringFromDb(name_delete);
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

    public void SaveDataToDB_Author (View view) {
        myBdMeneger.Insert_toAuthors(edName_Auth.getText().toString(), edYears.getText().toString(), edPhoto.getText().toString(), edBiog.getText().toString());
    }

    public void deleteDataFromDb_Author (View view) {
        String name_delete = edName_Auth.getText().toString();
        myBdMeneger.DeleteStringFromDb_Author(name_delete);
    }
}