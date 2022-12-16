package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.myapplication.bd.MyBdMeneger;
import com.example.myapplication.bd.MyConstants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WorkWithBD extends AppCompatActivity {
    private MyBdMeneger myBdMeneger;
    private EditText edUrl, edName, edAuthor, edDisc, edYears, edName_Auth, edPhoto, edBiog;
    private TextView author_dialog, name_dialog, disc_dialog;
    private ImageView imageView;
    Dialog dialog;

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

    public void dialog() {
        //dialog = new Dialog(WorkWithBD.this);
        // Передайте ссылку на разметку
        //dialog.setContentView(R.layout.dialog_view);
        // Найдите элемент TextView внутри вашей разметки
        // и установите ему соответствующий текст
    }

    public void onClick(View v)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Проверьте правильность введённых данных ");
        ScrollView view = (ScrollView) getLayoutInflater().inflate(R.layout.dialog_view, null);
        adb.setView(view);

        author_dialog = (TextView) view.findViewById(R.id.author_dialog);
        name_dialog = (TextView) view.findViewById(R.id.name_dialog);
        disc_dialog = (TextView) view.findViewById(R.id.disc_dialog);
        imageView = (ImageView) view.findViewById(R.id.dialog_photo);

        dialog = adb.create();
        dialog.show();

        if (edUrl.getText().toString() != "") {
            LoadPicPicasso(edUrl.getText().toString(), imageView);
        }
        author_dialog.setText(edAuthor.getText());
        name_dialog.setText(edName.getText());
        disc_dialog.setText(edDisc.getText());
    }

    public void onClick_delte(View v)
    {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        adb.setTitle("Удалить картину?");
        ScrollView view = (ScrollView) getLayoutInflater().inflate(R.layout.dialog_delete, null);
        adb.setView(view);

        name_dialog = (TextView) view.findViewById(R.id.name_dialog);
        imageView = (ImageView) view.findViewById(R.id.dialog_photo);

        dialog = adb.create();
        dialog.show();

        List<String> urls = myBdMeneger.getStringByName(edName.getText().toString());
        String[] url = urls.toArray(new String[0]);

        LoadPicPicasso(url[0], imageView);
    }

    public void LoadPicPicasso(String url, ImageView imageView){
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_baseline_close_24)
                .into(imageView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();

    }

    public void SaveDataToDB (View view) {
        myBdMeneger.Insert_toDb(edUrl.getText().toString(), edName.getText().toString(), edAuthor.getText().toString(), edDisc.getText().toString(), "0");
        edUrl.setText("");
        edAuthor.setText("");
        edName.setText("");
        edDisc.setText("");
        dialog.dismiss();
    }

    public void deleteDataFromDb (View view) {
        String name_delete = edName.getText().toString();
        myBdMeneger.DeleteStringFromDb(name_delete);
        edUrl.setText("");
        edAuthor.setText("");
        edName.setText("");
        edDisc.setText("");
        dialog.dismiss();
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
        edName_Auth.setText("");
        edYears.setText("");
        edPhoto.setText("");
        edBiog.setText("");

    }

    public void deleteDataFromDb_Author (View view) {
        String name_delete = edName_Auth.getText().toString();
        myBdMeneger.DeleteStringFromDb_Author(name_delete);
        edName_Auth.setText("");
        edYears.setText("");
        edPhoto.setText("");
        edBiog.setText("");
    }
}