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

public class game extends AppCompatActivity {
    //used to display the game
    HashMap<String,String> game;
    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //navbar back button press
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

}
