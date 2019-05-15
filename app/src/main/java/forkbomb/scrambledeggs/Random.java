package forkbomb.scrambledeggs;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Random extends Fragment implements View.OnClickListener {
    //random num gen
    java.util.Random rnd = new java.util.Random();
    //games
    ArrayList<Game> database;
    //flavor text
    String[] flavor = {
            "Find a game, with no frills!",
            "What's gonna hatch from this one?"
    };

    public Random() {
        // Required empty public constructor
    }

    public static Random newInstance(ArrayList<Game> games) {
        Random fragment = new Random();
        Bundle bundle = new Bundle();
        bundle.putSerializable("games", games);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        database = (ArrayList<Game>) args.getSerializable("games");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_random, container, false);
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //sets random flavor text
        TextView flavorText = view.findViewById(R.id.flavor);
        flavorText.setText(flavor[rnd.nextInt(flavor.length)]);
    }

    //handles button press
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button:
                activityStart(GameActivity.class);
                break;
            default:
                break;
        }
    }

    //starts an activity
    public void activityStart(Class t){
        if (database.size() > 0) {
            //creates a new intent
            Intent intent = new Intent(getActivity(), t);
            //creates a bundle to send
            Bundle bundle = new Bundle();
            bundle.putSerializable("gameDatabase", database);
            intent.putExtra("gameDatabase", bundle);
            intent.putExtra("origin", "random");
            //starts the new activity
            startActivity(intent);
        }
    }
}
