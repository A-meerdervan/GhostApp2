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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GhostHighScoresActivity extends AppCompatActivity {

    private ArrayList<Player> PlayersArray = new ArrayList<Player>();
    private Players players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_high_scores);
        //populateHighscoresList();
        //PlayersArray.add(new Player("joop", 8));
        // Get the players object to display
        players = new Players(getApplicationContext());
        populateListView();
        //Intent intent = this.getIntent();
        //if (intent.getSerializableExtra("players") != null){
        //    players = (Players)intent.getSerializableExtra("players");
        //    players.addPlayer(players.CurrentPlayer1);
        //    players.addPlayer(players.CurrentPlayer2);
        //}
       // else{
        //    Log.d("xzzr", "Het players object werd niet succesvol gestuurd naar de highscores activity");
       // }
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "Dutch");
        // Change the screen items:

    }

    private void populateListView() {
        //players.addPlayer(players.CurrentPlayer1);
        players.PlayersArray.add(players.CurrentPlayer1);
        players.PlayersArray.add(players.CurrentPlayer2);
        Log.d("xzzr", players.PlayersArray.get(0).Name);
        //Log.d("xzzr", players.PlayersArray.get(1).Name);
        // Fill the listview list
        ArrayAdapter<Player> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.HighScoresList);
        list.setAdapter(adapter);
    }

    public void onClickNewMatch(View view){
        this.finish();
    }

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

            // Find the car to work with.
            Player CurrentPlayer = players.PlayersArray.get(position);

            // Fill the view

            // Number:
            TextView Number = (TextView) itemView.findViewById(R.id.RankNumber);
            Number.setText("" + position);

            // Name:
            TextView Name = (TextView) itemView.findViewById(R.id.Name);
            Name.setText(CurrentPlayer.Name);

            // Score:
            TextView Score = (TextView) itemView.findViewById(R.id.Score);
            Score.setText("" + CurrentPlayer.Score);

            return itemView;
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
