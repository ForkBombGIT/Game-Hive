package forkbomb.scrambledeggs;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class QuestionHandler {
    //holds questions for specific quiz
    private HashSet<Integer> selectedIndexs;
    //holds the questions selected for the quiz
    public Question[] quizQuestions;
    public int quizLength;
    //holds all possible questions
    private Question[] questionList = new Question[]{
            new Question("Any regional preferences for developers?","region"),
            new Question("What should the game's length be?","length"),
            new Question("Do you want to see violence?","violence"),
            new Question("What dimension do you wanna move around in?","dimension"),
            new Question("Where's the camera?","camera"),
            new Question("What development standard do you prefer?","standard"),
            new Question("Who do you wanna play with?","players"),
            new Question("What decade was this game released?","decade"),
            new Question("Do you have any accessories you want to play with?","accessories"),
            new Question("What's the game's difficulty?","difficulty"),
            new Question("What's the learning curve?","curve"),
            new Question("What visually interests you?","visuals"),
            new Question("What kind of soundtrack do you like?","music"),
            new Question("What should the game's tone be?","tone"),
            new Question("What should the game's pace be?","pace"),
            new Question("Who are you playing as?","protag"),
            new Question("What's the setting?","setting"),
            new Question("What do you wanna set out to do?","goal"),
            new Question("Choose your weapon!","weapon"),
            new Question("What time period do you want to be in?","period"),
            new Question("How do you like your stories?","story"),
            new Question("What do you customize?","customizing"),
            new Question("Who are you fighting against?","enemy"),
            new Question("How should you feel when playing?","feel"),
            new Question("What kind of communities do you like?","communities"),
            new Question("What's the best achievement(s) in this game?","achievement"),
    };

    //constructor
    public QuestionHandler(int length, ArrayList<Game> db){
        quizLength = length;
        selectedIndexs = new HashSet<Integer>();
        quizQuestions = generateQuestions(db);
    }

    //generates questions for QuizActivity
    private Question[] generateQuestions(ArrayList<Game> db){
        Question[] temp = new Question[quizLength];
        //iterates through the array and adds random questions
        while (selectedIndexs.size() < quizLength) selectedIndexs.add(new Random(System.nanoTime()).nextInt(questionList.length));

        //adds the questions to the array
        Integer[] arr = selectedIndexs.toArray(new Integer[quizLength]);
        for (int i = 0; i < quizLength; i++) {
            temp[i] = questionList[arr[i]];
            Log.i("questionhandle",temp[i].tag);
            for (int j = 0; j < db.size(); j++) {
                String[] result = db.get(j).get(temp[i].tag).split(",");
                for (int k = 0; k < result.length; k++) {
                    temp[i].addAnswer(result[k].trim());
                }
            }
        }
        return temp;
    }

    //generates the answers
    public String generateAnswer(int index) {
        String answer = quizQuestions[index].possibleAnswers.get(new Random(System.nanoTime()).nextInt(quizQuestions[index].possibleAnswers.size()));
        if (!(Arrays.asList(quizQuestions[index].displayedAnswers).contains(answer))) return answer;
        return generateAnswer(index);
    }
}
