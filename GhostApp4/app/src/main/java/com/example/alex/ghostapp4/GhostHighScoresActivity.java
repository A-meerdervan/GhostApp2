package com.example.alex.ghostapp4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import java.util.List;

/**
 * Created by Alex van der Meer
 * Student number: 10400958
 * on 12-10-2015.
 *
 * This Activity displays the highscores, from highest score to lowest
 * it does this using a custom listview, the small custom listview class is embedded in this
 * file
 */

public class GhostHighScoresActivity extends AppCompatActivity {

    private Players players;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_high_scores);
        // initialize action bar
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        // Get the players object to display
        players = new Players(getApplicationContext());
        populateListView();
        // Display all screen items in the current language
        setViewsToLanguage();
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "dutch");
        // Change the screen items:
        TextView NameTV = (TextView) findViewById(R.id.TextViewName);
        Button NewGame = (Button) findViewById(R.id.NewMatchButtonHighscores);

        if (Language.equals("dutch")) {
            NameTV.setText("Naam");
            NewGame.setText("Niew spel");
        } else {
            NameTV.setText("Name");
            NewGame.setText("New game");
        }
    }

    private void populateListView() {
        // Sort the players on highscores
        players.sortArrayOnHighScore();
        // Fill the listview list
        ArrayAdapter<Player> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.HighScoresList);
        list.setAdapter(adapter);
    }

    // The game activity was not closed, so closing this activity brings the user back to the automaticaly reseted game.
    public void onClickNewMatch(View view){
        this.finish();
    }

    // This custom adapter class is made to fill the listview with player information
    private class MyListAdapter extends ArrayAdapter<Player> {
        private List<Player> SortedArray;
        public MyListAdapter() {
            super(GhostHighScoresActivity.this, R.layout.listvieww, players.sortArrayOnHighScore());
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listvieww, parent, false);
            }

            // Find the player to work with
            Player CurrentPlayer = players.sortArrayOnHighScore().get(position);

            // Fill the view

            // Rank:
            TextView Number = (TextView) itemView.findViewById(R.id.RankNumber);
            Number.setText("" + (position + 1));

            // Name:
            TextView Name = (TextView) itemView.findViewById(R.id.Name);
            Name.setText(CurrentPlayer.Name);

            // Score:
            TextView Score = (TextView) itemView.findViewById(R.id.Score);
            Score.setText("" + CurrentPlayer.Score);

            return itemView;
        }
    }

    // The settings activity can be clicked from the taskbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_high_scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar click on Settings
        int id = item.getItemId();

        if (id == R.id.Settings) {
            // go to the Settings page.
            startActivity(new Intent(getApplicationContext(), GhostSettingsActivity.class));
            // This activity is not closed, the user will return by pressing the back button from the rules activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
