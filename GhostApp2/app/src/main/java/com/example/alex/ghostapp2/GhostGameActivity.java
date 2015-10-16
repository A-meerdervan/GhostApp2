package com.example.alex.ghostapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GhostGameActivity extends AppCompatActivity {


    private GameEngine gameEngine;
    private Lexicon lexicon;
    private Players players;
    private int CurrentPlayer = 1; // it is initialized to 1 because player1 always starts the game
    private String Language = "uninitialized";
    private boolean GameStillGoing = true;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ghost_game);
        toolbar = (Toolbar)findViewById(R.id.AppBarGame);
        setSupportActionBar(toolbar);
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        Language = prefs.getString("Language", "dutch");
        // Create the dictorary object
        lexicon = new Lexicon(getApplicationContext(), Language);
        // if the there is a players object in the intent, get it
        players = new Players(getApplicationContext());
        //Intent intent = this.getIntent();
        // if (intent.getSerializableExtra("players") == null){
        //    players = new Players(getApplicationContext());
        //}
        // else{
        //    // get the players object from the setup activity
        //    players = (Players)intent.getSerializableExtra("players");
        //}
        // If the game was still going, restore it
        SharedPreferences SaveGame = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        if (SaveGame.getBoolean("GameStillGoing", false)){
            CurrentPlayer = SaveGame.getInt("CurrentPlayer", 1);
            gameEngine = new GameEngine(lexicon, Language, false);
            // fill the empty hashet with that of the previous game.
            HashSet<String> s = new HashSet<String>(SaveGame.getStringSet("CurrentFilteredSet", new HashSet<String>()));
            gameEngine.lexicon.CurrentFilteredSet = s;
            gameEngine.lexicon.LetterIndex = SaveGame.getInt("LetterIndex", 0);
            gameEngine.Prefix = SaveGame.getString("Prefix", "uninitialized");
            gameEngine.FirstGuessNotMadeYet = prefs.getBoolean("FirstGuessWasNotMadeY", true);
            TextView tvEditFragment = (TextView)findViewById(R.id.WordFragmentEdit);
            tvEditFragment.setText(SaveGame.getString("EditFragment", "uninitialized"));
        }
        else{
            gameEngine = new GameEngine(lexicon, Language);
        }
        // Display all screen items in the current language
        setViewsToLanguage();
        // Display who is up
        TextView TVplayer = (TextView)findViewById(R.id.CurrentPlayer);
        if (CurrentPlayer == 1){
            TVplayer.setText(players.CurrentPlayer1.Name);
        }
        else{
            TVplayer.setText(players.CurrentPlayer2.Name);
        }
    }

    private void setViewsToLanguage() {
        // Get the language
        SharedPreferences prefs = getSharedPreferences("SaveGame", Context.MODE_PRIVATE);
        String Language = prefs.getString("Language", "dutch");
        // Change the screen items:
        TextView WordFragment = (TextView) findViewById(R.id.WordFragment);
        TextView PlayerTV = (TextView) findViewById(R.id.CurrentPlayer);
        Button Submit = (Button) findViewById(R.id.SubmitButton);
        Button Restart = (Button) findViewById(R.id.RestartButton);

        if (Language.equals("dutch")) {
            WordFragment.setText("Woordfragment");
            Submit.setText("Voer in");
            Restart.setText("Reset spel");
        } else {
            WordFragment.setText("Word fragment");
            Submit.setText("Submit");
            Restart.setText("Restart");
        }
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
                if(PrefixNotAllLetters(Guess)){
                    Toast.makeText(getApplicationContext(), "Please only use letters and the apostrof sign", Toast.LENGTH_LONG).show();
                }
                // The input is legit so process it
                else {
                    if (gameEngine.gameEndedAfterGuess(Guess)) {
                        userLost();
                    } else { updateScreenAfterGuess();}
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
                if (guessIsNotAletter(Guess)){
                    Toast.makeText(getApplicationContext(), "Only use letters or the apostrof sign", Toast.LENGTH_LONG).show();
                }
                // In this case the input is legit
                else{

                    if (gameEngine.gameEndedAfterGuess(Guess))
                    {
                        userLost();
                    }
                    else{updateScreenAfterGuess();}
                }
            }
        }
    }

    public void updateScreenAfterGuess(){
        SwitchPlayers();
        // Switch the player on the screen
        TextView tvPlayer = (TextView)findViewById(R.id.CurrentPlayer);
        if (CurrentPlayer == 1){
            tvPlayer.setText(players.CurrentPlayer1.Name);
        }
        else{
            tvPlayer.setText(players.CurrentPlayer2.Name);
        }
        // Show the user the new wordfragment
        TextView tvWordFragment = (TextView)findViewById(R.id.WordFragmentEdit);
        tvWordFragment.setText(gameEngine.Prefix);
        // clear the edit tekst content
        EditText ETguess = (EditText)findViewById(R.id.SubmitEditText);
        ETguess.setText("");
    }

    public void SwitchPlayers(){
        if(CurrentPlayer == 1){
            CurrentPlayer = 2;
        }
        else{
            CurrentPlayer = 1;
        }
    }

    public boolean guessIsNotAletter(String Guess){
        boolean GuessIsNotALetter = false;
        String Alfabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'";
        if (!(Alfabet.contains(Guess))){
            GuessIsNotALetter = true;
        }
        return GuessIsNotALetter;
    }

    public boolean PrefixNotAllLetters(String Guess){
        boolean GuessIsNotValid = false;
        String Alfabet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ'";
        for (int i = 0; i < Guess.length(); i++) {
            if (!(Alfabet.contains(String.valueOf(Guess.charAt(i))))) {
                GuessIsNotValid = true;
            }
        }
        // denk later na over deze implementatie
        return GuessIsNotValid;
    }

    private void userLost(){
        // The game is not still going
        GameStillGoing = false;

        // Go to the winner page, and send why the player won and the players object(for use in the higscores activity)
        Intent intent = new Intent(getApplicationContext(),WinnerScreenActivity.class);
        String Winner;
        String ReasonForWinning;
        if (CurrentPlayer == 1){
            Winner = players.CurrentPlayer2.Name;
            ReasonForWinning = gameEngine.ReasonForWinning;
            players.player2Won();
        }
        else{
            Winner = players.CurrentPlayer1.Name;
            ReasonForWinning = gameEngine.ReasonForWinning;
            players.player1Won();
        }

        // fill intent and go to the highscores activity
        intent.putExtra("Winner", Winner);
        intent.putExtra("reasonforwinning", ReasonForWinning);
        players.writeDataToFile(getApplicationContext());
        Log.d("xzzr", "Score" + players.CurrentPlayer2.Score + " " + players.CurrentPlayer1.Score);
        //intent.putExtra("players", players);
        startActivity(intent);
        // The onClickRestartGame function needs a view so a bogus tekstview is created
        TextView onzin = (TextView)findViewById(R.id.Score);
        onClickRestartGame(onzin);
        // This activity is not closed, so it is easy to return.
    }

    public void onClickRestartGame(View view){
        gameEngine.resetGame();
        // Reset EditTekst
        EditText ETguess = (EditText)findViewById(R.id.SubmitEditText);
        ETguess.setText("");
        // Reset WordFragment
        TextView tvWordFragment = (TextView)findViewById(R.id.WordFragmentEdit);
        tvWordFragment.setText("_ _ _");
        // Show player 1
        CurrentPlayer = 1;
        TextView tvPlayer = (TextView)findViewById(R.id.CurrentPlayer);
        tvPlayer.setText(players.CurrentPlayer1.Name);
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
        if (id == R.id.PauzeAction) {
            // go to the Pauze page.
            startActivity(new Intent(getApplicationContext(), GhostPauseActivity.class));
            // This activity is not closed, the user will return by pressing the back button from the rules activity
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        // If the game is on going, save the state to a shared preferences file
        SharedPreferences prefs = this.getSharedPreferences("SaveGame",this.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        if (GameStillGoing){
            // Write to prefs
            editor.putBoolean("GameStillGoing",GameStillGoing);
            editor.putInt("CurrentPlayer", CurrentPlayer);
            editor.putBoolean("FirstGuessWasNotMadeY",gameEngine.FirstGuessNotMadeYet);
            Set<String> CurrentFilteredSet = new HashSet<String>();
            // Get the CurrentFilteredSet and write it to prefs.
            Iterator IteratorOnSet = gameEngine.lexicon.CurrentFilteredSet.iterator();
            while (IteratorOnSet.hasNext()) {
                String Word = IteratorOnSet.next().toString();
                CurrentFilteredSet.add(Word);
            }
            editor.putStringSet("CurrentFilteredSet", CurrentFilteredSet);
            TextView tvFragment = (TextView)findViewById(R.id.WordFragmentEdit);
            editor.putString("EditFragment", tvFragment.getText().toString());
            editor.putInt("LetterIndex", gameEngine.lexicon.LetterIndex);
            editor.putString("Prefix", gameEngine.Prefix);
        }
        else{
            editor.putBoolean("GameStillGoing", GameStillGoing);
        }
        editor.commit();
    }
}
