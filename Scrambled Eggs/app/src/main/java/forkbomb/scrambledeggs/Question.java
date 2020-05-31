package forkbomb.scrambledeggs;

import java.util.ArrayList;

public class Question {
    public final String question;
    public final ArrayList<String> possibleAnswers;
    public String[] displayedAnswers;
    public final ArrayList<String> userAnswers;
    public final String tag;

    public Question(String q, String t){
        this.question = q;
        this.tag = t;

        displayedAnswers = new String[4];
        possibleAnswers = new ArrayList<>();
        userAnswers = new ArrayList<>();
    }

    //adds a possible answer if not in the array
    public void addAnswer(String a){
        if (!possibleAnswers.contains(a))
            possibleAnswers.add(a);
    }
}
