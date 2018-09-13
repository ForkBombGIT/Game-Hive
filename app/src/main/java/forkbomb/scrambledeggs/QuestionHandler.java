package forkbomb.scrambledeggs;

import android.util.Log;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class QuestionHandler {
    //holds questions for specific quiz
    private HashSet<Integer> selectedIndexs;
    //holds the questions selected for the quiz
    public Question[] quizQuestions;
    //holds all possible questions
    private Question[] questionList = new Question[]{
            new Question("What does a visually interesting game look like to you?", new String[]{"Realistic","Pixel Art","Cartoon","Minimalist","Abstract"}, "visuals"),
            new Question("What would you like to see in a game’s soundtrack?", new String[]{"Ambient","Cheerful","Intense","Musical"}, "music"),
            new Question("Any regional preferences for developers?", new String[]{"Asian","North American","European","Obscure"}, "region"),
            new Question("What should the game’s tone be?", new String[]{"Casual","Energetic","Suspenseful","Adventurous"}, "tone"),
            new Question("What should the game’s pace be?", new String[]{"Casual","Fast","Slow","Adventurous"}, "pace"),
            new Question("What should the game’s length be?", new String[]{"Short","Medium","Long","Infinite"}, "length"),
            new Question("What dimension do you wanna play in?", new String[]{"2D","3D","2.5D","I don't wanna move around"}, "dimension"),
            new Question("Do you want to see violence?", new String[]{"Not at all","Cartoon violence is okay","A bit of blood won’t hurt","Gratuitous amounts"}, "violence"),
            new Question("Who are you playing as?", new String[]{"Yourself","A Tough Cookie","An Unlikely Hero","A Cutie", "Female Lead", "Male Lead", "Animal Lead","Royalty","Vehicle Lead","Peasant"}, "protag"),
            new Question("What’s the camera showing?", new String[]{"First-person","Behind the body","Overhead","Everything"}, "camera"),
            new Question("What’s the setting?", new String[]{"Fantasy","Horror","Modern","Sci-fi"}, "setting")
    };

    //constructor
    public QuestionHandler(){
        selectedIndexs = new HashSet<Integer>();
        quizQuestions = generateQuestions();
    }

    //generates questions for QuizActivity
    private Question[] generateQuestions(){
        Question[] temp = new Question[7];
        //iterates through the array and adds random questions
        while (selectedIndexs.size() < 7) selectedIndexs.add(new Random(System.nanoTime()).nextInt(questionList.length));

        //adds the questions to the array
        Integer[] arr = selectedIndexs.toArray(new Integer[7]);
        for (int i = 0; i < 7; i++)
            temp[i] = questionList[arr[i]];

        return temp;
    }

    //generates the answers
    public String generateAnswer(int index) {
        String answer = quizQuestions[index].possibleAnswers[new Random(System.nanoTime()).nextInt(quizQuestions[index].possibleAnswers.length)];
        if (!(Arrays.asList(quizQuestions[index].displayedAnswers).contains(answer))) return answer;
        return generateAnswer(index);
    }
}
