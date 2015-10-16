package com.example.alex.ghostapp2;

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
import android.support.v7.widget.Toolbar;

public class GhostPauseActivity extends AppCompatActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_pause);
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        // Display all screen items in the current language
        setViewsToLanguage();
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "dutch");
        // Change the screen items:
        Button Resume = (Button) findViewById(R.id.ResumeButton);
        Button Rules = (Button) findViewById(R.id.ViewRulesButton3);
        Button ChangePlayers = (Button) findViewById(R.id.ChangePlayersButton2);
        Button NewGame = (Button) findViewById(R.id.StartNewGameButton2);

        if (Language.equals("dutch")) {
            Resume.setText("Terug naar spel");
            Rules.setText("Spelregels");
            ChangePlayers.setText("Verander van spelers\n (dit sluit het huidig spel)");
            NewGame.setText("Start een nieuw spel");
        } else {
            Rules.setText("View the rules");
            Resume.setText("Resume");
            ChangePlayers.setText("Change Players \n(closes current game)");
            NewGame.setText("Start new game");
        }
    }

    public void onClickResume(View view){
        this.finish();
    }

    public void onClickViewRules(View view){
        // go to the rules page.
        startActivity(new Intent(getApplicationContext(), GhostRulesActivity.class));
        // This activity is not closed, the user will return by pressing the back button from the rules activity
    }

    public void onClickStartNewGame(View view){
        // go to the next activity, the game acitvity
        startActivity(new Intent(getApplicationContext(), GhostGameActivity.class));
        this.finish();
    }

    public void onClickChangePlayers(View view){
        // go to the next activity, The setup activity
        startActivity(new Intent(getApplicationContext(), GhostSetupActivity.class));

        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_pause, menu);
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
