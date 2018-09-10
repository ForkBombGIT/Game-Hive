package forkbomb.scrambledeggs;
import java.io.Serializable;

public class Game implements Serializable {
    public String title;
    public String year;
    public String publisher;
    public String developer;
    public String genre;
    public String platforms;
    public String region;
    public String rating;
    public String multiplayer;
    public String visuals;
    public String music;
    public String tone;
    public String pace;
    public String length;
    public String violence;
    public String protag;
    public String camera;
    public String setting;
    public String dimension;

    public Game(){ }

    public String get(String val){
        switch(val) {
            case ("title"):
                return title;
            case ("year"):
                return year;
            case ("publisher"):
                return publisher;
            case ("developer"):
                return developer;
            case ("genre"):
                return genre;
            case ("platforms"):
                return platforms;
            case ("rating"):
                return rating ;
            case ("multiplayer"):
                return multiplayer;
            case ("visuals"):
                return visuals;
            case ("music"):
                return music;
            case ("tone"):
                return tone;
            case ("pace"):
                return pace;
            case ("length"):
                return length;
            case ("violence"):
                return violence;
            case ("protag"):
                return protag;
            case ("camera"):
                return camera;
            case ("setting"):
                return setting;
            case ("dimension"):
                return dimension;
            default:
                return null;
        }
    }
}
