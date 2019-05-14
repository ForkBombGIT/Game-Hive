package forkbomb.scrambledeggs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Random extends Fragment {
    //random num gen
    java.util.Random rnd = new java.util.Random();
    String[] flavor = {
            "Find a game, with no frills!",
            "What's gonna hatch from this one?"
    };

    public Random() {
        // Required empty public constructor
    }

    public static Random newInstance() {
        Random fragment = new Random();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_random, container, false);
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
                break;
            default:
                break;
        }
    }
}
