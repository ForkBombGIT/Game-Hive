package forkbomb.scrambledeggs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import android.widget.SeekBar.OnSeekBarChangeListener;

public class ScrambledEggsQuizActivity extends AppCompatActivity {
    ArrayList<Game> database;
    int quizLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrambledeggs_quiz);

        //processes gamedatabase
        Bundle gameDatabase = getIntent().getBundleExtra("gameDatabase");
        database = (ArrayList<Game>) gameDatabase.getSerializable("gameDatabase");

        //sets up seek bar
        SeekBar seekBar = findViewById(R.id.wdg_seek);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(10);
        quizLength = 2;
        //final TextView seekVal = findViewById(R.id.txt_seekval);

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                float thumbPos = ((float) progress/seekBar.getMax()) * (float) seekBar.getWidth()/2;
                //seekVal.setText(String.valueOf(progress + 2));
                quizLength = progress+2;
            }
        });

        //sets up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //back button press event
    @Override
    public void onBackPressed() {
        //kills the activity
        finish();
        //animation
       // overridePendingTransition(R.anim.activity_slide_in, R.anim.activity_slide_out);
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
            case R.id.btn_start:
                activityStart(QuizActivity.class);
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
            intent.putExtra("length",quizLength);
            //starts the new activity
            startActivity(intent);
        }
    }
}
