package forkbomb.gamehive;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class XmlReader {
    public static ArrayList<HashMap<String,String>> readXml(Context context, int xml){
        ArrayList<HashMap<String,String>> gameDatabase = new ArrayList<>();
        //takes the file as input
        InputStream stream = context.getResources().openRawResource(xml);
        //reads the input file
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        //holds the line being read
        String readLine = null;
        //holds the data being added to the array list
        String data = "";
        //holds the current index of the array list
        int index = 0;
        try{
            //loops until the reaching a null line
            while ((readLine = reader.readLine()) != null){
                //removes whitespace
                readLine = readLine.trim();
                //checks if line is the start of a new tag
                if (readLine.startsWith("<") && !readLine.startsWith("</")){
                    if (readLine.startsWith("<game>")) gameDatabase.add(new HashMap<String, String>());
                    data = "";
                    continue;
                }
                //checks if the line is not a tag
                if (!readLine.startsWith("<")){data+=readLine; continue;}
                //checks if line is an end tags
                if (readLine.startsWith("</")){
                    //checks if the end of a GameActivity tag, and if so increment index
                    if (readLine.startsWith("</game>")) {index++;}
                    //otherwise, add the data to the map
                    else if (readLine.startsWith("</title>")) {gameDatabase.get(index).put("title",data);}
                    else if (readLine.startsWith("</year>")) {gameDatabase.get(index).put("year",data);}
                    else if (readLine.startsWith("</publisher>")) {gameDatabase.get(index).put("publisher",data);}
                    else if (readLine.startsWith("</developer>")) {gameDatabase.get(index).put("developer",data);}
                    else if (readLine.startsWith("</genre>")) {gameDatabase.get(index).put("genre",data);}
                    else if (readLine.startsWith("</platforms>")) {gameDatabase.get(index).put("platforms",data);}
                    else if (readLine.startsWith("</region>")) {gameDatabase.get(index).put("region",data);}
                    else if (readLine.startsWith("</rating>")) {gameDatabase.get(index).put("rating",data);}
                    else if (readLine.startsWith("</multiplayer>")) {gameDatabase.get(index).put("multiplayer",data);}
                    else if (readLine.startsWith("</visuals>")) {gameDatabase.get(index).put("visuals",data);}
                    else if (readLine.startsWith("</music>")) {gameDatabase.get(index).put("music",data);}
                    else if (readLine.startsWith("</tone>")) {gameDatabase.get(index).put("tone",data);}
                    else if (readLine.startsWith("</pace>")) {gameDatabase.get(index).put("pace",data);}
                    else if (readLine.startsWith("</length>")) {gameDatabase.get(index).put("length",data);}
                    else if (readLine.startsWith("</violence>")) {gameDatabase.get(index).put("violence",data);}
                    else if (readLine.startsWith("</protag>")) {gameDatabase.get(index).put("protag",data);}
                    else if (readLine.startsWith("</camera>")) {gameDatabase.get(index).put("camera",data);}
                    else if (readLine.startsWith("</setting>")) {gameDatabase.get(index).put("setting",data);}
                }
            }
            //closes the reader
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gameDatabase;
    }
}
