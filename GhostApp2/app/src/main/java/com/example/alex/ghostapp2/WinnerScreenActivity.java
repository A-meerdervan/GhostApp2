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
import android.widget.TextView;

public class WinnerScreenActivity extends AppCompatActivity {

    private String Winner = "uninitialized";
    private String ReasonForWinning = "uninitialized bla bla";
    private Players players;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_screen);
        Intent intent = this.getIntent();
        Winner = intent.getStringExtra("Winner");
        ReasonForWinning = intent.getStringExtra("reasonforwinning");
        TextView tvWinner = (TextView)findViewById(R.id.Winner);
        TextView tvReason = (TextView)findViewById(R.id.Reason);
        tvWinner.setText("The winner is " + Winner + "!!!");
        tvReason.setText(ReasonForWinning);
        //if (intent.getSerializableExtra("players") != null){
        //    players = (Players)intent.getSerializableExtra("players");
        //}
        //else{
        //    Log.d("xzzr", "Het players object werd niet succesvol gestuurd naar de winnerScreen activity");
       // }

        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "Dutch");
        // Change the screen items:
    }

    public void onClickViewHighScores(View view){
        // Move to teh Highscores activity

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
