package forkbomb.scrambledeggs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.text.HtmlCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class ScrambledEggsActivity extends AppCompatActivity {
    //controls whether or not the app is transitioning between activities
    Boolean switchingActivity;
    //random val gen
    Random rnd = new Random();
    //var to hold the top toolbar
    Toolbar toptoolbar;
    //title of GameActivity
    TextView title, description, dev, pub, release, genreEntries, platformsEntries,genre,platforms;
    //cards in view
    CardView card1, card2;
    //used to hold date data, to check if its a new day
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //holds GameActivity database
    ArrayList<Game> gameDatabase;
    //holds length of database
    int DB_LENGTH = 0;
    //holds seen random games
    ArrayList<Integer> seenGames;
    //controls the add object
    InterstitialAd mInterstitialAd;

    //on create function, when the app is initially created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrambledeggs);

        //sets up toolbar
        toptoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toptoolbar);

        //initializes the array list
        gameDatabase = XmlReader.readXml(ScrambledEggsActivity.this, R.raw.gamedatabase);
        DB_LENGTH = gameDatabase.size();
        seenGames = new ArrayList<>();

        //checks if there is a new day
        checkForNewDate();
        //displays game of the day
        displayData(getIndex());
        switchingActivity = false;
    }

    //called when the activity starts
    @Override
    protected void onStart(){
        switchingActivity = false;
        super.onStart();
    }

    //when the activity unpauses
    @Override
    protected void onResume(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.eggonlyicon512);
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, bm, getResources().getColor(R.color.colorPrimary));
        this.setTaskDescription(td);
        switchingActivity = false;
        super.onResume();
    }

    //called when the activity stops
    @Override
    protected void onStop(){
        getDate(getIndex());
        super.onStop();
    }

    //called when the switching to another activity
    @Override
    protected void onPause(){
        getDate(getIndex());
        super.onPause();
    }

    //called when activity is closed
    @Override
    protected void onDestroy(){
        getDate(getIndex());
        super.onDestroy();
    }

    //sets up drop down menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //checks for drop down menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_random:
                if (!switchingActivity) {
                    activityStart(ScrambledEggsRandomActivity.class);
                    switchingActivity = true;
                }
                return true;
            case R.id.navigation_quiz:
                if (!switchingActivity) {
                    activityStart(ScrambledEggsQuizActivity.class);
                    switchingActivity = true;
                }
                return true;
            default:
                return false;
        }
    }

    //checks to see if the app is ran on a new day
    public void checkForNewDate(){
        //index used to display GameActivity
        int index = getIndex();
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        editor = preferences.edit();
        if (gameDatabase != null) {
            if (preferences.contains("day") && preferences.contains("month") && preferences.contains("year")) {
                int day = preferences.getInt("day",-1);
                int month = preferences.getInt("month",-1);
                int year = preferences.getInt("year",-1);
                //checks if theres a difference in days
                if (year == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR)) {
                    if (month == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)) {
                        if ((day != Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH)))
                            index = generateRandomGame();
                    }
                    else index = generateRandomGame();
                }
                else index = generateRandomGame();
            }
        }
        //adds current index to preferences
        editor.putInt("index",index);
        editor.commit();
    }

    //generates a new random game
    public int generateRandomGame(){
        if (gameDatabase.size() > 0) {
            int index = rnd.nextInt(gameDatabase.size());
            if (seenGames.size() == DB_LENGTH)
                seenGames.clear();
            else if (!(seenGames.contains(index))) {
                seenGames.add(index);
                return index;
            }
            generateRandomGame();
        }
        return 0;
    }

    //gets the correct index
    public int getIndex(){
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        return preferences.getInt("index",0);
    }

    //gets the current date
    public void getDate(int i){
        //stores the current day in preferences
        editor = preferences.edit();
        int day = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH);
        int month = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH);
        int year = Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR);

        editor.putInt("day",day);
        editor.putInt("month",month);
        editor.putInt("year",year);
        editor.putInt("index",i);

        editor.commit();
    }

    //displays game data
    public void displayData(int index){
        //finds views
        title = findViewById(R.id.game_title);
        description = findViewById(R.id.game_desc);
        release = findViewById(R.id.game_release);
        dev = findViewById(R.id.game_dev);
        pub = findViewById(R.id.game_pub);
        genre = findViewById(R.id.game_genre);
        platforms = findViewById(R.id.game_platform);
        genreEntries = findViewById(R.id.genre_entries);
        platformsEntries = findViewById(R.id.platform_entries);

        //title text
        title.setText(gameDatabase.get(index).title);
        //description text
        description.setText(gameDatabase.get(index).description);
        //release text
        release.setText(HtmlCompat.fromHtml("<b>REL:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        release.append(" " + gameDatabase.get(index).year);
        //developer text
        dev.setText(HtmlCompat.fromHtml("<b>DEV:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        dev.append(" " + gameDatabase.get(index).developer);
        //publisher text
        pub.setText(HtmlCompat.fromHtml("<b>PUB:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        pub.append(" " + gameDatabase.get(index).publisher);
        //genre title text
        genre.setText(HtmlCompat.fromHtml("<b>GENRE:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        //platform title text
        platforms.setText(HtmlCompat.fromHtml("<b>PLATFORM(S):</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        //genre entry text
        String[] genres = gameDatabase.get(index).genre.split(",");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i].trim() + ((i == (genres.length - 1)) ? "" : "\n");
        genreEntries.setText(genreText);
        //platform entry text
        String[] platformEntries = gameDatabase.get(index).platforms.split(",");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i].trim() + ((i == (platformEntries.length - 1)) ? "" : "\n");
        platformsEntries.setText(platformText);

    }

    //starts an activity
    public void activityStart(Class t){
        //creates a new intent
        Intent intent = new Intent(this,t);
        //creates a bundle to send
        Bundle bundle = new Bundle();
        bundle.putSerializable("gameDatabase",gameDatabase);
        intent.putExtra("gameDatabase",bundle);
        //starts the new activity
        startActivity(intent);
        //overrides default animation
        overridePendingTransition(R.anim.activity_slide_in_home, R.anim.activity_slide_out_home);
    }
}
