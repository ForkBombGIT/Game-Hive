package forkbomb.scrambledeggs;

import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    //random gen
    Random rand = new Random();
    //title of GameActivity
    TextView title, dev, pub, release, genre, platforms,genreEntries,platformsEntries;
    //data used to display the GameActivity
    ArrayList<Game> gameData;
    //used to tell what started activity
    String origin;
    //used for generating random games
    int DB_LENGTH;
    ArrayList<Integer> seenGames;

    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //origin of acitivity
        origin = getIntent().getStringExtra("origin");

        //gets bundle
        Bundle bundle = getIntent().getBundleExtra("gameDatabase");
        gameData = (ArrayList<Game>) bundle.getSerializable("gameDatabase");

        if (origin.equals("quiz")) {
            findViewById(R.id.button).setVisibility(View.GONE);
            displayData(getIntent().getIntExtra("index",0));

            platformsEntries = (TextView) findViewById(R.id.platform_entries);
            ConstraintLayout mConstraintLayout  = (ConstraintLayout)findViewById(R.id.constraint);
            ConstraintSet set = new ConstraintSet();

            mConstraintLayout.removeView(platformsEntries);
            mConstraintLayout.addView(platformsEntries,0);
            set.clone(mConstraintLayout);
            set.connect(platformsEntries.getId(), ConstraintSet.BOTTOM, mConstraintLayout.getId(), ConstraintSet.BOTTOM, 32);
            set.applyTo(mConstraintLayout);

        } else {
            DB_LENGTH = gameData.size();
            seenGames = new ArrayList<>();
            generateRandomGame();
        }

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_downicon512));
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
        genre.setText(Html.fromHtml("<b>GENRE(S):</b>"));

        platforms = (TextView) findViewById(R.id.game_platform);
        platforms.setText(Html.fromHtml("<b>PLATFORM(S):</b>"));

        genreEntries = (TextView) findViewById(R.id.genre_entries);
        String[] genres = gameData.get(index).genre.split(",");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i].trim() + ((i == (genres.length - 1)) ? "" : "\n");
        genreEntries.setText(genreText);

        platformsEntries = (TextView) findViewById(R.id.platform_entries);
        String[] platformEntries = gameData.get(index).platforms.split(",");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i].trim() + ((i == (platformEntries.length - 1)) ? "" : "\n");
        platformsEntries.setText(platformText);
    }

    //generates a new random GameActivity
    public void generateRandomGame(){
       if (gameData.size() > 0) {
           int index = rand.nextInt(gameData.size());
           if (seenGames.size() >= DB_LENGTH)
               seenGames.clear();
           if (!(seenGames.contains(index))) {
               displayData(index);
               seenGames.add(index);
               return;
           }
           generateRandomGame();
       }
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                if (origin.equals("random"))
                    generateRandomGame();
                break;
            default:
                break;
        }
    }

}
