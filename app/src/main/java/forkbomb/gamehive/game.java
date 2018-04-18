package forkbomb.gamehive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class game extends AppCompatActivity {
    //title of game
    TextView title, dev, pub, release;
    //data used to display the game
    HashMap<String,String> gameData;
    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //gets bundle
        Bundle game = getIntent().getBundleExtra("randomGame");
        gameData = (HashMap<String,String>) game.getSerializable("randomGame");

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //displays text for game of the day
        title = (TextView) findViewById(R.id.game_title);
        title.setText(gameData.get("title"));

        release = (TextView) findViewById(R.id.game_release);
        release.append(" " + gameData.get("year"));

        dev = (TextView) findViewById(R.id.game_dev);
        dev.append(" " + gameData.get("developer"));

        pub = (TextView) findViewById(R.id.game_pub);
        pub.append(" " + gameData.get("publisher"));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //navbar back button press
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //generates a new random game
    public void generateRandomGame(){

    }

}
