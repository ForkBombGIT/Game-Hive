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

public class GameActivity extends AppCompatActivity {
    Random rand = new Random();
    //title of GameActivity
    TextView title, dev, pub, release, genre, platforms;
    //data used to display the GameActivity
    ArrayList<HashMap<String,String>> gameData;
    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //gets bundle
        Bundle bundle = getIntent().getBundleExtra("gameDatabase");
        gameData = (ArrayList<HashMap<String,String>>) bundle.getSerializable("gameDatabase");
        generateRandomGame();

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //navbar back button press
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void displayData(int index){
        //displays text for GameActivity of the day
        title = (TextView) findViewById(R.id.game_title);
        title.setText(gameData.get(index).get("title"));

        release = (TextView) findViewById(R.id.game_release);
        release.setText("REL:");
        release.append(" " + gameData.get(index).get("year"));

        dev = (TextView) findViewById(R.id.game_dev);
        dev.setText("DEV:");
        dev.append(" " + gameData.get(index).get("developer"));

        pub = (TextView) findViewById(R.id.game_pub);
        pub.setText("PUB:");
        pub.append(" " + gameData.get(index).get("publisher"));

        genre = (TextView) findViewById(R.id.genre_entries);
        String[] genres = gameData.get(index).get("genre").split(",");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i] + "\n";
        genre.setText(genreText);

        platforms = (TextView) findViewById(R.id.platform_entries);
        String[] platformEntries = gameData.get(index).get("platforms").split(",");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i] + "\n";
        platforms.setText(platformText);
    }

    //generates a new random GameActivity
    public void generateRandomGame(){
       if (gameData.size() > 0) displayData(rand.nextInt(gameData.size()));

    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                generateRandomGame();
                break;
            default:
                break;
        }
    }

}
