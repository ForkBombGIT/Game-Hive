package forkbomb.gamehive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class Game_Hive extends AppCompatActivity {
    //var to hold the top toolbar
    Toolbar toptoolbar;

    //on create function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game__hive);

        //sets up toolbar
        toptoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toptoolbar);
    }

    //drop down menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_home:
                randomStart(Game_Hive.class);
                return true;
            case R.id.navigation_random:
                randomStart(gamehive_random.class);
                return true;
            case R.id.navigation_quiz:
                randomStart(gamehive_quiz.class);
                return true;
            default:
                return false;
        }
    }

    public void randomStart(Class t){
        Intent intent = new Intent(this,t);
        startActivity(intent);
    }
}
