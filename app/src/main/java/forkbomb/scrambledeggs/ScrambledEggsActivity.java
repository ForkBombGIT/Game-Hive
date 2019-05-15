package forkbomb.scrambledeggs;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.TimeZone;

public class ScrambledEggsActivity extends AppCompatActivity {
    //random val gen
    Random rnd = new Random();
    //var to hold the top toolbar
    Toolbar toptoolbar;
    //used to hold date data, to check if its a new day
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    //holds GameActivity database
    ArrayList<Game> gameDatabase;
    //holds length of database
    int DB_LENGTH = 0;
    //holds seen random games
    ArrayList<Integer> seenGames;

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

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.getMenu().findItem(R.id.navigation_quiz).setChecked(false);
        bottomNavigationView.getMenu().findItem(R.id.navigation_gotd).setChecked(true);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);
        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            layoutParams.height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            layoutParams.width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, displayMetrics);
            iconView.setLayoutParams(layoutParams);
        }
        bottomNavigationView.setOnNavigationItemSelectedListener
        (new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.navigation_quiz:
                        selectedFragment = Quiz.newInstance();
                        break;
                    case R.id.navigation_gotd:
                        selectedFragment = GameOfTheDay.newInstance(gameDatabase,getIndex());
                        break;
                    case R.id.navigation_random:
                        selectedFragment = forkbomb.scrambledeggs.Random.newInstance(gameDatabase);
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        //Manually displaying the first fragment - one time only
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, GameOfTheDay.newInstance(gameDatabase,getIndex()));
        transaction.commit();
    }

    //called when the activity starts
    @Override
    protected void onStart(){
        super.onStart();
    }

    //when the activity unpauses
    @Override
    protected void onResume(){
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.eggonlyicon512);
        ActivityManager.TaskDescription td = new ActivityManager.TaskDescription(null, bm, getResources().getColor(R.color.colorPrimary));
        this.setTaskDescription(td);
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


}
