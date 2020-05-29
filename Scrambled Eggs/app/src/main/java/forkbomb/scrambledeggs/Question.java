package forkbomb.scrambledeggs;

import java.util.ArrayList;

public class Question {
    public String question;
    public ArrayList<String> possibleAnswers;
    public String[] displayedAnswers;
    public ArrayList<String> userAnswers;
    public String tag;

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
