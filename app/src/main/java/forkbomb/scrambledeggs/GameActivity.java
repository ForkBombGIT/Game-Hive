package forkbomb.scrambledeggs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    Random rand = new Random();
    //title of GameActivity
    TextView title, dev, pub, release, genre, platforms,genreEntries,platformsEntries;
    //data used to display the GameActivity
    ArrayList<Game> gameData;
    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //gets bundle
        Bundle bundle = getIntent().getBundleExtra("gameDatabase");
        gameData = (ArrayList<Game>) bundle.getSerializable("gameDatabase");
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
        title.setText(gameData.get(index).title);

        release = (TextView) findViewById(R.id.game_release);
        release.setText(Html.fromHtml("<b>REL:</b>"));
        release.append(" " + gameData.get(index).year);

        dev = (TextView) findViewById(R.id.game_dev);
        dev.setText(Html.fromHtml("<b>DEV:</b>"));
        dev.append(" " + gameData.get(index).developer);

        pub = (TextView) findViewById(R.id.game_pub);
        pub.setText(Html.fromHtml("<b>PUB:</b>"));
        pub.append(" " + gameData.get(index).publisher);

        genre = (TextView) findViewById(R.id.game_genre);
        genre.setText(Html.fromHtml("<b>GENRE:</b>"));

        platforms = (TextView) findViewById(R.id.game_platform);
        platforms.setText(Html.fromHtml("<b>PLATFORM(S):</b>"));

        genreEntries = (TextView) findViewById(R.id.genre_entries);
        String[] genres = gameData.get(index).genre.split(",");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i] + ((i == (genres.length - 1)) ? "" : "\n");
        genreEntries.setText(genreText);

        platformsEntries = (TextView) findViewById(R.id.platform_entries);
        String[] platformEntries = gameData.get(index).platforms.split(",");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i] + ((i == (platformEntries.length - 1)) ? "" : "\n");
        platformsEntries.setText(platformText);
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
