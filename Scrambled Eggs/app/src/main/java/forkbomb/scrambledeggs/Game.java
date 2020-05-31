package forkbomb.scrambledeggs;

import java.io.Serializable;

public class Game implements Serializable {
    public String title, description, year, publisher, developer, genre, platforms, region, visuals, music,
                  tone, pace, length, violence, protag, camera, setting, dimension, standard, players, goal,
                  weapon, story, decade, accessories, customizing, enemy, difficulty, curve, feel,
                  communities, achievement;

    public Game(){
    }

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
