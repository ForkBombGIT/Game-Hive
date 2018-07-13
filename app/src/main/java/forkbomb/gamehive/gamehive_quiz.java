package forkbomb.gamehive;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class gamehive_quiz extends AppCompatActivity {
    ArrayList<HashMap<String,String>> database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamehive_quiz);

        //processes gamedatabase
        Bundle gameDatabase = getIntent().getBundleExtra("gameDatabase");
        database = (ArrayList<HashMap<String,String>>) gameDatabase.getSerializable("gameDatabase");

        //sets up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                activityStart(quiz.class);
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
}
