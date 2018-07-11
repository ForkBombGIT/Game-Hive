package forkbomb.gamehive;

public class QuestionHandler {
    //holds questions for specific quiz
    public Question[] quizQuestions;
    //holds all possible questions
    private Question[] questionList = new Question[]{
            new Question("What does a visually interesting game look like to you?", new String[]{"Realistic","Pixel Art","Cartoon","Minimalist"})
    };

    //constructor
    public QuestionHandler(){
        quizQuestions = generateQuestions();
    }

    //generates questions for quiz
    private Question[] generateQuestions(){
        return null;
    }
}
