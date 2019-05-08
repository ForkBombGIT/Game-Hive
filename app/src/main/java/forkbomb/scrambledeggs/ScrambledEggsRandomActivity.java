package forkbomb.scrambledeggs;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import java.util.ArrayList;
import java.util.Random;

public class ScrambledEggsRandomActivity extends AppCompatActivity {
    //random num gen
    Random rnd = new Random();
    //used to hold the games
    ArrayList<Game> database;

    //possible flavor text
    TextView flavorText;
    String[] flavor = {
            "Find a game, with no frills!",
            "What's gonna hatch from this one?"
    };
    //controls the add object
    InterstitialAd mInterstitialAd;

    //on create event
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrambledeggs_random);

        //sets random flavor text
        flavorText = findViewById(R.id.flavor);
        flavorText.setText(flavor[rnd.nextInt(flavor.length)]);

        //processes gamedatabase
        Bundle gameDatabase = getIntent().getBundleExtra("gameDatabase");
        database = (ArrayList<Game>) gameDatabase.getSerializable("gameDatabase");

        //sets toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, getString(R.string.admob_app_id));
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                mInterstitialAd = new InterstitialAd(ScrambledEggsRandomActivity.this);
                mInterstitialAd.setAdUnitId(getString(R.string.admob_interstitial_id));
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
                mInterstitialAd.setAdListener(new AdListener() {
                    public void onAdLoaded() {
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        }
                    }
                    public void onAdClosed() {
                        activityStart(GameActivity.class);
                    }

                });
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
            intent.putExtra("origin", "random");
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
