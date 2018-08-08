package forkbomb.scrambledeggs;

import java.util.ArrayList;

public class Question {
    public String question;
    public String[] possibleAnswers;
    public String[] displayedAnswers;
    public ArrayList<String> userAnswers;
    public String tag;

    public Question(String q, String[] a, String t){
        this.question = q;
        this.possibleAnswers = a;
        this.tag = t;

        displayedAnswers = new String[4];
        userAnswers = new ArrayList<>();
    }
}
