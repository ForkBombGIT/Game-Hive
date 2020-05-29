//developed by: Eros Di Pede
//developement began: 2018/07/09
using System;
using System.IO;

public class QuestionWriter {
        static void Main(string[] args){
            //starting text
            Console.WriteLine("Starting up Question Writer");
            Console.WriteLine("if you must list, please comma seperate");
            //creates file
            if (!File.Exists("./questions.txt"))
                File.WriteAllText("./questions.txt", "");
            
            //controls whether or not to continue accepting input
            var loop = true;
            while(loop){
                var final = "" + System.Environment.NewLine;
                Console.Write("Question:");
                final += "new Question(\"";
                final += Console.ReadLine();
                final += "\",new String[]{";
                var answers = true;
                var counter = 0;
                while (answers){
                    Console.WriteLine("Answer " + (counter++) + ": ");
                    final += '"';
                    final += Console.ReadLine();
                    //checks if user wants to input another game
                    Console.WriteLine("Another? (yes/no)");
                    answers = ((Console.ReadLine().ToLower()) != "no");
                final += (answers) ? "\"," : "\"},";
                }
                Console.WriteLine("Tag: ");
                final += "\"";
                final += Console.ReadLine();
                final += "\")," + System.Environment.NewLine;
                
                Console.WriteLine(final);
                File.AppendAllText("./questions.txt", final);
                
                //checks if user wants to input another game
                Console.WriteLine("Another? (yes/no)");
                loop = ((Console.ReadLine().ToLower()) != "no");
            }
        }
};