package com.example.alex.ghostapp4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

/**
 * Created by Alex on 12-10-2015.
 *
 * This Activity is used only to set the player names that will play the next game
 * it holds a function to already fill in the previous names, so the user only has
 * to click continue to start with the same players
 *
 * All previously used player names are displayed in a auto-completion manner by a custom adapter class
 *
 */

public class GhostSetupActivity extends AppCompatActivity {

    private Players players;
    private Toolbar toolbar;
    private String Language;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_setup);
        // initialize action bar
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        players = new Players(getApplicationContext());
        startUpAutoCompletion();
        presentPlayerNameSuggestions();
        // Display all screen items in the current language
        setViewsToLanguage();
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        Language = prefs.getString("Language", "dutch");
        // Change the screen items:
        TextView EnterName1 = (TextView) findViewById(R.id.ChangeNameTV);
        TextView EnterName2 = (TextView) findViewById(R.id.ChangeNameTV2);
        Button Continue = (Button) findViewById(R.id.ContinueButton2);

        if (Language.equals("dutch")) {
            String EnterNaam = "Voer uw naam in";
            EnterName1.setText(EnterNaam);
            EnterName2.setText(EnterNaam);
            Continue.setText("Volgende");
        } else {
            String EnterName = "Enter your username";
            EnterName1.setText(EnterName);
            EnterName2.setText(EnterName);
            Continue.setText("Continue");
        }
    }

    private void startUpAutoCompletion() {
        // Make a Custom adapter that keeps track of what is typed, and gives the auto completion options for previously used player names
        // The adapter is linked to both player name input Views in the interface
        ArrayAdapter<Player> adapter1 = new AutoCompleteAdapter(GhostSetupActivity.this, R.layout.suggestion_item, R.id.SuggestionTextView, players.PlayersArray);
        AutoCompleteTextView AutoComplete1 = (AutoCompleteTextView) findViewById(R.id.EditPlayer1);
        AutoComplete1.setAdapter(adapter1);
        ArrayAdapter<Player> adapter2 = new AutoCompleteAdapter(GhostSetupActivity.this, R.layout.suggestion_item, R.id.SuggestionTextView, players.PlayersArray);
        AutoCompleteTextView AutoComplete2 = (AutoCompleteTextView) findViewById(R.id.EditPlayer2);
        AutoComplete2.setAdapter(adapter2);
    }

    private void presentPlayerNameSuggestions() {
        AutoCompleteTextView EditPlayer1 = (AutoCompleteTextView)findViewById(R.id.EditPlayer1);
        EditPlayer1.setText(players.CurrentPlayer1.Name);
        AutoCompleteTextView EditPlayer2 = (AutoCompleteTextView)findViewById(R.id.EditPlayer2);
        EditPlayer2.setText(players.CurrentPlayer2.Name);
    }

    public void onClickContinue(View view){

        // Save the edittext player name data as players in the players class
        AutoCompleteTextView EditPlayer1 = (AutoCompleteTextView)findViewById(R.id.EditPlayer1);
        AutoCompleteTextView EditPlayer2 = (AutoCompleteTextView)findViewById(R.id.EditPlayer2);
        String Name1 = EditPlayer1.getText().toString();
        String Name2 = EditPlayer2.getText().toString();
        // If the names are the same, give an error message
        if (Name1.equals(Name2)){
            if (Language.equals("dutch")){
                Toast.makeText(getApplicationContext(), "Je kan geen potje tegen jezelf spelen", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(getApplicationContext(), "You cant play a game against yourself", Toast.LENGTH_LONG).show();
            }
            return;
        }
        players.setCurrentPlayers(Name1,Name2);
        // go to the game activity
        Intent intent = new Intent(getApplicationContext(), GhostGameActivity.class);
        startActivity(intent);
        this.finish();
    }

    // The settings activity can be clicked from the taskbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_setup, menu);
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
