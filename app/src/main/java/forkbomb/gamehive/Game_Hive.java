package forkbomb.gamehive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
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

public class Game_Hive extends AppCompatActivity {
    //var to hold the top toolbar
    Toolbar toptoolbar;
    //title of game
    TextView title, dev, pub, release, genre, platforms;
    //holds game database
    ArrayList<HashMap<String,String>> gameDatabase;
    private static final String TAG = "Game_Hive";

    //on create function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__hive);

        //sets up toolbar
        toptoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toptoolbar);

        //initializes the array list
        gameDatabase = new ArrayList<HashMap<String,String>>();
        //calls the parser
        parseXML(R.raw.gamedatabase);

        if (gameDatabase.size() > 0) {
            //displays text for game of the day
            title = (TextView) findViewById(R.id.game_title);
            title.setText(gameDatabase.get(1).get("title"));

            dev = (TextView) findViewById(R.id.game_dev);
            dev.append(" " + gameDatabase.get(1).get("developer"));

            pub = (TextView) findViewById(R.id.game_pub);
            pub.append(" " + gameDatabase.get(1).get("publisher"));

            release = (TextView) findViewById(R.id.game_release);
            release.append(" " + gameDatabase.get(1).get("year"));

            // genre = (TextView) findViewById(R.id.game_genre);
            //genre.setText(gameDatabase.get(0).get("genre"));

            // platforms = (TextView) findViewById(R.id.game_platform);
            //platforms.setText(gameDatabase.get(0).get("platforms"));
        }
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
            case R.id.navigation_fav:
                activityStart(gamehive_fav.class);
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

        //iterates through list, printing titles (for testing)
        for (int i = 0; i < gameDatabase.size(); i++){Log.i(TAG,gameDatabase.get(i).get("title"));}
    }
}
