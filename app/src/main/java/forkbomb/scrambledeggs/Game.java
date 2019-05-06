package forkbomb.scrambledeggs;
import java.io.Serializable;

public class Game implements Serializable {
    public String title;
    public String description;
    public String year;
    public String publisher;
    public String developer;
    public String genre;
    public String platforms;
    public String region;
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
    public String standard;
    public String players;
    public String goal;
    public String weapon;
    public String period;
    public String story;
    public String decade;
    public String accessories;
    public String customizing;
    public String enemy;
    public String difficulty;
    public String curve;
    public String feel;
    public String communities;
    public String achievement;


    public Game(){ }

    public String get(String val){
        switch(val) {
            case ("title"):
                return title;
            case ("description"):
                return description;
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
            case ("visuals"):
                return visuals;
            case ("region"):
                return region;
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
            case("decade"):
                return decade;
            case("standard"):
                return standard;
            case("players"):
                return players;
            case("goal"):
                return goal;
            case("weapon"):
                return weapon;
            case("period"):
                return period;
            case("story"):
                return story;
            case("accessories"):
                return accessories;
            case("customizing"):
                return customizing;
            case("enemy"):
                return enemy;
            case("difficulty"):
                return difficulty;
            case("curve"):
                return curve;
            case("feel"):
                return feel;
            case("communities"):
                return communities;
            case("achievement"):
                return achievement;
            default:
                return null;
        }
    }
}
