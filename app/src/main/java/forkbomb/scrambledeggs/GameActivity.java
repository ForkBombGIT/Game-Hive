package forkbomb.scrambledeggs;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.text.HtmlCompat;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    //random gen
    Random rand = new Random();
    //data used to display the GameActivity
    ArrayList<Game> database;
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
        Bundle bundle = getIntent().getBundleExtra("database");
        database = (ArrayList<Game>) bundle.getSerializable("database");

        //checks the origin of the activity
        //if its created from the quiz activity, the fab must be hidden, then the matched game will be displayed
        //otherwise a random game will be generated
        if (origin.equals("quiz")) {
              findViewById(R.id.button).setVisibility(View.GONE);
              displayData(getIntent().getIntExtra("index",-1));
        } else {
            DB_LENGTH = database.size();
            seenGames = new ArrayList<>();
            generateRandomGame();
        }

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_downicon512, null));

        //sets fab elevation
        ((FloatingActionButton) findViewById(R.id.button)).setCompatElevation(0);

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
        //finds views
        TextView title = findViewById(R.id.game_title);
        TextView description = findViewById(R.id.game_desc);
        TextView release = findViewById(R.id.game_release);
        TextView relet = findViewById(R.id.release_entries);
        TextView dev = findViewById(R.id.game_dev);
        TextView devet = findViewById(R.id.developer_entries);
        TextView pub = findViewById(R.id.game_pub);
        TextView pubet = findViewById(R.id.publisher_entries);
        TextView genre = findViewById(R.id.game_genre);
        TextView platforms = findViewById(R.id.game_platform);
        TextView genreEntries = findViewById(R.id.genre_entries);
        TextView platformsEntries = findViewById(R.id.platform_entries);

        //title text
        title.setText(database.get(index).title);
        //description text
        description.setText(database.get(index).description);
        //release text
        release.setText(HtmlCompat.fromHtml("<b>" + getResources().getString(R.string.label_rel) + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        relet.setText(database.get(index).year.replace("|",", "));
        //developer text
        dev.setText(HtmlCompat.fromHtml("<b>" + getResources().getString(R.string.label_dev) + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        devet.setText(database.get(index).developer.replace("|",", "));
        //publisher text
        pub.setText(HtmlCompat.fromHtml("<b>" + getResources().getString(R.string.label_pub) + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        pubet.setText(database.get(index).publisher.replace("|",", "));
        //genre title text
        genre.setText(HtmlCompat.fromHtml("<b>" + getResources().getString(R.string.label_genre) + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        //platform title text
        platforms.setText(HtmlCompat.fromHtml("<b>" + getResources().getString(R.string.label_platforms) + "</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //genre entry text
        String[] genres = database.get(index).genre.split("\\|");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i].trim() + ((i == (genres.length - 1)) ? "" : "\n");
        genreEntries.setText(genreText);
        //platform entry text
        String[] platformEntries = database.get(index).platforms.split("\\|");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i].trim() + ((i == (platformEntries.length - 1)) ? "" : "\n");
        platformsEntries.setText(platformText);
    }

    //generates a new random GameActivity
    public void generateRandomGame(){
        if (database.size() > 0) {
            int index = rand.nextInt(database.size());
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
