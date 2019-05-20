package forkbomb.scrambledeggs;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GameOfTheDay extends Fragment {
    //games
    ArrayList<Game> database;
    //index of gotd
    int index;
    public GameOfTheDay() {
        // Required empty public constructor
    }

    public static GameOfTheDay newInstance(ArrayList<Game> games, int index) {
        GameOfTheDay fragment = new GameOfTheDay();
        Bundle bundle = new Bundle();
        bundle.putSerializable("games", games);
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        database = (ArrayList<Game>) args.getSerializable("games");
        index = args.getInt("index");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game_of_the_day, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        displayData(view);
    }

    //displays game data
    public void displayData(View v){
        //finds views
        TextView title = v.findViewById(R.id.game_title);
        TextView description = v.findViewById(R.id.game_desc);
        TextView release = v.findViewById(R.id.game_release);
        TextView dev = v.findViewById(R.id.game_dev);
        TextView pub = v.findViewById(R.id.game_pub);
        TextView genre = v.findViewById(R.id.game_genre);
        TextView platforms = v.findViewById(R.id.game_platform);
        TextView genreEntries = v.findViewById(R.id.genre_entries);
        TextView platformsEntries = v.findViewById(R.id.platform_entries);

        //title text
        title.setText(database.get(index).title);
        //description text
        description.setText(database.get(index).description);
        //release text
        release.setText(HtmlCompat.fromHtml("<b>REL:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        release.append(" " + database.get(index).year.replace("|",", "));
        //developer text
        dev.setText(HtmlCompat.fromHtml("<b>DEV:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        dev.append(" " + database.get(index).developer.replace("|",", "));
        //publisher text
        pub.setText(HtmlCompat.fromHtml("<b>PUB:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        pub.append(" " + database.get(index).publisher.replace("|",", "));
        //genre title text
        genre.setText(HtmlCompat.fromHtml("<b>GENRE:</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));
        //platform title text
        platforms.setText(HtmlCompat.fromHtml("<b>PLATFORM(S):</b>", HtmlCompat.FROM_HTML_MODE_LEGACY));

        //genre entry text
        String[] genres = database.get(index).genre.split("\\|");
        String genreText = "";
        for (int i = 0; i < genres.length; i++)
            genreText += genres[i].trim() + ((i == (genres.length - 1)) ? "" : "\n");
        genreEntries.setText(genreText);
        //platform entry text
        String[] platformEntries = database.get(index).platforms.split("\\|");
        String platformText = "";
        for (int i = 0; i < platformEntries.length; i++)
            platformText += platformEntries[i].trim() + ((i == (platformEntries.length - 1)) ? "" : "\n");
        platformsEntries.setText(platformText);
    }
}
