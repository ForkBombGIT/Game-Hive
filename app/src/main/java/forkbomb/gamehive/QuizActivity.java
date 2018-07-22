package forkbomb.gamehive;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;
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
    Button button;

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
        button = (Button) findViewById(R.id.button);
        answers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!questionHandler.quizQuestions[questionNumber].userAnswers.contains(answers.getItemAtPosition(position).toString())) {
                    view.setBackgroundColor(Color.parseColor("#CCCCCC"));
                    questionHandler.quizQuestions[questionNumber].userAnswers.add(answers.getItemAtPosition(position).toString());
                }
                else {
                    view.setBackgroundColor(Color.TRANSPARENT);
                    questionHandler.quizQuestions[questionNumber].userAnswers.remove(answers.getItemAtPosition(position).toString());
                }

                button.setText((questionHandler.quizQuestions[questionNumber].userAnswers.size() > 0) ? "NEXT" : "SKIP");
            }
        });

        for (int i = 0; i < 4; i++)
            questionHandler.quizQuestions[questionNumber].displayedAnswers[i] = questionHandler.generateAnswer(questionNumber);

        handleQuiz();
    }

    //controls QuizActivity
    public void handleQuiz(){
        question.setText(questionHandler.quizQuestions[questionNumber].question);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionHandler.quizQuestions[questionNumber].displayedAnswers);
        answers.setAdapter(adapter);
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                questionHandler.quizQuestions[questionNumber].displayedAnswers = new String[4];
                if (questionNumber < 6)
                    questionNumber++;
                else questionNumber = 0;
                for (int i = 0; i < 4; i++)
                    questionHandler.quizQuestions[questionNumber].displayedAnswers[i] = questionHandler.generateAnswer(questionNumber);

                button.setText((questionHandler.quizQuestions[questionNumber].userAnswers.size() > 0) ? "NEXT" : "SKIP");
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
