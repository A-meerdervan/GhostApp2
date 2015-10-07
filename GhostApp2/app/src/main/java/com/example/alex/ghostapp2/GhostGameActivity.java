package com.example.alex.ghostapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class GhostGameActivity extends AppCompatActivity {

    private GameEngine gameEngine;
    private Lexicon lexicon;
    //private boolean FirstGuessNotMadeYet = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_game);
        String Language = "dutch";
        lexicon = new Lexicon(getApplicationContext(), Language);
        gameEngine = new GameEngine(lexicon,"Player1", "Player2");
    }

    public void OnClickSubmitGuess(View view){
        EditText ETguess = (EditText)findViewById(R.id.SubmitEditText);
        String Guess = ETguess.getText().toString();

        // For the first input, the length must be 3 letters
        if (gameEngine.FirstGuessNotMadeYet){
            //  If the input is not 3 letters, give a toast
            if (Guess.length() != 3){
                Toast.makeText(getApplicationContext(), "Please enter a prefix of 3 letters", Toast.LENGTH_LONG).show();
            }
            // In this case the input is 3 characters
            else{
                // if the input are unused characters, give a toast
                if(PrefixNotAllLetters()){
                    Toast.makeText(getApplicationContext(), "Please only use letters and the apostrof sign", Toast.LENGTH_LONG).show();
                }
                // The input is legit so process it
                else{
                    if(gameEngine.gameEndedAfterGuess(Guess)){
                        userLost();
                    }
                    // clear the edit tekst content
                    ETguess.setText("");
                }
            }
        }
        // This is for when a one letter input is needed
        else {
            // If the input is not 1 letter, give a toast
            if (Guess.length() != 1){
                Toast.makeText(getApplicationContext(), "Please enter only 1 letter", Toast.LENGTH_LONG).show();
            }
            // In this case the input is size 1
            else{
                // if the input is an unused character, give a toast
                if (guessIsNotAletter()){
                    Toast.makeText(getApplicationContext(), "Only use letters or the apostrof sign", Toast.LENGTH_LONG).show();
                }
                // In this case the input is legit
                else{
                    // clear the eddit text content
                    ETguess.setText("");
                    if (gameEngine.gameEndedAfterGuess(Guess))
                    {
                        userLost();
                    }
                }
            }
        }
        updateScreenAfterGuess();
    }

    public void updateScreenAfterGuess(){
        // Show the user the new wordfragment
        TextView tvWordFragment = (TextView)findViewById(R.id.WordFragmentEdit);
        tvWordFragment.setText(gameEngine.Prefix);
        // Switch the player
        TextView tvPlayer = (TextView)findViewById(R.id.CurrentPlayer);
        tvPlayer.setText(gameEngine.CurrentPlayer);
    }

    public boolean guessIsNotAletter(){
        // verzin iets
        return false;
    }

    public boolean PrefixNotAllLetters(){
        // denk later na over deze implementatie
        return false;
    }

    private void userLost(){
        // Make an intent to the highscores activity
        String Message = gameEngine.OtherPlayer + " won the game!, the Reason for winning was: " + gameEngine.ReasonForWinning;
        Toast.makeText(getApplicationContext(), Message, Toast.LENGTH_LONG).show();
    }

    public void onClickRestartGame(View view){
        gameEngine.resetGame();
        // clear all text on screen
        EditText ETguess = (EditText)findViewById(R.id.SubmitEditText);
        ETguess.setText("");
        TextView tvWordFragment = (TextView)findViewById(R.id.WordFragmentEdit);
        tvWordFragment.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ghost_game, menu);
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
