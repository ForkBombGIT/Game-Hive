package forkbomb.gamehive;

import android.content.Intent;
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

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                activityStart(game.class);
                break;
            default:
                break;
        }
    }

    //starts an activity
    public void activityStart(Class t){
        if (database.size() > 0) {
            //creates a new intent
            Intent intent = new Intent(this, t);
            //creates a bundle to send
            Bundle bundle = new Bundle();
            bundle.putSerializable("gameDatabase", database);
            intent.putExtra("gameDatabase", bundle);
            //starts the new activity
            startActivity(intent);
        }
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

}
