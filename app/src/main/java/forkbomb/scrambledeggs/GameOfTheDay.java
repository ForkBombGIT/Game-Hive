package forkbomb.scrambledeggs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameOfTheDay extends Fragment {

    public GameOfTheDay() {
        // Required empty public constructor
    }

    public static GameOfTheDay newInstance(String param1, String param2) {
        GameOfTheDay fragment = new GameOfTheDay();
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
