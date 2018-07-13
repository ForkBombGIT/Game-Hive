package forkbomb.gamehive;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class gamehive extends AppCompatActivity {
    //random
    Random rnd = new Random();
    //var to hold the top toolbar
    Toolbar toptoolbar;
    //title of game
    TextView title, dev, pub, release, genre, platforms;
    //used to hold date data, to check if its a new day
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //holds game database
    ArrayList<HashMap<String,String>> gameDatabase;

    private static final String TAG = "Game_Hive";

    //on create function, when the app is initially created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG,"on create");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamehive);

        //sets up toolbar
        toptoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toptoolbar);

        //initializes the array list
        gameDatabase = new ArrayList<HashMap<String,String>>();
        //calls the parser
        parseXML(R.raw.gamedatabase);

        checkForNewDate();
        displayGame(getIndex());
    }

    //called when the activity starts
    @Override
    protected void onStart(){
        Log.i(TAG,"on start");
        checkForNewDate();
        displayGame(getIndex());
        super.onStart();
    }

    //when the activity unpauses
    @Override
    protected void onResume(){
        Log.i(TAG,"on resume");
        checkForNewDate();
        displayGame(getIndex());
        super.onResume();
    }

    //called when the activity stops
    @Override
    protected void onStop(){
        Log.i(TAG,"on stop");
        getDate(getIndex());
        super.onStop();
    }

    //called when the switching to another activity
    @Override
    protected void onPause(){
        Log.i(TAG,"on pause");
        getDate(getIndex());
        super.onPause();
    }

    //called when activity is closed
    @Override
    protected void onDestroy(){
        Log.i(TAG,"on destroy");
        getDate(getIndex());
        super.onDestroy();
    }

    //gets the correct index
    public int getIndex(){
        preferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        if (preferences.contains("index")){return preferences.getInt("index",0);}
        return 2;
    }

    //checks to see if the app is ran on a new day
    public void checkForNewDate(){
        //index used to display game
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
                if (year == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR))
                    if (month == Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH))
                        if ((day < Calendar.getInstance(TimeZone.getDefault()).get(Calendar.DAY_OF_MONTH)))
                            index = rnd.nextInt(gameDatabase.size());
                        else if (month < Calendar.getInstance(TimeZone.getDefault()).get(Calendar.MONTH)) index = rnd.nextInt() % gameDatabase.size();
                        else if (year < Calendar.getInstance(TimeZone.getDefault()).get(Calendar.YEAR)) index = rnd.nextInt() % gameDatabase.size();
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

    //displays game
    public void displayGame(int index){
        //displays text for game of the day
        title = (TextView) findViewById(R.id.game_title);
        title.setText(gameDatabase.get(index).get("title"));

        release = (TextView) findViewById(R.id.game_release);
        release.setText("REL:");
        release.append(" " + gameDatabase.get(index).get("year"));

        dev = (TextView) findViewById(R.id.game_dev);
        dev.setText("DEV:");
        dev.append(" " + gameDatabase.get(index).get("developer"));

        pub = (TextView) findViewById(R.id.game_pub);
        pub.setText("PUB:");
        pub.append(" " + gameDatabase.get(index).get("publisher"));

        genre = (TextView) findViewById(R.id.genre_entries);
        String[] genres = gameDatabase.get(index).get("genre").split(",");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i] + "\n";
        genre.setText(genreText);

        platforms = (TextView) findViewById(R.id.platform_entries);
        String[] platformEntries = gameDatabase.get(index).get("platforms").split(",");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i] + "\n";
        platforms.setText(platformText);
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
                activityStart(gamehive_random.class);
                return true;
            case R.id.navigation_quiz:
                activityStart(gamehive_quiz.class);
                return true;
            default:
                return false;
        }
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
        overridePendingTransition(R.anim.activity_slide_in_home,R.anim.activity_slide_out_home);
    }

    //parses xml
    public void parseXML(int xml){
        //takes the file as input
        InputStream stream = getResources().openRawResource(xml);
        //reads the input file
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        //holds the line being read
        String readLine = null;
        //holds the data being added to the array list
        String data = "";
        //holds the current index of the array list
        int index = 0;
        try{
            //loops until the reaching a null line
            while ((readLine = reader.readLine()) != null){
                //removes whitespace
                readLine = readLine.trim();
                //checks if line is the start of a new tag
                if (readLine.startsWith("<") && !readLine.startsWith("</")){
                    if (readLine.startsWith("<game>")) gameDatabase.add(new HashMap<String, String>());
                    data = "";
                    continue;
                }
                //checks if the line is not a tag
                if (!readLine.startsWith("<")){data+=readLine; continue;}
                //checks if line is an end tags
                if (readLine.startsWith("</")){
                    //checks if the end of a game tag, and if so increment index
                    if (readLine.startsWith("</game>")) {index++;}
                    //otherwise, add the data to the map
                    else if (readLine.startsWith("</title>")) {gameDatabase.get(index).put("title",data);}
                    else if (readLine.startsWith("</year>")) {gameDatabase.get(index).put("year",data);}
                    else if (readLine.startsWith("</publisher>")) {gameDatabase.get(index).put("publisher",data);}
                    else if (readLine.startsWith("</developer>")) {gameDatabase.get(index).put("developer",data);}
                    else if (readLine.startsWith("</genre>")) {gameDatabase.get(index).put("genre",data);}
                    else if (readLine.startsWith("</platforms>")) {gameDatabase.get(index).put("platforms",data);}
                    else if (readLine.startsWith("</region>")) {gameDatabase.get(index).put("region",data);}
                    else if (readLine.startsWith("</rating>")) {gameDatabase.get(index).put("rating",data);}
                    else if (readLine.startsWith("</multiplayer>")) {gameDatabase.get(index).put("multiplayer",data);}
                }
            }
            //closes the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
