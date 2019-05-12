package forkbomb.scrambledeggs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    //random gen
    Random rand = new Random();
    //gameactivity text fields
    TextView title, description, dev, pub, release, genre, platforms,genreEntries,platformsEntries;
    //cards in view
    CardView card1, card2;
    //data used to display the GameActivity
    ArrayList<Game> gameData;
    //used to tell what started activity
    String origin;
    //ad
    InterstitialAd mInterstitialAd;
    //used for generating random games
    int DB_LENGTH;
    ArrayList<Integer> seenGames;
    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //origin of activity
        origin = getIntent().getStringExtra("origin");

        //gets bundle
        Bundle bundle = getIntent().getBundleExtra("gameDatabase");
        gameData = (ArrayList<Game>) bundle.getSerializable("gameDatabase");

        //checks the origin of the activity
        //if its created from the quiz activity, the fad must be hidden, then the matched game will be displayed
        //otherwise a random game will be generated
        if (origin.equals("quiz")) {
              findViewById(R.id.button).setVisibility(View.GONE);
              displayData(getIntent().getIntExtra("index",-1));
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
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_downicon512, null));

        //init ads
        MobileAds.initialize(this, getString(R.string.admob_app_id));
        mInterstitialAd = new InterstitialAd(GameActivity.this);
        mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("FECC64101B6F54F6DD54A045D1CEBEEC").build());
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdClosed() {
                finish();
            }
        });
    }

    //navbar back button press
    @Override
    public boolean onSupportNavigateUp() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else finish();
        return true;
    }

    //back button press event
    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else finish();
    }

    //displays game data
    public void displayData(int index){
        if (index == -1){
            //gets views by id
            title = (TextView) findViewById(R.id.game_title);
            description = (TextView) findViewById(R.id.game_desc);

            //set title text
            title.setText(R.string.activity_game_noresult_title);
            //set description text
            description.setText(R.string.activity_game_noresult_desc);

            //sets cards invisible
            card1 = findViewById(R.id.cardView1);
            card2 = findViewById(R.id.cardView2);
            card1.setVisibility(View.GONE);
            card2.setVisibility(View.GONE);
        }
        else {
            //finds views
            card1 = findViewById(R.id.cardView1);
            card2 = findViewById(R.id.cardView2);
            title = findViewById(R.id.game_title);
            description = findViewById(R.id.game_desc);
            release = findViewById(R.id.game_release);
            dev = findViewById(R.id.game_dev);
            pub = findViewById(R.id.game_pub);
            genre = findViewById(R.id.game_genre);
            platforms = findViewById(R.id.game_platform);
            genreEntries = findViewById(R.id.genre_entries);
            platformsEntries = findViewById(R.id.platform_entries);

            card1.setVisibility(View.VISIBLE);
            card2.setVisibility(View.VISIBLE);

            //title text
            title.setText(gameData.get(index).title);
            //description text
            description.setText(gameData.get(index).description);
            //release text
            release.setText(HtmlCompat.fromHtml("<b>REL:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            release.append(" " + gameData.get(index).year);
            //developer text
            dev.setText(HtmlCompat.fromHtml("<b>DEV:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            dev.append(" " + gameData.get(index).developer);
            //publisher text
            pub.setText(HtmlCompat.fromHtml("<b>PUB:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            pub.append(" " + gameData.get(index).publisher);
            //genre title text
            genre.setText(HtmlCompat.fromHtml("<b>GENRE:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            //platform title text
            platforms.setText(HtmlCompat.fromHtml("<b>PLATFORM(S):</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
            //genre entry text
            String[] genres = gameData.get(index).genre.split(",");
            String genreText = "";
            for (int i = 0; i < genres.length; i++)
                genreText += genres[i].trim() + ((i == (genres.length - 1)) ? "" : "\n");
            genreEntries.setText(genreText);
            //platform entry text
            String[] platformEntries = gameData.get(index).platforms.split(",");
            String platformText = "";
            for (int i = 0; i < platformEntries.length; i++)
                platformText += platformEntries[i].trim() + ((i == (platformEntries.length - 1)) ? "" : "\n");
            platformsEntries.setText(platformText);
        }
    }

    //generates a new random GameActivity
    public void generateRandomGame(){
        if (gameData.size() > 0) {
            int index = rand.nextInt(gameData.size());
            if (seenGames.size() == DB_LENGTH)
                seenGames.clear();
            else if (!(seenGames.contains(index))) {
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
