package forkbomb.scrambledeggs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {
    Random rnd = new Random();
    //controls what questions will be used for the QuizActivity
    private QuestionHandler questionHandler;
    //controls what question the user is on
    private int questionNumber = 0;
    //used to hold the games
    ArrayList<Game> database;
    //view elements
    //changes the text for the question
    TextView question;
    ListView answers;
    int answerSize;
    //adapter for filling listview
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //toolbar setup
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_downicon512, null));

        //load database
        Bundle gameDatabase = getIntent().getBundleExtra("gameDatabase");
        database = (ArrayList<Game>) gameDatabase.getSerializable("gameDatabase");

        //used for handling questions, answers, and user selections
        questionHandler = new QuestionHandler(getIntent().getIntExtra("length", 2),database);

        //sets up activity elements
        question = findViewById(R.id.tv_question);
        answers = findViewById(R.id.answers);
        answerSize = (questionHandler.quizQuestions[questionNumber].possibleAnswers.size() < 4) ? questionHandler.quizQuestions[questionNumber].possibleAnswers.size() : 4;
        questionHandler.quizQuestions[questionNumber].displayedAnswers = new String[answerSize];

        //event handling for when item in list view is clicked
        answers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //checks if question is not selected
                if (!questionHandler.quizQuestions[questionNumber].userAnswers.contains(answers.getItemAtPosition(position).toString())) {
                    //switches color, adds to array
                    view.setBackground(getDrawable(R.drawable.listview_entries_selected));
                    ((TextView)view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorWhite));
                    ((Button)getWindow().getDecorView().findViewById(R.id.button)).setText(R.string.activity_quiz_button_next);
                    questionHandler.quizQuestions[questionNumber].userAnswers.add(answers.getItemAtPosition(position).toString());
                }
                //checks if question is selected
                else {
                    //switches color, removes from array
                    view.setBackgroundColor(Color.TRANSPARENT);
                    view.setBackground(getDrawable(R.drawable.listview_entries_default));
                    ((TextView)view).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorBlack));
                    questionHandler.quizQuestions[questionNumber].userAnswers.remove(answers.getItemAtPosition(position).toString());
                    if (questionHandler.quizQuestions[questionNumber].userAnswers.size() == 0)
                        ((Button)getWindow().getDecorView().findViewById(R.id.button)).setText(R.string.activity_quiz_button_refresh);
                }
            }
        });
        generateAnswers(answerSize);
        handleQuiz();
    }

    public void generateAnswers(int size){
        for (int i = 0; i < size; i++)
            questionHandler.quizQuestions[questionNumber].displayedAnswers[i] = questionHandler.generateAnswer(questionNumber);
    }

    //controls QuizActivity
    public void handleQuiz(){
        question.setText(questionHandler.quizQuestions[questionNumber].question);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, questionHandler.quizQuestions[questionNumber].displayedAnswers){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                float fontSize = androidx.core.math.MathUtils .clamp(((TextView)findViewById(R.id.tv_question)).getTextSize()/4,24,44);
                textView.setTextSize(fontSize);
                textView.setTextAlignment(convertView.TEXT_ALIGNMENT_CENTER);
                textView.setBackground(getContext().getDrawable(R.drawable.listview_entries_default));
                return textView;
            }
        };
        answers.setAdapter(adapter);
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                if (questionHandler.quizQuestions[questionNumber].userAnswers.size() > 0) {
                    //increments question number
                    if (questionNumber < questionHandler.quizLength - 1) {
                        //resets displayed answers array
                        answerSize = (questionHandler.quizQuestions[++questionNumber].possibleAnswers.size() < 4) ? questionHandler.quizQuestions[questionNumber].possibleAnswers.size() : 4;
                        questionHandler.quizQuestions[questionNumber].displayedAnswers = new String[answerSize];

                        //generates new answers
                        for (int i = 0; i < answerSize; i++)
                            questionHandler.quizQuestions[questionNumber].displayedAnswers[i] = questionHandler.generateAnswer(questionNumber);

                        ((Button)getWindow().getDecorView().findViewById(R.id.button)).setText(R.string.activity_quiz_button_refresh);
                    }
                    else {
                        Intent intent = new Intent(this, GameActivity.class);
                        //creates a bundle to send
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("gameDatabase",database);
                        intent.putExtra("gameDatabase",bundle);
                        intent.putExtra("index", generateGame());
                        intent.putExtra("origin","quiz");
                        //starts the new activity
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    //resets displayed answers array
                    questionHandler.quizQuestions[questionNumber].displayedAnswers = new String[answerSize];
                    for (int i = 0; i < answerSize; i++) {
                        questionHandler.quizQuestions[questionNumber].displayedAnswers[i] = questionHandler.generateAnswer(questionNumber);
                    }
                }
                handleQuiz();
                break;
            default:
                break;
        }
    }

    public int generateGame(){
        ArrayList<Integer> matches = new ArrayList<>();
        int highest = 0;
        for (int i = 0; i < database.size(); i++){
            int counter = 0;
            for (int j = 0; j < questionHandler.quizLength; j++){
                for (int k = 0; k < questionHandler.quizQuestions[j].userAnswers.size(); k++){
                    String tag = (database.get(i).get(questionHandler.quizQuestions[j].tag) != null) ? database.get(i).get(questionHandler.quizQuestions[j].tag) : "";
                    if ((tag).contains(questionHandler.quizQuestions[j].userAnswers.get(k))) counter++;
                }
            }
            if (counter > highest){
                highest = counter;
                matches.clear();
                matches.add(i);
            }
            else if (counter == highest) matches.add(i);
        }
        if (highest == 0) return -1;
        if (matches.size() > 0) return (matches.get(rnd.nextInt(matches.size())));
        return -1;
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
