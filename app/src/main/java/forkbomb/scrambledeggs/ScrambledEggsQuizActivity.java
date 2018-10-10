package forkbomb.scrambledeggs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Random;

import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ScrambledEggsQuizActivity extends AppCompatActivity {
    //random number gen
    Random rnd = new Random();
    //games
    ArrayList<Game> database;
    //used to display eggs
    android.support.v7.widget.GridLayout imgGrid;
    ImageView[] eggs = new ImageView[12];
    //holds length of quiz
    int quizLength;

    //possible flavor text
    TextView flavorText;
    String[] flavor = {
            "How many eggs ya want?",
            "How many eggs ya want?",
            "How many eggs ya want?"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrambledeggs_quiz);

        //processes gamedatabase
        Bundle gameDatabase = getIntent().getBundleExtra("gameDatabase");
        database = (ArrayList<Game>) gameDatabase.getSerializable("gameDatabase");

        //sets random flavor text
        flavorText = findViewById(R.id.flavor);
        flavorText.setText(flavor[rnd.nextInt(flavor.length)]);

        //sets imageview array
        imgGrid = findViewById(R.id.imggrid);
        for (int i = 0; i < imgGrid.getChildCount(); i++){
            eggs[i] = (ImageView) imgGrid.getChildAt(i);
            eggs[i].setColorFilter(getResources().getColor(R.color.itemBorder));
        }

        //sets up seek bar
        SeekBar seekBar = findViewById(R.id.wdg_seek);
        seekBar.setProgress(0);
        seekBar.incrementProgressBy(1);
        seekBar.setMax(11);
        quizLength = 2;

        for (int i = 0; i < quizLength;i++){
            eggs[i].setColorFilter(Color.TRANSPARENT);
        }

        seekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                //seekVal.setText(String.valueOf(progress + 2));
                quizLength = progress + 1;
                for (int i = 0; i < quizLength;i++){
                    eggs[i].setColorFilter(Color.TRANSPARENT);
                }
                for (int i = quizLength; i < 12; i++){
                    eggs[i].setColorFilter(getResources().getColor(R.color.itemBorder));
                }
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
