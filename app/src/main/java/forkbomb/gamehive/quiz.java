package forkbomb.gamehive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class quiz extends AppCompatActivity {
    //controls what questions will be used for the quiz
    private QuestionHandler questionHandler;
    //controls what question the user is on
    private int questionNumber = 0;

    //view elements
    //changes the text for the question
    TextView question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //quiz setup
        questionHandler = new QuestionHandler();
        question = (TextView) findViewById(R.id.tv_question);
        handleQuiz();
    }

    //controls quiz
    public void handleQuiz(){
        question.setText(questionHandler.quizQuestions[questionNumber].question);
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                questionNumber++;
                handleQuiz();
                break;
            default:
                break;
        }
    }

    //navbar back button press
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    //back button press event
    @Override
    public void onBackPressed() {
        //kills the activity
        finish();
    }

}
