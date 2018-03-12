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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class Game_Hive extends AppCompatActivity {
    //var to hold the top toolbar
    Toolbar toptoolbar;
    //holds game database
    List<HashMap<String,String>> gameDataBase;
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
        gameDataBase = new ArrayList<HashMap<String,String>>();
        //calls the parser
        parseXML(R.raw.gamedatabase);
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
        Intent intent = new Intent(this,t);
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
                    if (readLine.startsWith("<game>")) gameDataBase.add(new HashMap<String, String>());
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
                    else if (readLine.startsWith("</title>")) {gameDataBase.get(index).put("title",data);}
                    else if (readLine.startsWith("</year>")) {gameDataBase.get(index).put("year",data);}
                    else if (readLine.startsWith("</publisher>")) {gameDataBase.get(index).put("publisher",data);}
                    else if (readLine.startsWith("</developer>")) {gameDataBase.get(index).put("developer",data);}
                    else if (readLine.startsWith("</genre>")) {gameDataBase.get(index).put("genre",data);}
                    else if (readLine.startsWith("</platforms>")) {gameDataBase.get(index).put("platforms",data);}
                    else if (readLine.startsWith("</region>")) {gameDataBase.get(index).put("region",data);}
                    else if (readLine.startsWith("</rating>")) {gameDataBase.get(index).put("rating",data);}
                    else if (readLine.startsWith("</multiplayer>")) {gameDataBase.get(index).put("multiplayer",data);}
                }
            }
            //closes the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //iterates through list, printing titles (for testing)
        for (int i = 0; i < gameDataBase.size(); i++){Log.i(TAG,gameDataBase.get(i).get("title"));}
    }
}
