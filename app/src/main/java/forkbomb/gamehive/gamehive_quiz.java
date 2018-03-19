package forkbomb.gamehive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class gamehive_quiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamehive_quiz);

        //processes gamedatabase
        Bundle gameDatabase = getIntent().getBundleExtra("gameDatabase");
        ArrayList<HashMap<String,String>> database = (ArrayList<HashMap<String,String>>) gameDatabase.getSerializable("gameDatabase");

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

}
