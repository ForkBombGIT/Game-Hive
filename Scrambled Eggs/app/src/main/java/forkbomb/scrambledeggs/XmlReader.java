package forkbomb.scrambledeggs;

import android.content.Context;
import android.util.Log;
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
            String tagName = parser.getName();
            if (eventType == XmlPullParser.START_TAG) {
                //searches through tag names and adds to game obj
                switch (tagName) {
                    case ("title"):
                        game.title = parser.nextText().trim().trim();
                        break;
                    case ("description"):
                        game.description = parser.nextText().trim().trim();
                        break;
                    case ("year"):
                        game.year = parser.nextText().trim().trim();
                        break;
                    case ("publisher"):
                        game.publisher = parser.nextText().trim().trim();
                        break;
                    case ("developer"):
                        game.developer = parser.nextText().trim().trim();
                        break;
                    case ("genre"):
                        game.genre = parser.nextText().trim();
                        break;
                    case ("platforms"):
                        game.platforms = parser.nextText().trim();
                        break;
                    case ("region"):
                        game.region = parser.nextText().trim();
                        break;
                    case ("visuals"):
                        game.visuals = parser.nextText().trim();
                        break;
                    case ("music"):
                        game.music = parser.nextText().trim();
                        break;
                    case ("tone"):
                        game.tone = parser.nextText().trim();
                        break;
                    case ("pace"):
                        game.pace = parser.nextText().trim();
                        break;
                    case ("length"):
                        game.length = parser.nextText().trim();
                        break;
                    case ("violence"):
                        game.violence = parser.nextText().trim();
                        break;
                    case ("protag"):
                        game.protag = parser.nextText().trim();
                        break;
                    case ("dimension"):
                        game.dimension = parser.nextText().trim();
                        break;
                    case ("camera"):
                        game.camera = parser.nextText().trim();
                        break;
                    case ("setting"):
                        game.setting = parser.nextText().trim();
                        break;
                    case ("standard"):
                        game.standard = parser.nextText().trim();
                        break;
                    case ("players"):
                        game.players = parser.nextText().trim();
                        break;
                    case ("goal"):
                        game.goal = parser.nextText().trim();
                        break;
                    case ("weapon"):
                        game.weapon = parser.nextText().trim();
                        break;
                    case ("story"):
                        game.story = parser.nextText().trim();
                        break;
                    case ("decade"):
                        game.decade = parser.nextText().trim();
                        break;
                    case ("accessories"):
                        game.accessories = parser.nextText().trim();
                        break;
                    case ("customizing"):
                        game.customizing = parser.nextText().trim();
                        break;
                    case ("enemy"):
                        game.enemy = parser.nextText().trim();
                        break;
                    case ("difficulty"):
                        game.difficulty = parser.nextText().trim();
                        break;
                    case ("curve"):
                        game.curve = parser.nextText().trim();
                        break;
                    case ("feel"):
                        game.feel = parser.nextText().trim();
                        break;
                    case ("communities"):
                        game.communities = parser.nextText().trim();
                        break;
                    case ("achievement"):
                        game.achievement = parser.nextText().trim();
                        break;

                }

            } else if (eventType == XmlPullParser.END_TAG) {
                if (tagName.equals("game")){
                    games.add(game);
                    game = new Game();
                }
            }

            eventType = parser.next();
        }

        return games;
    }
}
