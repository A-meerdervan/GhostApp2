package com.example.alex.ghostapp2;

import android.util.Log;

/**
 * Created by Alex on 5-10-2015.
 */
public class GameEngine {

    public GameEngine(Lexicon lexiconn, String player1, String player2){
        CurrentPlayer = player1;
        OtherPlayer = player2;
        lexicon = lexiconn;
    }

    public Lexicon lexicon;
    // Wellicht is de regel hieronder nooit nodig
    public String Prefix = "";
    public String CurrentPlayer = "Uninitialised Player 1";
    public String OtherPlayer = "Unitialised Player 2";
    public boolean FirstGuessNotMadeYet = true;
    public String ReasonForWinning = "Uninitialised";

    public boolean gameEndedAfterGuess(String Guess){
        // The first guess is 3 letters long
        if (FirstGuessNotMadeYet){
            Prefix = Guess;
            lexicon.FilterOnInitialPrefix(Prefix);
            // If The user made a prefix, with wich, no words start, the user loses (A verry short game)
            if (lexicon.count() == 0){
                currentUserLost();
                ReasonForWinning = "The other user made a prefix with which, no words start.";
                return true;
            }
            // the game did not end
            else {
                SwitchPlayers();
                FirstGuessNotMadeYet = false;
                return false;
            }

        }
        // When the guess is after the first 3 letter guess, it is 1 letter.
        else{
            // If the user guessed an existing word, the user loses
            if(lexicon.CurrentFilteredSet.contains(Prefix + Guess)){
                currentUserLost();
                ReasonForWinning = "The other user guessed an existing word";
                return true;
            }
            else{
                Prefix = Prefix + Guess;
                // Dit moet nog mooi, weet nog niet hoe
                lexicon.FilterOnLetter(Guess.charAt(0));
                // The user made a prefix, with wich, no words start.
                if (lexicon.count() == 0){
                    currentUserLost();
                    ReasonForWinning = "The other user made a prefix with which, no words start.";
                    return true;
                }
                // the game did not end
                else {
                    SwitchPlayers();
                    return false;
                }
            }
        }
    }


    public void currentUserLost(){
        Log.d("xzzr", "Je hebt verloren man");
        // Nog geen idee
        // Sowieso de activity laten weten dat ie moet eindigen enzo
    }

    public void resetGame(){
        // what to do?
        // reset lexicon
        FirstGuessNotMadeYet = true;
        Prefix = "";
        lexicon.resetClass();
    }

    public void winner(){
        // verzin nog iets.
        // Je kunt een int returnen met of het player 1 of 2 is ofzo.
    }

    public void SwitchPlayers(){
        String TempPlayer = CurrentPlayer;
        CurrentPlayer = OtherPlayer;
        OtherPlayer = TempPlayer;
    }
}
