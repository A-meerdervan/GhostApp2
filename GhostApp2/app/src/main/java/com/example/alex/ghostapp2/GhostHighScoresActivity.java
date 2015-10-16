package com.example.alex.ghostapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;

public class GhostHighScoresActivity extends AppCompatActivity {

    private Players players;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_high_scores);
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        //Intent intent = this.getIntent();
        //if (intent.getSerializableExtra("players") != null){
        //    players = (Players)intent.getSerializableExtra("players");
        //    players.addPlayer(players.CurrentPlayer1);
        //    players.addPlayer(players.CurrentPlayer2);
        //}
       // else{
        //    Log.d("xzzr", "Het players object werd niet succesvol gestuurd naar de highscores activity");
       // }
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
        public MyListAdapter() {
            super(GhostHighScoresActivity.this, R.layout.listvieww, players.PlayersArray);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Make sure we have a view to work with (may have been given null)
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.listvieww, parent, false);
            }

            // Find the player to work with
            Player CurrentPlayer = players.PlayersArray.get(position);

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

    // update the players object
    @Override
    protected void onStop() {
        super.onStop();
        players.writeDataToFile(getApplicationContext());
        // TEST:
        players.fillFromFile();
        for (int i = 0; i < players.PlayersArray.size(); i++){
            Log.d("xzzr", players.PlayersArray.get(i).Name);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_high_scores, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
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
