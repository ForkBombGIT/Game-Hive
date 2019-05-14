package forkbomb.scrambledeggs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;

public class GameOfTheDay extends Fragment {

    public GameOfTheDay() {
        // Required empty public constructor
    }

    public static GameOfTheDay newInstance(ArrayList<Game> games) {
        GameOfTheDay fragment = new GameOfTheDay();
        ArrayList<Game> db = games;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_of_the_day, container, false);
    }
}
