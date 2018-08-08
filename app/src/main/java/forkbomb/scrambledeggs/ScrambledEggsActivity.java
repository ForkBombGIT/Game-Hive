package forkbomb.scrambledeggs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class ScrambledEggsActivity extends AppCompatActivity {
    //random val gen
    Random rnd = new Random();
    //var to hold the top toolbar
    Toolbar toptoolbar;
    //title of GameActivity
    TextView title, dev, pub, release, genre, platforms;
    //used to hold date data, to check if its a new day
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //holds GameActivity database
    ArrayList<Game> gameDatabase;
    private static final String TAG = "ScrambledEggsActivity";

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

        checkForNewDate();
        displayGame(getIndex());
    }

    //called when the activity starts
    @Override
    protected void onStart(){
        checkForNewDate();
        displayGame(getIndex());
        super.onStart();
    }

    //when the activity unpauses
    @Override
    protected void onResume(){
        checkForNewDate();
        displayGame(getIndex());
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
                activityStart(ScrambledEggsRandomActivity.class);
                return true;
            case R.id.navigation_quiz:
                activityStart(ScrambledEggsQuizActivity.class);
                return true;
            default:
                return false;
        }
    }

    //gets the correct index
    public int getIndex(){
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (preferences.contains("index")){return preferences.getInt("index",0);}
        return 2;
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
                //to-do: check for year change
                if (year == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR)) {
                    if (month == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)) {
                        if ((day < Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH)))
                            index = rnd.nextInt(gameDatabase.size());
                    }
                    else if (month < Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH))
                        index = rnd.nextInt(gameDatabase.size());
                }
                else if (year < Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR)) index = rnd.nextInt(gameDatabase.size());
            }
        }
        //adds current index to preferences
        editor.putInt("index",index);
        editor.commit();
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

    //displays GameActivity
    public void displayGame(int index){
        //displays text for GameActivity of the day
        title = (TextView) findViewById(R.id.game_title);
        title.setText(gameDatabase.get(index).title);

        release = (TextView) findViewById(R.id.game_release);
        release.setText("REL:");
        release.append(" " + gameDatabase.get(index).year);

        dev = (TextView) findViewById(R.id.game_dev);
        dev.setText("DEV:");
        dev.append(" " + gameDatabase.get(index).developer);

        pub = (TextView) findViewById(R.id.game_pub);
        pub.setText("PUB:");
        pub.append(" " + gameDatabase.get(index).publisher);

        genre = (TextView) findViewById(R.id.genre_entries);
        String[] genres = gameDatabase.get(index).genre.split(",");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i] + "\n";
        genre.setText(genreText);

        platforms = (TextView) findViewById(R.id.platform_entries);
        String[] platformEntries = gameDatabase.get(index).platforms.split(",");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i] + "\n";
        platforms.setText(platformText);
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
    }
}
