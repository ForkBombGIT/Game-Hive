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
            new Question("Do you want to see violence?",new String[]{"Not at all","Cartoon violence is okay","A bit of blood won't hurt","Gratuitous amounts"},"violence"),
            new Question("What dimension do you wanna move around in?",new String[]{"2D","2.5D","3D","I don't want to move around"},"dimension"),
            new Question("Where's the camera?",new String[]{"First-person","Behind your back","Side view","Top down"},"camera"),
            new Question("What development standard do you prefer?",new String[]{"AAA","Indie","Regular","Freeware"},"standard"),
            new Question("Who do you wanna play with?",new String[]{"Nobody","An individual","A group","The whole world"},"players"),
            new Question("What decade was this game released?",new String[]{"1980","1990","2000","2010"},"decade"),
            new Question("Do you have any accessories you want to play with?",new String[]{"Motion controls","Steering wheel","Flight stick","Musical instrument","Light gun","Arcade stick","Other"},"accessories"),
            new Question("What's the game's difficulty?",new String[]{"Easy","Normal","Hard","Extreme","I get to choose"},"difficulty"),
            new Question("What's the learning curve?",new String[]{"A baby could do this","Easy to learn; hard to master","Takes a bit of practice","Takes a lot of practice","Accustomed for experts"},"curve"),
            new Question("What visually interests you?",new String[]{"Realism","Pixel art","Limited colors","Cartoons","Minimalism","Abstractism","2D graphics","3D graphics","Cuteness","Horror","Anime","Grit","Fantasy","Sci-fi","Other"},"visuals"),
            new Question("What kind of soundtrack do you like?",new String[]{"Retro","Cheerful","Intense","Musical","Orchestral","Epic","Adventurous","Energetic","Rockin'","Funky","Peaceful","Other","None"},"music"),
            new Question("What should the game's tone be?",new String[]{"Casual","Energetic","Suspensful","Adventurous","Exciting","Surreal","Intense","Dark","Fun","Tragic","Goofy","Atmospheric","Other"},"tone"),
            new Question("What should the game's pace be?",new String[]{"Casual","Fast","Slow","Adventurous","Tense","Relaxing"},"pace"),
            new Question("Who are you playing as?",new String[]{"Yourself","A cutie","A tough guy","An unlikely hero","A human","A guy","A girl","A guy","A warrior","A sharpshooter","A solider or agent","An animal","A driver or pilot","Unidentified","A creature","A weirdo","An inanimate object","A god","Other"},"protag"),
            new Question("What's the setting?",new String[]{"Fantasy","Horror","Mythical","Modern","Sci-fi","Surreal","Steampunk","Space","Natural","Futuristic","Post-apocalyptic","Urban","Unidentifiable","Other"},"setting"),
            new Question("What do you wanna set out to do?",new String[]{"Save the world","Solve puzzles","Create stuff","Explore the world","Collect stuff","Become stronger","Perform a song or show","Perform cool tricks","Destroy stuff","Conquer stuff","Fight enemies","Shoot things","Beat stuff up","Fight evildoers","Live my life","Other"},"goal"),
            new Question("Choose your weapon!",new String[]{"Sword","Guns","Blaster","Your fists","Magic","Something unconventional","Nothing","Other"},"weapon"),
            new Question("What time period do you want to be in?",new String[]{"Wild West","Colonial era","Feudal period","Prehistory","The future","Post-apocalyptic","Modern era","Victorian era","Middle Ages","Undetermined","Other"},"period"),
            new Question("How do you like your stories?",new String[]{"Sophisticated","With lots of worldbuilding","With interesting characters","As a journey","Like a book or film","Pretty basic","Short and sweet","Any kind is fine","Almost non-existent","Other"},"story"),
            new Question("What do you customize?",new String[]{"The player","Levels","Vehicles and machines","Weapons","Apparel","Powers and abilities","My life","An army","Creatures","The world","A character","Nothing","House","Other"},"customizing"),
            new Question("Who are you fighting against?",new String[]{"Martial Artists","Cute critters","Warriors from different worlds","Supernatural forces","Aliens","Robots","Military might","Sharpshooters","Ancient warriors","Characters of folklore","Competition","Mother nature","Criminals","The Undead","Nothing","Other"},"enemy"),
            new Question("How should you feel when playing?",new String[]{"Relaxed","Adventurous","Scared","Tense","Excited","Angry","Sad","Happy","Cool","Smart","Weirded out","Focused","Nothing","Other"},"feel"),
            new Question("What kind of communities do you like?",new String[]{"Competitive","Casual","Modding / Hacking","Non-existent","Character builds and customization","Speedrunning","Don't care","Fandom","Role-playing","Other"},"communities"),
            new Question("What's the best achievement(s) in this game?",new String[]{"Getting the highest score or rank","Getting the fastest time","Obtaining all collectibles","Finishing the hardest difficulty","Getting every ending","Getting the best or secret ending","Completing every level","Simply finishing the last level","Other","Nothing"},"achievement"),
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
