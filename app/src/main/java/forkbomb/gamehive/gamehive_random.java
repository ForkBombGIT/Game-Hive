package forkbomb.gamehive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class gamehive_random extends AppCompatActivity {
    //used for random number generation
    Random rand = new Random();
    //used to hold the games
    ArrayList<HashMap<String,String>> database;
    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamehive_random);

        //processes gamedatabase
        Bundle gameDatabase = getIntent().getBundleExtra("gameDatabase");
        database = (ArrayList<HashMap<String,String>>) gameDatabase.getSerializable("gameDatabase");

        //button handler
        Button button = (Button) findViewById(R.id.button);

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //back button press event
    @Override
    public void onBackPressed() {
        //kills the activity
        finish();
        //animation
        overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
    }

    //navbar back button press
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        //animation
        overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
        return true;
    }

    //generates a random game
    public HashMap<String,String> generateRandomGame(){
        return (database.get(rand.nextInt(database.size())));
    }


}
