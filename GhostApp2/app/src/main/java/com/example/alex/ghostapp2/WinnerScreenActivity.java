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
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;



public class WinnerScreenActivity extends AppCompatActivity {

    private String Winner = "uninitialized";
    private String ReasonForWinning = "uninitialized bla bla";
    private Players players;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_screen);
        toolbar = (Toolbar)findViewById(R.id.AppBar);
        setSupportActionBar(toolbar);
        // get content to display from intent
        Intent intent = this.getIntent();
        Winner = intent.getStringExtra("Winner");
        ReasonForWinning = intent.getStringExtra("reasonforwinning");
        //if (intent.getSerializableExtra("players") != null){
        //    players = (Players)intent.getSerializableExtra("players");
        //}
        //else{
        //    Log.d("xzzr", "Het players object werd niet succesvol gestuurd naar de winnerScreen activity");
       // }

        // Display all screen items in the current language
        setViewsToLanguage();
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "dutch");
        // Change the screen items:
        TextView tvWinner = (TextView)findViewById(R.id.Winner);
        TextView tvReason = (TextView)findViewById(R.id.Reason);

        if (Language.equals("dutch")) {
            tvWinner.setText(Winner + " heeft gewonnen!!!");
            tvReason.setText(ReasonForWinning);
        } else {
            tvWinner.setText("The winner is " + Winner + " !!!");
            tvReason.setText(ReasonForWinning);
        }
    }

    public void onClickViewHighScores(View view){
        // Move to the Highscores activity
        Intent intent = new Intent(getApplicationContext(), GhostHighScoresActivity.class);
        //intent.putExtra("players", players);
        startActivity(intent);
        this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_winner_screen, menu);
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
