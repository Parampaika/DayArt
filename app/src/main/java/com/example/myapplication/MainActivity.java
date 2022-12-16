package com.example.myapplication;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.loader.content.AsyncTaskLoader;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.bd.MyBdMeneger;
import com.example.myapplication.bd.MyConstants;
import com.example.myapplication.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private MyBdMeneger myBdMeneger;
    ImageButton imageView;
    ImageView photo_of_author;
    TextView text_about_pic, name_text, author_text, name_of_author, author_years_life;
    int count = 0;
    int MyDBSize, MyDBSize_auth;
    Button readFull, readFullAuthor;
    ImageButton save, save_empty;
    private String url, name, author, disk, likes;

    class Museum extends AsyncTask<URL, Void, String> {
        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response = NetworkUtils.getResponseFromUrl(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            String new_url = null;
            try {
                JSONObject jsonObject = new JSONObject(response);
                //JSONObject objectID = jsonObject.getJSONObject("objectID");

                new_url = jsonObject.getString("primaryImage");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            url = new_url;
        }
    }



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        myBdMeneger = new MyBdMeneger(this);
        imageView = (ImageButton) findViewById(R.id.imageView2);
        name_text = findViewById(R.id.Name);
        author_text = findViewById(R.id.Author);
        text_about_pic = findViewById(R.id.textOfPic);
        readFull = findViewById(R.id.readFullDisc);

        save = (ImageButton) findViewById(R.id.save_disk);
        save_empty = (ImageButton) findViewById(R.id.save_empty_disk);

        readFullAuthor = findViewById(R.id.readFullBiog);
        name_of_author = findViewById(R.id.Author_Name);
        author_years_life = findViewById(R.id.Author_years_life);
        photo_of_author = findViewById(R.id.photo_of_author);

        Log.d("PATH", "Path: " + myBdMeneger.getDbPath(this, MyConstants.DB_NAME).toString());

        //"https://collectionapi.metmuseum.org/public/collection/v1/objects/1";

        URL generatedUrl = NetworkUtils.generateUrl("100");

        new Museum().execute(generatedUrl);

    }


    @Override
    protected void onResume() {
        super.onResume();
        myBdMeneger.Open_db();
        init();
        LoadDailyPic();



    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        myBdMeneger.CloseDB();
    }
    public void init(){
        MyDBSize = myBdMeneger.getSizeBD(MyConstants.TABLE_NAME);
        MyDBSize_auth = myBdMeneger.getSizeBD(MyConstants.TABLE_NAME_2);
        if (MyDBSize < 1) {
            myBdMeneger.Insert_toDb("https://upload.wikimedia.org/wikipedia/commons/thumb/7/7a/Ilya_Repin_-_Barge_Haulers_on_the_Volga_-_Google_Art_Project.jpg/675px-Ilya_Repin_-_Barge_Haulers_on_the_Volga_-_Google_Art_Project.jpg",
                    "Бурлаки на Волге", "Илья Ефимович Репин", "«Бурлаки́ на Во́лге» — картина русского художника Ильи Репина (1844—1930), над которой он работал с 1870 по 1873 год. Хранится в Государственном Русском музее в Санкт-Петербурге (инв. Ж-4056). Размер — 131,5 × 281 см[1][2][3] (по другим данным — 132 × 283 см)[4][5]. Изображает ватагу бурлаков во время транспортировки барки[6]. Замысел картины возник у Репина в конце 1860-х годов, во время прогулки на пароходе по Неве, которую он совершал вместе с Константином Савицким[7][8].\n" +
                            "\n" +
                            "Для того чтобы запечатлеть настоящих бурлаков, в 1870 году Репин отправился в поездку на Волгу вместе с художниками Фёдором Васильевым и Евгением Макаровым, а также своим младшим братом Василием[9]. \n После возвращения из поездки привезённые Ильёй Репиным работы были показаны великому князю Владимиру Александровичу, который заказал художнику большую картину по одному из эскизов[10]. \nВесной 1871 года первая версия большого полотна «Бурлаки на Волге» экспонировалась на ежегодной конкурсной выставке Общества поощрения художников в Санкт-Петербурге[10][11], где она получила первую премию среди произведений жанровой живописи[11]. После выставки Репин продолжил работу над полотном и внёс в него ряд существенных изменений[12][13]. В 1872 году художник совершил ещё одну поездку по Волге, во время которой создал ряд этюдов, впоследствии использованных для завершения полотна «Бурлаки на Волге». Кроме того, он написал другой вариант картины, известный под названием «Бурлаки, идущие вброд»[14].", "1");
            myBdMeneger.Insert_toDb("https://upload.wikimedia.org/wikipedia/commons/thumb/4/4a/Hovhannes_Aivazovsky_-_The_Ninth_Wave_-_Google_Art_Project.jpg/760px-Hovhannes_Aivazovsky_-_The_Ninth_Wave_-_Google_Art_Project.jpg",
                    "Девя́тый вал", "Иван Константинович Айвазовский", "«Девятый вал» — одна из самых знаменитых картин русского художника-мариниста Ивана Айвазовского, хранится в Русском музее в Санкт-Петербурге. Написана в 1850 году. \n Живописец изображает море после очень сильного ночного шторма и людей, потерпевших кораблекрушение. Лучи солнца освещают громадные волны.", "1");
        }
        if (MyDBSize_auth < 1) {
            myBdMeneger.Insert_toAuthors("Илья Ефимович Репин", "1844 - 1930", "https://upload.wikimedia.org/wikipedia/commons/thumb/8/86/Ilya_Repin_%281909%29.jpg/390px-Ilya_Repin_%281909%29.jpg", "Илья́ Ефи́мович Ре́пин (24 июля [5 августа] 1844, Чугуев, Российская империя — 29 сентября 1930, Куоккала, Финляндия)[1] — русский живописец[2][3][4], педагог, профессор, действительный член Императорской Академии художеств.\n" +
                    "\n" +
                    "Уже с самого начала своего творческого пути, с 1870-х годов, Репин стал одной из ключевых фигур русского реализма. Художнику удалось решить задачу отражения в живописном произведении всего разнообразия окружающей жизни. В своём творчестве он сумел охватить все стороны современности, затронуть темы, волнующие общественность, живо реагировал на злобу дня. Репинскому художественному языку была свойственна пластичность, он воспринимал различные стилистические направления от испанских и голландских художников XVII века до Александра Иванова и современных французских импрессионистов.\n" +
                    "\n" +
                    "Расцвет творчества Репина пришёлся на 1880-е годы. Он создаёт галерею портретов современников, работает как исторический художник и мастер бытовых сцен. В области исторической живописи его привлекала возможность раскрыть эмоциональную выразительность предложенной ситуации. Стихией художника была современность, и, даже создавая картины на темы легендарного прошлого, он оставался мастером животрепещущего настоящего, сокращая дистанцию между зрителем и героями своих произведений. По мнению искусствоведа В. В. Стасова, творчество Репина — «энциклопедия пореформенной России». Последние 30 лет жизни Репин провёл в своём имении Пенаты в Куоккале, в окрестностях Санкт-Петербурга. Из них 13 лет в вынужденной эмиграции, после передачи этой территории Финляндии. Он продолжал работать, хотя уже не так интенсивно, как прежде. В последние годы он обратился к библейским сюжетам. В Куоккале Репин написал мемуары, ряд его очерков вошёл в книгу воспоминаний «Далёкое близкое»..");
            myBdMeneger.Insert_toAuthors("Иван Константинович Айвазовский", "1817 - 1900", "https://upload.wikimedia.org/wikipedia/commons/thumb/c/c6/Aivazovsky_-_Self-portrait_1874.jpg/405px-Aivazovsky_-_Self-portrait_1874.jpg", "Ива́н Константи́нович Айвазо́вский (арм. Հովհաննես Այվազյան, Ованнес Айвазян; 17 [29] июля 1817 года, Феодосия, Таврическая губерния, Российская империя — 19 апреля [2 мая] 1900 года, там же) — русский живописец-маринист армянского происхождения, коллекционер, меценат.\n" +
                    "\n" +
                    "Живописец Главного Морского штаба, действительный тайный советник, академик и почётный член Императорской Академии художеств в Санкт-Петербурге, почётный член Академий художеств в Амстердаме, Риме, Париже, Флоренции и Штутгарте.\n" +
                    "\n" +
                    "Наиболее выдающийся художник армянского происхождения XIX века[5][6]. Брат армянского историка и архиепископа Армянской Апостольской Церкви Габриэла Айвазовского.\n" +
                    "\n" +
                    "Будучи художником с мировой известностью, Иван Константинович Айвазовский предпочитал жить и работать на своей родине — в Крыму. Айвазовский наиболее известен своими морскими пейзажами, которые составляют больше половины его работ. Художник считается одним из величайших маринистов всех времён.");
        }
    }

    public void WorkWithBD(View v) {
        Intent intent = new Intent(this, WorkWithBD.class);
        startActivity(intent);
    }

    public void OpenFavorites(View v) {
        Intent intent = new Intent(this, favorites.class);
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
        }, 0, 20000);
    }

    private void timerTick() {
        this.runOnUiThread(doTask);
    }

    private Runnable doTask = new Runnable() {
        public void run() {
            MyDBSize = myBdMeneger.getSizeBD(MyConstants.TABLE_NAME);
            count++;
            if(count > MyDBSize){
                count = 1;
            }
            List<String> info = myBdMeneger.getOneStringFromDB(count);
            //url = info.get(0);
            name = info.get(1);
            author = info.get(2);
            disk = info.get(3);
            likes = info.get(4);
            LoadPicPicasso(url, imageView);
            setOnClick(readFull, url, name, author, disk, likes, true);

            setOnClick(imageView, url);
            name_text.setText(name);
            author_text.setText(author);
            //text_about_pic.setText(disk);

            setAuthor(author);

            if(Integer.parseInt(likes) == 0) {
                save.setVisibility(View.INVISIBLE);
                save_empty.setVisibility(View.VISIBLE);
            }
            else {
                save.setVisibility(View.VISIBLE);
                save_empty.setVisibility(View.INVISIBLE);
            }
            save(save_empty, save);
            unsave(save, save_empty);

        }
    };
    private void setAuthor (String author) {
        List<String> authors = myBdMeneger.getOneStringFromDB_AUTHORS(author);
        if (authors.get(0) == "") {
            name_of_author.setText("К сожалению, данного автора нет в нашей базе данных");
            author_years_life.setText("");
        }
        else {
            String author_name = authors.get(0);
            String years = authors.get(1);
            String photo = authors.get(2);
            String biogr = authors.get(3);
            setOnClick(readFullAuthor, photo, author_name, years, biogr, "0", false);
            LoadPicPicasso(photo, photo_of_author);
            name_of_author.setText(author_name);
            author_years_life.setText(years);
        }
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

    private void setOnClick(final Button btn, final String url_times, final String name, final String author, final String disc, final String likes, boolean choose){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFullDiscActivity(url_times, name, author, disc, likes, choose);
            }
        });
    }
    public void startFullDiscActivity (String url, String name, String author, String disc, String likes, boolean choose) {
        Intent intent = new Intent(this, ReadFullDisc.class);
        intent.putExtra("url", url);
        intent.putExtra("name", name);
        intent.putExtra("author", author );
        intent.putExtra("disc", disc);
        intent.putExtra("likes", likes);
        intent.putExtra("pic_or_auth", choose);
        startActivity(intent);
    }

    public void LoadPicPicasso(String url, ImageView imageView){
        String size = Integer.toString(myBdMeneger.getSizeBD(MyConstants.TABLE_NAME));
        Picasso.with(this)
                .load(url)
                .placeholder(R.drawable.loading)
                .error(R.drawable.ic_baseline_close_24)
                .into(imageView);
    }

    private void save(final ImageButton btn, final ImageButton btn2){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBdMeneger.update(url, name, author, disk, "1");
                btn.setVisibility(View.INVISIBLE); //To set visible
                btn2.setVisibility(View.VISIBLE); //To set visible
            }
        });
    }

    private void unsave(final ImageButton btn, final ImageButton btn2){
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myBdMeneger.update(url, name, author, disk, "0");
                btn.setVisibility(View.INVISIBLE); //To set visible
                btn2.setVisibility(View.VISIBLE); //To set visible
            }
        });
    }

}

