package forkbomb.gamehive;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class QuizActivity extends AppCompatActivity {
    //controls what questions will be used for the QuizActivity
    private QuestionHandler questionHandler;
    //controls what question the user is on
    private int questionNumber = 0;

    //view elements
    //changes the text for the question
    TextView question;
    ListView answers;

    //adapter for filling listview
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //used for handling questions, answers, and user selections
        questionHandler = new QuestionHandler();

        //sets up activity elements
        question = (TextView) findViewById(R.id.tv_question);
        answers = (ListView) findViewById(R.id.answers);
        answers.setOnItemClickListener(null);

        handleQuiz();
    }

    //controls QuizActivity
    public void handleQuiz(){
        question.setText(questionHandler.quizQuestions[questionNumber].question);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionHandler.quizQuestions[questionNumber].possibleAnswers);
        answers.setAdapter(adapter);
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
