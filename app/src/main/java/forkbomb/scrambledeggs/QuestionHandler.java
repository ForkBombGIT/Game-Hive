package forkbomb.scrambledeggs;

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

            new Question("Any regional preferences for developers?",new String[]{"Asian","North American","European","Other"},"region"),
            new Question("What should the game's length be?",new String[]{"Short","Medium","Long","Infinite"},"length"),
            new Question("Do you want to see violence?",new String[]{"Not at all","Cartoon violence is okay","A bit of blood won't hurt","Casual violence is fine","Gratuitous amounts"},"violence"),
            new Question("What dimension do you wanna move around in?",new String[]{"2D","2.5D","3D","I don't want to move around"},"dimension"),
            new Question("Where's the camera?",new String[]{"First-person","Behind your back","Side view","Top down"},"camera"),
            new Question("What development standard do you prefer?",new String[]{"AAA","Indie","Regular","Freeware"},"standard"),
            new Question("Who do you wanna play with?",new String[]{"Nobody","A friend","A group","The whole world"},"players"),
            new Question("What decade was this game released?",new String[]{"1980s","1990s","2000s","2010s"},"decade"),
            new Question("Do you have any accessories you want to play with?",new String[]{"Motion controls","Steering wheel","Flight stick","Musical instrument","Light gun","Arcade stick","Other"},"accessories"),
            new Question("What's the game's difficulty?",new String[]{"Easy","Normal","Hard","Extreme","I get to choose"},"difficulty"),
            new Question("What's the learning curve?",new String[]{"A baby could do this","Easy to learn; hard to master","Takes a bit of practice","Takes a lot of practice","Accustomed for experts"},"curve"),
    };

    //constructor
    public QuestionHandler(int length){
        quizLength = length;
        selectedIndexs = new HashSet<Integer>();
        quizQuestions = generateQuestions();
    }

    //generates questions for QuizActivity
    private Question[] generateQuestions(){
        Question[] temp = new Question[quizLength];
        //iterates through the array and adds random questions
        while (selectedIndexs.size() < quizLength) selectedIndexs.add(new Random(System.nanoTime()).nextInt(questionList.length));

        //adds the questions to the array
        Integer[] arr = selectedIndexs.toArray(new Integer[quizLength]);
        for (int i = 0; i < quizLength; i++)
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
