package com.example.alex.ghostapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class GhostSetupActivity extends AppCompatActivity {

    private Players players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_setup);
        players = new Players(getApplicationContext());
        presentPlayerNameSuggestions();
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "Dutch");
        // Change the screen items:

    }

    private void presentPlayerNameSuggestions() {
        EditText EditPlayer1 = (EditText)findViewById(R.id.EditTPlayer1);
        EditPlayer1.setText(players.CurrentPlayer1.Name);
        EditText EditPlayer2 = (EditText)findViewById(R.id.EditTPlayer2);
        EditPlayer2.setText(players.CurrentPlayer2.Name);
    }

    public void onClickContinue(View view){

        // Save the edittext player name data as players in the players class
        EditText EditPlayer1 = (EditText)findViewById(R.id.EditTPlayer1);
        EditText EditPlayer2 = (EditText)findViewById(R.id.EditTPlayer2);
        String Name1 = EditPlayer1.getText().toString();
        String Name2 = EditPlayer2.getText().toString();
        players.setCurrentPlayers(Name1,Name2);
        players.writeDataToFile(getApplicationContext());
        // go to the game activity
        Intent intent = new Intent(getApplicationContext(), GhostGameActivity.class);
        //intent.putExtra("players", players);
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_setup, menu);
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

    @Override
    protected void onStop() {
        super.onStop();
        players.writeDataToFile(getApplicationContext());
    }
}
