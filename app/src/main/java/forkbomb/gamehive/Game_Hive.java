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
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Game_Hive extends AppCompatActivity {
    //var to hold the top toolbar
    Toolbar toptoolbar;
    private static final String TAG = "Game_Hive";

    //on create function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__hive);

        //sets up toolbar
        toptoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toptoolbar);

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
        overridePendingTransition(R.anim.activity_slide_in_home,R.anim.activity_slide_out_home);
    }

    //parses xml
    public void parseXML(int xml){
        InputStream stream = getResources().openRawResource(R.raw.gamedatabase);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String readLine = null;
        try{
            while ((readLine = reader.readLine()) != null){
                Log.i(TAG,readLine);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
