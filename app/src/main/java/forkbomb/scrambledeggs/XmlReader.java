package forkbomb.scrambledeggs;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class XmlReader {
    public static ArrayList<Game> readXml(Context context, int xml) {
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            //takes the file as input
            InputStream stream = context.getResources().openRawResource(xml);
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);

            return processParser(parser);

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static ArrayList<Game> processParser(XmlPullParser parser) throws IOException, XmlPullParserException{
        ArrayList<Game> games = new ArrayList<>();
        int eventType = parser.getEventType();
        Game game = new Game();

        //iterates until end of document
        while (eventType != XmlPullParser.END_DOCUMENT){
            String tagName;
            if (eventType == XmlPullParser.START_TAG) {
                tagName = parser.getName();
                //searches through tag names and adds to game obj
                switch (tagName) {
                    case ("game"):
                        games.add(game);
                        game = new Game();
                        break;
                    case ("title"):
                        game.title = parser.nextText();
                        break;
                    case ("description"):
                        game.description = parser.nextText();
                        break;
                    case ("year"):
                        game.year = parser.nextText();
                        break;
                    case ("publisher"):
                        game.publisher = parser.nextText();
                        break;
                    case ("developer"):
                        game.developer = parser.nextText();
                        break;
                    case ("genre"):
                        game.genre = parser.nextText();
                        break;
                    case ("platforms"):
                        game.platforms = parser.nextText();
                        break;
                    case ("regin"):
                        game.region = parser.nextText();
                        break;
                    case ("rating"):
                        game.rating = parser.nextText();
                        break;
                    case ("visuals"):
                        game.visuals = parser.nextText();
                        break;
                    case ("music"):
                        game.music = parser.nextText();
                        break;
                    case ("tone"):
                        game.tone = parser.nextText();
                        break;
                    case ("pace"):
                        game.pace = parser.nextText();
                        break;
                    case ("length"):
                        game.length = parser.nextText();
                        break;
                    case ("decade"):
                        game.decade = parser.nextText();
                        break;
                    case ("violence"):
                        game.violence = parser.nextText();
                        break;
                    case ("protag"):
                        game.protag = parser.nextText();
                        break;
                    case ("dimension"):
                        game.dimension = parser.nextText();
                        break;
                    case ("camera"):
                        game.camera = parser.nextText();
                        break;
                    case ("setting"):
                        game.setting = parser.nextText();
                        break;
                    case ("standard"):
                        game.standard = parser.nextText();
                        break;
                    case ("players"):
                        game.players = parser.nextText();
                        break;
                    case ("goal"):
                        game.goal = parser.nextText();
                        break;
                    case ("weapon"):
                        game.weapon = parser.nextText();
                        break;
                    case ("period"):
                        game.period = parser.nextText();
                        break;
                    case ("story"):
                        game.story = parser.nextText();
                        break;
                    case ("accessories"):
                        game.accessories = parser.nextText();
                        break;
                    case ("customizing"):
                        game.customizing = parser.nextText();
                        break;
                    case ("enemy"):
                        game.enemy = parser.nextText();
                        break;
                    case ("difficulty"):
                        game.difficulty = parser.nextText();
                        break;
                    case ("curve"):
                        game.curve = parser.nextText();
                        break;
                    case ("feel"):
                        game.feel = parser.nextText();
                        break;
                    case ("communities"):
                        game.communities = parser.nextText();
                        break;
                    case ("achievement"):
                        game.achievement = parser.nextText();
                        break;

                }

            }
        }

        return games;
    }
}
