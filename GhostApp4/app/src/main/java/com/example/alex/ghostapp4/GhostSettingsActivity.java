package com.example.alex.ghostapp4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

/**
 * Created by Alex on 12-10-2015.
 *
 * This is the settings activity and for easy manouvering, it can be reached by every other
 * activity through the taskbar.
 */

public class GhostSettingsActivity extends AppCompatActivity {

    private Players players;
    private Toolbar toolbar;
    private String Language;
    private Boolean LanguageChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_settings);
        // initialize action bar
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        // Create players so that user history can be removed
        players = new Players(getApplicationContext());
        // Display all screen items in the current language
        setViewsToLanguage();
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        Language = prefs.getString("Language", "dutch");
        // Change the screen items:
        TextView ChangeLanguage = (TextView) findViewById(R.id.LanguageTV2);
        Button Rules = (Button) findViewById(R.id.ViewRulesButton2);
        Button ChangePlayers = (Button) findViewById(R.id.ChangePlayersButton);
        Button NewGame = (Button) findViewById(R.id.StartNewGameButton);
        Button RemoveHistory = (Button) findViewById(R.id.RemoveHistoryButton);

        if (Language.equals("dutch")) {
            ChangeLanguage.setText("Kies uw taal");
            Rules.setText("Spelregels");
            ChangePlayers.setText("Verander van spelers\n (dit sluit het huidig spel)");
            NewGame.setText("Start een nieuw spel");
            RemoveHistory.setText("Verwijder gebruikers geschiedenis");
        } else {
            ChangeLanguage.setText("Choose language");
            Rules.setText("View the rules");
            ChangePlayers.setText("Change Players \n(closes current game)");
            NewGame.setText("Start new game");
            RemoveHistory.setText("Remove users history");
        }
    }

    public void onClickViewRules(View view){
        // go to the rules page.
        startActivity(new Intent(getApplicationContext(), GhostRulesActivity.class));
        // This activity is not closed, the user will return by pressing the back button from the rules activity
    }

    // Set the Language that matches the flag that was clicked
    public void onClickLanguageFlag(View view) {
        SharedPreferences prefs = this.getSharedPreferences("SaveGame", this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (view.getId() == R.id.DutchFlag2) {
            editor.putString("Language", "dutch");
            editor.commit();
            // Show user the language changed
            Toast.makeText(getApplicationContext(), "De taal is nu Nederlands", Toast.LENGTH_LONG).show();
            // Change screen items to current language
            setViewsToLanguage();
        } else {
            editor.putString("Language", "english");
            editor.commit();
            // Show user the language changed
            Toast.makeText(getApplicationContext(), "The language is now english", Toast.LENGTH_LONG).show();
            setViewsToLanguage();
        }
        LanguageChanged = true;
    }

    public void onClickStartNewGame(View view){
        Intent intent = new Intent(getApplicationContext(), GhostGameActivity.class);
        if (LanguageChanged){
            intent.putExtra("LanguageChanged", LanguageChanged);
            intent.putExtra("Language", Language);
        }
        // go to the next activity, the game acitvity
        startActivity(intent);
        this.finish();
    }

    public void onClickChangePlayers(View view){
        Intent intent = new Intent(getApplicationContext(), GhostGameActivity.class);
        if (LanguageChanged){
            intent.putExtra("LanguageChanged", LanguageChanged);
            intent.putExtra("Language", Language);
        }
        // go to the next activity, the setup acitvity
        startActivity(intent);
        this.finish();
    }

    public void onClickRemoveHistory(View view){
        players.removeUserHistory();
        // Show that the action took place
        if (Language.equals("dutch")){
            Toast.makeText(getApplicationContext(), "De gebruikers geschiedenis is verwijderd", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(getApplicationContext(), "The user history was removed", Toast.LENGTH_LONG).show();
        }
    }

    // The settings activity can not be clicked from the taskbar but it is visible
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // This has no items in the taskbar
        return super.onOptionsItemSelected(item);
    }
}
