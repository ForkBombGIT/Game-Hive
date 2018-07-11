package forkbomb.gamehive;

import java.util.ArrayList;

public class Question {
    public String question;
    public String[] possibleAnswers;
    public ArrayList<String> userAnswers;

    public Question(String q, String[] a){
        this.question = q;
        this.possibleAnswers = a;
    }
}
