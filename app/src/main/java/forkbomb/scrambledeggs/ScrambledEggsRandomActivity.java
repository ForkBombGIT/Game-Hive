package forkbomb.scrambledeggs;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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
            "Pick a game, but no peeking!",
            "Find a game, with no frills!",
            "What's gonna hatch from this one?"
    };

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
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                activityStart(GameActivity.class);
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
